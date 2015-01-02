/*
Hubert Walton
CSCI 310
*/
#ifndef MATRIX_H_INCLUDED
#define MATRIX_H_INCLUDED



#endif // MATRIX_H_INCLUDED

#define ddtype double

void alloc2Darray(int m, int n, ddtype ***A);
int check2Darray(ddtype **A);
void free2Dgraph(ddtype **A);
void print2Dgraph(int m, int n, ddtype **A);
void make_graph(int n, int l, int u, ddtype ***A);
void write_graph (char *file_name, int n, ddtype **A);
void read_graph(char *file_name, int *n, ddtype ***A);
