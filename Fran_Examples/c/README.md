
This directory contains several C examples.  They have various degrees
of complexity.  Below is a suggested ordering to read them in.


## Basics:

hello
	* contains a very simple "hello world" program.

kat
	* a program that functions much like cat(1), written with file descriptors

arguments
	* print out all of the arguments on the command line

simplearray
	* a program to use an array to represent data, store it on disk

strlen
	* calculate the length of a string; accompanying assembler is included
		in this directory also

linking
	* a simple example with two files to demonstrate linking issues



## Data manipulation:

records
	* a simple record-based data storage utility

float
	* explores the IEEE float format

bitstring
	* an example of single-bit operations, and storage of a bitstring to a
	  file

pointers
	* an exploration of how pointers allow us to see what is in memory,
		and at what location

linked-list
	* an example linked list program

strtok
	* a demonstration of using strtok() for parsing

qsort
	* from the K+R text; shows function pointers



## Shell interaction:

exitwith
	* a program that exits with the indicated (integer argument) value

printenv
	* print out all of the environment variables from the parent shell

ttyinput
	* fill up a data record with data from standard input; detect if
		we are attached to a tty or a pipe to determine if prompts
		are to be used



## Advanced:

fsize
	* determine the size of files in Windows or POSIX/UNIX

forkpipe
	* create a child running more, and pipe data to it

testpipe
	* create a second process, and use a pipe to communicate with it

globbing
	* do wildcard expansion of file names

dl
	* dynamic library loading from C

