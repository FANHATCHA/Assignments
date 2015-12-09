#include <stdio.h>
#include <string.h>
#include <errno.h>

#define MAXLINELEN	256

int
readAndPrintAFile(char *filename)
{
	char linebuffer[MAXLINELEN];
	FILE *fp;
	int nlines = 0;

	fp = fopen(filename, "r");
	if (fp == NULL)
	{
		fprintf(stderr, "Cannot open file '%s' : %s\n",
			filename, strerror(errno));
		return -1;
	}

	while (fgets(linebuffer, MAXLINELEN, fp) != NULL)
	{
		printf("I read line %3d : %s", ++nlines, linebuffer);
	}

	fclose(fp);
	return nlines;
}

int main(int argc, char *argv[])
{
	int i;

	for (i = 1; i < argc; i++)
	{
		if (readAndPrintAFile(argv[i]) < 0)
		{
			fprintf(stderr, "Could not read file '%s'\n", argv[i]);
			return -1;
		}
	}

	return 0;
}

