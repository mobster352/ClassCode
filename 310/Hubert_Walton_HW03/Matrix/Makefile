CFLAGS = -g -Wall -Wstrict-prototypes
PROGS = make-matrix print-matrix mm-serial
LDFLAGS = -lm 
CC = gcc

all: $(PROGS)

matrix.o: matrix.c
	$(CC) $(CFLAGS) -c matrix.c

make-matrix.o: make-matrix.c
	$(CC) $(CFLAGS) -c make-matrix.c

print-matrix.o: print-matrix.c
	$(CC) $(CFLAGS) -c print-matrix.c

mm-serial.o: mm-serial.c
	$(CC) $(CFLAGS) -c mm-serial.c

make-matrix: matrix.o make-matrix.o
	$(CC) $(LDFLAGS) -o make-matrix make-matrix.o matrix.o
print-matrix: matrix.o print-matrix.o 
	$(CC) $(LDFLAGS) -o print-matrix print-matrix.o matrix.o
mm-serial: matrix.o mm-serial.o
	$(CC) $(LDFLAGS) -o mm-serial mm-serial.o matrix.o
clean:
	rm -f $(PROGS) *.o core* test.*
