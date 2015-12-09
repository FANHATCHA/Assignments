#include <stdio.h>
#include <string.h>
#include <ctype.h>

#include "bitstring.h"

static int sBitValues[] = {
		0, 1, 0, 1, 1, 1, 1, 0,
		0, 1, 0, 1, 0, 1, 0, 0,
		1, 1, 1, 1, 1, 1, 1, 1,
		0, 0, 0, 0, 0, 0, 0, 0,
		1, 0, 1,
		-1
	};

int
main()
{
	BITSTRING bs;
	int i, n;

	/** count how many values we were given */
	for (n = 0; sBitValues[n] >= 0; n++)
		;

	bs = ALLOC_BITSTRING(n);
	ZERO_BITSTRING(bs, n);

	/** load up the bitstring */
	for (i = 0; i < n; i++)
	{
		SET_BIT(bs, i, sBitValues[i]);
	}


	/** print out the bit flags */
	printf("Bitstring values:\n");
	for (i = 0; i < n; i++)
	{
		if (i > 0 && (i % 8 == 0))	printf("\n");
		printf("%3d : %d\n", i, GET_BIT(bs, i));
	}
	printf("\n");


	/** print out the bitstring as a series of chars */
	printf("Bitstring as chars:\n");
	for (i = 0; i < BITSTRING_LEN(n); i++)
	{
		if (i > 0 && (i % 8 == 0))	printf("\n");

		if (isprint( ((char *)bs)[i] ))
		{
			printf("%3d : %02x '%c'\n", i,
					(unsigned char) ((char *)bs)[i],
					((char *)bs)[i] );
		}
		else
		{
			printf("%3d : %02x\n", i,
					(unsigned char) ((char *)bs)[i] );
		}
	}
	printf("\n");


	return 0;
}

