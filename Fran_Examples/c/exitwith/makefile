#
# A simple makefile
# $Id: makefile 225 2007-01-26 14:54:11Z andrew $
#

CC = gcc
CFLAGS = -g -Wall

exitwith : exit-with-indicated-status.o
	$(CC) $(CFLAGS) -o exitwith exit-with-indicated-status.o

clean :
	@ echo "cleaning up!"
	- rm -f exit-with-indicated-status.o exitwith
