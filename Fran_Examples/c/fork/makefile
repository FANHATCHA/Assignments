
CC		= gcc
CFLAGS	= -g

EXE1		= singlesimple
EXE2		= singleexec
EXE3		= multifork

all : $(EXE1) $(EXE2) $(EXE3)


$(EXE1) : single.o
	$(CC) -o $(EXE1) single.o

$(EXE2) : single-exec.o
	$(CC) -o $(EXE2) single-exec.o

$(EXE3) : multi.o
	$(CC) -o $(EXE3) multi.o

clean :
	- rm -f $(EXE1) $(EXE2) $(EXE3)
	- rm -f *.o

