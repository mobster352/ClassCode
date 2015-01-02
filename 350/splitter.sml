fun readList (file,li) =
  let
    val sopt = TextIO.inputLine(file)
    val s = valOf(sopt)
  in
    case sopt of
     NONE => (TextIO.closeIn(file); li)
   | SOME(s) => ((valOf(Int.fromString(s)) :: li); readList(file,li))
      handle Option => li
  end;

fun splitter nil even odd = (even,odd)
  | splitter (x::xs) even odd = if (x mod 2)=0 then splitter xs (x::even) odd 
                                else splitter xs even (x::odd);
fun printList nil = "\n"
  | printList (x::xs) = (print(Int.toString(x)^" "); printList xs);

fun getT1 (t1,t2) = printList t1;
fun getT2 (t1,t2) = printList t2;

print ("input: " ^ printList(readList(TextIO.openIn("input.dat"),[])));
print ("evens: " ^ getT1(splitter(readList(TextIO.openIn("input.dat"),[])) [] []));
print ("odds: " ^ getT2(splitter(readList(TextIO.openIn("input.dat"),[])) [] []));

OS.Process.exit(OS.Process.success);
