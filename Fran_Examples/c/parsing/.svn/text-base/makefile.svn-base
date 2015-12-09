# 
# Sample parser header file calling flex and bison as replacements
# for lex and yacc
# 

LEX		= flex
YACC	= bison -y
YFLAGS	= -d
OBJS	 = calcparser.o calctokenizer.o calcvariables.o

calc: $(OBJS)
	$(CC) -o calc $(OBJS)

# make sure that make realizes the the tokenizer need to be made
# before the parser, because of the generated header file
y.tab.h : calctokenizer.c
calcparser.c : y.tab.h

clean :
	- rm -f calc
	- rm -f $(OBJS)
	- rm -f y.tab.h
	- rm -f calcparser.c
	- rm -f calctokenizer.c
	- rm -f testdata/*.raw-output
	- rm -f testdata/*.cmp-output
