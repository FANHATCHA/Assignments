
This directory contains two implementations of some simple code to write
and read records with respect to a file.

In both implementations the actual record is the same -- the difference is
that in the *FD utilities, "low level I/O" based on read(2) and write(2)
are used.  In the *FP utilities "buffered I/O" based on the functions
fread(3) and fwrite(3) are used.

In a given program, if you wish to mix calls to block based reads/writes and
formatted reads/writes, you are better to use a FILE * and the "buffered
I/O" throughout.

If you simply need block based access, the "low level" read/write will
suffice, and can be used directly with a file descriptor.

If you have a file descriptor, and you wish to switch to "buffered I/O",
you can generate a FILE * (which will contain the file descriptor) by
using fdopen(3).  I would not recommend then trying to switch back to
"low level I/O" for that file descriptor.


To test out the code in this directory, it may be instructive to do
the following:
* use make(1) to create the programs:

	$ make

* use the "low level I/O" version to create a data file:

	$ ./writeRecordsFD testdatafile-lowlevel.dat


* use the "low level I/O" version to dump the data file

	$ ./dumpRecordsFD testdatafile-lowlevel.dat


* view the file directly using a tool like hexdump(1) (the -C "canonical"
  option to hexdump(1) is very useful as it prints out the values found
  in both hexadecimal and as ASCII

	$ hexdump -C testdatafile-lowlevel.dat


* use the "buffered I/O" version to dump the "low level" data file

	$ ./dumpRecordsFP testdatafile-lowlevel.dat


* use the "buffered I/O" version to create a data file:

	$ ./writeRecordsFP testdatafile-buffered.dat


* compare the two data files -- they should be exactly the same (both
	cmp and diff indicate "no difference" by saying nothing):

	$ diff testdatafile-lowlevel.dat testdatafile-buffered.dat
	$ cmp testdatafile-lowlevel.dat testdatafile-buffered.dat
