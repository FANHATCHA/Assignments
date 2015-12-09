# 
# Sample parser header file calling flex and bison as replacements
# for lex and yacc
# 

OBJS	= commandlineparser.o commandline.o 

EXE		= commandline

$(EXE) : $(OBJS)
	$(CC) $(CFLAGS) -o $(EXE) $(OBJS)

clean :
	- rm -f $(EXE)
	- rm -f $(OBJS)
