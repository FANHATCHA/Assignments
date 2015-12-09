#
# Build up the whole thing, SWIG and all
#
# $Id: makefile 365 2007-03-09 18:50:45Z andrew $
#

CC			= gcc
INCLUDES	= -I/usr/include/python2.4
CFLAGS		= -g -Wall $(INCLUDES)
LDFLAGS		= -static


OBJS		= simple.o simple_wrap.o
TARGLIB		= _simple.so



$(TARGLIB) : $(OBJS)
	$(CC) -shared $(OBJS) -o $(TARGLIB)
	chcon -t textrel_shlib_t $(TARGLIB)


simple_wrap.c : simple.i
	swig $(INCLUDES) -python simple.i

test : $(TARGLIB)
	python ./testit.py

clean :
	@ echo "cleaning up!"
	- rm -f $(TARGLIB)
	- rm -f $(OBJS)
	- rm -f simple.py
	- rm -f simple.pyc
	- rm -f simple_wrap.c
	- rm -f simple.so

