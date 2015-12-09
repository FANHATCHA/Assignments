#
# Build our phonebook system
#

CC		= gcc
CFLAGS	= -g
MEMOBJS	= phonebook-mem.o
SAVOBJS	= phonebook-todisk.o
GETOBJS	= phonebook-fromdisk.o

all : memdump memtodisk diskdump

memdump : $(MEMOBJS)
	$(CC) $(CFLAGS) -o memdump $(MEMOBJS)

memtodisk : $(SAVOBJS)
	$(CC) $(CFLAGS) -o memtodisk $(SAVOBJS)

diskdump : $(GETOBJS)
	$(CC) $(CFLAGS) -o diskdump $(GETOBJS)

clean :
	@ echo "cleaning up!"
	- rm -f memdump $(MEMOBJS)
	- rm -f memtodisk $(SAVOBJS)
	- rm -f diskdump $(GETOBJS)
