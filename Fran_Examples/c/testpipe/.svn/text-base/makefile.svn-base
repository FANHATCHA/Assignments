#
# Build the example
#

CC		= gcc
CFLAGS	= -g -Wall

EXE		= pipetest
OBJS	= pipetest.o

$(EXE) : $(OBJS)
	$(CC) $(CFLAGS) -o $(EXE) $(OBJS)

clean :
	@ echo "cleaning up!"
	- rm -f $(EXE)
	- rm -f $(OBJS)
