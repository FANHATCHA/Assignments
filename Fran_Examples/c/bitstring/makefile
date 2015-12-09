#
# compile the two bitstring examples
# 

CC	= gcc
CFLAGS	= -g -Wall

FLAGEXE		= fileflagexample
SIMPLEEXE	= simpleexample

FLAGOBJS	= fileflagstorage.o
SIMPLEOBJS	= simplebitstring.o

all : $(FLAGEXE) $(SIMPLEEXE)

$(FLAGEXE) : $(FLAGOBJS)
	$(CC) $(CFLAGS) -o $(FLAGEXE) $(FLAGOBJS)

$(SIMPLEEXE) : $(SIMPLEOBJS)
	$(CC) $(CSIMPLES) -o $(SIMPLEEXE) $(SIMPLEOBJS)

clean :
	@ echo "cleaning up!"
	- rm -f $(FLAGEXE)
	- rm -f $(FLAGOBJS)
	- rm -f $(SIMPLEEXE)
	- rm -f $(SIMPLEOBJS)
