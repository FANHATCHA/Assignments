#
# Build the float example
#

CC = gcc48
CFLAGS = -g

all : floatformat

floatformat : floatformat.o
	$(CC) $(CFLAGS) -o floatformat floatformat.o

clean :
	@ echo "cleaning up!"
	- rm -f floatformat.o floatformat
