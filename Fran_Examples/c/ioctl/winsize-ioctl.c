
/*
 * IOCTL based interpretation of cbreak mode
 */

#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <unistd.h>

#include <signal.h>

#ifdef __linux
# include <termio.h>
#else
# include <sys/ioctl.h>
# include <termios.h>
#endif

void sigHandler(int code)
{
	struct winsize ws;

	memset(&ws, 0, sizeof(struct winsize));

	ioctl(0, TIOCGWINSZ, &ws);

	printf("new window size: (%d, %d)\n", ws.ws_row, ws.ws_col);
}

int
main(int argc, char *argv[])
{
	struct winsize ws;
	int c;

	signal(SIGWINCH, sigHandler);

	memset(&ws, 0, sizeof(struct winsize));

	ioctl(0, TIOCGWINSZ, &ws);

	printf("Initial window size: (%d, %d)\n", ws.ws_row, ws.ws_col);

	c = fgetc(stdin);

	return 0;
}
