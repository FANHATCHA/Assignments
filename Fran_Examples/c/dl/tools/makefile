#
# Makefile to build the library only
#

CC		= gcc
CFLAGS	= -g -Wall -fpic -I../include

OBJS	= tool-apple.o tool-banana.o

TARGET	= ../libs/libsomeTools.so


$(TARGET) : $(OBJS)
	gcc -shared -fpic -o $(TARGET) $(OBJS)

clean :
	- rm -f $(OBJS)
	- rm -f $(TARGET)

