#
# A simple makefile
#

CC = gcc
CFLAGS = -g -Wall

printargs : printargs.o
	$(CC) $(CFLAGS) -o printargs printargs.o

clean :
	@ echo "cleaning up!"
	- rm -f printargs.o printargs
