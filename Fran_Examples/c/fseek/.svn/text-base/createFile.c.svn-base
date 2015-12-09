
/*----------------------------------------------------------------
 * Read in some data from a binary (little endian) file.
 *
 * $Author: andrew $
 * $Date: 2009-03-01 16:18:15 -0400 (Sun, 01 Mar 2009) $
 */

#include <stdio.h>
#include <errno.h>
#include <math.h>
#include <strings.h>


static float sFiledata[] = {
			10,
			0.1,
			0.01,
			0.01,
			0.0000000001,
			0.5,
			0.25,
			0.125,
			0,
			1e-6,
			1e-4,
			1e-2,
			1e-1,
			1e+1,
			1e+2,
			1e+4,
			1e+6,
			M_E,
			M_LOG2E,
			M_LOG10E,
			M_LN2 ,
			M_LN10,
			M_PI,
			M_PI_2,
			M_PI_4,
			M_1_PI,
			M_2_PI,
			M_2_SQRTPI,
			M_SQRT2,
			M_SQRT1_2,
			-1
		};


int
main()
{
	char *filename = "numbers.dat";
	int nFloats = 0;
	float aFloat;
	FILE *ofp;
	int i;


	/** count up how many floats we have */
	for (nFloats = 0; sFiledata[nFloats] >= 0; nFloats++)
		;


	/** leave room for log(0) */
	nFloats++;


	/*open the file */
	ofp = fopen(filename, "w");
	if (ofp == NULL)
	{
		fprintf(stderr, "Failed opening output file '%s' : %s\n",
				filename, strerror(errno));
		return (-1);
	}


	if (fwrite(&nFloats, sizeof(int), 1, ofp) != 1)
	{
		fprintf(stderr, "Failed writing to file '%s' : %s\n",
				filename, strerror(errno));
		return (-1);
	}


	for (i = 0; sFiledata[i] >= 0; i++)
	{
		aFloat = sFiledata[i];
		if (fwrite(&aFloat, sizeof(float), 1, ofp) != 1)
			perror("Failed writing data");
	}

	aFloat = log(0);
	if (fwrite(&aFloat, sizeof(float), 1, ofp) != 1)
		perror("Failed writing data");

	fclose(ofp);

	return (0);
}

