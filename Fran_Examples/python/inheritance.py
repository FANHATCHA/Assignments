#!/usr/bin/env python

# Demonstrate the inheritance of objects in Python
#
# $Id: inheritance.py 246 2007-01-31 14:38:31Z andrew $


# this is a simple class
class AParent(object):

	# the __init__() function is used at constructor
	# time to populate any data values, etc, through
	# reference to "self"
	def __init__(self, arg):
		self.name = arg + " in A"
		print "AParent -- name : ", self.name


# another trivial class, used to show multiple inheritance
class BParent(object):
	def __init__(self):
		print "BParent"


class CChild(AParent):
	def __init__(self, arg):
		print "CChild","arg=",arg
		AParent.__init__(self, arg)
		print "CChild -- name : ", self.name


class DChild(BParent):
	def __init__(self, arg):
		print "DChild", "arg=",arg
		BParent.__init__(self)


class EGrandchild(CChild,DChild):
	def __init__(self, arg):
		print "EGrandchild", "arg=",arg 
		CChild.__init__(self, arg)
		DChild.__init__(self, arg)
		print "EGrandchild -- name : ", self.name


AParent("Antonio")

print "-----"
EGrandchild("Bassanio")

