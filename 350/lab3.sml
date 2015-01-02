(***********************************************************)
(*       LISP Parser                                       *)
(*       Hubert Walton                                     *)
(*       D. Brian Larkins                                  *)
(*       Lab 3, CSCI 350                                   *)
(*                                                         *)
(***********************************************************)

exception LexerError;
exception ParseOK;
exception ParseError of string;

(***********************************************************)
(* type declarations                                       *)
(***********************************************************)

datatype sign = 
    Plus 
  | Minus
;

datatype atom =
  T
| NIL
| Int of int
| Ident of string
;

datatype token = 
  Lparen
| Rparen
| Dot
| Sign of sign
| Atom of atom
;

datatype sexp = 
    AtomExp of atom
  | Sexp of sexp * sexp;


(***********************************************************)
(* printing functions                                      *)
(***********************************************************)

(* function: print_tokens - prints out a token stream  *)
fun print_tokens [] = print("\n")
  | print_tokens (Lparen :: t) = (print("Lparen "); print_tokens(t))
  | print_tokens (Rparen :: t) = (print("Rparen "); print_tokens(t))
  | print_tokens (Dot :: t) = (print("Dot "); print_tokens(t))
  | print_tokens (Sign(Plus) :: t) = (print("Plus "); print_tokens(t))
  | print_tokens (Sign(Minus) :: t) = (print("Minus "); print_tokens(t))
  | print_tokens (Atom(a) :: t) = 
  (case a of 
   T => (print("Atom(T) "); print_tokens(t))
   | NIL => (print("Atom(NIL) "); print_tokens(t))
   | Int i => (print("Atom(Int(" ^ Int.toString(i) ^ ")) "); print_tokens(t))
   | Ident s => (print("Atom(Ident(" ^ s ^ ")) "); print_tokens(t)))
  ;

(* function: string_of_op -  converts an operator token to a string *)
fun string_of_op Plus = "+"
 |  string_of_op Minus = "-";


(* function: is_list - predicate function returning true if s-expression is a list *)
fun is_list (Sexp (h, AtomExp(NIL))) = true
 |  is_list (Sexp (h, t)) = is_list t
 |  is_list _ = false;


(* function: string_of_atom - converts a primitive atom to a string *)
fun string_of_atom (Atom(a)) = 
	(case a of
		T => "T"
	|	NIL => "NIL"
	|	Int i => Int.toString(i)
	|	Ident s => s)
 | string_of_atom _ = raise ParseError "string_of_atom"
;

(* function: string_of_token - converts a lexer token to a string *)
fun string_of_token (Lparen) = "("
 |  string_of_token (Rparen) = ")"
 |  string_of_token (Dot) = "."
 |  string_of_token (Sign(Plus)) = string_of_op Plus
 |  string_of_token (Sign(Minus)) = string_of_op Minus
 |  string_of_token (Atom(a)) = string_of_atom (Atom(a)) 
;

(* function: print_list - prints an s-expression in list format *)
fun print_list sexp =
	(case sexp of
	AtomExp(exp) => print ""
	| Sexp(h,t) => (print(string_of_token(Lparen));print_sexp h;print_list t;print(string_of_token(Rparen))))

and
(* function: print_sexp - prints an s-expression in either dotted or list format *)
    print_sexp sexp =
	(case sexp of
	AtomExp(exp) => print(string_of_atom(Atom(exp))) 
	| Sexp(h,t) => if is_list (Sexp(h,t)) then print_list (Sexp(h,t))
		else 
			(
			(case h of
			AtomExp(h1) => print(string_of_atom(Atom(h1)))
			| Sexp(hd,tl) => if is_list (Sexp(hd,tl)) then print_list (Sexp(hd,tl)) 
				else		
					(print_sexp hd;print(string_of_token(Dot));print_sexp tl;print(string_of_token(Rparen)))	
			);

			print(string_of_token(Dot));

			(case t of
			AtomExp(t1) => print(string_of_atom(Atom(t1)))
			| Sexp(hh,tt) => if is_list (Sexp(hh,tt)) then print_list (Sexp(hh,tt))
				else
					(print(string_of_token(Lparen));print_sexp hh;print(string_of_token(Dot));print_sexp tt)
			);
			
			print(string_of_token(Rparen))
			)
	)
;


(***********************************************************)
(* lexer implementation                                    *)
(***********************************************************)

fun spaces (#" " :: t)  = spaces t
  | spaces (#"\t" :: t) = spaces t
  | spaces (#"\n" :: t) = spaces t
  | spaces l = l
;

fun char_to_int(c) = 
   let
      val copt = Int.fromString(Char.toString(c))
   in
      (case copt of
        SOME(vv) => vv
      | NONE => raise LexerError)
   end
;


fun lexid (s, []) = (s, [])
  | lexid (s, h::t) =
      if Char.isAlphaNum(h) then
        lexid(s ^ Char.toString(h), t)
      else 
        (s, h::t)
;


fun lexint (v, []) = (v, [])
  | lexint (v, h::t) =
  if Char.isDigit(h) then
     lexint((10*v)+char_to_int(h), t)
  else
     (v, h::t)
;


fun  lexer( #"(" :: t) =   Lparen :: lexer(t) 
  |  lexer( #")" :: t) =  Rparen :: lexer(t) 
  |  lexer( #"." :: t) =  Dot :: lexer(t) 
  |  lexer( #"-" :: t) =  Sign(Minus) :: lexer(t) 
  |  lexer( #"+" :: t) =  Sign(Plus) :: lexer(t) 
  |  lexer( h::t ) = 
        if Char.isAlpha(h) then
          let 
             val (idstr,tt) = lexid(Char.toString(h), t)
          in 
             Atom(Ident(idstr)) :: lexer(tt)
          end
        else if Char.isDigit(h) then
          let
             val (intval, tt) = lexint(char_to_int(h), t)
          in
             Atom(Int(intval)) :: lexer(t)
          end
        else if Char.isSpace(h) then
          lexer(spaces(t))
        else
          raise LexerError
   |   lexer [] = []
;


(***********************************************************)
(* parser implementation                                   *)
(***********************************************************)

(* function: check_sign - both validates and combines sign and integer token pairs *)
fun check_sign (Sign(Minus)::(Atom(Int(i)))::rest) = (AtomExp(Int(~i)),rest)
 |  check_sign (Sign(Plus)::(Atom(Int(i)))::rest) = (AtomExp(Int(i)),rest)
 |  check_sign _ = raise ParseError "+/- sign may only be used with integer literals"
;

(* function: check_atom - converts identifiers to internal primitives, nested defun check *)
fun check_atom (Atom(Ident("T"))::rest) = (AtomExp(T),rest)
 |  check_atom (Atom(Ident("t"))::rest) = (AtomExp(T),rest)
 |  check_atom (Atom(Ident("NIL"))::rest) = (AtomExp(NIL),rest)
 |  check_atom (Atom(Ident("nil"))::rest) = (AtomExp(NIL),rest)
 |  check_atom (Atom(a)::rest) = (AtomExp(a),rest)
 |  check_atom _ = raise ParseError "internal error - check_atom"
;
(* function: parse_sexp - top-level parser: takes stream of tokens, returns sexp-tree *)
(* S ::= E *)
fun parse_sexp [] = raise ParseOK
 |  parse_sexp exp = parse_exp exp

(* E ::= atom | '(' X          *)
and
     parse_exp (Lparen::rest) = parse_x rest
 |   parse_exp (Atom(a)::rest) = check_atom (Atom(a)::rest)
 |   parse_exp (Sign(a)::rest) = check_sign (Sign(a)::rest) 
 |   parse_exp _ = raise ParseError "parse_exp"

(* X ::= E Y | ')'   *)
and
     parse_x (x::xs) = 
	let
		val (xpx,tkx) = parse_exp (x::xs)
		val (xpy,tky) = parse_y tkx
	in
		(Sexp(xpx,xpy),tky)
	end

 |   parse_x rest = (AtomExp(NIL),rest)


(* Y ::= '.' E ')' | R ')'    *)
and
     parse_y (Dot::rest) = 
	let
		val (xp,tk) = parse_exp rest
		val tkl = parse_rparen tk
	in	
		(xp,tkl)
	end
 |   parse_y sexp = 
	let
		val (xp,tk) = parse_r sexp
		val tkl = parse_rparen tk
	in
		(xp,tkl)
	end

(* R ::= E R | empty  *)
and
     parse_r (Atom(a)::rest) =
	let
		val (xp,tk) = parse_exp (Atom(a)::rest)
		val (xpp,tkk) = parse_r tk
	in
		(Sexp(xp,xpp),tkk)
	end 
 |   parse_r (Sign(a)::rest) =
	let
		val (xp,tk) = parse_exp (Sign(a)::rest)
		val (xpp,tkk) = parse_r tk
	in
		(Sexp(xp,xpp),tkk)
	end 
  |  parse_r (Lparen::rest) = 
	let
		val (xp,tk) = parse_exp (Lparen::rest)
		val (xpp,tkk) = parse_r tk
	in
		(Sexp(xp,xpp),tkk)
	end 
 |   parse_r rest = (AtomExp(NIL),rest)

(* convenience production for right parens *)
and
    parse_rparen (Rparen::rest) = rest
 |  parse_rparen _ = raise ParseError "parse_rparen"
;


(*****************************************)
(* helper routines                       *)
(*****************************************)

fun get_sexp [] = (AtomExp(NIL),[])
 |  get_sexp s = parse_sexp s
;
 
fun next_sexp [] = OS.Process.exit(OS.Process.success)
 | next_sexp s =
   let 
      val (e,rest) = get_sexp s
   in
      (print_sexp e;
       print "\n";
       next_sexp rest
       handle ParseError msg => print ("Parse Error: " ^ msg ^ "\n")
            | ParseOK => OS.Process.exit(OS.Process.success))
   end

fun reader(copt: char option, is, l) = 
  case copt of
    NONE    => (TextIO.closeIn is; l)
  | SOME(c) => reader (TextIO.input1 is, is, (l@[c]))
  ;


(*****************************************)
(* main                                  *)
(*****************************************)
val args = CommandLine.arguments();
val ins = TextIO.openIn(hd(args));

let
   val (sexp,ts) = get_sexp(lexer(reader(TextIO.input1 ins, ins, [])));
in
   (print_sexp(sexp);
    print "\n";
    next_sexp ts)
end


