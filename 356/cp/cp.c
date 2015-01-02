/*
Hubert Walton
CSCI 356
Lab 1
*/
#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>

int main(int argc, char *argv[])
{
    FILE *file;
    FILE *file2;
    const char* f1exists = argv[1];
    const char* f2exists = argv[2];

    if(access(f2exists, F_OK) != -1)//if second file exists, delete it
    {
        remove(f2exists);
    }

    if(access(f1exists, F_OK) == -1)//exit if source does not exist : if error, report to user and exit program
    {
        printf("Source file does not exist\n");
        exit(EXIT_FAILURE);
    }

    file = fopen(argv[1], "r");
    file2 = fopen(argv[2], "w");

    int x;
    while((x = fgetc(file)) != EOF)
    {
        //printf("%c", x);
        fputc(x, file2);    //copies one character at a time from the first file to the second file
    }
    fclose(file);
    fclose(file2);
    return 0;
}
