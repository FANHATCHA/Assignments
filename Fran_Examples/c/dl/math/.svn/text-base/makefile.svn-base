#
# Build our math example
#

EXE			= cosof2

CFLAGS		= -g

OBJS		= loadcos.o

$(EXE) : $(OBJS)
	$(CC) -rdynamic -o $(EXE) $(OBJS) -ldl

clean :
	- rm -f $(OBJS)
	- rm -f $(EXE)
	- rm -f *.o core.*

