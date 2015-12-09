#include <stdio.h>
#include <fcntl.h>
#include <stdlib.h>
#include <string.h>
#include <time.h>
#include <assert.h>
#include <sys/types.h>
#include <sys/uio.h>
#include <unistd.h>

#include "bitstring.h"

int
storeBitstring(filestem, n)
char *filestem;
int n;
{
	char filename[FILENAME_MAX];
	BITSTRING bitflags;
	int *intflags;
	int i, v, fd;

	/** ensure we won't have buffer overflow */
	assert((strlen(filestem) + strlen("int.txt") + 1) < FILENAME_MAX);


	intflags = (int *) malloc(sizeof(int) * n);
	bzero(intflags, sizeof(int) * n);


	bitflags = ALLOC_BITSTRING(n);
	ZERO_BITSTRING(bitflags, n);


	for (i = 0; i < n; i++)
	{
		v = random() & 0x01;
		intflags[i] = v;
		SET_BIT(bitflags, i, v);
	}


	sprintf(filename, "%sint.txt", filestem);
	fd = open(filename, O_CREAT|O_TRUNC|O_WRONLY, 0666);
	if (write(fd, intflags, sizeof(int) * n) < (sizeof(int) * n))
	{
		perror("write failed");
		return (-1);
	}
	close(fd);


	sprintf(filename, "%sbit.txt", filestem);
	fd = open(filename, O_CREAT|O_TRUNC|O_WRONLY, 0666);
	if (write(fd, bitflags, BITSTRING_LEN(n)) < BITSTRING_LEN(n))
	{
		perror("write failed");
		return (-1);
	}
	close(fd);
	
	return 0;
}



int
compareBitstring(filestem, n)
char *filestem;
int n;
{
	char filename[FILENAME_MAX];
	BITSTRING bitflags;
	int *intflags;
	int i, vInt, vBit, fd;

	/** ensure we won't have buffer overflow */
	assert((strlen(filestem) + strlen("int.txt") + 1) < FILENAME_MAX);


	intflags = (int *) malloc(sizeof(int) * n);
	bzero(intflags, sizeof(int) * n);


	bitflags = ALLOC_BITSTRING(n);
	ZERO_BITSTRING(bitflags, n);



	/**
	 * load in all the data
	 */
	sprintf(filename, "%sint.txt", filestem);
	fd = open(filename, O_RDONLY);
	if (read(fd, intflags, sizeof(int) * n) < (sizeof(int) * n))
	{
		perror("write failed");
		return (-1);
	}
	close(fd);


	sprintf(filename, "%sbit.txt", filestem);
	fd = open(filename, O_RDONLY);
	if (read(fd, bitflags, BITSTRING_LEN(n)) < BITSTRING_LEN(n))
	{
		perror("write failed");
		return (-1);
	}
	close(fd);
	



	/** now compare that they are the same */
	for (i = 0; i < n; i++)
	{
		vInt = intflags[i];
		vBit = GET_BIT(bitflags, i);
		if (vInt == vBit)
		{
			printf("<PASS> : %d (%d == %d)\n", i, vInt, vBit);
		} else
		{
			printf("<FAIL> : %d (%d != %d)\n", i, vInt, vBit);
		}
	}

	return 0;
}


int
main(argc, argv)
int argc;
char **argv;
{
	int i, n = 64, didSomething = 0;
	long t;

	t = (long) time(NULL);
	srandom(t);

	for (i = 1; i < argc; i++)
	{
		if (argv[i][0] == '-')
		{
			if (sscanf(&argv[i][1], "%d", &n) != 1)
			{
				fprintf(stderr, "Cannot parse int from '%s'\n",
						argv[i]);
				exit (-1);
			}
		} else
		{

			if (storeBitstring(argv[i], n) < 0)
			{
				fprintf(stderr, "Aborting . . .\n");
				exit (-1);
			}

			if (compareBitstring(argv[i], n) < 0)
			{
				fprintf(stderr, "Aborting . . .\n");
				exit (-1);
			}
			didSomething = 1;
		}
	}

	if (!didSomething)
	{
		fprintf(stderr, "%s [ -<N> ] <filestem>\n", argv[0]);
		exit (1);
	}

	exit (0);
}
