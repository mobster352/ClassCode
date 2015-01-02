fun fib a = if a = 0 then 1 else a * fib(a-1);

val _ =
  let
    val args = CommandLine.arguments()
    val n = valOf(Int.fromString(hd(args)))
    val x = fib n
  in
    print ("fib result = " ^ Int.toString(x) ^ "\n")
  end;

OS.Process.exit(OS.Process.success);
