/*
 * Test our program by loading up some doubles
 *
 * $Id: loadLib.c 411 2012-02-13 18:03:35Z andrew $
 */
#include <stdio.h>
#include <stdlib.h>
#include <dlfcn.h>


int
doLoading(const char *name)
{
	void *libHandle;
	char libname[64];
	char *error;

	int intResult;
	float floatResult;
	char *str;
	int status = 0;

	/** these must match the signature of our to-be-called functions */
	char * (*fnName)();
	int (*fnApple)();
	int (*fnPear)(int, int, int);
	float (*fnBanana)(float, char *);


	/*
	 * we need to specify the full path to our
	 * lib as it is not installed into the
	 * LD_LIBRARY_PATH, or the database managed
	 * by ldconfig(1) (depending on which OS
	 * we are on, one or the other of these
	 * tracks the standard library installation
	 * locations.
	 */
	sprintf(libname, "../libs/lib%s.so", name);


	printf("Loading library '%s' . . .\n", libname);
	libHandle = dlopen (libname, RTLD_LAZY);
	if (libHandle == NULL)
	{
		fprintf(stderr, "Failed loading library : %s\n", dlerror());
		return -1;
	}

	/* Clear any existing error */
	dlerror();



	/** >>> name ***/
	printf("Locating symbol getAName() in '%s' lib . . .\n", name);

	fnName = dlsym(libHandle, "getAName");
	if ((error = dlerror()) != NULL)  {
		fprintf (stderr, "DL error trying to find 'getAName' : %s\n", error);
		return -1;
	}

	printf("Calling getAName() in '%s' lib . . .\n", name);
	str = (*fnName)();

	printf ("Call to getAName() returned '%s'\n", str);
	/** <<< name ***/




	/** >>> apple ***/
	printf("Locating symbol apple() in '%s' lib . . .\n", name);

	fnApple = dlsym(libHandle, "apple");
	if ((error = dlerror()) != NULL)  {
		fprintf (stderr, "DL error trying to find 'apple' : %s\n", error);
		status = -1;
	} else
	{

		printf("Calling apple() in '%s' lib . . .\n", name);
		intResult = (*fnApple)();

		printf ("Call to apple() returned %d\n", intResult);
	}
	/** <<< apple ***/




	/** >>> banana ***/
	printf("Locating symbol banana() in '%s' lib . . .\n", name);

	fnBanana = dlsym(libHandle, "banana");
	if ((error = dlerror()) != NULL)  {
		fprintf (stderr, "DL error trying to find 'banana' : %s\n", error);
		status = -1;
	} else
	{

		printf("Calling banana() in '%s' lib . . .\n", name);
		intResult = (*fnBanana)(3.5, "fruitastic!");

		printf ("Call to banana() returned %d\n", intResult);
	}
	/** <<< banana ***/




	/** >>> pear ***/
	printf("Locating symbol pear() in '%s' lib . . .\n", name);

	fnPear = dlsym(libHandle, "pear");
	if ((error = dlerror()) != NULL)  {
		fprintf (stderr, "DL error trying to find 'pear' : %s\n", error);
		status = -1;
	} else
	{

		printf("Calling pear() in '%s' lib . . .\n", name);
		intResult = (*fnPear)(100, 200, 30);

		printf ("Call to pear() returned %d\n", intResult);
	}
	/** <<< pear ***/



	printf("Unloading library '%s' . . .\n", libname);
	dlclose(libHandle);


	printf("All done with '%s'\n", name);

	return 0;
}


int
main(argc, argv)
	int argc;
	char **argv;
{
	int i;

	if (argc < 2)
	{
		printf("Need to supply a command: tool or toy\n");
		exit (1);
	}

	for (i = 1; i < argc; i++)
	{
		if (strncasecmp(argv[i], "tool", 4) == 0)
		{
			printf("Command: TOOLS\n");
			if (doLoading("someTools") < 0)
			{
				printf("error: failed loading tools - aborting\n");
				exit (-1);
			}

		} else if (strncasecmp(argv[i], "toy", 3) == 0)
		{
			printf("Command: TOYS\n");
			if (doLoading("someToys") < 0)
			{
				printf("error: failed loading toys - aborting\n");
				exit (-1);
			}
		}
		else
		{
			printf("Command: '%s' not understood - ignoring\n", argv[i]);
		}
		printf("\n\n");
	}

	return 0;
}

