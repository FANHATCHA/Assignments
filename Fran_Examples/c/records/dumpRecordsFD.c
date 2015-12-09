
/*
 * read some standard records from a file,
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
readRecord(
		int fd,
		DataRecord *record
	)
{
	int readResult;

	readResult = read(fd, record, sizeof(DataRecord));
	if (readResult != sizeof(DataRecord) && readResult != 0)
	{
		fprintf(stderr, "Read failed : %d %s\n", errno, strerror(errno));
		return -1;
	}

	return readResult == sizeof(DataRecord) ? 1 : 0;
}

int
readRecordAtPos(
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

	return readRecord(fd, record);
}


int
readHeader(int fd, short *nRecords, short *headRecord)
{
	/** go to the beginning of the file */
	if (lseek(fd, 0, SEEK_SET) < 0)
	{
		fprintf(stderr, "Seek failed : %s\n", strerror(errno));
		return -1;
	}
		

	/** now record the number of records */
	if (read(fd, nRecords, sizeof(short)) != sizeof(short))
	{
		fprintf(stderr, "Read failed : %s\n", strerror(errno));
		return (-1);
	}
		
	/** and the start of the list */
	if (read(fd, headRecord, sizeof(short)) != sizeof(short))
	{
		fprintf(stderr, "Read failed : %s\n", strerror(errno));
		return (-1);
	}

	return 1;
}


int
main(int argc, char **argv)
{
	int fd = -1;
	DataRecord rec;
	short nRecords, listHead;
	int i;


	if (argc <= 1)
	{
		printf("Filename not given\n");
		exit (-1);
	}

	fd = open(argv[1], O_RDONLY);
	if (fd < 0)
	{
		fprintf(stderr, "Cannot open file '%s' : %s\n",
				argv[1], strerror(errno));
		exit (-2);
	}


	/** clear our our record */
	memset(&rec, 'X', sizeof(DataRecord));


	if (readHeader(fd, &nRecords, &listHead) < 0)
	{
		fprintf(stderr, "Aborting\n");
		exit (-1);
	}

	printf("nRecords = %d\n", nRecords);

	for (i = 0; i < nRecords; i++)
	{
		if (readRecord(fd, &rec) < 0)
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

	close(fd);

	exit (0);
}


