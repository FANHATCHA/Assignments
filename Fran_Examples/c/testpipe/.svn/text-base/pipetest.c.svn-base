#include <stdio.h>
#include <string.h>
#include <errno.h>
#include <unistd.h>
#include <stdlib.h>
#include <sys/types.h>
#include <sys/wait.h>


/**
 * Simple pipe debugging
 */
int
main()
{
	int apipe[2];
	int pid, gotpid;
	int childstatus;

	/*
	 *  create the pipe
	 *
	 *  must be done BEFORE the fork
	 */
	if (pipe(apipe) < 0 ) {
		perror("pipe");
		exit(1);
	}

	{
		char tmpw[11];
		sprintf(tmpw, "-PRE PIPE-");
		write(apipe[1], tmpw, 10);
	}

	{
		char tmpr[11];
		read(apipe[0], tmpr, 10);
		tmpr[10] = 0;
		fprintf(stderr, "got buffer [%s]\n", tmpr);
	}


	/**
	 * Create a child process -- this will turn into "more"
	 */
	if ((pid = fork()) < 0 ) {
		perror("fork");
		exit(1);
	}

	/*
	 * child stuff
	 */
	if (pid == 0) {
		int newfd;

		/*
		 * make read side of pipe stdin
		 * -- this is the magic to get the
		 * new file descriptor to appear
		 * where you want.  This works because
		 * you will get the lowest numbered
		 * available file descriptor when you
		 * open a new one.
		 */
		close(0);
		newfd = dup(apipe[0]);
		close(apipe[0]);

		/*
		 * close the write side of the pipe
		 * (because the child (more) is not
		 * going to be writing back to the
		 * parent
		 */
		close(apipe[1]);

		{
			int nread;
			char c;

			printf("Child reading on fd %d : [", newfd);
			while ((nread = read(0, &c, 1)) == 1)
			{
				fputc(c, stdout);
			}
			printf("]\n");
		}

		exit(1);
	}

	/*
	 *  parent code -- the child exitted in the
	 *  above if() block
	 */
	
	/*
	 *  close read side of pipe -- same logic as above
	 */
	close(apipe[0]);

	/*
	 *  write some (more) data into the pipe
	 */
	{
		char buffer[16];
		int len;

		sprintf(buffer, "*ABCDEFGHIJLM*");
		len = strlen(buffer);
		fprintf(stderr, "Parent writing [%s]\n", buffer);
		write(apipe[1], buffer, len);
	}



	/** close the pipe -- this will flush the last of the data through */
	close(apipe[1]);


	/*
	 *  wait for termination of the child "more" program
	 */
	gotpid = wait(&childstatus);
	if (gotpid != pid)
	{
		fprintf(stderr, "Expecting child '%d', got child '%d\n",
				gotpid, pid);
	}
	else
	{
		printf("Got expected child '%d'\n", gotpid);
	}

	exit(0);
}

