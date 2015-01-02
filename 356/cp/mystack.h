#pragma once

struct stackelementS {
    void* contents;                 // the stack item
    struct stackelementS* next;     // pointer to next item in stack

};
typedef struct stackelementS stackelement;

struct stackS {
    stackelement* top;             // the top of the stack
};
typedef struct stackS* stack;      // a stack is a pointer...

/*
 * creates a stack
 * returns: a pointer to a stack
 */
stack newstack ();

/*
 * checks the status of a stack
 * stack s: a stack to check for emptiness; must not NULL
 * returns: value is >0 iff stack has no elements
 */
int isempty (const stack s);

/*
 * pushes item onto stack
 * stack s:     a stack to check for emptiness; must not NULL
 * void* item:  a pointer to an item to be pushed
 * returns:     item is head of stack
 */
void push (stack s, void* item);

/*
 * pops head from stack
 * stack s: a stack to check for emptiness; must not NULL
 * returns: item returned was head of stack
 *          and next element is new head of stack
 */
void* pop (stack s);
