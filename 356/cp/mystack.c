#include "mystack.h"
#include <stdio.h>
#include <stdlib.h>
#include <assert.h>

stack newstack()
{
    stack s = malloc(sizeof(struct stackS));
    s->top = NULL;

    return s;
}

void push (stack s, void* item)
{
    assert(s!=NULL);

    stackelement* n = malloc(sizeof(stackelement));
    stackelement* se = s->top;

    n->contents = item;

    if(isempty(s)==0)//something is there
    {
        n->next = se;
    }
    else
    {
        n->next = NULL;
    }

    s->top = n;

}

void* pop (stack s)
{
    assert(s!=NULL);
    assert(!isempty(s));

    stackelement* se = s->top;

    void* cont = se->contents;
    s->top = se->next;
    free(se);
    se = NULL;

    return cont;
}

int isempty (stack s)
{
    assert(s!=NULL);
    return (s->top==NULL);
}
