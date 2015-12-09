
PYVER	= python2.4

CC		= gcc
CFLAGS	= -g -Wall -I/usr/include/$(PYVER)
LDFLAGS	= -L/usr/lib/$(PYVER)
LDLIBS	= -l$(PYVER) -lm

all : callPythonString callPythonArgs callPythonFixedExample

callPythonString : callPythonString.o
	$(CC) $(CFLAGS) $(LDFLAGS) -o callPythonString callPythonString.o $(LDLIBS)

callPythonArgs : callPythonArgs.o
	$(CC) $(CFLAGS) $(LDFLAGS) -o callPythonArgs callPythonArgs.o $(LDLIBS)

callPythonFixedExample : callPythonFixedExample.o
	$(CC) $(CFLAGS) $(LDFLAGS) -o callPythonFixedExample callPythonFixedExample.o $(LDLIBS)

clean :
	- rm -f callPythonString.o callPythonString
	- rm -f callPythonArgs.o callPythonArgs
	- rm -f callPythonFixedExample.o callPythonFixedExample
