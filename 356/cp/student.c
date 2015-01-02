#include "student.h"
#include "mystack.h"
#include "mystack.c"
#include <stdio.h>
#include <stdlib.h>

int main(void)
{
    stack s = newstack();
    student* st;
    void* v;
    char str[256];
    int i = 0;
    char buf[2];
    int tem = 0;
    int inc = 0;
    int counter = 0;
    void* ret;
    char* n;
    student x;
    int d = 0;
    int* arr;
    int min = 0;
    int max = 0;
    int sum = 0;
    int avg = 0;

    while(scanf("%1[\n]", buf) == 0)//stops on newline
    {
        tem = scanf("%s %d", str, &i);

        if(tem==2)
        {
            st = (student*)malloc(sizeof(student));
            st->name = str;
            st->score = i;
            v = st;
            push(s, v);
            counter++;
        }
        scanf("%*[^\n]");
        scanf("%*1[\n]");
    }
    for(inc=0; inc<counter; inc++)
    {
        ret = pop(s);   //only returning last item pushed
        st = (student*) ret;
        //n = st->name;
        d = st->score;
        arr[inc] = d;
        //printf("%s %d\n", n, d);
        free(ret);
        ret = NULL;
    }

    for(inc=0; inc<counter; inc++)
    {
        sum += arr[inc];
        if(arr[inc+1]!=arr[counter])
        {
            if(arr[inc]<=arr[inc+1])
            {
                if(inc==0 || arr[inc]<min)
                {
                    min = arr[inc];
                }
            }
            if(arr[inc+1]<=arr[inc])
            {
                if(inc==0 || arr[inc+1]<min)
                {
                    min = arr[inc+1];
                }
            }

            if(arr[inc]>=arr[inc+1])
            {
                if(inc==0 || arr[inc]>max)
                {
                    max = arr[inc];
                }
            }
            if(arr[inc+1]>=arr[inc])
            {
                if(inc==0 || arr[inc+1]>max)
                {
                    max = arr[inc+1];
                }
            }
        }
        else
        {
            if(arr[inc]<min)
            {
                min = arr[inc];
            }
            if(arr[inc]>max)
            {
                max = arr[inc];
            }
        }
    }
    avg = sum / counter;

    printf("min: %d\n", min);
    printf("max: %d\n", max);
    printf("average: %d\n", avg);

    return 0;
}
