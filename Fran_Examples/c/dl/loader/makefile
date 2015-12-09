#
# Build just the local driver program
#

EXE			= loadIt

CFLAGS		= -g -I../include

OBJS		= loadLib.o

$(EXE) : $(OBJS)
	$(CC) -rdynamic -o $(EXE) $(OBJS) -ldl

clean :
	- rm -f $(OBJS)
	- rm -f $(EXE)
	- rm -f *.o core.*

