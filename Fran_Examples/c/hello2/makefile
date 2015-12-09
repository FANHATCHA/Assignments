#
# A simple makefile
# $Id: makefile 225 2007-01-26 14:54:11Z andrew $
#

CC = gcc
CFLAGS = -g -Wall

hello : hello.o
	$(CC) $(CFLAGS) -o hello hello.o

clean :
	@ echo "cleaning up!"
	- rm -f hello.o hello
