#include <stdio.h>
#include <errno.h>
#include <fcntl.h>
#include <string.h>
#include <sys/types.h>
#include <sys/uio.h>
#include <unistd.h>
#include <stdlib.h>

#include "Python.h"


static char *
readFileIntoString(const char *filename)
{
	char *scriptbuffer = NULL;
	int fd;
	int nBytes;
	char c;

	fd = open(filename, O_RDONLY);
	if (fd < 0)
	{
		fprintf(stderr, "Cannot open python script file '%s' : %s\n", filename, strerror(errno));
		return NULL;
	}

	nBytes = 0;
	while (read(fd, &c, 1) > 0)
		nBytes++;

	if (lseek(fd, 0, SEEK_SET) < 0)
	{
		fprintf(stderr, "Cannot rewind script file '%s' : %s\n", filename, strerror(errno));
		return NULL;
	}

	scriptbuffer = (char *) malloc(nBytes + 2);
	if (read(fd, scriptbuffer, nBytes) != nBytes)
	{
		fprintf(stderr, "Failed (re)reading script in file '%s' : %s\n", filename, strerror(errno));
		free(scriptbuffer);
		return NULL;
	}

	scriptbuffer[nBytes] = '\n';
	scriptbuffer[nBytes+1] = 0;

	close(fd);

	return scriptbuffer;
}

int
main(int argc, char **argv)
{
    char *command = NULL;
    PyCompilerFlags cf;
	int status;


    cf.cf_flags = 0;


	if (argc < 2)
	{
		fprintf(stderr, "Please supply file with Python script in it\n");
		exit (-1);
	}

	command = readFileIntoString(argv[1]);
	if (command == NULL)
		exit (-1);


	/** start up Python */
	Py_Initialize();

	status = PyRun_SimpleStringFlags(command, &cf) != 0;
    
	/** shut down Python */
    Py_Finalize();


	free(command);

    return status;
}

