#include <stdio.h>

#include "Python.h"


int
main(int argc, char **argv)
{
	int status;

	if (argc < 2)
	{
		fprintf(stderr, "Please supply a python script and args\n");
		exit (-1);
	}

	/** overwrite argv */
	argv[0] = "python";


	/** start up Python */
	Py_Initialize();

	status = Py_Main(argc, argv) != 0;

	/** shut down Python */
    Py_Finalize();


    return status;
}

