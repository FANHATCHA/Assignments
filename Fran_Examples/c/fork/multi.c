#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <sys/types.h>
#include <sys/wait.h>


/*
 * A simple demonstration of fork: creates three children that
 * print their pids, then a fourth child that calls "ps -l"
 */
int
main()
{
	int pidlist[4], childpid;
	int sleeptime;
	int status;
	int i;

	printf("I am the parent, with pid %d\n", getpid());

	
	sleeptime = 5;
	if ((pidlist[0] = fork()) == 0)
	{
		printf("Child 0, with pid %d: sleeping %d seconds\n",
				getpid(), sleeptime);
		sleep(sleeptime);
		printf("Child 0, with pid %d: I am done sleeping.  Bye!\n",
				getpid());
		exit(0);	/* use our id as exit status for this example */
	}

	
	sleeptime = 7;
	if ((pidlist[1] = fork()) == 0)
	{
		printf("Child 1, with pid %d: sleeping %d seconds\n",
				getpid(), sleeptime);
		sleep(sleeptime);
		printf("Child 1, with pid %d: I am done sleeping.  Bye!\n",
				getpid());
		exit(1);	/* use our id as exit status for this example */
	}

	
	sleeptime = 1;
	if ((pidlist[2] = fork()) == 0)
	{
		printf("Child 2, with pid %d: sleeping %d seconds\n",
				getpid(), sleeptime);
		sleep(sleeptime);
		printf("Child 2, with pid %d: I am done sleeping.  Bye!\n",
				getpid());
		exit(2);	/* use our id as exit status for this example */
	}

	
	if ((pidlist[3] = fork()) == 0)
	{
		printf("Child 3, with pid %d: now becoming ps -l\n", getpid());

		execlp("ps", "ps", "-l", NULL);

		perror("exec failed!");
		exit (-127);
	}


	/**
	 * Now wait for all of the children (up to ID 3)
	 */
	for (i = 0; i < 4; i++)
	{
		childpid = wait(&status);
		if (WIFEXITED(status))
			printf("Child with pid %d exitted with status %d\n",
					childpid, WEXITSTATUS(status));
		else
			printf("Child with pid %d crashed!\n", childpid);
	}

	printf("Done everything!\n");

	/** now exit from the parent */
	exit (0);
}

