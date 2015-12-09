
CFLAGS	= 	-g

all : fseek createFile

fseek : fseek.o
	$(CC) $(CFLAGS) -o fseek fseek.o

createFile : createFile.o
	$(CC) $(CFLAGS) -o createFile createFile.o -lm

clean :
	- rm -f fseek fseek.o
	- rm -f createFile createFile.o

