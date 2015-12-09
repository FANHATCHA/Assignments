
/*
 * Determine if standard input is a tty
 */

#include <stdio.h>
#include <unistd.h>

int
main(int argc, char *argv[])
{
	if (isatty(0))
		printf("Standard input is a TTY\n");
	else
		printf("Standard input is NOT a TTY\n");

	return 0;
}
