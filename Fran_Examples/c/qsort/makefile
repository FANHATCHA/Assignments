
CC	= gcc
CFLAGS	= -g -Wall

sortdemo : main.o qsort-any.o qsort-int.o
	$(CC) -o sortdemo main.o qsort-any.o qsort-int.o

clean : 
	- rm -f *.o sortdemo

