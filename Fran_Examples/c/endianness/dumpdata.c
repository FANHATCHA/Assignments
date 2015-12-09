#include <stdio.h>
#include <math.h>
#include <ctype.h>

struct a_struct {
	short sval;
	unsigned long lval;
	float fval;
};

void
dumpData(void *vptr, int length)
{
	struct a_struct *sptr;
	int *iptr;
	long *lptr;
	float *fptr;
	double *dptr;
	unsigned char *uptr;
	int i;

	sptr = (struct a_struct *) vptr;
	iptr = (int *) vptr;
	lptr = (long *) vptr;
	fptr = (float *) vptr;
	dptr = (double *) vptr;
	uptr = (unsigned char *) vptr;

	printf(">> start dump >>\n");

	printf("As struct : (%d bytes)\n", sizeof(*sptr));
	printf("   sval : %04x (%u)\n", sptr->sval, sptr->sval);
	printf("   lval : %08lx (%ld)\n", sptr->lval, sptr->lval);
	printf("   fval : %f\n", sptr->fval);
	printf("\n");

	printf("As ints : (%d bytes)\n", sizeof(*iptr));
	for (i = 0; i < sizeof(struct a_struct) / sizeof(int); i++)
	{
		printf("    int[%3d] 0x%08x (%d)\n", i, iptr[i], iptr[i]);
	}
	printf("\n");

	printf("As longs : (%d bytes)\n", sizeof(*lptr));
	for (i = 0; i < sizeof(struct a_struct) / sizeof(long); i++)
	{
		printf("    int[%3d] 0x%08x (%d)\n", i, lptr[i], lptr[i]);
	}
	printf("\n");

	
	printf("As floats : (%d bytes)\n", sizeof(*fptr));
	for (i = 0; i < sizeof(struct a_struct) / sizeof(float); i++)
	{
		printf("    float[%3d] %f \n", i, fptr[i]);
	}
	printf("\n");

	
	printf("As doubles : (%d bytes)\n", sizeof(*dptr));
	for (i = 0; i < sizeof(struct a_struct) / sizeof(double); i++)
	{
		printf("    double[%3d] %lf \n", i, dptr[i]);
	}
	printf("\n");

	
	printf("As chars : (%d byte)\n", sizeof(*uptr));
	for (i = 0; i < sizeof(struct a_struct); i++)
	{
		printf("    char[%3d] %02x %4u", i, uptr[i], uptr[i]);
		if (isprint(uptr[i]))
			printf(" '%c'", uptr[i]);
		else
			printf(" (unprintable)");
		printf("\n");
	}
	printf("<< done dump <<\n");
	printf("\n");

}

int
main()
{
	struct a_struct *sptr;
	int *iptr;
	float *fptr;
	char *cptr;
	char buffer[sizeof(struct a_struct)];
	int i;


	sptr = (struct a_struct *) &buffer[0];
	iptr = (int *) &buffer[0];
	fptr = (float *) &buffer[0];
	cptr = (char *) &buffer[0];


	/* IBM likes debugging with this pattern */
	sptr->lval = 0xdeadbeef;

	sptr->sval = 16000;
	sptr->fval = (float) M_PI;
	printf("Set Structure to:  %d, %x, %f\n", sptr->sval, sptr->lval, sptr->fval);
	dumpData(buffer, sizeof(struct a_struct));

	
	for (i = 0; i < sizeof(struct a_struct) / sizeof(int); i++)
	{
		iptr[i] = 0xbeadbead + i;
		printf("Set iptr[%3d] to 0x%0x (%d)\n", i, iptr[i], iptr[i]);
	}
	dumpData(buffer, sizeof(struct a_struct));

	
	for (i = 0; i < sizeof(struct a_struct) / sizeof(float); i++)
	{
		fptr[i] = 1.0 / (float) i;
		printf("Set fptr[%3d] to %f\n", i, fptr[i]);
	}
	dumpData(buffer, sizeof(struct a_struct));

	
	for (i = 0; i < sizeof(struct a_struct); i++)
	{
		cptr[i] = 33 + i;
		printf("Set cptr[%3d] to %c\n", i, cptr[i]);
	}
	dumpData(buffer, sizeof(struct a_struct));

	return 0;
}
