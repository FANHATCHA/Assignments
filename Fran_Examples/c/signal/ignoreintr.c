#include <stdio.h>
#include <string.h>
#include <signal.h>
#include <unistd.h>

void
intrHandler(int code)
{
	printf("Control-C was pressed, but I am ignoring it (type \"exit\" to exit)\n");
}

int main()
{
	char buffer[BUFSIZ];

	signal(SIGINT, intrHandler);

	printf("Type something, or \'exit\' to exit\n");
	while (fgets(buffer, BUFSIZ, stdin) != NULL)
	{
		if (strcmp(buffer, "exit\n") == 0)
			break;
	}

	printf("Exitting loop because exit was typed\n");
	return (0);
}
