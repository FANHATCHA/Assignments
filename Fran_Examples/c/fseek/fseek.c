
/*----------------------------------------------------------------
 * Read in some data from a binary (little endian) file.
 *
 * $Author: andrew $
 * $Date: 2009-03-01 16:18:15 -0400 (Sun, 01 Mar 2009) $
 */

#include <stdio.h>
#include <errno.h>
#include <stdlib.h>
#include <string.h>
#include <unistd.h>


#define		DEFAULT_BASE	10

#define		VERBOSE(level, cmd)	\
				if (verbose > (level)) cmd


int
dumpNumbersFile(
		FILE *ofp,
		const char *filename,
		int verbose,
		int start,
		int stop,
		int base
	)
{
	FILE *ifp;
	int nFloats = 0;
	float aFloat;
	off_t seekStart;
	int result;
	int i;

	VERBOSE(0, printf("In dumpNumbersFile(%s, %d, %d)\n", filename, start, stop));

	if (stop > 0 && stop < start)
	{
		fprintf(stderr, "Stop location is before start!\n");
		return -1;
	}


	VERBOSE(2, printf(" == opening file '%s'\n", filename));


	ifp = fopen(filename, "r");
	if (ifp == NULL)
	{
		fprintf(stderr, "Failed opening input file '%s' : %s\n",
				filename, strerror(errno));
		return (-1);
	}


	VERBOSE(2, printf(" == reading number of floats in file\n"));


	/** read in the number of elements */
	if (fread(&nFloats, sizeof(int), 1, ifp) != 1)
	{
		fprintf(stderr, "No data in input file '%s' : %s\n",
				filename, strerror(errno));
		return (-1);
	}

	VERBOSE(1, printf(" = file declares %d floats\n", nFloats));


	/** make sure that everything is within a sensible range */
	if (start < 0) start = 0;
	if (start > nFloats) start = nFloats;
	if (stop > nFloats || stop < 0) stop = nFloats;



	VERBOSE(2, printf(" == seeking to start location %d:0x%x\n", start, start));


	/** figure out where to start */
	seekStart = sizeof(int) + start * sizeof(float);


	VERBOSE(3, printf(" === fseek(ifp, %08lx, %d)\n",
				(unsigned long) seekStart, SEEK_SET));


	/** go to the starting location */
	if (fseek(ifp, seekStart, SEEK_SET) < 0)
	{
		perror("Failed on seek");
		return (-1);
	}
  


	VERBOSE(2, printf(" == dumping until stop location %d:0x%x\n", stop, stop));
	for (i = start; i < stop; i++)
	{
		/** read in a single float */
		result = fread(&aFloat, sizeof(float), 1, ifp);


		VERBOSE(3, printf(" === fread() returned %d\n", result));

		/** fread() returns the number of elements read */
		if (result != 1) 
		{
			perror("Failed reading data");
			return (-1);
		}

		/** do a raw dump of the bytes of the float */
		if (verbose > 3)
		{
			int j;
			const char *cp;

			cp = (const char *) &aFloat;

			printf(" === float stored at %p : ", cp);
			for (j = 0; j < sizeof(float); j++)
			{
				printf(" %2x", cp[j]);
			}
			printf("\n");
		}

		if (base == 16)
			fprintf(ofp, "%X : %g\n", i, aFloat);
		else
			fprintf(ofp, "%d : %g\n", i, aFloat);
	}

	fclose(ifp);



	VERBOSE(2, printf(" == done with '%s'\n", filename));


	return (stop - start);
}

void
printHelp(const char *progname)
{
	printf("\n");
	printf("%s <options> <file> [ ... ]\n", progname);
	printf("\n");
	printf("Read a file of binary floats (Intel 32 bit architecture assumed)\n");
	printf("\n");
	printf("Options:\n");
	printf("\n");
	printf("-b <LOC>  : begin at location LOC (default base %d)\n", DEFAULT_BASE);
	printf("-e <LOC>  : end at location LOC\n");
	printf("-o <file> : put output in <file>, rather than on standard output\n");
	printf("-d        : specific locations in decimal\n");
	printf("-x        : specific locations in hexadecimal\n");
	printf("-v        : be more verbose\n");
	printf("-h        : this help\n");
	printf("\n");
}


int
main(int argc, char **argv)
{
	FILE *ofp = stdout;
	char *progname;
	char optionChar;
	char *endOfNumber;
	int stop = (-1), start = (-1);
	int base = DEFAULT_BASE;
	int verbose = 0;
	int nFailed = 0;
	int nRun = 0;
	int i;


	/** save the name of the program for use in the help */
	progname = argv[0];


	/** use getopt(3) to parse up any option flags we may have */
	while ((optionChar = getopt(argc, argv, "hvxdo:b:e:")) != -1) {
		switch (optionChar) {

		case 'v':
			verbose++;
			break;

		case 'd':
			base = 10;
			break;

		case 'x':
			base = 16;
			break;

		case 'b':
			start = (int) strtol(optarg, &endOfNumber, base);
			if ( ! (*optarg != 0 && *endOfNumber == 0)) {
				fprintf(stderr, "Cannot parse number in base %d from '%s'\n",
						base, optarg);
				exit(1);
			}
			break;

		case 'e':
			stop = (int) strtol(optarg, &endOfNumber, base);
			if ( ! (*optarg != 0 && *endOfNumber == 0)) {
				fprintf(stderr, "Cannot parse number in base %d from '%s'\n",
						base, optarg);
				exit(1);
			}
			break;


		case 'o':
			/* override the standard output destination */
			ofp = fopen(optarg, "w");
			if (ofp == NULL)
			{
				/*
				 * if we couldn't open the file, use strerror(3)
				 * to let the user know why
				 */
				(void) fprintf(stderr,
						"%s: %s: %s\n",
						progname, optarg, strerror(errno));
				exit(1);
			}
			break;


		case 'h':
		case '?':
		default:
			/* 
			 * use fall-through to get here with any of these
			 * three options
			 */
			printHelp(progname);
			exit (1);
		}
	}

	/* skip past the arguments processed by getopt(3) */
	argc -= optind;
	argv += optind;

	/* sort any files seen, collecting (logical) exit status */
	for (i = 0; i < argc; i++)
	{
		nRun++;
		if (dumpNumbersFile(ofp, argv[i], verbose, start, stop, base) < 0)
			nFailed++;
	}

	if (nRun == 0)
		fprintf(stderr, "No files to run?\n");

	if (nFailed > 0)
		exit(-nFailed);

	exit (0);
}


