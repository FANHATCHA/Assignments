/*
 * some of the code for our library
 *
 * $Id: tool-banana.c 411 2012-02-13 18:03:35Z andrew $
 */

#include <stdio.h>
#include <stdlib.h>


float banana(float bval, char *aString)
{
	printf(" . Tool library: in banana(%f, %s)\n", bval, aString);
	return bval / 2.0;
}



