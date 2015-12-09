#include <stdio.h>

/*
 * Figure out endianness
 */

/** first, try *BSD, Mac, SGI/IRIX, HP/UX, IBM/AIX and Sun/Solaris */
#if defined( __BIG_ENDIAN__ ) || defined( _BIG_ENDIAN )
#  define OS_BIG_ENDIAN
#elif defined( __LITTLE_ENDIAN__ ) || defined( _LITTLE_ENDIAN )
#  define OS_LITTLE_ENDIAN

/** same as above, but some compilers omit endian if on 64-bit X86 */
#elif defined( __x86_64__ )
#  define OS_LITTLE_ENDIAN

/** some linux versions are detected above, but check the rest */
#elif defined( linux )
#  if defined( __i486__ )
#    define OS_LITTLE_ENDIAN
#  elif defined( __i386__ )
#    define OS_LITTLE_ENDIAN
#  else
#    error Unknown endian on Linux
#  endif

/** Redmond doesn't play by anyone else's rules */
#elif defined( __CYGWIN__ )
#  define OS_LITTLE_ENDIAN
#elif defined( _WIN32 )
#  define OS_LITTLE_ENDIAN

/** if we get here, we have a problem */
#else
#   error Unknown OS
#endif



typedef union FloatExplore {
	float f;
	int i;
	char c[1];
} FloatExplore;


static void dumpFloat(float data);

int
main()
{
	dumpFloat(0.5);
	dumpFloat(1.75);
	dumpFloat(0.5 + 1.75);

	dumpFloat(0.1);
	/*
	dumpFloat(10);
	dumpFloat(-10);

	printf("---\n");

	dumpFloat(0.5);
	dumpFloat(1.0);
	dumpFloat(2.0);

	printf("---\n");

	dumpFloat(-1.0);
	dumpFloat(2.5);
	dumpFloat(123.456);


	printf("--- close to 1.0:\n");

	{
		FloatExplore fe;
# 	ifdef	OS_LITTLE_ENDIAN
		fe.c[0] = 0xFF;
		fe.c[1] = 0xFF;
		fe.c[2] = 0x7F;
		fe.c[3] = 0x7F;
#	else
		fe.c[3] = 0xBE;
		fe.c[2] = 0x7F;
		fe.c[1] = 0xFF;
		fe.c[0] = 0xFF;
#	endif
		dumpFloat(fe.f);
		dumpFloat(0.99999997);
		dumpFloat(0.99999992);
	}
	*/


	return 0;
}


static void
dumpFloat(float fdata)
{
	FloatExplore data;

	int sign;
	int exponentXS127, exponent;
	int fractionBits;
	float fraction;


	data.f = fdata;

	sign = data.i >> 31;

	exponentXS127 = (data.i >> 23) & 0xff;
	exponent = (exponentXS127 - 127);

	fractionBits = ((data.i & 0x007fffff) | 0x00800000);
	fraction = (((float) fractionBits) / ((float) 0x00ffffff));

	printf("Value = %f\n", fdata);
	printf("         size: %ld (%ld)\n", sizeof(float), sizeof(FloatExplore));
	printf("    hex:  int: 0x%08x\n", data.i);

	printf("        bytes: 0x%02x %02x %02x %02x\n",
# 	ifdef	OS_LITTLE_ENDIAN
					(unsigned char) data.c[3], (unsigned char) data.c[2],
					(unsigned char) data.c[1], (unsigned char) data.c[0]
#	else
					(unsigned char) data.c[0], (unsigned char) data.c[1],
					(unsigned char) data.c[2], (unsigned char) data.c[3]
#	endif
				);

	printf("        bits:"

		printf(" %d%d%d%d %d%d%d%d ",
# 	ifdef	OS_LITTLE_ENDIAN
				(((unsigned char) data.c[3]) >> 7),
				(((unsigned char) data.c[3]) >> 6),
				(((unsigned char) data.c[3]) >> 5),
				(((unsigned char) data.c[3]) >> 4),
				(((unsigned char) data.c[3]) >> 3),
				(((unsigned char) data.c[3]) >> 2),
				(((unsigned char) data.c[3]) >> 1),
				(((unsigned char) data.c[3]) >> 0));
#	else
				(((unsigned char) data.c[0]) >> 7),
				(((unsigned char) data.c[0]) >> 6),
				(((unsigned char) data.c[0]) >> 5),
				(((unsigned char) data.c[0]) >> 4),
				(((unsigned char) data.c[0]) >> 3),
				(((unsigned char) data.c[0]) >> 2),
				(((unsigned char) data.c[0]) >> 1),
				(((unsigned char) data.c[0]) >> 0));
#	endif

		printf(" ");
		printf(" %d%d%d%d %d%d%d%d ",
# 	ifdef	OS_LITTLE_ENDIAN
				(((unsigned char) data.c[2]) >> 7),
				(((unsigned char) data.c[2]) >> 6),
				(((unsigned char) data.c[2]) >> 5),
				(((unsigned char) data.c[2]) >> 4),
				(((unsigned char) data.c[2]) >> 3),
				(((unsigned char) data.c[2]) >> 2),
				(((unsigned char) data.c[2]) >> 1),
				(((unsigned char) data.c[2]) >> 0));
#	else
				(((unsigned char) data.c[1]) >> 7),
				(((unsigned char) data.c[1]) >> 6),
				(((unsigned char) data.c[1]) >> 5),
				(((unsigned char) data.c[1]) >> 4),
				(((unsigned char) data.c[1]) >> 3),
				(((unsigned char) data.c[1]) >> 2),
				(((unsigned char) data.c[1]) >> 1),
				(((unsigned char) data.c[1]) >> 0));
#	endif

		printf(" ");
		printf(" %d%d%d%d %d%d%d%d ",
# 	ifdef	OS_LITTLE_ENDIAN
				(((unsigned char) data.c[1]) >> 7),
				(((unsigned char) data.c[1]) >> 6),
				(((unsigned char) data.c[1]) >> 5),
				(((unsigned char) data.c[1]) >> 4),
				(((unsigned char) data.c[1]) >> 3),
				(((unsigned char) data.c[1]) >> 2),
				(((unsigned char) data.c[1]) >> 1),
				(((unsigned char) data.c[1]) >> 0));
#	else
				(((unsigned char) data.c[2]) >> 7),
				(((unsigned char) data.c[2]) >> 6),
				(((unsigned char) data.c[2]) >> 5),
				(((unsigned char) data.c[2]) >> 4),
				(((unsigned char) data.c[2]) >> 3),
				(((unsigned char) data.c[2]) >> 2),
				(((unsigned char) data.c[2]) >> 1),
				(((unsigned char) data.c[2]) >> 0));
#	endif

		printf(" ");
		printf(" %d%d%d%d %d%d%d%d ",
# 	ifdef	OS_LITTLE_ENDIAN
				(((unsigned char) data.c[0]) >> 7),
				(((unsigned char) data.c[0]) >> 6),
				(((unsigned char) data.c[0]) >> 5),
				(((unsigned char) data.c[0]) >> 4),
				(((unsigned char) data.c[0]) >> 3),
				(((unsigned char) data.c[0]) >> 2),
				(((unsigned char) data.c[0]) >> 1),
				(((unsigned char) data.c[0]) >> 0));
#	else
				(((unsigned char) data.c[3]) >> 7),
				(((unsigned char) data.c[3]) >> 6),
				(((unsigned char) data.c[3]) >> 5),
				(((unsigned char) data.c[3]) >> 4),
				(((unsigned char) data.c[3]) >> 3),
				(((unsigned char) data.c[3]) >> 2),
				(((unsigned char) data.c[3]) >> 1),
				(((unsigned char) data.c[3]) >> 0));
#	endif

		printf("\n");

	printf("         sign: %d\n", sign);
	printf("          exp: %d  (xs=%d)\n", exponent, exponentXS127);
	printf("    frac bits: 0x%08x (%d)\n", fractionBits, fractionBits);
	printf("         frac: %f  (%24.20f)\n", fraction, fraction);
	printf("\n");
}

