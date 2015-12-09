#
# Build up the strings examples
# Note that we have several targets in here; "make all" will build all of them
#

CC = gcc
CFLAGS = -g -Wall

all : \
		simple \
		allocate \
		typesAndSizes \
		mmap \
		functionargs \
		sorting \
		stringPtr \
		strings \
		stringsOutOfBounds \
		stringsOutOfBoundsEfence

strings : strings.o
	$(CC) $(CFLAGS) -o strings strings.o

allocate : allocate.o
	$(CC) $(CFLAGS) -o allocate allocate.o

typesAndSizes : typesAndSizes.o
	$(CC) $(CFLAGS) -o typesAndSizes typesAndSizes.o

stringsOutOfBounds : stringsOutOfBounds.o
	$(CC) $(CFLAGS) -o stringsOutOfBounds stringsOutOfBounds.o

stringsOutOfBoundsEfence : stringsOutOfBounds.o
	$(CC) $(CFLAGS) -o stringsOutOfBoundsEfence stringsOutOfBounds.o

simple : simple.o
	$(CC) $(CFLAGS) -o simple simple.o

stringPtr : stringPtr.o
	$(CC) $(CFLAGS) -o stringPtr stringPtr.o

functionargs : functionargs.o
	$(CC) $(CFLAGS) -o functionargs functionargs.o

mmap : mmap.o
	$(CC) $(CFLAGS) -o mmap mmap.o

sorting : sorting.o
	$(CC) $(CFLAGS) -o sorting sorting.o

clean :
	@ echo "cleaning up!"
	- rm -f strings.o strings
	- rm -f stringsOutOfBounds.o stringsOutOfBounds stringsOutOfBoundsEfence
	- rm -f stringPtr.o stringPtr
	- rm -f functionargs.o functionargs
	- rm -f mmap.o mmap
	- rm -f sorting.o sorting
	- rm -f simple.o simple
	- rm -f allocate.o allocate
	- rm -f typesAndSizes.o typesAndSizes

