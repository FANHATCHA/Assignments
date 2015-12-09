
# Python Examples

This directory contains several Python examples.  For
additional notes and resources, please see the main
Python resource website
	http://www.python.org

These examples are primarily constructed for Python2
(recently tested under version 2.7).  The upcoming
Python3 is seeing slow adoption, so Python2 will be
with us for some time.

## Style

Note that these scripts have been indented using tabs.
This has been done as according to the style guide
	https://www.python.org/dev/peps/pep-0008/
consistency is best (and I certainly agree).  As many
students may be using an editor that can only include
tabs when the tab button is pressed, this seems a good
way to start.

### Converting to spaces only

You can easily convert an example to use spaces only,
with a four-space-per-tab conversion, by using the
program expand:
	expand -t4 celsius > celsius.tmp
	mv celsius.tmp celsius


## Several python scripts -- roughly in complexity order:

### Simple examples:
	bottlesOfBeer and bottlesOfBeerWithNoComments

	celsius
	fahrenheit
	fahrenheitparse
	fahrenheitparse-forloop

	printArgs
	printArgsMainFunction
	printLines

	arrayloop

	simpledictionary
	dictionary

### programs
	calcVariance

### inheritance and objects
	inheritance.py
	inheritanceInstance.py

### library use
	xmlParse
	htmlParse

### embedding in the shell
	inline
	inline2
	simple-parser

### windows and buttons
	helloTk
	helloTkButton

### a skeleton for a new python program
	python-skeleton
