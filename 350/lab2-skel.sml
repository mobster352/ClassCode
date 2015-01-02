(***********************************************************)
(*       LISP lexer                                        *)
(*                                                         *)
(*       Your name here                                    *)
(*       Reference Skeleton, CSCI 350                      *)
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


(* implement spaces *)
(*
fun spaces ... = 
;
*)

(* implement lexid *)
(*
fun lexid  ... = 
;
*)


(* implement lexint *)
(*
fun lexint ... = 
;
*)

(* complete implementation of lexer *)
fun  lexer (#"." :: t)  = Dot :: lexer(t)
 |   lexer [] = []
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

val _ = OS.Process.exit(OS.Process.success)
