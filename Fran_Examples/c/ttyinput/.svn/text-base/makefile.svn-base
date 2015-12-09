
CC		= 	gcc
CFLAGS	= 	-g

OBJS	= parseBirthday.o parseRecords.o readUserInput.o

readRecords : $(OBJS)
	$(CC) $(CFLAGS) -o readRecords $(OBJS)

clean :
	- rm -f readRecords $(OBJS)

tags ctags : dummy
	- ctags *.c

dummy:
