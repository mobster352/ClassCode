(***********************************************************)
(*       LISP lexer                                        *)
(*                                                         *)
(*       C.J. Mack                                         *)
(*       Lab 2, CSCI 350                                   *)
(*                                                         *)
(***********************************************************)

exception LexerError;

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

(*val l = [#"H", #" ", #" "s, #"M"];
val w = [#" ", #" ", #" ", #"M"];*)

fun spaces [] = []
|       spaces (x :: xs) = if (x = #" ")
                        then (spaces xs)
                        else [x]@ xs
;

(*spaces l;*)

(* implement lexid *)
fun lexid [] words rest = (words, rest)
|       lexid (x :: xs) words rest = if Char.isAlphaNum x
                                                  then lexid xs (x :: words) rest
                                                  else (words ,(x :: rest))
;

(*
val p = #"h";
lexid p;*)

(* implement lexint *)
fun lexint x = valOf (Int.fromString(x))
(*
val s = #"2";
lexint (str(s))
;*)

(* complete implementation of lexer *)
fun lexer (#"." :: t) = Dot :: lexer(t)
 |   lexer [] = []
 |   lexer (#"(" :: t) = Lparen :: lexer(t)
 |   lexer (#")" :: t) = Rparen :: lexer(t)                         
 |   lexer (#"-" :: t) = Sign(Minus) :: lexer(t)
 |   lexer (#"+" :: t) = Sign(Plus) :: lexer(t)
 |   lexer (a :: t) =
  (*if (a = #"t") then Atom(T) :: lexer(t)
  else if (a = #"n") then Atom(NIL) :: lexer(t)*)
  (*else if Char.isAlpha a then Atom(Ident(str(a) ^ implode(lexid(t)))) :: lexer(t)*)
  if Char.isAlpha a then
                    let
                        val original = [a] @ t
                        val tuple = lexid original [] []
                        val chars = rev(#1(tuple))
                        val rest = rev(#2(tuple))
                    in  Atom(Ident(implode(chars))) :: lexer(rest)
                    end
  else if Char.isDigit a then Atom(Int(lexint(str(a)))) :: lexer(t)
  else if Char.isSpace a then lexer(spaces(t))
  else raise LexerError
;

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


fun reader(copt: char option, is, l) =
  case copt of
    NONE    => (TextIO.closeIn is; l)
  | SOME(c) => reader (TextIO.input1 is, is, (l@[c]))
  ;

val args = CommandLine.arguments();
val ins = TextIO.openIn(hd(args));

print_tokens(lexer(reader(TextIO.input1 ins, ins, [])));

val _ = OS.Process.exit(OS.Process.success);
                                              
