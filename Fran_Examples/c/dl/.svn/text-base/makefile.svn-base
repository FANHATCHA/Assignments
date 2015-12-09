#
# build and clean everything
#

DIRS = loader tools toys math

build : dummy
	for d in $(DIRS) ; \
	do \
		if [ -d $${d} ] ; then \
			( cd $${d} ; make ) ; \
		fi ; \
	done

clean : dummy
	for d in $(DIRS) ; \
	do \
		if [ -d $${d} ] ; then \
			( cd $${d} ; make clean ) ; \
		fi ; \
	done

dummy :
