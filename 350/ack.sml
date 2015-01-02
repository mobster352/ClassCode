fun ack (0,n) = n + 1
  | ack (m,0) = ack((m-1), 1)
  | ack (m,n) = ack((m-1), ack(m, (n-1)));

val args = CommandLine.arguments();
val m = valOf(Int.fromString(hd(args)));
val n = valOf(Int.fromString(hd(tl(args))));
ack(m,n);

OS.Process.exit(OS.Process.success);
