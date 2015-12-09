/*
 * Demonstrate parameter passing to functions using pointers
 *
 * Note that the values of the _local variables_ changed in aFunction()
 * are not seen in main() -- only changes made through a pointer
 * are seen
 */
#include <stdio.h>
#include <stdlib.h>
#include <string.h>

#define		QUOTECHAR	'\''

int
aFunction(int i, int *pi, char c, char *pc)
{
	printf("  >>>\n");

	printf("  func: &i  = 0x%08lx, i  = %d\n",
			(unsigned long) &i, i);
	printf("  func: &pi = 0x%08lx, pi = 0x%08lx\n",
			(unsigned long) &pi, (unsigned long) pi);
	printf("  func: &c  = 0x%08lx, c  = %d, '%c'\n",
			(unsigned long) &c, c, c);
	printf("  func: &pc = 0x%08lx, pc = 0x%08lx\n",
			(unsigned long) &pc, (unsigned long) pc);

	printf("  ---- making changes ----\n");

	i = 321;
	c = 'z';

	pi = NULL;
	pc = (char *) &i;
	*pc = 'A';

	printf("  func: &i  = 0x%08lx, i  = %d\n",
			(unsigned long) &i, i);
	printf("  func: &pi = 0x%08lx, pi = 0x%08lx\n",
			(unsigned long) &pi, (unsigned long) pi);
	printf("  func: &c  = 0x%08lx, c  = %d, '%c'\n",
			(unsigned long) &c, c, c);
	printf("  func: &pc = 0x%08lx, pc = 0x%08lx\n",
			(unsigned long) &pc, (unsigned long) pc);

	printf("  <<<\n");
	return 1;
}

int
main()
{
	int i;
	char c;
	int (*funcPtr)(int i, int *pi, char c, char *pc);

	i = 12345678;
	c = 'Q';

	printf("main:   &i  = 0x%08lx, i  = %d\n", (unsigned long) &i, i);
	printf("main:   &c  = 0x%08lx, c  = %d\n", (unsigned long) &c, c);

	aFunction(i, &i, c, &c);

	printf("main:   &i  = 0x%08lx, i  = %d\n", (unsigned long) &i, i);
	printf("main:   &c  = 0x%08lx, c  = %d\n", (unsigned long) &c, c);

	
	printf("\nRunning test again:\n\n");


	funcPtr = aFunction;

	i = 10;
	c = '*';

	printf("main:   funcPtr = 0x%08lx\n", (unsigned long) funcPtr);

	printf("main:   &i  = 0x%08lx, i  = %d\n", (unsigned long) &i, i);
	printf("main:   &c  = 0x%08lx, c  = %d, '%c'\n", (unsigned long) &c, c, c);

	(*funcPtr)(i, &i, c, &c);

	printf("main:   &i  = 0x%08lx, i  = %d\n", (unsigned long) &i, i);
	printf("main:   &c  = 0x%08lx, c  = %d, '%c'\n", (unsigned long) &c, c, c);


	return 0;
}

