(***********************************************************)
(*       LISP lexer                                        *)
(*                                                         *)
(*       Hubert Walton                                     *)
(*       Lab 2:  CSCI 350                                  *)
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
fun spaces [] = []
 |  spaces (x::xs) = (if Char.isSpace(x) then spaces(xs) else ([x]@xs)) 
;

(* implement lexid *)	
fun lexid ([],l) = (l,[])
 |  lexid ((x::xs),l) = 
(
 if Char.isAlphaNum(x) then (lexid (xs,(str(x)^l)) ) else ((l,[x]@xs))
)
;

(* implement lexint *)
fun lexint ([],l) = (l,[])
 |  lexint ((x::xs),l) = 
(
 if Char.isDigit (x) then (lexint (xs,(str(x)^l)) ) else ((l,[x]@xs))
)
;

(* thanks google *)
fun reverse(m,z) = if null(m) then z else reverse(tl(m),hd(m)::z)
;

(* complete implementation of lexer *)
fun  lexer [] = []
 |   lexer (#"." :: t)  = Dot :: lexer(t)
 |   lexer (#"(" :: t)  = Lparen :: lexer(t)
 |   lexer (#")" :: t)  = Rparen :: lexer(t)
 |   lexer (#"+" :: t)  = Sign(Plus) :: lexer(t)
 |   lexer (#"-" :: t)  = Sign(Minus) :: lexer(t)
 |   lexer (x :: t) = 
(
let
 val (m,a) = lexid([x]@t,"")
 val m_r = implode(reverse(explode (m),[]))
 val (mm,aa) = lexint([x]@t,"")
 val mm_r = implode(reverse(explode(mm),[]))
 val iopt = Int.fromString(mm_r)
in
      if Char.isSpace x then lexer(spaces([x]@t))
 else if m_r = "NIL" then (Atom(NIL) :: lexer(a))
 else if m_r = "T" then (Atom(T) :: lexer(a))
 else if Char.isDigit x then (Atom(Int (valOf(iopt))) :: lexer(aa))
 else if Char.isAlpha x then (Atom(Ident (m_r)) :: lexer(a))
 else raise LexerError
end
) 
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
