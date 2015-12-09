
/*
 * write out some standard records to a file,
 * using a file pointer ("buffered I/O")
 */

#include <stdio.h>
#include <errno.h>
#include <stdlib.h>
#include <string.h>
#include <unistd.h>
#include "record.h"


int
writeRecord(
		FILE *fp,
		DataRecord *record
	)
{
	if (fwrite(record, 1, sizeof(DataRecord), fp) != sizeof(DataRecord))
	{
		fprintf(stderr, "Write failed : %s\n", strerror(errno));
		return -1;
	}
	return 1;
}

int
writeRecordAtPos(
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

	return writeRecord(fp, record);
}


int
writeHeader(FILE *fp, short nRecords, short headRecord)
{
	/** go to the beginning of the file */
	if (fseek(fp, 0, SEEK_SET) < 0)
	{
		fprintf(stderr, "Seek failed : %s\n", strerror(errno));
		return -1;
	}
		

	/** now record the number of records */
	if (fwrite(&nRecords, 1, sizeof(short), fp) != sizeof(short))
	{
		fprintf(stderr, "Write failed : %s\n", strerror(errno));
		return (-1);
	}
		
	/** and the start of the list */
	if (fwrite(&headRecord, 1, sizeof(short), fp) != sizeof(short))
	{
		fprintf(stderr, "Write failed : %s\n", strerror(errno));
		return (-1);
	}

	return 1;
}


char *names[] = {
		"Sally",
		"Arthur",
		"Susan",
		"Zaphod",
		"Ford",
		NULL
	};

char *numbers[] = {
		"123456",
		"3640000",
		"411",
		"z9-6000",
		"banana",
		NULL
	};


int
main(int argc, char **argv)
{
	FILE *fp = NULL;
	DataRecord rec;
	short nRecords;
	int i;


	if (argc <= 1)
	{
		printf("Filename not given\n");
		exit (-1);
	}

	fp = fopen(argv[1], "w");
	if (fp == NULL)
	{
		fprintf(stderr, "Cannot open file '%s' : %s\n",
				argv[1], strerror(errno));
		exit (-2);
	}


	/** clear our our record */
	memset(&rec, '~', sizeof(DataRecord));


	for (i = 0; names[i] != NULL; i++)
	{
		strcpy(rec.name, names[i]);
		strcpy(rec.phone, numbers[i]);
		rec.id = i;
		rec.nextByName = -1;

		/*
		 * What would have happened if we simply called this instead?
		 *	(writeRecord(fp, &rec) < 0)
		 */
		if (writeRecordAtPos(fp, 2 * sizeof(short), &rec) < 0)
		{
			fprintf(stderr, "Aborting\n");
			exit (-1);
		}
	}

	if (writeHeader(fp, i, -1) < 0)
	{
		fprintf(stderr, "Aborting\n");
		exit (-1);
	}

	fclose(fp);

	exit (0);
}


