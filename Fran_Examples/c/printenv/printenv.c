/*
 * show accessor of command line args and environment variables
 */
#include <stdio.h>
#include <stdlib.h>
#include <string.h>

int
main(int argc, const char *argv[], const char *env[])
{
	int i;
	
	printf("Program name: '%s'\n", argv[0]);

	for (i = 1; argv[i] != NULL; i++)
	{
		if (i == 1)	printf("Program arguments:\n");
		printf("   %3d : '%s'\n", i, argv[i]);
	}
	if (i == 1)	printf("No program arguments.\n");

	printf("\n");


	for (i = 0; env[i] != NULL; i++)
	{
		if (i == 0)	printf("Environment variables:\n");
		printf("   %3d : '%s'\n", i, env[i]);
	}
	if (i == 1)	printf("No environment variables.\n");

	exit (0);
}

