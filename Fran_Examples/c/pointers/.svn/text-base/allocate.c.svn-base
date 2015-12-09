/*
 * A simple program showing pointers
 */
#include <stdio.h>
#include <stdlib.h>
#include <math.h>

double *allocateDoubles(int len)
{
	return (double *) malloc(len * sizeof(double));
}

int *allocateInts(int len)
{
	int i, *result;

	result = (int *) malloc(len * sizeof(int));
	for (i = 0; i < len; i++)
		result[i] = 100 * i;
	return result;
}

void
allocateLists(double **doublelist, int **integerList, int len)
{
	(*doublelist) = allocateDoubles(len);
	(*integerList) = allocateInts(len);
}

int main()
{
	double *listOfDoubles = NULL;
	int *listOfInts = NULL;
	int i;

	/** allocate the first time */
	allocateLists(&listOfDoubles, &listOfInts, 10);

	for (i = 0; i < 10; i++) {
		listOfDoubles[i] = M_PI * i;
	}

	printf("Printing lists:\n");
	for (i = 0; i < 10; i++) {
		printf("%2d: %3d [at %p]  %7.3lf [at %p]\n",
				i,
				listOfInts[i], &listOfInts[i],
				listOfDoubles[i], &listOfDoubles[i]);
	}

	/** free them */
	free(listOfDoubles);
	free(listOfInts);

	return 0;
}


