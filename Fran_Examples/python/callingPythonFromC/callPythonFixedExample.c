#include <stdio.h>
#include <string.h>
#include <stdlib.h>

#include "Python.h"


int
main()
{
    char *command = "print 'Hello from Python!'\nprint 1 + 2 + 3\n";
    PyCompilerFlags cf;
	int status;

    cf.cf_flags = 0;

	printf("I am about to run this Python program:\n%s", command);

	/** start up Python */
	Py_Initialize();

	printf("Here goes . . .\n");
	status = PyRun_SimpleStringFlags(command, &cf);
    
	/** shut down Python */
    Py_Finalize();

	printf("Python ran and the resulting status was %d\n", status);

    return 0;
}

