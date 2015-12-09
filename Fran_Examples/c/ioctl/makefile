#
#

CC		= gcc
CFLAGS	= -g -Wall

EXE_IOCTL	= testioctl
EXE_POSIX	= testposix
EXE_WINDOW	= testwinsize
EXE_ISTTY	= istty

OBJ_IOCTL	= tty-ioctl.o
OBJ_POSIX	= tty-posix.o
OBJ_WINDOW	= winsize-ioctl.o
OBJ_ISTTY	= istty.o

all: $(EXE_WINDOW) $(EXE_ISTTY) $(EXE_POSIX) $(EXE_IOCTL)

$(EXE_IOCTL) : $(OBJ_IOCTL)
	$(CC) $(CFLAGS) -o $(EXE_IOCTL) $(OBJ_IOCTL)

$(EXE_POSIX) : $(OBJ_POSIX)
	$(CC) $(CFLAGS) -o $(EXE_POSIX) $(OBJ_POSIX)

$(EXE_WINDOW) : $(OBJ_WINDOW)
	$(CC) $(CFLAGS) -o $(EXE_WINDOW) $(OBJ_WINDOW) 

$(EXE_ISTTY) : $(OBJ_ISTTY)
	$(CC) $(CFLAGS) -o $(EXE_ISTTY) $(OBJ_ISTTY) 


clean :
	@ echo "cleaning up!"
	- rm -f $(EXE_IOCTL) $(OBJ_IOCTL)
	- rm -f $(EXE_POSIX) $(OBJ_POSIX)
	- rm -f $(EXE_WINDOW) $(OBJ_WINDOW)
	- rm -f $(EXE_ISTTY) $(OBJ_ISTTY)
