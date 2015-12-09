#include <stdio.h>
#include "commandline.h"

#define	MAX_PIPE_LEN	16

void
freeCommandLine(struct cmdLine *commandLine)
{
	free(commandLine->commands);
	free(commandLine);
}

struct cmdLine
getFileCommandLine(FILE *fp)
{
	cmdLine *newCommand;
	char *token, *tokenState = NULL;
	int nPipes;

	/** allocate command structure to hold line buffer */
	newCommand = (cmdLine *) malloc(sizeof(newCommand));
	newCommand->nCommandsInPipe = 0;
	newCommand->isBackground = 0;
	newCommand->commands = NULL;

	if (fgets(newCommand->buffer, COMMANDLINE_BUFFER_MAX, fp) == NULL)
	{
		free(newCommand);
		return NULL;
	}

	token = getFirstToken(newCommand->buffer, &tokenState);
	if (token == NULL) {
		return newCommand;
	}

	while ((token
	nPipes = 0;
	bufferTail = newCommand->buffer;
	while (index(bufferTail, '|') != NULL)
	{
		/* increment so next search starts one character later */
		bufferTail++;
	}

}

struct cmdLine
getFDCommandLine(FILE *fp)
{
	return getFileCommandLine(fdopen(fp, "r"));
}

/** prototypes */
struct cmdLine getFileCommandLine(FILE *fp);
struct cmdLine getFDCommandLine(int fd);
struct cmdLine getStringCommandLine(char *lineOfText);
G

#define	COMMANDLINE_BUFFER_MAX	8096

struct cmdLineCommand {
	int argc;
	char **argv;
};

struct cmdLine {
	char buffer[COMMANDLINE_BUFFER_MAX];
	int n_commands_in_pipe;
	cmdLineCommand *commands;
};

/** set up typedefs so the struct keyword is not needed */
typedef struct cmdLineCommand cmdLineCommand;
typedef struct cmdLine cmdLine;


/** prototypes */
struct cmdLine getFileCommandLine(FILE *fp);
struct cmdLine getFDCommandLine(int fd);
struct cmdLine getStringCommandLine(char *lineOfText);
void freeCommandLine(struct cmdLine *commandLine);

