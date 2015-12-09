/*
 * Dump out memory, to show memory mapping
 */
#include <stdio.h>
#include <stdlib.h>
#include <string.h>

struct MyStruct {
	char tag[6];
	int i;
};

int
main()
{
	unsigned char *cp;
	unsigned long *lp;

	unsigned char c;
	struct MyStruct s;
	unsigned char e = 0xBB;
	int nDigits;


	/** need two hex digits for each byte of address */
	nDigits = sizeof(void *) * 2;

	/**
	 * initialize both local char, and struct data
	 * - copy 5 chars (data + term) into char array
	 * - initialize struct long to a recognizable value
	 */
	c = 'Z';
	s.i = 0xC0DEBEEF;
	strcpy(s.tag, "Hello");

	printf("&c = 0x%0*lx\n", nDigits, (unsigned long) &c);
	printf("&e = 0x%0*lx\n", nDigits, (unsigned long) &e);



	printf("sizeof(char) = %lu\n", (unsigned long) sizeof(char));
	printf("sizeof(char *) = %lu\n", (unsigned long) sizeof(char *));
	printf("Life as a char *\n");
	/**
	 * print out all of our data as a sequence of byte values,
	 * and the longs corresponding to the same byte values
	 */
	cp = &c;
	while (cp >= &e)
	{
		printf("0x%0*lX : ", nDigits, (unsigned long) cp);
		printf("  long: 0x%0*lx", nDigits, *((unsigned long *)cp));
		printf("  char: %02x", *cp);
		if (*cp > ' ' && *cp <= '~')
			printf(" '%c'", *cp);
		else
			printf(" '\\%o'", *cp);
		printf("\n");
		cp--;
	}

	printf("\n");

	printf("sizeof(int) = %lu\n", (unsigned long) sizeof(int));
	printf("sizeof(int *) = %lu\n", (unsigned long) sizeof(int *));
	printf("Life as an long *\n");
	/**
	 * using an long pointer (and .'. long pointer arithmetic)
	 * print out the same list
	 *
	 * note the casting to get rid of compiler type warnings
	 */
	lp = (unsigned long *) &c;
	while (lp >= (unsigned long *) &e)
	{
		unsigned char tc = (unsigned char) *lp;

		printf("0x%0*lX : ", nDigits, (unsigned long) lp);
		printf("  long: 0x%0*lx", nDigits, *((unsigned long *)lp));
		printf("  char: ");

		if (tc > ' ' && tc <= '~')
			printf(" '%c'", tc);
		else
			printf(" '\\%o'", tc);
		printf("\n");
		lp--;
	}


	return 0;
}


