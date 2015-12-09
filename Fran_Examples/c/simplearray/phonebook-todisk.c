/**
 * Manage a simple phonebook in memory
 *
 */
#include <stdio.h>
#include <fcntl.h>
#include <errno.h>
#include <string.h>
#include "phonebook.h"

#define	PB_FILE	"records.pb"

static PhonebookEntry thePhonebook[] = {
		{ "Alice",	"5065551212", "ali@yahoo.com", 22	},
		{ "Bob",	"9025550000", "robotman@hotmail.com", 24	},
		{ "Clarice",	"5061234567", "zzclary@gmail.com", 21	},
		{ "David",	"5065551212", "daverino6000@gmail.com", 24	},
		{ "",	"", "", -1	}
	};

/**
 * the entry point for any C program
 */
int
main(int argc, char *argv[])
{
	int nRecords;
	int fd;
	int i;

	/** count how many records we have */
	for (i = 0; thePhonebook[i].age >= 0; i++)
		;
	nRecords = i;


	fd = open(PB_FILE, O_CREAT|O_TRUNC|O_WRONLY, 0666);
	if (fd < 0)
	{
		printf("Error - cannot open file: %s\n", strerror(errno));
		return (-1);
	}


	for (i = 0 ; i < nRecords; i++)
	{
		if (write(fd, &thePhonebook[i],
					sizeof(PhonebookEntry)) != sizeof(PhonebookEntry))
		{
			printf("Error - write failed: %s\n", strerror(errno));
			return (-1);
		}
	}

	printf("Wrote %d records\n", nRecords);

	return 0;
}

