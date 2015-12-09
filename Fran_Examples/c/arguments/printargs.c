/*
 * Standard "intro" C program
 */
#include <stdio.h>

int
main(int argc, char *argv[])
{
	int i;

	printf("The command '%s' has %d arguments:\n", argv[0], argc);
	for (i = 0; i < argc; i++) {
		printf("  argument %2d : '%s'\n", i, argv[i]);
	}

	return (0);
}

