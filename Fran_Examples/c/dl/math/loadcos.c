/**
 * Example showing the loading of a known system library
 * (in this case, the math library) and the calling of a
 * single symbol from within it.
 */
#include <stdio.h>
#include <stdlib.h>
#include <dlfcn.h>

int main(int argc, char **argv)
{
	void *handle;
	double (*cosine)(double);
	char *error;

	/**
	 * open the library, deferring the binding of
	 * names until the actual call (faster if we
	 * only use a few of the names).
	 */
	handle = dlopen ("libm.so", RTLD_LAZY);
	if (!handle) {
		fprintf (stderr, "%s\n", dlerror());
		exit(1);
	}

	/*
	 * Clear any existing error -- there should
	 * be none at this point, but if we were
	 * doing other dl work previously, something
	 * might be left over, so this is always
	 * good practice
	 */
	dlerror();


	/*
	 * Look up our symbol "cos" and assign the
	 * result to a function pointer of the right
	 * type here
	 */
	cosine = dlsym(handle, "cos");
	if ((error = dlerror()) != NULL)  {
		fprintf (stderr, "%s\n", error);
		exit(1);
	}

	/** call the cos() function through our pointer */
	printf ("%f\n", (*cosine)(2.0));
	dlclose(handle);
	return 0;
}

