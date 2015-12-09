#ifndef	__COMMAND_LINE__
#define	__COMMAND_LINE__

#define	COMMANDLINE_BUFFER_MAX	8096

struct cmdLineCommand {
	int argc;
	char **argv;
};

struct cmdLine {
	char buffer[COMMANDLINE_BUFFER_MAX];
	int nCommandsInPipe;
	int isBackground;
	cmdLineCommand *commands;
};

/** set up typedefs so the struct keyword is not needed */
typedef struct cmdLineCommand cmdLineCommand;
typedef struct cmdLine cmdLine;


/** prototypes */
struct cmdLine getFileCommandLine(FILE *fp);
struct cmdLine getFDCommandLine(int fd);

void freeCommandLine(struct cmdLine *commandLine);

#endif /*	__COMMAND_LINE__ */
