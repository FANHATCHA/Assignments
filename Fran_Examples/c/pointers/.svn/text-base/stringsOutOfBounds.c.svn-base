/*
 * A simple demonstration of strings, malloc() and free()
 */
#include <stdio.h>
#include <stdlib.h>
#include <string.h>

#define		QUOTECHAR	'\''


/*
 * Using a string passed in as an argument, allocate a buffer
 * large enough for a quoted version of the string, copy and
 * escape all quotes in the given string, and pass it back
 * as a return value
 *
 * args:
 *	s : a string (pointer to char)
 * returns:
 *  a newly allocated string (pointer to char)
 */
char *
quotingFunction(char *s)
{
	char *quotedString, *load;
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

	/**
	 * Oop!  we don't have enough space here, as we don't have the
	 * two characters required for the begin/end quotes themselves!
	 */
	calculatedNewLength = l + 1 + numQuotes;
	load = quotedString = (char *) malloc(calculatedNewLength);
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

	return quotedString;
}

int
main()
{
	char *strings[] = { "hi", "there", "how's", "it", "goin'?", NULL };
	char *q;
	int i;

	for (i = 0; strings[i] != NULL; i++)
	{
		q = quotingFunction(strings[i]);
		printf("%s (%d) :\t%s (%d)\n",
					strings[i], (int) strlen(strings[i]),
					q, (int) strlen(q));
		free(q);
	}

	exit (0);
}

