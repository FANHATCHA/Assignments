/*
 * A very simple C program: parses status from command line, and exits
 * with that status
 */
#include <stdio.h>
#include <stdlib.h>

int
main(int argc, char *argv[])
{
	int value;

	if (argc < 1) {
		fprintf(stderr, "No argument given!\n");
		exit (-1);
	}

	value = atoi(argv[1]);
	
	printf("Exitting with status %d\n", value);
	return(value);
}

