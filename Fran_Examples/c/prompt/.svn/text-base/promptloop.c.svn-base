/*
 * Read data in a loop, printing a prompt if not reading from a pipe
 */
#include <stdio.h>
#include <unistd.h>
#include <string.h>

int
prompt(const char *promptstring)
{
	if (isatty(0))
	{
		fputs(promptstring, stdout);
		fflush(stdout);
		if (ferror(stdout))	return (-1);
		return 1;
	}

	return 0;
}

#define	MAX_LINE_LEN	80

int
readloop()
{
	char *pString = "enter input> ";
	char line[MAX_LINE_LEN];
	int lineLen;


	prompt(pString);
	while (fgets(line, MAX_LINE_LEN, stdin) != NULL)
	{
		lineLen = strlen(line);

		/**
		 * lines whose last character are newline fit into the buffer;
		 * lines whose last character are not newlines will be
		 * MAX_LINE_LEN-1 bytes long and will be properly truncated
		 * as C strings, but will not actually be complete lines
		 */
		if (line[lineLen-1] == '\n')
		{
			printf("read: %s", line);
		} else
		{
			printf("overflow of %d byte buffer\n", MAX_LINE_LEN);
			printf("partial read [%s]\n", line);
		}

		prompt(pString);
	}

	/** return error if read error */
	if (ferror(stdin))	return -1;

	return 0;
}


int
main()
{
	readloop();

	return (0);
}
