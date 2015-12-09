
/*
 * tcsetattr based interpretation of cbreak mode
 */

#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <ctype.h>

#ifdef __linux
# include <termio.h>
#else
# include <sys/ioctl.h>
# include <termios.h>
#endif

int
main(int argc, char *argv[])
{
	struct termios orig,now;
	int c, done;

	setvbuf(stdout, NULL, _IONBF ,0);

	tcgetattr(0, &orig);

	now = orig;
	now.c_lflag &= ~(ISIG|ICANON|ECHO);
	now.c_cc[VMIN] = 1;
	now.c_cc[VTIME] = 2;

	tcsetattr(0, TCSANOW, &now);

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

	tcsetattr(0, TCSANOW, &orig);

	return 0;
}
