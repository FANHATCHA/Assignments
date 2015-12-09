#
# Build "eval"
#

CC = gcc
CFLAGS = -g -Wall

eval : eval.o
	$(CC) $(CFLAGS) -o eval eval.o

clean :
	@ echo "cleaning up!"
	- rm -f eval.o eval
