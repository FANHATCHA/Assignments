/**
 * Kinda Cat -- read input, print on output
 *
 */

#include <stdio.h>
#include <sys/types.h>
#include <unistd.h>
#include <stdlib.h>
#include <fcntl.h>
#include <errno.h>


/** forward declarations */
static void printHelp(FILE *fp, char *progname);


/*
 * Read everything from the input, place on output
 */
int
processFD(FILE *ofp, FILE *ifp)
{
	/** where does BUFSIZ come from?  /usr/include . . . */
	char buffer[BUFSIZ];
	int len;

	while ((len = fread(buffer, 1, BUFSIZ, ifp)) > 0) {
		if (fwrite(buffer, 1, len, ofp) < 0) {
			perror("Failed on write");
			return -1;
		}
	}
	if (len < 0) {
		perror("Failed on read");
		return len;
	}

	/** return the number of bytes read, or < 0 on failure */
	return len;
}

/**
 * the entry point for any C program
 */
int
main(int argc, char *argv[])
{
	/** where do these special numbers come from? */
	FILE *inputFP = NULL;
	FILE *outputFP = stdout;
	int i;


	/** Parse command line options */
	for (i = 1; i < argc; i++) {
		if (argv[i][0] == '-') {

			if (argv[i][1] == 'h' || argv[i][1] == 'H') {
				printHelp(stdout, argv[0]);


			} else if (argv[i][1] == 'o') {
				if (argv[i][2] == 0) {
					if (argc > i + 1) {
						outputFP = fopen(argv[++i], "w");
						if (outputFP == NULL) {
							fprintf(stderr,
									"Cannot open output file '%s'\n",
									argv[i]);
							exit (1);
						}
					} else {
						printHelp(stderr, argv[0]);
						exit(1);
					}
				} else {
					outputFP = fopen(&argv[i][2], "w");
					if (outputFP == NULL) {
						fprintf(stderr,
									"Cannot open output file '%s'\n",
									argv[i]);
						exit(1);
					}
				}


			} else {
				fprintf(stderr, "Unknown argument '%s'\n", argv[i]);
				printHelp(stderr, argv[0]);
				exit (1);
			}

		} else {

			inputFP = fopen(argv[i], "r");
			if (inputFP < 0) {
				fprintf(stderr, "Cannot open input file '%s'\n",
						argv[i]);
				exit (1);
			}

			if (processFD(outputFP, inputFP) < 0) {
				fprintf(stderr, "Failed processing data in '%s'\n",
						argv[i]);
				exit (1);
			}
			fclose(inputFP);
		}
	}

	/** if we haven't seen any files, then move ahead with stdin */
	if (inputFP == NULL) {
		if (processFD(outputFP, stdin) < 0) {
			fprintf(stderr, "Failed processing stdin\n");
			exit (1);
		}
		fclose(inputFP);
	}
	fclose(outputFP);

	return 0;

}



static void
printHelp(FILE *fp, char *progname)
{
	fprintf(fp, "\n");
	fprintf(fp, "%s [ <options> ] <input file> [ <input file> ...]\n",
			progname);
	fprintf(fp, "\n");
	fprintf(fp, "Options:\n");
	fprintf(fp, "-o<filename> : place output in <filename>\n");
	fprintf(fp, "\n");
	fprintf(fp, "\n");
}

