#include <stdio.h>
#include <stdlib.h>

/* include for fabs() */
#include <math.h>

#include "arrayAllocator.h"


/* define our comparison epsilon as 1e-3 = 0.001 */
#define		EPSILON		(1e-3)

#define		X	0
#define		Y	1
#define		Z	2

#define		TYPE_INTEGER		100
#define		TYPE_CHAR			101
#define		TYPE_DOUBLE			102

static void
printArray(void ***a, int nDepths, int nRows, int nCols, int typeSpec)
{
	int i, j, k;

	printf("Array of %d x %d x %d:\n", nDepths, nRows, nCols);
	for (i = 0; i < nDepths; i++)
	{
		printf("Depth %d:\n", i);
		for (j = 0; j < nRows; j++)
		{
			printf("%3d %3d :", i, j);
			for (k = 0; k < nCols; k++)
			{

				if (typeSpec == TYPE_INTEGER)
					printf(" %5d", ((int ***)a)[i][j][k]);

				else if (typeSpec == TYPE_CHAR)
				{
					unsigned char c = ((char ***)a)[i][j][k];
					if (c >= ' ' && c <= '~')
						printf(" %c:%03d", c, c);
					else
						printf("   %03d", c);
				}
				else if (typeSpec == TYPE_DOUBLE)
					printf(" %7.4lg", ((double ***)a)[i][j][k]);
			}
			printf("\n");
		}
		printf("\n");
	}
}


static int
runTestInteger(int x, int y, int z)
{
	int i, j, k, ll;
	int ***a;
	int status = 1;

	printf("Testing using int (size %lu):\n", sizeof(int));

	a = (int ***) allocate3d(z, y, x, sizeof(int));

	for (i = 0; i < x * y * z; i++)
	{
		a[0][0][i] = i;
	}

	printArray((void ***)a, z,y,x, TYPE_INTEGER);

	ll = 0;
	for (i = 0; i < z; i++)
	{
		for (j = 0; j < y; j++)
		{
			for (k = 0; k < x; k++)
			{
				if (a[i][j][k] != ll)
				{
					status = 0;
					fprintf(stderr,
							"Mismatch at (%d,%d,%d) : got %d, expected %d)\n",
							i, j, k,
							a[i][j][k],
							ll);
				}
				ll++;
			}
		}
	}

	free3d(a);

	return status;
}

static int
runTestChar(int x, int y, int z)
{
	int i, j, k, ll;
	unsigned char ***a, c;
	int status = 1;

	printf("Testing using unsigned char (size %lu):\n",
			sizeof(unsigned char));

	a = (unsigned char ***) allocate3d(z, y, x, sizeof(unsigned char));

	for (i = 0; i < x * y * z; i++)
	{
		a[0][0][i] = i + ' ';
	}

	printArray((void ***)a, z,y,x, TYPE_CHAR);

	ll = 0;
	for (i = 0; i < z; i++)
	{
		for (j = 0; j < y; j++)
		{
			for (k = 0; k < x; k++)
			{
				c = ll + ' ';
				if (a[i][j][k] != c)
				{
					status = 0;
					fprintf(stderr,
							"Mismatch at (%d,%d,%d) : got %d, expected %d)\n",
							i, j, k,
							a[i][j][k],
							c);
				}
				ll++;
			}
		}
	}

	free3d(a);

	return status;
}

static int
runTestDouble(int x, int y, int z)
{
	int i, j, k, ll;
	double ***a;
	int status = 1;

	printf("Testing using double (size %lu):\n", sizeof(double));

	a = (double ***) allocate3d(z, y, x, sizeof(double));

	for (i = 0; i < x * y * z; i++)
	{
		a[0][0][i] = (double) sqrt(i);
	}

	printArray((void ***)a, z,y,x, TYPE_DOUBLE);

	ll = 0;
	for (i = 0; i < z; i++)
	{
		for (j = 0; j < y; j++)
		{
			for (k = 0; k < x; k++)
			{
				if (fabs(a[i][j][k] - sqrt(ll)) > EPSILON)
				{
					status = 0;
					fprintf(stderr,
							"Mismatch at (%d,%d,%d) : got %g, expected %g)\n",
							i, j, k,
							a[i][j][k],
							sqrt(ll));
				}
				ll++;
			}
		}
	}

	free3d(a);

	return status;
}

int
main(argc, argv)
	int argc;
	char **argv;
{
	int i;
	int dimension[3] = { 4, 3, 2 }, curDimension = Z;
	int type = TYPE_DOUBLE;
	int status = 1;

	for (i = 1; i < argc; i++)
	{
		if (argv[i][0] == '-')
		{
			switch (argv[i][1])
			{
			case 'i':
				type = TYPE_INTEGER;
				break;
			case 'c':
				type = TYPE_CHAR;
				break;
			case 'd':
				type = TYPE_DOUBLE;
				break;
			}
		}
		else
		{
			if (curDimension < X)
			{
				fprintf(stderr, "Too many dimensions specified\n");
			}
			else
			{
				dimension[curDimension--] = strtol(argv[i], NULL, 10);
			}
		}
	}


	if (type == TYPE_INTEGER)
	{
		status = runTestInteger(dimension[X], dimension[Y], dimension[Z]);
	}
	else if (type == TYPE_CHAR)
	{
		status = runTestChar(dimension[X], dimension[Y], dimension[Z]);
	}
	else if (type == TYPE_DOUBLE)
	{
		status = runTestDouble(dimension[X], dimension[Y], dimension[Z]);
	}

	if (status == 1)
		printf("PASS : all tests passed\n");

	exit( status == 1 ? 0 : -1);
}

