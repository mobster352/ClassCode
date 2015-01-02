/*
Hubert Walton
CSCI 310
*/
#define ddtype double
#include "matrix.h"
#include <stdio.h>
#include <stdio.h>
#include <time.h>
#include <stdlib.h>

void write_graph(char *file_name, int n, ddtype **A) {
   FILE* out = fopen(file_name, "w");
   if(check2Darray(A) == 1) {
      printf("in write_graph, 2D array appears to have a problem\n");
      exit(0);
   }
   printf("\nwriting graph to file %s\n", file_name);
   if(!(out = fopen(file_name, "w"))) {
      printf("--> Cannot open file %s; ", file_name);
      fflush(NULL);
      perror("");
      exit(0);
   }
    fwrite(&n, sizeof(int), 1, out); // write  n to file
    fwrite(A[0], sizeof(ddtype), n*n, out); // write the rest of the data
    fclose(out);
}

void alloc2Darray(int m, int n, ddtype ***A){
    ddtype* temp;
    int i;

    *A = (ddtype**)malloc(n * sizeof(ddtype*));
    temp = (ddtype*)malloc(n*n * sizeof(ddtype));
    for (i = 0; i < n; i++) {
        (*A)[i] = temp;
    }
}

void make_graph(int n, int l, int u, ddtype ***A){
    int v;
    ddtype num;
    srand(time(NULL));

    for(v=0; v<n*n; v++)

        {
            ddtype base_random = rand();
            if (RAND_MAX == base_random) return make_graph(n, l, u, A);
            int range       = u - l,
                remainder   = RAND_MAX % range,
                bucket      = RAND_MAX / range;
           if (base_random < RAND_MAX - remainder)
            {
                num = l + base_random/bucket;
            }
            else
            {
                return make_graph(n, l, u, A);
            }
            (**A)[v] = num;
        }
}

void read_graph(char *file_name, int *n, ddtype ***A)
{
    FILE* in;
    printf("reading graph from file %s\n", file_name);
    int j;

    if(!(in = fopen(file_name, "r")))
    {
      printf("--> Cannot open file %s; ", file_name);
      fflush(NULL);
      perror("");
      exit(0);
    }
    fread(n, sizeof(int), 1, in);
    j = *n;
    alloc2Darray(0, j, A);
    fread((*A)[0], sizeof(ddtype), j * j, in);
    fclose(in);

}

void print2Dgraph(int m, int n, ddtype **A)
{
    int k;
    int q = n-1;
    printf("\nPrinting Graph\n");

    for(k=0; k<n*n; k++)

        {
            printf("%.2f    ", (*A)[k]);
            if(k==q)
            {
                printf("\n");
                q += n;
            }
        }
}

int check2Darray(ddtype **A)
{
    if(A == NULL)
    {
        return 1;
    }
    else
    {
        return 0;
    }
}

void free2Dgraph(ddtype **A)
{
    free(A);
}
