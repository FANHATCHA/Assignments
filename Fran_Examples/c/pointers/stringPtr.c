/*
 * Demonstrate parameter passing to functions using pointers
 */
#include <stdio.h>
#include <stdlib.h>
#include <string.h>

#define		QUOTECHAR	'\''

/*
 * Using a string passed in as an argument, allocate a buffer
 * large enough for a quoted version of the string, and place
 * a reference to this new buffer into the variable pointed to
 * by the argument "quotedString".  The length of the required
 * buffer is stored in the location pointed to by "newLength".
 *
 * Into the new buffer, copy and escape all quotes in the given
 * string "s", and then return a success/failure indication.
 *
 * args:
 *	s : a string (pointer to char)
 *	quotedString : a pointer to a string (pointer to pointer to char)
 *	newLength : a pointer to an interger in which to store the new length
 * returns:
 *  (-1) on failure, or 0 on success
 */
int
quotingFunction(char *s, char **quotedString, int *newLength)
{
	char *allocatedString, *load;
	int numQuotes = 0;
	int i, l, calculatedNewLength;

	/** figure out length of string */
	l = strlen(s);

	/** count the number of quotes */
	for (i = 0; i < l; i++)
	{
		if (s[i] == QUOTECHAR)
		{
			numQuotes++;
		}
	}

	/** calculate space required for data + term + quote escapes */
	calculatedNewLength = l + 1 + 2 + numQuotes;
	load = allocatedString = (char *) malloc(calculatedNewLength);

	/** check for failure from malloc() */
	if (allocatedString == NULL)
	{
		return (-1);
	}

	*load++ = QUOTECHAR;

	for (i = 0; i < l; i++)
	{
		if (s[i] == QUOTECHAR)
		{
			*load++ = '\\';
		}
		*load++ = s[i];
	}
	*load++ = QUOTECHAR;
	*load = 0;


	*quotedString = allocatedString;
	*newLength = calculatedNewLength;

	return 0;
}

int
main()
{
	char *strings[] = { "hi", "there", "how's", "it", "goin'?", NULL };
	char *q;
	int i, l;

	for (i = 0; strings[i] != NULL; i++)
	{
		if (quotingFunction(strings[i], &q, &l) < 0) {
			exit (-1);
		}
		printf("%s (%d) :\t%s (%d:%d)\n",
				strings[i], (int) strlen(strings[i]),
				q, (int) strlen(q), l);
		free(q);
	}

	exit (0);
}

