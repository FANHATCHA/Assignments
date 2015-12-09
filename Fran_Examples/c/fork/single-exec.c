#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <sys/types.h>
#include <sys/wait.h>


/*
 * A simple demonstration of fork w exec: make a child, wait for it to be done
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
		char *execargv[5];

		execargv[0] = "echo";
		execargv[1] = "what a nice day    ...     to say \"Hello\" to everyone:";
		execargv[2] = "Hello!";
		execargv[3] = "*";
		execargv[4] = NULL;

		/** this is in the child (or clone) */
		printf(" CHILD: Hello from the child (pid %d) -- I will now transmogrify myself into echo...\n",
				getpid());

		execv("/bin/echo", execargv);

		/** we only can get here if execv failed! */
		exit(-1);
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

