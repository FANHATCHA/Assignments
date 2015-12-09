
/*
 * read some standard records from a file,
 * using a file pointer ("buffered I/O")
 */

#include <stdio.h>
#include <errno.h>
#include <stdlib.h>
#include <string.h>
#include <unistd.h>
#include "record.h"


int
readRecord(
		FILE *fp,
		DataRecord *record
	)
{
	int readResult;

	readResult = fread(record, 1, sizeof(DataRecord), fp);
	if (readResult != sizeof(DataRecord) && readResult != 0)
	{
		fprintf(stderr, "Read failed : %d %s\n", errno, strerror(errno));
		return -1;
	}

	return readResult == sizeof(DataRecord) ? 1 : 0;
}

int
readRecordAtPos(
		FILE *fp,
		int headerSize,
		DataRecord *record
	)
{
	if (fseek(fp, headerSize + sizeof(DataRecord) * record->id, SEEK_SET) < 0)
	{
		fprintf(stderr, "Seek failed : %s\n", strerror(errno));
		return -1;
	}

	return readRecord(fp, record);
}


int
readHeader(FILE *fp, short *nRecords, short *headRecord)
{
	/** go to the beginning of the file */
	if (fseek(fp, 0, SEEK_SET) < 0)
	{
		fprintf(stderr, "Seek failed : %s\n", strerror(errno));
		return -1;
	}
		

	/** now record the number of records */
	if (fread(nRecords, 1, sizeof(short), fp) != sizeof(short))
	{
		fprintf(stderr, "Read failed : %s\n", strerror(errno));
		return (-1);
	}
		
	/** and the start of the list */
	if (fread(headRecord, 1, sizeof(short), fp) != sizeof(short))
	{
		fprintf(stderr, "Read failed : %s\n", strerror(errno));
		return (-1);
	}

	return 1;
}


int
main(int argc, char **argv)
{
	FILE *fp = NULL;
	DataRecord rec;
	short nRecords, listHead;
	int i;


	if (argc <= 1)
	{
		printf("Filename not given\n");
		exit (-1);
	}

	fp = fopen(argv[1], "r");
	if (fp == NULL)
	{
		fprintf(stderr, "Cannot open file '%s' : %s\n",
				argv[1], strerror(errno));
		exit (-2);
	}


	/** clear our our record */
	memset(&rec, 'X', sizeof(DataRecord));


	if (readHeader(fp, &nRecords, &listHead) < 0)
	{
		fprintf(stderr, "Aborting\n");
		exit (-1);
	}

	printf("nRecords = %d\n", nRecords);

	for (i = 0; i < nRecords; i++)
	{
		if (readRecord(fp, &rec) < 0)
		{
			fprintf(stderr, "Aborting\n");
			exit (-1);
		}

		printf("Record %d: id %d (0x%x)\n", i, rec.id, rec.id);
		printf("    name : [%s]\n", rec.name);
		printf("    phone: [%s]\n", rec.phone);
		printf("    next :  %d\n", rec.nextByName);
		printf("\n");

	}

	fclose(fp);

	exit (0);
}


