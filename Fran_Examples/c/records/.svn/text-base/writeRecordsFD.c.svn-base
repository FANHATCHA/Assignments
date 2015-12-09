
/*
 * write out some standard records to a file,
 * using a file descriptor ("low level I/O")
 */

#include <stdio.h>
#include <errno.h>
#include <stdlib.h>
#include <string.h>
#include <unistd.h>
#include <fcntl.h>
#include "record.h"


int
writeRecord(
		int fd,
		DataRecord *record
	)
{
	if (write(fd, record, sizeof(DataRecord)) != sizeof(DataRecord))
	{
		fprintf(stderr, "Write failed : %s\n", strerror(errno));
		return -1;
	}
	return 1;
}

int
writeRecordAtPos(
		int fd,
		int headerSize,
		DataRecord *record
	)
{
	if (lseek(fd, headerSize + sizeof(DataRecord) * record->id, SEEK_SET) < 0)
	{
		fprintf(stderr, "Seek failed : %s\n", strerror(errno));
		return -1;
	}

	return writeRecord(fd, record);
}


int
writeHeader(int fd, short nRecords, short headRecord)
{
	/** go to the beginning of the file */
	if (lseek(fd, 0, SEEK_SET) < 0)
	{
		fprintf(stderr, "Seek failed : %s\n", strerror(errno));
		return -1;
	}
		

	/** now record the number of records */
	if (write(fd, &nRecords, sizeof(short)) != sizeof(short))
	{
		fprintf(stderr, "Write failed : %s\n", strerror(errno));
		return (-1);
	}
		
	/** and the start of the list */
	if (write(fd, &headRecord, sizeof(short)) != sizeof(short))
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
	int fd = -1;
	DataRecord rec;
	short nRecords;
	int i;


	if (argc <= 1)
	{
		printf("Filename not given\n");
		exit (-1);
	}

	fd = open(argv[1], O_WRONLY|O_CREAT|O_TRUNC, 0640);
	if (fd < 0)
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
		 *	(writeRecord(fd, &rec) < 0)
		 */
		if (writeRecordAtPos(fd, 2 * sizeof(short), &rec) < 0)
		{
			fprintf(stderr, "Aborting\n");
			exit (-1);
		}
	}

	if (writeHeader(fd, i, -1) < 0)
	{
		fprintf(stderr, "Aborting\n");
		exit (-1);
	}

	close(fd);

	exit (0);
}


