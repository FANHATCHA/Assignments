/**
 * Manage a simple phonebook in memory
 *
 */
#include <stdio.h>
#include "phonebook.h"


static PhonebookEntry thePhonebook[] = {
		{ "Alice",	"5065551212", "ali@yahoo.com", 22	},
		{ "Bob",	"9025550000", "robotman@hotmail.com", 24	},
		{ "Clarice",	"5061234567", "zzclary@gmail.com", 21	},
		{ "David",	"5065551212", "daverino6000@gmail.com", 24	},
		{ "",	"", "", -1	}
	};

static int pbIndexByName[] = { 0, 1, 2, 3, -1 };
static int pbIndexByEmail[] = { 0, 3, 1, 2, -1 };

void printEntry(PhonebookEntry *pbRecArray, int index)
{
	printf("  %-8s   Phone: (%c%c%c) %c%c%c %c%c%c%c   Email: <%s> (%d years old)\n",
			pbRecArray[index].name,
			pbRecArray[index].phone[0],
					pbRecArray[index].phone[1],
					pbRecArray[index].phone[2],
			pbRecArray[index].phone[3],
					pbRecArray[index].phone[4],
					pbRecArray[index].phone[5],
			pbRecArray[index].phone[6],
					pbRecArray[index].phone[7],
					pbRecArray[index].phone[8],
					pbRecArray[index].phone[9],
			pbRecArray[index].email,
			pbRecArray[index].age);
}

/**
 * the entry point for any C program
 */
int
main(int argc, char *argv[])
{
	int nRecords;
	int i;

	/** count how many records we have */
	for (i = 0; thePhonebook[i].age >= 0; i++)
		;
	nRecords = i;

	printf("Printing in name order:\n");
	for (i = 0 ; i < nRecords; i++)
	{
		printEntry(thePhonebook, pbIndexByName[i]);
	}

	printf("\n");
	printf("Printing in email order:\n");
	for (i = 0 ; i < nRecords; i++)
	{
		printEntry(thePhonebook, pbIndexByEmail[i]);
	}

	return 0;
}

