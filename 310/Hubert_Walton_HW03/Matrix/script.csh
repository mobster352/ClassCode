#!/bin/csh
@ count = 3
while ($count != 651)
    ./make-matrix -n $count -l 0 -u 100 -o a.o
    ./make-matrix -n $count -l 0 -u 100 -o b.o
    ./mm-serial a.o b.o c.o
    #echo $count
    @ count++
    end
