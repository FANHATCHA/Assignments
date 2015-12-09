#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <sys/types.h>
#include <sys/wait.h>


/*
 * A simple demonstration of fork: make a child, wait for it to be done
 */
int
main()
{
	int newpid, waitedpid;
	int status;

	printf("PARENT: I am the parent, with pid %d\n", getpid());

	
	/** clone ourselves; child will havea new pid */
	newpid = fork();

	if (newpid < 0)
	{
		perror("Something bad happened -- no child was created");
		exit (-1);
	}


	/*
	 * we can determine whether we are the clone/child or the
	 * original by the return value from fork
	 */
	if (newpid == 0)
	{
		/** this is in the child (or clone) */
		printf(" CHILD: Hello from the child (pid %d) -- I will sleep for 3 seconds\n",
				getpid());

		sleep(3);

		printf(" CHILD: Back from my nap!  Child %d is done -- Bye!\n", getpid());

		/** exit with a number we want to pass back to the parent */
		exit(42);
	}


	/**
	 * because we called exit in the if statement above, only the parent
	 * will run this code . . .
	 */
	printf("PARENT: I am the parent -- I know I created a child with pid %d\n", newpid);


	/** wait until a child is done (we only have one) */
	printf("PARENT: about to call wait()\n");
	waitedpid = wait(&status);
	printf("PARENT: wait() has returned!\n");


	/** we can figure out whether the child exitted or crashed . . . */
	if (WIFEXITED(status))
		printf("PARENT: Child with pid %d exitted with status %d\n",
				waitedpid, WEXITSTATUS(status));
	else
		printf("PARENT: Child with pid %d crashed!\n", waitedpid);


	printf("PARENT: Parent done waiting -- Bye!\n");

	/** now exit from the parent */
	exit (0);
}

