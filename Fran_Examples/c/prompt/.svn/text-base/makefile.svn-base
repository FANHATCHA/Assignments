#
# A simple read-and-print input system
#

CC		= gcc
CFLAGS	= -g -Wall

EXE		= readlines

OBJS	= promptloop.o

$(EXE) : $(OBJS)
	$(CC) $(CFLAGS) -o $(EXE) $(OBJS)

clean :
	@ echo "cleaning up!"
	- rm -f $(EXE) $(OBJS)
