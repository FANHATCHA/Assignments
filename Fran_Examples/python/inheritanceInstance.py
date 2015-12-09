#!/usr/bin/env python

# Demonstrate the inheritance of objects in Python
#
# $Id: inheritance.py 246 2007-01-31 14:38:31Z andrew $


# this is a simple class
class AParent(object):
	
	# the data member
	value = 0

	# the __init__() function is used at constructor
	# time to populate any data values, etc, through
	# reference to "self"
	def __init__(self, arg):
		self.name = arg + " in A"
		print "AParent -- name : ", self.name

	def set(self, newValue):
		print "in AParent.set(", newValue, ")"
		self.value = newValue


	def get(self):
		print "in AParent.get()"
		return self.value

# another trivial class, used to show multiple inheritance
class BParent(object):

	value = (-1)

	def __init__(self):
		print "BParent"

	def set(self, newValue):
		print "in BParent.set(", newValue, ")"
		self.value = newValue

	def get(self):
		print "in BParent.get()"
		return self.value

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

	def set(self, aValue, bValue):
		CChild.set(self, aValue)
		DChild.set(self, bValue)

a = AParent("Antonio")
b = BParent()
e = EGrandchild("Eduardo")

print "A value : ", a.get()
print "B value : ", b.get()
print "E value : ", e.get()

a.set(12)
b.set(456)
e.set(42, 56)

print "B", b.get()
print "E", e.get()

