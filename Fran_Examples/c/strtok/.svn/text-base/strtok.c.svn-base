#include <stdio.h>
#include <string.h>
#include <stdlib.h>

static char *sData[] = {
		"My dog has no nose",
		"How\t\tdoes      he smell?",
		"Terrible",
		NULL
	};

int
main()
{
	int i, j;
	char *delimiters = " \t\n";
	char *workingBuffer;
	char *token;

	/* handle each string separately */
	for (i = 0; sData[i] != NULL; i++)
	{
		j = 0;
		workingBuffer = strdup(sData[i]);

		/*
		 * start up the tokenizer, which will remember the string
		 * given in an internal static pointer
		 */
		token = strtok(workingBuffer, delimiters);
		while (token != NULL)
		{
			printf("%3d : I got token [%s]\n", j++, token);

			/*
			 * Subsequent calls with a NULL data string mean "use
			 * the data I gave you before, and give me the next
			 * token"
			 */
			token = strtok(NULL, delimiters);
		}
		printf("Done string %d\n\n", i);
		free(workingBuffer);
	}

	return 0;
}
