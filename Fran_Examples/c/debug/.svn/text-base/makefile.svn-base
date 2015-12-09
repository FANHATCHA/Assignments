#
# A makefile to build all of the examples in this directory:
# - working.c
# - nullreference.c
# - abw.c
# - badalloc.c
#

CC		= gcc
CFLAGS	= -g -Wall
LIBS	= -lefence

all : working nullreference abw badalloc badindex

working : working.o
	$(CC) $(CFLAGS) -o working working.o $(LIBS)

nullreference : nullreference.o
	$(CC) $(CFLAGS) -o nullreference nullreference.o $(LIBS)

abw : abw.o
	$(CC) $(CFLAGS) -o abw abw.o $(LIBS)

badalloc : badalloc.o
	$(CC) $(CFLAGS) -o badalloc badalloc.o $(LIBS)

badindex : badindex.o
	$(CC) $(CFLAGS) -o badindex badindex.o $(LIBS)

clean :
	@ echo "cleaning up!"
	- rm -f working.o working
	- rm -f nullreference.o nullreference
	- rm -f abw.o abw
	- rm -f badalloc.o badalloc
	- rm -f badindex.o badindex

