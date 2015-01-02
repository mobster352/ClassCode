/*
Hubert Walton
CSCI 310
*/
#define ddtype double
#include "matrix.h"
#include <stdio.h>
#include <unistd.h>

int main(int argc, char **argv){
    int n;
    ddtype **A;
    char* file_name;
    int index;

    for(index=optind; index<argc; index++)
    {
        file_name = argv[index];
    }

    read_graph(file_name, &n, &A);
    print2Dgraph(0, n, A);
    free2Dgraph(A);
    return 0;
}
