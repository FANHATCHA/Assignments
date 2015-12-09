
CC = gcc
CFLAGS = -g -Wall

# target to make the executable
printenv : printenv.o
	$(CC) $(CFLAGS) -o printenv printenv.o

clean :
	@ echo "cleaning up!"
	- rm -f printenv.o printenv
