
/*
 * Read and process input, but do this differently depending on if we have a
 * tty or not
 */

#include <stdio.h>
#include <string.h>
#include <errno.h>
#include <time.h>

#include "records.h"

static struct MonthConvert {
	char *shortname;
	char *longname;
} sMonthMatch[] = {
		{	"jan", "january" },
		{	"feb", "february" },
		{	"mar", "march" },
		{	"apr", "april" },
		{	"may", "may" },
		{	"jun", "june" },
		{	"jul", "july" },
		{	"aug", "august" },
		{	"sep", "september" },
		{	"oct", "october" },
		{	"nov", "november" },
		{	"dec", "december" }
	};

static int
findMonthNumber(char *string)
{
	int i;

	/** first, make string lower case */
	for (i = 0; i < strlen(string); i++)
		string[i] = tolower(string[i]);

	/** now see if we can find a match */
	for (i = 0; i < 12; i++)
		if (strcmp(string, sMonthMatch[i].shortname) ||
				strcmp(string, sMonthMatch[i].longname))
			return (i); /* we will count months from zero */
	return -1;
}

/**
 * this is a tough one -- we want to convert an actual date.
 * we will parse this in a format like:
 *	Jan 1, 1970
 */
int
parseBirthdayFromBuffer(PhonebookRecord *record, char *line, int size)
{
	char monthstr[16];
	int day, year, month, n;
	struct tm tm;

	/** use sscanf to parse the string */
	n = sscanf(line, "%s %d, %d", monthstr, &day, &year);
	if (n != 3)
	{
		printf("Only recognized %d of 3 items in '%s'\n", n, line);
		printf("(expecting a form like \"Jan 1, 1970\"\n");
		return -1;
	}

	/** convert the month to a number */
	month = findMonthNumber(monthstr);
	if (month < 0)
	{
		printf("Cannot parse month from '%s'\n", monthstr);
		return -1;
	}

	/** clear, then fill up our struct tm */
	memset(&tm, 0, sizeof(struct tm));
	tm.tm_mday = day;
	tm.tm_mon  = month;
	tm.tm_year = year - 1900;


	/** now get our time_t from this */
	record->birthday = mktime(&tm);

	return 1;
}


