/*
Hubert Walton
CSCI 356
Lab 2: Pipes
*/

#include <stdio.h>
#include <sys/types.h>
#include <stdlib.h>
#include <stddef.h>
#include <errno.h>
#include <signal.h>
#include <string.h>
#include <sys/time.h>

void ParentProcess(char* argv[],int* child_read1,int* child_read2,int* child_write1,int* child_write2);
void ChildProcess(int* child_read,int* child_write);                /* child process prototype  */
void setup(char* argv[]);
void nextrd(int rd, char* argv[]);
int randint(int n); //stole this online

int randint(int n) {
  if ((n - 1) == RAND_MAX) {
    return rand();
  } else {
    // Chop off all of the values that would cause skew...
    long end = RAND_MAX / n; // truncate skew

    end *= n;

    // ... and ignore results from rand() that fall above that limit.
    // (Worst case the loop condition should succeed 50% of the time,
    // so we can expect to bail out of this loop pretty quickly.)
    int r;
    while ((r = rand()) >= end);

    return r % n;
  }
}

void nextrd(int rd, char* argv[])
{
    int x = atoi(argv[1]);
    if(rd<x)
    {
        printf("Round %d:\n", rd+1);
    }
}

void ChildProcess(int* child_read,int* child_write)
{
    int t = 0;
    char* buffer = malloc(sizeof(char*));
    int p = 10;
    int rand = 0;

    close(child_read[1]);
    close(child_write[0]);

    struct timeval t1;
    gettimeofday(&t1,NULL);
    srand(t1.tv_usec * t1.tv_sec * getpid());
    //write ready to parent
    p = getpid();
    write(child_write[1], &p, sizeof(p));

    while(p!=-1)
    {
        if(p==0)
        {

            rand = randint(3);
            rand++;
            p = rand;
            write(child_write[1],&p,sizeof(p));
        }
        read(child_read[0], &p, sizeof(p));
    }

    exit(0);
}

void ParentProcess(char* argv[],int* child_read1,int* child_read2,int* child_write1,int* child_write2)
{
    char* buffer = malloc(sizeof(char*));
    int p = 0;
    int rd = 0;
    char* a;
    int x;
    int p2;
    int score1 = 0;
    int score2 = 0;
    int buf2;
    int buf;
    close(child_read1[0]);
    close(child_read2[0]);
    close(child_write1[1]);
    close(child_write2[1]);

    //read pid 1

    read(child_write1[0],&buf,sizeof(buf));
    printf("Child 1 PID: %d\n",buf);

    //read pid 2
    read(child_write2[0],&buf,sizeof(buf));
    printf("Child 2 PID: %d\n", buf);

    setup(argv);
    a = argv[1];
    x = atoi(a);
    while(rd < x)
    {
        nextrd(rd,argv);
        buf = 0;
        write(child_read1[1],&buf,sizeof(buf));  //tell child 1 to go
        read(child_write1[0],&buf,sizeof(buf));  //get result from child 1

        if(buf==1)
        {
            printf("Child 1 throws Scissors!\n");
        }
        else if(buf==2)
        {
            printf("Child 1 throws Rock!\n");
        }
        else if(buf==3)
        {
            printf("Child 1 throws Paper!\n");
        }

        buf2 = 0;
        write(child_read2[1],&buf2,sizeof(buf2));  //tell child 2 to go
        read(child_write2[0],&buf2,sizeof(buf2)); //get result from child 2

        if(buf2==1)
        {
            printf("Child 2 throws Scissors!\n");
        }
        else if(buf2==2)
        {
            printf("Child 2 throws Rock!\n");
        }
        else if(buf2==3)
        {
            printf("Child 2 throws Paper!\n");
        }
        if(buf==buf2)
        {
            printf("Draw!\n");
        }
        else if(buf==1 && buf2==3 || buf==2 && buf2==1 || buf==3 && buf2==2)
        {
            printf("Child 1 Wins!\n");
            score1++;
        }
        else if(buf2==1 && buf==3 || buf2==2 && buf==1 || buf2==3 && buf==2)
        {
            printf("Child 2 Wins!\n");
            score2++;
        }
        printf("------------------------\n");
        rd++;
    }

    printf("------------------------\n");
    printf("Results:\nChild 1: %d\nChild 2: %d\n", score1, score2);

    if(score1==score2)
    {
        printf("Draw!\n");
    }
    else if(score1>score2)
    {
        printf("Child 1 Wins!\n");
    }
    else if(score2>score1)
    {
        printf("Child 2 Wins\n");
    }

    buf = -1;
    write(child_read1[1],&buf,sizeof(buf));

    write(child_read2[1],&buf,sizeof(buf));
}

void setup(char* argv[])
{
    char* a = argv[1];
    int r = atoi(a);    //converts from char* to int
    printf("Beginning %d Rounds...\n", r);
    printf("Fight!\n");
    printf("------------------------\n");
}

int  main(int argc, char* argv[])
{
    //[0] = read   [1] = write
    int child_read1[2];
    int child_write1[2];
    int child_read2[2];
    int child_write2[2];
    int pid = 0;
    int pid2 = 0;

    if(pipe(child_read1)==-1)
    {
        perror("pipe");
        exit(-1);
    }

    if(pipe(child_write1)==-1)
    {
        perror("pipe");
        exit(-1);
    }

    if(pipe(child_read2)==-1)
    {
        perror("pipe");
        exit(-1);
    }

    if(pipe(child_write2)==-1)
    {
        perror("pipe");
        exit(-1);
    }

    pid = fork();
    if(pid==0)//child 1
    {
        ChildProcess(child_read1,child_write1);
    }
    else if(pid<0)
    {
        perror("Fork");
        exit(-1);
    }
    pid2 = fork();
    if(pid2==0)//child 2
    {
        ChildProcess(child_read2,child_write2);
    }
    else if(pid<0)
    {
        perror("Fork");
        exit(-1);
    }

    ParentProcess(argv,child_read1,child_read2,child_write1,child_write2);
    exit(0);

}
