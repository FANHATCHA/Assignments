#include <stdio.h>
#include <string.h>

static struct specdata {
	char *name;
	int red, green, blue;
} spectrum[] = {
	{	"red",		255,   0,   0	},
	{	"orange",	255, 100,   0	},
	{	"yellow",	255, 255,   0	},
	{	"green",	  0, 255,   0	},
	{	"blue",		  0,   0, 255	},
	{	"indigo",	  0,  50, 100	},
	{	"violet",	100,   0, 255	}
	};


extern void qsortint(int v[], int left, int right);
extern void qsortany(void *v[], int left, int right, int (*comp)(void *, void *));

static void printints(int a[], int);
static void printstrings(char *s[], int);
static void printstructs(struct specdata s[], int);
static void printstructps(struct specdata *s[], int);

static int speccmp(struct specdata *, struct specdata *);



int
main()
{
	int idata[] = { 0, 8, 3, 1, 9, 2, 7 };
	char *strings[] = {
			"Father", "charles", "goes", "down", "and", "ends", "battle"
		};
	struct specdata *sdatap[7];
	sdatap[0] = &spectrum[0];
	sdatap[1] = &spectrum[1];
	sdatap[2] = &spectrum[2];
	sdatap[3] = &spectrum[3];
	sdatap[4] = &spectrum[4];
	sdatap[5] = &spectrum[5];
	sdatap[6] = &spectrum[6];

	printints(idata, 7);
	printstrings(strings, 7);
	printstructs(spectrum, 7);
	printstructps(sdatap, 7);

	qsortint(idata, 0, 6);
	qsortany((void **) strings, 0, 6,
			(int (*)(void *, void *)) strcmp);
	qsortany((void **) sdatap, 0, 6,
			(int (*)(void *, void *)) speccmp);

	printints(idata, 7);
	printstrings(strings, 7);
	printstructs(spectrum, 7);
	printstructps(sdatap, 7);

	return 0;
}

static void
printints(int a[], int n)
{
	int i;

	printf("Integers:\n");
	for (i = 0 ; i < n; i++)
	{
		printf("   %d\n", a[i]);
	}
	printf("\n");
}

static void
printstrings(char *s[], int n)
{
	int i;

	printf("Strings:\n");
	for (i = 0 ; i < n; i++)
	{
		printf("   %s\n", s[i]);
	}
	printf("\n");
}

static void
printstructs(struct specdata s[], int n)
{
	int i;

	printf("Spectral data (struct):\n");
	for (i = 0 ; i < n; i++)
	{
		printf("   %-7s %3d %3d %3d\n",
				s[i].name, s[i].red, s[i].green, s[i].blue);
	}
	printf("\n");
}

static void
printstructps(struct specdata *s[], int n)
{
	int i;

	printf("Spectral data (struct *):\n");
	for (i = 0 ; i < n; i++)
	{
		printf("   %-7s %3d %3d %3d\n",
				s[i]->name, s[i]->red, s[i]->green, s[i]->blue);
	}
	printf("\n");
}

static int
speccmp(struct specdata *a, struct specdata *b)
{
	return strcmp(a->name, b->name);
}
