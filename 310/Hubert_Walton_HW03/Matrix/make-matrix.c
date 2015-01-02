/*
Hubert Walton
CSCI 310
*/
#define ddtype double
#include <stdio.h>
#include "matrix.h"
#include <unistd.h>
#include <stdlib.h>

int main(int argc, char **argv){
    int c;
    int n;
    int l;
    int u;
    char *file_name;
    ddtype **A = NULL;
    int t;

    while((c = getopt(argc, argv, "n:l:u:o:")) != -1)
    {
        switch(c)
        {
            case 'n':
                n = atoi(optarg);
                break;
            case 'l':
                l = atoi(optarg);
                break;
            case 'u':
                u = atoi(optarg);
                break;
            case 'o':
                file_name = optarg;
                break;
            default:
                c = -1;
        }
    }

    t = n;
    alloc2Darray(0, t, &A);
    make_graph(t, l, u, &A);
    write_graph(file_name, t, A);
    free2Dgraph(A);

    return 0;
}
