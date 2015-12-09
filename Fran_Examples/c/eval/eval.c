#include <stdio.h>
#include <sys/types.h>
#include <sys/wait.h>
#include <unistd.h>



int main(int argc, char **argv)
{
	int pid;
	int status;

	++argv;

	if ((pid = fork()) == 0)
	{
		execvp(argv[0], argv);
	}

	wait(&status);

	if (WIFEXITED(status))
		return (WEXITSTATUS(status));
	return (-1);
}

