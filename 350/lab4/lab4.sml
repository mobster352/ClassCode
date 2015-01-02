(***********************************************************)
(*       LISP interpreter                                  *)
(*       Hubert Walton                                     *)
(*       D. Brian Larkins                                  *)
(*       Reference Skeleton, CSCI 350                      *)
(*                                                         *)
(***********************************************************)

exception EvalError of string;
exception LexerError;
exception ParseOK;
exception ParseError of string;
exception UnboundVar;
exception ParameterMismatch;

(***********************************************************)
(* type declarations                                       *)
(***********************************************************)

datatype sign = 
	 Plus 
       | Minus;

datatype atom =
	 T
	 | NIL
	 | Int of int
	 | Ident of string;

datatype token = 
	 Lparen
	 | Rparen
	 | Dot
	 | Sign of sign
	 | Atom of atom;

datatype sexp = 
	 AtomExp of atom
	 | Sexp of sexp * sexp;

let 
    (***********************************************************)
    (* globals                                                 *)
    (***********************************************************)
    val lineno = ref 1;
    val dlist = ref (AtomExp(NIL));

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
	   | Ident s => (print("Atom(Ident(" ^ s ^ ")) "); print_tokens(t)));

    (* function: string_of_op -  converts an operator token to a string *)
    fun string_of_op Plus = "+"
     |  string_of_op Minus = "-";


    (* function: is_list - predicate function returning true if s-expression is a list *)
    fun is_list (Sexp(h, AtomExp(NIL))) = true
     |  is_list (Sexp(h, t)) = is_list t
     |  is_list _ = false;


    (* function: string_of_atom - converts a primitive atom to a string *)
    fun string_of_atom (T) = "t"
     |  string_of_atom (NIL) = "nil"
     |  string_of_atom (Int(i)) = Int.toString i
     |  string_of_atom (Ident(i)) = i;

    (* function: string_of_token - converts a lexer token to a string *)
    fun string_of_token (Lparen) = "("
     |  string_of_token (Rparen) = ")"
     |  string_of_token (Dot) = "."
     |  string_of_token (Sign(s)) = string_of_op s
     |  string_of_token (Atom(a)) = string_of_atom a;

    (* function: print_list - prints an s-expression in list format *)
    fun print_list (AtomExp(NIL)) = ()
     |  print_list (AtomExp(a)) = print(string_of_atom a)
     |  print_list (Sexp(h,AtomExp(NIL))) = print_sexp h
     |  print_list (Sexp(h,t)) = 
	(print_sexp h;
	 print " ";
	 print_list t)

    (* function: print_sexp - prints an s-expression in either dotted or list format *)
    and 
    print_sexp s = 
    if (is_list s) then
	(print "(";
	 print_list s;
	 print ")")
    else 
	(case s of
            AtomExp(a) => print (string_of_atom a)
	  | Sexp(h,t) =>
            (print "(";
             print_sexp h;
             print " . ";
             print_sexp t;
             print ")"));



    (***********************************************************)
    (* lexer implementation                                    *)
    (***********************************************************)

    (* function: spaces - eats whitespace between tokens *)
    fun spaces (#" " :: t)  = spaces t
      | spaces (#"\t" :: t) = spaces t
      | spaces (#"\n" :: t) = (lineno := !lineno + 1; spaces t)
      | spaces l = l;

    (* function: char_to_int - converts character to integer with error checking *)
    fun char_to_int(c) = 
	let
	    val copt = Int.fromString(Char.toString(c))
	in
	    (case copt of
		 SOME(vv) => vv
	       | NONE => raise LexerError)
	end;
    

    (* function: lexid - assembles characters into an Ident token *)
    fun lexid (s, []) = (s, [])
      | lexid (s, h::t) =
	if Char.isAlphaNum(h) then
            lexid(s ^ Char.toString(h), t)
	else 
            (s, h::t);

    (* function: lexint - assembles digits into an Int token *)
    fun lexint (v, []) = (v, [])
      | lexint (v, h::t) =
	if Char.isDigit(h) then
	    lexint((10*v)+char_to_int(h), t)
	else
	    (v, h::t);

    (* function: lexer - main tokenizer driver; maps character stream to token stream *)
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
		 Atom(Int(intval)) :: lexer(tt)
             end
	 else if (h = #"\n") then
	     (lineno := !lineno + 1; lexer(spaces(t)))
	 else if Char.isSpace(h) then
             lexer(spaces(t))
	 else
             (print ("ERROR: Illegal character on line " ^ Int.toString(!lineno) ^ ": " ^ Char.toString(h));
	      raise LexerError)
      |   lexer [] = [];


    (***********************************************************)
    (* parser implementation                                   *)
    (***********************************************************)

    (* function: check_sign - both validates and combines sign and integer token pairs *)
    fun check_sign (Sign(Minus)::(Atom(Int(i)))::rest) = (AtomExp(Int(~i)),rest)
     |  check_sign (Sign(Plus)::(Atom(Int(i)))::rest) = (AtomExp(Int(i)),rest)
     |  check_sign _ = raise ParseError "+/- sign may only be used with integer literals";


    (* function: check_atom - converts identifiers to internal primitives, nested defun check *)
    fun check_atom (Atom(Ident("T"))::rest) = (AtomExp(T),rest)
     |  check_atom (Atom(Ident("t"))::rest) = (AtomExp(T),rest)
     |  check_atom (Atom(Ident("NIL"))::rest) = (AtomExp(NIL),rest)
     |  check_atom (Atom(Ident("nil"))::rest) = (AtomExp(NIL),rest)
     |  check_atom (Atom(a)::rest) = (AtomExp(a),rest)
     |  check_atom _ = raise ParseError "internal error - check_atom"

    (* function: parse_sexp - top-level parser: takes stream of tokens, returns sexp-tree *)
    (* S ::= E *)
    fun parse_sexp [] = raise ParseOK
     |  parse_sexp exp = parse_exp exp

    (* E ::= atom | '(' X          *)
    and parse_exp (Lparen::rest) = parse_x rest
     |  parse_exp (Sign(s)::rest) = check_sign (Sign(s)::rest)
     |  parse_exp (Atom(a)::rest) = check_atom (Atom(a)::rest)
     |  parse_exp _ = raise ParseError "parse ended expecting '(' or an atom expression"

    (* X ::= E Y | ')'   *)
    and parse_x (Rparen::rest) = (AtomExp(NIL),rest)
     |  parse_x sexp = 
	let 
	    val (e,rest1) = parse_exp sexp
	    val (y,rest2) = parse_y   rest1
	in
	    (Sexp(e,y), rest2)
	end

    (* Y ::= '.' E ')' | R ')'    *)
    and parse_y (Dot::rest) = 
	let 
	    val (e, rest1) = parse_exp rest
	    val rest2 = parse_rparen rest1
	in
	    (e,rest2)
	end
     |  parse_y sexp =
	let
	    val (r, rest1) = parse_r sexp
	    val rest2 = parse_rparen rest1
	in
	    (r,rest2)
	end

    (* R ::= E R | empty  *)
    and parse_r (Lparen::rest) = 
	let 
	    val (e,rest1) = parse_exp (Lparen::rest)
	    val (r,rest2) = parse_r   rest1
	in
	    (Sexp(e,r), rest2)
	end
     |  parse_r (Sign(s)::rest) = 
	let 
	    val (e,rest1) = parse_exp (Sign(s)::rest)
	    val (r,rest2) = parse_r   rest1
	in
	    (Sexp(e,r), rest2)
	end
     |  parse_r (Atom(a)::rest) = 
	let 
	    val (e,rest1) = parse_exp (Atom(a)::rest)
	    val (r,rest2) = parse_r   rest1
	in
	    (Sexp(e,r), rest2)
	end
     | parse_r rest = (AtomExp(NIL),rest)

    (* convenience production for right parens *)
    and parse_rparen (Rparen::rest) = rest
     |  parse_rparen rest = raise ParseError "parser ended expecting ')'";



    (*****************************************)
    (* interpretation functions              *)
    (*****************************************)

    (* function: bound - checks that referenced variables are bound in a-list *)
    fun bound x [] = NIL
     |  bound x z = if (hd(hd(z)) = x) then T
			else bound x tl(z)
    ;
    (* function: getval - returns the value of a variable from the a-list *)
    fun getval name alist = if (hd(hd(alist)) = name) then hd(tl(hd(alist)))
				else getval name tl(alist)
    ;

    (* function: eval_defun - checks defun usage and adds function def to the global d-list *)
    fun eval_defun s a d = if (hd(hd(s)) = AtomExp(DEFUN)) then raise EvalError("cannot redefine DEFUN")
			else if (hd(hd(s)) = AtomExp(COND)) then raise EvalError("cannot redefine COND")
			else if (hd(hd(s)) = AtomExp(QUOTE)) then raise EvalError("cannot redefine QUOTE")
			else if (check_formal hd(tl(hd(s))) = T) then raise EvalError("cannot use T or NIL as formal parameters")
			else if (check_unique hd(tl(hd(s))) = T) then raise EvalError("formal parameters must be unique")
		(* (function::(parameters::body))::dlist *)			
			else 
				let
					val name = hd(hd(s))
					val paramlist = hd(tl(hd(s)))
					val body = tl(tl(hd(s)))
				in
					((name::(paramlist::body))::d)
				end
    ;
    
    (* checks that formal parameters are unique *)
    fun check_unique [] = NIL
    |   check_unique x = if (tl(x)=nil) then NIL
			else if (hd(x)=hd(tl(x))) then T
			else check_unique tl(x)

    ;

    (* function: check_formal - checks function parameters from matching T or NIL *)
    fun check_formal [] = NIL
    |   check_formal x = if (hd(x) = AtomExp(T)) then T
			else if (hd(x) = AtomExp(NIL)) then T
			else check_formal tl(x)
    ;

    (* function: addpairs - checks function parameters and binds formals to actuals *)
    fun addpairs xlist ylist z = if (tl(xlist) = nil) then hd(xlist)::hd(ylist)::z
				else hd(xlist)::hd(ylist)::addpairs tl(xlist) tl(ylist) z
    ;
      
    (* function: eval - top-level s-expression evaluation loop *)
    fun eval exp a d = 
	(if (exp = AtomExp(x)) then
		(if (x = T) then T
		else if (x = NIL) then NIL
		else if (x = Int(i)) then i
		else if (bound x a) then getval x a
		else raise UnboundVar
	else
		(if (hd(exp) = QUOTE) then hd(tl(exp))
		else if (hd(exp) = COND) then evcon tl(exp) a d
		else if (hd(exp) = DEFUN) then eval_defun tl(exp) a d
		else apply hd(exp) (evlist tl(exp) a d) a d))
		
	

    (* function: evcon - evaluates a COND statement *)
    and evcon x a d = if (x = nil) then raise EvalError("conditional without match!")
			else if (eval hd(hd(x)) a d = T) then (eval tl(hd(x)) a d)
			else (evcon tl(x) a d)

    (* function: evlist - evaluates a list of expressions and returns a list of results *)	
    and evlist x a d = if (x = nil) then nil
			else (eval hd(x) a d :: evlist tl(x) a d)

    (* function: apply - performs function application, handles built-ins *)
    and apply f x a d = 
	(if f = AtomExp(Ident(y)) then
		(if y = CAR then hd(hd(x))
		else if y = CDR then tl(hd(x))
		else if y = CONS then hd(x)::hd(tl(x))
		else if y = ATOM then (if (hd(x) = AtomExp(atom)) then AtomExp(T)
				else AtomExp(NIL))
		else if y = EQ then (if (hd(x) = hd(tl(x))) then AtomExp(T)
				else AtomExp(NIL))
		else if y = INT then (if (hd(x) = AtomExp(Int(xx))) then AtomExp(T)
				else AtomExp(NIL))
		else if y = NULL then (if (hd(x) = AtomExp(NIL)) then AtomExp(T)
				else AtomExp(NIL))
		else if y = PLUS then hd(x) + hd(tl(x))
		else if y = MINUS then hd(x) - hd(tl(x))
		else if y = TIMES then hd(x) * hd(tl(x))
		else if y = QUOTIENT then hd(x) / hd(tl(x))
		else if y = REMAINDER then hd(x) % hd(tl(x))
		else if y = LESS then (if (hd(x) < hd(tl(x))) then AtomExp(T)
				else AtomExp(NIL))
		else if y = GREATER then (if (hd(x) > hd(tl(x))) then AtomExp(T)
				else AtomExp(NIL))
		else 
					(*check to see if y is in dlist*)
			(let 
				fun in_dlist y [] = raise EvalError("undefined function"))
				| in_dlist y d = if (y = hd(hd(d))) then (eval hd(tl(tl(d))) (addpairs hd(getval y d) x a) d)
						else (in_dlist y tl(d))

			in
				in_dlist y d
			end)
		)
	else (eval tl(getval f d) (addpairs hd(getval f d) x a) d
	)
    ;


    (*****************************************)
    (* helper routines                       *)
    (*****************************************)

    fun get_sexp [] = (AtomExp(NIL),[])
     |  get_sexp s = parse_sexp s;
    
    fun next_sexp [] = OS.Process.exit(OS.Process.success)
      | next_sexp s =
	let 
	    val (e,rest) = get_sexp s
	    val e' = eval e (AtomExp(NIL)) dlist
	in
	    (print_sexp e';
	     print "\n";
	     next_sexp rest
	     handle ParseError msg => print ("Parse Error: " ^ msg ^ "\n")
		  | EvalError msg =>  print ("Evaluation Error: " ^ msg ^ "\n")
		  | ParseOK => OS.Process.exit(OS.Process.success))
	end

    fun reader(copt: char option, is, l) = 
	case copt of
	    NONE    => (TextIO.closeIn is; l)
	  | SOME(c) => reader (TextIO.input1 is, is, (l@[c]));


    (*****************************************)
    val args = CommandLine.arguments()
    val ins = TextIO.openIn(hd(args))
    val (sexp,ts) = get_sexp(lexer(reader(TextIO.input1 ins, ins, [])))
    val se' = (eval sexp (AtomExp(NIL)) dlist)


in
    (*****************************************)
    (* main                                  *)
    (*****************************************)

    print_sexp(se');
    print "\n";
    next_sexp ts
end
handle ParseError msg =>  print ("Parse Error: " ^ msg ^ "\n")
     | EvalError msg =>  print ("Evaluation Error: " ^ msg ^ "\n") 
     | ParseOk =>  OS.Process.exit(OS.Process.success);
