/*
 * some of the code for our library
 *
 * $Id: tool-apple.c 411 2012-02-13 18:03:35Z andrew $
 */

#include <stdio.h>
#include <stdlib.h>


/**
 * The declaration
 *	__attribute__((constructor))
 * is a GCC extension to identify the shared library objects
 * that perform library setup and cleanup
 */

__attribute__((constructor)) int
init()
{
	printf(" . Tool library: in initialization\n");
	return 1;
}

__attribute__((destructor)) int
fini()
{
	printf(" . Tool library: in finalization\n");
	return 1;
}



int apple()
{
	printf(" . Tool library: in apple()\n");
	return 1;
}

int pear(int a, int b, int c)
{
	int result;

	result = (a + b) - c;
	printf(" . Tool library: in pear(%d, %d, %d) -- returning %d\n",
			a, b, c, result);
	return result;
}

char *getAName()
{
	return "Hello from the Tools Library";
}


