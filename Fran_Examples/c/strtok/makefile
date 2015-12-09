#
# A simple makefile
# $Id: makefile 225 2007-01-26 14:54:11Z andrew $
#

CC = gcc
CFLAGS = -g -Wall

tokenize : strtok.o
	$(CC) $(CFLAGS) -o tokenize strtok.o

clean :
	@ echo "cleaning up!"
	- rm -f strtok.o tokenize
