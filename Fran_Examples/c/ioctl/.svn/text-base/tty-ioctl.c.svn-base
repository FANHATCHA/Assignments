
/*
 * IOCTL based interpretation of cbreak mode
 */

#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <ctype.h>

#include <termio.h>

int
main(int argc, char *argv[])
{
	struct termio orig,now;
	int c, done;

	setvbuf(stdout, NULL, _IONBF ,0);

	ioctl(0, TCGETA, (char *) &orig);

	now = orig;
	now.c_lflag &= ~(ISIG|ICANON|ECHO);
	now.c_cc[VMIN] = 1;
	now.c_cc[VTIME] = 2;

	ioctl(0,TCSETA, (char *) &now);

	done=0;
	while(!done) {
		printf("hit a key: ");
		c=fgetc(stdin);
		if (isprint(c))
			printf("key %03x (%c) \n", c, c);
		else
			printf("key %03x\n", c);
		done = (c == 'q');
	}

	ioctl(0,TCSETA, (char *) &orig);

	return 0;
}
