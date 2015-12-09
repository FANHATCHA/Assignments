
CC			= gcc
CFLAGS		= -g -Wall

OBJS		= LLNode.o main.o


listtest : $(OBJS)
	$(CC) $(CFLAGS) -o listtest $(OBJS)

clean :
	- rm -f $(OBJS)
	- rm -f listtest
	- rm -f tags

tags : dummy
	ctags *.c

dummy:
