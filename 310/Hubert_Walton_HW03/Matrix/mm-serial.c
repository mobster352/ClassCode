/*
Hubert Walton
CSCI 310
*/
#define ddtype double
#include "matrix.h"
#include <stdio.h>
#include <unistd.h>
#include <time.h>

int main(int argc, char **argv){
    int dex;
    char* input1;
    char* input2;
    char* output;
    int n1; //for input1
    int n2; //for input2
    ddtype** A; //array input1
    ddtype** B; //array input2
    ddtype** C; //array output
    int v;
    int x;
    ddtype y;
    int z;
    int zz;
    ddtype sum;
    int index;
    int xx;
    int dif;
    clock_t start, stop;
    double time;

    for(dex=optind; dex<argc; dex++)
    {
        if(dex==1)
        {
            input1 = argv[dex];
        }
        if(dex==2)
        {
            input2 = argv[dex];
        }
        if(dex==3)
        {
            output = argv[dex];
        }
    }

    //read in 2 input files
    read_graph(input1, &n1, &A);
    read_graph(input2, &n2, &B);
    alloc2Darray(0, n1, &C);

    //compute product of matrices
    if(n1!=n2)
    {
        printf("\nCannot multiply matrices of different sizes\n");
        return 0;
    }
    else
    {
        y = 0.0;
        sum = 0.0;
        zz = 0;
        index = 0;
        xx = 0;

        //time started
        start = clock();

        for(v=0; v<n1; v++)
        {
            for(z=0; z<n1; z++)
            {
                x = xx;
                dif = z;
                zz = 0;
                while(zz<n1*n1)
                {
                    y = (*A)[x] * (*B)[dif];
                    sum += y;
                    x++;
                    dif += n1;
                    zz += n1;
                }
                (*C)[index] = sum;
                index++;
            }
            sum = 0;
            xx = x;
        }

        //time ended
        stop = clock();
        time = ((double) (stop - start)) / CLOCKS_PER_SEC;
        printf("%f\n", time);
    }


    //write result in output file
    write_graph(output, n1, C);
    free2Dgraph(A);
    free2Dgraph(B);
    free2Dgraph(C);

    return 0;
}
