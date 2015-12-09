/*
 * A C program that should crash.  This program attempts to reference
 * a null pointer value
 */
#include <stdio.h>
#include <stdlib.h>
#include <string.h>

#include "record.h"


/**
 * Because this data is set up outside of any function, it is available
 * to all of the functions in this file.  The "static" here means that
 * it is private to this file.
 *
 * Note that we create both sSetupData (an array) and sNSetupRecords
 * (an integer, which will be used to store the number of valid values
 * in the array).  sNSetupRecords is used (as a global) in both the
 * setup function, and in main -- a bad practice that leads in part to
 * our problem.
 */
static struct {
		char *first;
		char *last;
		char *phone;
	} sSetupData[] = {
			{	"Someone",	"Famous",	"1234567890"	},
			{	"Noone",	"Famous",	"1234567890"	},
			{	"Someone",	"Else",		"1235551212"	},
			{	NULL,		NULL,		NULL	}
		};
static int sNSetupRecords = 0;



/** print out a record, after receiving its address */
void
printRecord(char *callingFunction, int id, struct DataRecord *rec)
{
	printf("  Printing record %d as passed from %s\n", id, callingFunction);

	printf("  First name: %s\n", rec->firstname);
	printf("  Last name : %s\n", rec->lastname);
	printf("  Phone num : (%c%c%c) %c%c%c-%c%c%c%c\n",
				rec->phone[0], rec->phone[1], rec->phone[2],
				rec->phone[3], rec->phone[4], rec->phone[5],
				rec->phone[6], rec->phone[7], rec->phone[8], rec->phone[9]);
	printf("\n");
}


/** print out all the records */
void
printAllRecord(char *callingFunction, struct DataRecord *rec, int nRecords)
{
	int i;

	printf("Printing all records as passed from %s:\n", callingFunction);

	for (i = 0; i <= nRecords; i++)
		printRecord(callingFunction, i, &rec[i]);
}



/** set up all the records */
void
setupRecords(struct DataRecord **rec)
{
	int i;

	/** count the records */
	for (sNSetupRecords = 0;
				sSetupData[sNSetupRecords].first != NULL;
					sNSetupRecords++)
		;

	/** allocate for all of the records */
	printf("Allocating memory for %d records...\n", sNSetupRecords);
	(*rec) = (struct DataRecord *)
			malloc(sNSetupRecords * sizeof(struct DataRecord));

	for (i = 0; i < sNSetupRecords; i++)
	{
		(*rec)[i].firstname = sSetupData[i].first;
		(*rec)[i].lastname = sSetupData[i].last;

		strncpy((*rec)[i].phone, sSetupData[i].phone, 10);

		printRecord("setupRecord()", i, &(*rec)[i]);
	}
}

/** clean up all the records */
void
cleanupRecords(struct DataRecord *rec)
{
	free(rec);
}


/**
 * Main routine, asking for a record to be set up, and then printed
 *
 * Note that we are using the global (to this file) value sNSetupRecords
 * to access the number of records to print
 */
int
main()
{
	struct DataRecord *theRecord = NULL;

	setupRecords(&theRecord);

	printAllRecord("main()", theRecord, sNSetupRecords);

	cleanupRecords(theRecord);

	return 0;
}

