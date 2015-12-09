#
# Build "kat"
#

CC = gcc
CFLAGS = -g

all : kat fkat sloppykat

kat : kat.o
	$(CC) $(CFLAGS) -o kat kat.o

sloppykat : sloppykat.o
	$(CC) $(CFLAGS) -o sloppykat sloppykat.o

fkat : fkat.o
	$(CC) $(CFLAGS) -o fkat fkat.o

clean :
	@ echo "cleaning up!"
	- rm -f kat.o kat
	- rm -f fkat.o fkat
	- rm -f sloppykat.o sloppykat
