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

void printEntry(PhonebookEntry *record, int index)
{
	printf("  %-8s   Phone: (%c%c%c) %c%c%c %c%c%c%c   Email: <%s> (%d years old)\n",
			record->name,
			record->phone[0],
					record->phone[1],
					record->phone[2],
			record->phone[3],
					record->phone[4],
					record->phone[5],
			record->phone[6],
					record->phone[7],
					record->phone[8],
					record->phone[9],
			record->email,
			record->age);
}


/**
 * the entry point for any C program
 */
int
main(int argc, char *argv[])
{
	PhonebookEntry pbRecord;
	int nBytes;
	int fd;
	int i;

	fd = open(PB_FILE, O_RDONLY);
	if (fd < 0)
	{
		printf("Error - cannot open file: %s\n", strerror(errno));
		return (-1);
	}


	while ((nBytes = read(fd, &pbRecord,
					sizeof(PhonebookEntry))) > 0)
	{
		if (nBytes != sizeof(PhonebookEntry))
		{
			printf("Error - read failed: %s\n", strerror(errno));
			return (-1);
		}
		printEntry(&pbRecord, i);
	}

	return 0;
}

