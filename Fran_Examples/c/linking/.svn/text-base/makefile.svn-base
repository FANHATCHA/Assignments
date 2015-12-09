
CC	= gcc
CFLAGS	= -g -Wall

tt : main.o myfunc.o
	$(CC) -o tt main.o myfunc.o

broken : main.o
	$(CC) -o broken main.o

clean : 
	- rm -f *.o tt

