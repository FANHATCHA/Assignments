
/*
 * Read and process input, but do this differently depending on if we have a
 * tty or not
 */

#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <unistd.h>

#include "records.h"



int
printRecord(
		FILE *ofp,
		PhonebookRecord *record
	)
{
	fprintf(ofp, "Record %d:\n", record->id);
	fprintf(ofp, "    Name : %s\n", record->name);
	fprintf(ofp, "    Phone: %s\n", record->phone);
	fprintf(ofp, "    Email: %s\n", record->email);
	fprintf(ofp, "    B-day: %s", ctime(&record->birthday));
}


int
main(int argc, char **argv)
{
	PhonebookRecord rec;
	int nRecords = 1;
	int i;


	if (argc > 1)
	{
		if ((nRecords = abs(atoi(argv[1]))) == 0)
		{
			fprintf(stderr, "Nonsense in number of records: '%s'\n", argv[1]);
			exit (-1);
		}
	}


	/** clear our our record (with nul characters) */
	memset(&rec, 0x00, sizeof(PhonebookRecord));


	for (i = 0; i < nRecords; i++)
	{
		/** read a record from standard input */
		if (inputRecordFromUser(&rec) < 0)
		{
			fprintf(stderr, "Fatal error when parsing -- aborting\n");
			exit (-1);
		}

		/** set the record ID */
		rec.id = i;

		/** now print it out */
		if (printRecord(stdout, &rec) < 0)
		{
			fprintf(stderr, "Fatal error when printing -- aborting\n");
			exit (-1);
		}

	}

	exit (0);
}


