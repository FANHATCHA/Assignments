/*
 * A simple program showing pointers
 */
#include <stdio.h>

void mySuperDuperFunction(long llFunc, short *ssFunc)
{
	printf("   >> arrived in function >>:\n");
	printf("    &llFunc=%p  llFunc=%ld\n", &llFunc, llFunc);
	printf("    &ssFunc=%p  ssFunc=%lu=0x%08lx *ssFunc=%d\n",
			&ssFunc,
			(unsigned long) ssFunc, (unsigned long) ssFunc,
			(int) *ssFunc);
	printf("   >>>>>>>>>>>>>>>>>>>:\n");

	printf("\n");

	llFunc  = 123;
	*ssFunc = 456;


	printf("   << leaving function <<:\n");
	printf("    &llFunc=%p  llFunc=%ld\n", &llFunc, llFunc);
	printf("    &ssFunc=%p  ssFunc=%lu=0x%08lx *ssFunc=%d\n",
			&ssFunc,
			(unsigned long) ssFunc, (unsigned long) ssFunc,
			(int) *ssFunc);
	printf("   <<<<<<<<<<<<<<<<<<<:\n");
}

int main()
{
	long lMain;
	short sMain;

	lMain = 42;
	sMain = -2;


	printf("Before Call:\n");
	printf("&lMain=%p  lMain=%ld\n", &lMain, lMain);
	printf("&sMain=%p  sMain=%ld\n", &sMain, (long) sMain);

	printf("\n");

	mySuperDuperFunction(lMain, &sMain);

	printf("\n");

	printf("After Call:\n");
	printf("&lMain=%p  lMain=%ld\n", &lMain, lMain);
	printf("&sMain=%p  sMain=%ld\n", &sMain, (long) sMain);

	return 0;
}


