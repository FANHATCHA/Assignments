#include <glob.h>
#include <stdio.h>



static void
printGlobArgv(int startIndex, glob_t *glob)
{
	int i;

	printf("  path count : %d\n", (int) glob->gl_pathc);
	printf("  offset     : %d\n", (int) glob->gl_offs);
	printf("  flags      : %x\n", glob->gl_flags);

	for (i = startIndex; i < startIndex + glob->gl_pathc; i++)
	{
		printf("  %3d : [%s]>\n", i, glob->gl_pathv[i]);
	}
}

int main()
{
	glob_t g;

	g.gl_offs = 2;

	printf("Calculating glob of \"*.c *.h\"\n");

	/** expand all of the .c files into the glob list */
	glob("*.c", GLOB_DOOFFS, NULL, &g);

	printf("List after *.c:\n");
	printGlobArgv(2, &g);

	/** expand all of the .h files into the list */
	glob("*.h", GLOB_DOOFFS | GLOB_APPEND, NULL, &g);

	printf("List after *.h:\n");
	printGlobArgv(2, &g);


	g.gl_pathv[0] = "ls";
	g.gl_pathv[1] = "-l";

	printf("List before exec:\n");
	printGlobArgv(0, &g);
	execvp("ls", g.gl_pathv);
}
