
/*
 * Read and process input, but do this differently depending on if we have a
 * tty or not
 */

#include <stdio.h>
#include <string.h>
#include <errno.h>
#include <unistd.h>

#include "records.h"

/**
 * This is easy, because we just take whatever is in the buffer
 * (except the trailing carriage return)
 */
int
parseNameFromBuffer(PhonebookRecord *record, char *line, int size)
{
	/** truncate line, either at the max length, or at the \n */
	if (strlen(line) > MAX_NAME_LEN)
		line[MAX_NAME_LEN-1] = 0;
	else
		line[strlen(line)-1] = 0;

	/** copy it into the record */
	strcpy(record->name, line);
	return 1;
}

int
parsePhonenumberFromBuffer(PhonebookRecord *record, char *line, int size)
{
	int i;

	/** similar to above, but make sure everything is a digit */
	if (strlen(line) > MAX_PHONE_LEN)
		line[MAX_PHONE_LEN-1] = 0;
	else
		line[strlen(line)-1] = 0;

	for (i = 0; line[i] != 0; i++)
		if (! isdigit(line[i]))
		{
			printf("Non-numeric data in phone number: '%s'\n", line);
			return -1;
		}

	/** copy it into the record */
	strcpy(record->phone, line);
	return 1;
}

int
parseEmailFromBuffer(PhonebookRecord *record, char *line, int size)
{
	char *atLocation;

	/** similar to above, but make sure we contain a '@' character */
	if (strlen(line) > MAX_EMAIL_LEN)
		line[MAX_EMAIL_LEN-1] = 0;
	else
		line[strlen(line)-1] = 0;

	atLocation = strchr(line, '@');
	if (atLocation == NULL)
	{
		printf("No '@' character in email address: '%s'\n", line);
		return -1;
	}

	/** copy it into the record */
	strcpy(record->email, line);
	return 1;
}


/**
 * Read a record from standard input; prompt if this is from the keyboard
 */
int
inputRecordFromUser(PhonebookRecord *record)
{
	char line[BUFSIZ];
	int istty;

	/**
	 * find out if there is in fact a TTY device giving us data on
	 * standard input (file descriptor 0); we will use this information
	 * to determine whether to print prompts
	 */
	istty = isatty(0);
	if (istty)
	{
		printf("Detected TTY on stdin; prompts enabled\n");
	}


	/** NAME : */
	/** print and flush prompt, if on a tty */
	if (istty) printf("Name: "), fflush(stdout);

	/** if first line is empty, just assume EOF */
	if (fgets(line, BUFSIZ, stdin) == NULL) return 0;
	/** parse the name from the buffer */
	if (parseNameFromBuffer(record, line, BUFSIZ) < 0)	return -1;
	


	/** PHONE : */
	/** print and flush prompt, if on a tty */
	if (istty) printf("Phone #: "), fflush(stdout);

	/** now an empty line is an error */
	if (fgets(line, BUFSIZ, stdin) == NULL) return -1;
	/** parse the phone number from the buffer */
	if (parsePhonenumberFromBuffer(record, line, BUFSIZ) < 0)	return -1;
	


	/** EMAIL : */
	/** print and flush prompt, if on a tty */
	if (istty) printf("Email #: "), fflush(stdout);

	/** now an empty line is an error */
	if (fgets(line, BUFSIZ, stdin) == NULL) return -1;
	/** parse the phone number from the buffer */
	if (parseEmailFromBuffer(record, line, BUFSIZ) < 0)	return -1;
	


	/** Birthday : */
	/** print and flush prompt, if on a tty */
	if (istty) printf("Birthday #: "), fflush(stdout);

	/** now an empty line is an error */
	if (fgets(line, BUFSIZ, stdin) == NULL) return -1;
	/** parse the phone number from the buffer */
	if (parseBirthdayFromBuffer(record, line, BUFSIZ) < 0)	return -1;
	

	return 1;
}

