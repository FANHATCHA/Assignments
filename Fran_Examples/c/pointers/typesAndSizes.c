/*
 * Print out the sizes of various types and their pointers
 */

#include <stdio.h>

typedef struct customer_record {
	int id;
	char name[36];
	char age;
	float balance;
} CustomerRecord;

int
main()
{
	CustomerRecord customer_list[10];
	CustomerRecord *custp;
	char c, *cp;

	c = 12;
	cp = &c;

	/* initialize a pointer-to-customer-record using
	 * the address of an array location
	 */
	custp = &customer_list[0];


	printf("Sizes calculated from type names:\n");
	printf("  sizeof(char *)      = %d; sizeof(char)      = %d\n",
			(int) sizeof(char *), (int) sizeof(char));
	printf("  sizeof(short *)     = %d; sizeof(short)     = %d\n",
			(int) sizeof(short *), (int) sizeof(short));
	printf("  sizeof(int *)       = %d; sizeof(int)       = %d\n",
			(int) sizeof(int *), (int) sizeof(int));
	printf("  sizeof(long *)      = %d; sizeof(long)      = %d\n",
			(int) sizeof(long *), (int) sizeof(long));
	printf("  sizeof(long long *) = %d; sizeof(long long) = %d\n",
			(int) sizeof(long long *), (int) sizeof(long long));
	printf("  sizeof(float *)     = %d; sizeof(float)     = %d\n",
			(int) sizeof(float *), (int) sizeof(float));
	printf("  sizeof(double *)    = %d; sizeof(double)    = %d\n",
			(int) sizeof(double *), (int) sizeof(double));

	printf("\n");
	printf("  sizeof(unsigned char *) = %d; sizeof(unsigned char) = %d\n",
			(int) sizeof(unsigned char *), (int) sizeof(unsigned char));
	printf("  sizeof(unsigned int *)  = %d; sizeof(unsigned int)  = %d\n",
			(int) sizeof(unsigned int *), (int) sizeof(unsigned int));

	printf("\n");
	printf("Sizes calculated from variable declarations:\n");
	printf("  sizeof(char *)           = %d; sizeof(char) = %d\n",
			(int) sizeof(cp), (int) sizeof(c));

	printf("  sizeof(customer_list)    = %d\n", (int) sizeof(customer_list));

	printf("  sizeof(CustomerRecord *) = %d; sizeof(CustomerRecord) = %d\n",
			(int) sizeof(CustomerRecord *), (int) sizeof(CustomerRecord));

	printf("  sizeof(custp)            = %d; sizeof(*custp)         = %d\n",
			(int) sizeof(custp), (int) sizeof(*custp));

	return 0;
}


