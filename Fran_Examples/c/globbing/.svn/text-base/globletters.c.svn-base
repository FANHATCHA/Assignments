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
	char *globPattern = "files/[A-Z]*";

	g.gl_offs = 1;

	printf("Calculating glob of '%s'\n", globPattern);

	/** expand all of the .c files into the glob list */
	glob(globPattern, GLOB_DOOFFS, NULL, &g);


	printf("List after expansion:\n");
	printGlobArgv(1, &g);


	g.gl_pathv[0] = "echo";
	execvp("echo", g.gl_pathv);
}
