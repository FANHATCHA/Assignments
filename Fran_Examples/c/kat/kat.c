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


/**
 ** forward declarations, so that we can place these functions after main()
 **/

/* static here means "only visible from other functions in this file" */
static void printHelp(FILE *fp, char *progname);

/*
 * because it is declared without static, we could call processFD from any
 * file linked into this executable program
 */
int processFD(int ofd, int ifd);



/**
 * the entry point for any C program, with arguments containing
 * our command-line options
 */
int
main(int argc, char *argv[])
{
	/** where do these special numbers come from? */
	int inputFD = 0;
	int outputFD = 1;
	int i;


	/** Parse command line options */
	for (i = 1; i < argc; i++) {
		if (argv[i][0] == '-') {

			if (argv[i][1] == 'h' || argv[i][1] == 'H') {
				printHelp(stdout, argv[0]);


			} else if (argv[i][1] == 'o') {
				if (argv[i][2] == 0) {
					if (argc > i + 1) {
						outputFD = open(argv[++i],
								O_WRONLY|O_CREAT|O_TRUNC,
								0666);
						if (outputFD < 0) {
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
					outputFD = open(&argv[i][2],
									O_WRONLY|O_CREAT|O_TRUNC,
								0666);
					if (outputFD < 0) {
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

			/*
			 * use the inputFD variable for our file descriptor,
			 * and then change its value to -1 so that we can
			 * tell that we came into this part of the if
			 */
			inputFD = open(argv[i], O_RDONLY);
			if (inputFD < 0) {
				fprintf(stderr, "Cannot open input file '%s'\n",
						argv[i]);
				exit (1);
			}

			if (processFD(outputFD, inputFD) < 0) {
				fprintf(stderr, "Failed processing data in '%s'\n",
						argv[i]);
				exit (1);
			}
			close(inputFD);
			inputFD = (-1);
		}
	}

	/** if we haven't seen any files, then move ahead with stdin */
	if (inputFD >= 0) {
		if (processFD(outputFD, inputFD) < 0) {
			fprintf(stderr, "Failed processing stdin\n");
			exit (1);
		}
		close(inputFD);
	}
	close(outputFD);

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

/*
 * Read everything from the input, place on output
 */
int
processFD(int ofd, int ifd)
{
	/** where does BUFSIZ come from?  /usr/include . . . */
	char buffer[BUFSIZ];
	int len;

	while ((len = read(ifd, buffer, BUFSIZ)) > 0) {
		if (write(ofd, buffer, len) < 0) {
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

