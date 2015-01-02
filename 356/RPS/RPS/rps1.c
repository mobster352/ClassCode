/*
Hubert Walton
CSCI 356
Lab 2: Sockets
*/

#include <stdio.h>
#include <sys/types.h>
#include <stdlib.h>
#include <sys/socket.h>
#include <sys/un.h>
#include <stddef.h>
#include <errno.h>
#include <signal.h>
#include <string.h>
#include <sys/time.h>

#define  SERVER  "/tmp/thebestsocket"

void ParentProcess(char* argv[],int sock);
void ChildProcess(struct sockaddr_un name);                /* child process prototype  */
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

void ChildProcess(struct sockaddr_un name)
{
    int s1 = socket(PF_UNIX, SOCK_STREAM, 0);
    int t = 0;
    int p = 0;
    int rand = 0;
    char* buffer = malloc(sizeof(char*));
    struct sockaddr* temp = &name;
    t = connect(s1,temp, sizeof(name));
    if(t!=0)
    {
        perror("Connection unsuccessful");
        exit(-1);
    }
    //write ready to parent
    p = getpid();
    snprintf(buffer,sizeof(buffer),"%d",p);
    write(s1, buffer, sizeof(buffer));
    struct timeval t1;
    gettimeofday(&t1,NULL);
    srand(t1.tv_usec * t1.tv_sec * getpid());
    while(p!=-1)
    {
        if(p==0)
        {
            rand = randint(3);
            rand++;
            snprintf(buffer,sizeof(buffer),"%d",rand);
            write(s1,buffer,sizeof(buffer));
        }
        read(s1, buffer, sizeof(buffer));
        p = atoi(buffer);
    }

    close(s1);
    exit(0);
}

void ParentProcess(char* argv[],int sock)
{
    char* buffer = malloc(sizeof(char*));
    int p = 0;
    int rd = 0;
    int c1;  //child 1 fd
    int c2;  //child 2 fd
    char* a;
    int x;
    int p2;
    int score1 = 0;
    int score2 = 0;

    if((c1 = accept(sock,NULL,NULL))==-1)
    {
        perror("accept1 error");
        exit(-1);
    }

    if((c2 = accept(sock,NULL,NULL))==-1)
    {
        perror("accept2 error");
        exit(-1);
    }

    //read pid 1
    read(c1,buffer,sizeof(buffer));
    p = atoi(buffer);
    printf("Child 1 PID: %d\n", p);

    //read pid 2
    read(c2,buffer,sizeof(buffer));
    p = atoi(buffer);
    printf("Child 2 PID: %d\n", p);

    setup(argv);
    a = argv[1];
    x = atoi(a);
    while(rd < x)
    {
        nextrd(rd,argv);
        p = 0;
        snprintf(buffer,sizeof(buffer),"%d",0);
        write(c1,buffer,sizeof(buffer));  //tell child 1 to go
        read(c1,buffer,sizeof(buffer));  //get result from child 1
        p = atoi(buffer);
        if(p==1)
        {
            printf("Child 1 throws Scissors!\n");
        }
        else if(p==2)
        {
            printf("Child 1 throws Rock!\n");
        }
        else if(p==3)
        {
            printf("Child 1 throws Paper!\n");
        }

        snprintf(buffer,sizeof(buffer),"%d",0);
        write(c2,buffer,sizeof(buffer));  //tell child 2 to go
        read(c2,buffer,sizeof(buffer)); //get result from child 2
        p2 = atoi(buffer);
        if(p2==1)
        {
            printf("Child 2 throws Scissors!\n");
        }
        else if(p2==2)
        {
            printf("Child 2 throws Rock!\n");
        }
        else if(p2==3)
        {
            printf("Child 2 throws Paper!\n");
        }
        if(p==p2)
        {
            printf("Draw!\n");
        }
        else if(p==1 && p2==3 || p==2 && p2==1 || p==3 && p2==2)
        {
            printf("Child 1 Wins!\n");
            score1++;
        }
        else if(p2==1 && p==3 || p2==2 && p==1 || p2==3 && p==2)
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

    snprintf(buffer,sizeof(buffer),"%d",-1);
    write(c1,buffer,sizeof(buffer));

    snprintf(buffer,sizeof(buffer),"%d",-1);
    write(c2,buffer,sizeof(buffer));

    unlink(SERVER);
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
  struct sockaddr_un name;
  int sock;
  int pid;
  int pid2;

  memset(&name, 0, sizeof(name));

  /* Create the socket.   */

      sock = socket (PF_UNIX, SOCK_STREAM, 0);
      if (sock < 0)
        {
          perror ("socket");
          exit (EXIT_FAILURE);
        }

      /* Bind a name to the socket.   */

      name.sun_family = AF_UNIX;
      strcpy (name.sun_path, SERVER);

      unlink(SERVER); //unlink path

      if (bind (sock, (struct sockaddr *) &name, sizeof(name)) < 0)
        {
          perror ("bind");
          exit (EXIT_FAILURE);
        }

      listen(sock,5);

    pid = fork();
    if(pid==0)//child 1
    {
        ChildProcess(name);
    }
    else if(pid<0)
    {
        perror("Fork");
        exit(-1);
    }
    pid2 = fork();
    if(pid2==0)//child 2
    {
        ChildProcess(name);
    }
    else if(pid<0)
    {
        perror("Fork");
        exit(-1);
    }

    ParentProcess(argv,sock);

    //close fds
    close(sock);
    exit(0);

}
