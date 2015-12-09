
#
# Build all of the executables in this directory.  There are four.
# 
# writeRecordsFD and dumpRecordsFD use a file descriptor ("low level"
# I/O) to read/write values based on a structure in memory to and from
# a file
#
# 
# writeRecordsFP and dumpRecordsFP use a FILE * (buffered I/O) interface
# to do the same thing, with the same structure.
#
# This means that files written with either write utility can be read
# with either dump utility, as the data on the disk is fundamentally
# the same, as it has simply been copied from memory.
#

CC		= 	gcc
CFLAGS	= 	-g

all : writeRecordsFD dumpRecordsFD writeRecordsFP dumpRecordsFP


writeRecordsFD : writeRecordsFD.o
	$(CC) $(CFLAGS) -o writeRecordsFD writeRecordsFD.o

dumpRecordsFD : dumpRecordsFD.o
	$(CC) $(CFLAGS) -o dumpRecordsFD dumpRecordsFD.o


writeRecordsFP : writeRecordsFP.o
	$(CC) $(CFLAGS) -o writeRecordsFP writeRecordsFP.o

dumpRecordsFP : dumpRecordsFP.o
	$(CC) $(CFLAGS) -o dumpRecordsFP dumpRecordsFP.o


clean :
	- rm -f writeRecordsFD writeRecordsFD.o
	- rm -f dumpRecordsFD dumpRecordsFD.o
	- rm -f writeRecordsFP writeRecordsFP.o
	- rm -f dumpRecordsFP dumpRecordsFP.o

