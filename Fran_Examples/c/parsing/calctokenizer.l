%{
#include <stdlib.h>
#include <string.h>
#include <stdarg.h>	/* for vfprintf */

#include "y.tab.h"

	/**
	 * forward declaration for our own error reporting
	 */
	void yyerror(char *, ...);
%}

%option yylineno

%%

[0-9]+		{
				yylval.numericValue = atoi(yytext);
				return NUMBER;
			}

set			{
				return KEYWORD_SET;
			}

[A-Za-z][A-Za-z_]*		{
				yylval.variableName = strdup(yytext);
				return VARIABLE;
			}

[-+=$]		{	/* return the operator character itself */
				return *yytext;	
			}

[\n]		{	/* handle newlines separately, as the mean something to us */
				return EOL;	
			}

[ \v\f\r\t]		;	/* skip any other whitespace */

.			yyerror("Unknown character '%c'", *yytext);

%%

/**
 * This function should switch the input to the "next" file,
 * returning 0 if there is another file, and returning 1
 * when there are no more input files.  We simply return
 * zero here to indicate that stdin (the default input) is
 * the only input source
 */
int yywrap(void)
{
	return 1;
}

/**
 * return the line number we manage based on the option yylineno
 */
int getYYLineNumber(void)
{
	return yylineno;
}

/**
 * provide a new version of the standard yyerror function that will
 * take a variable number of arguments (in the manner of printf) and
 * will also indicate the line and token at which the error occurred
 */
void yyerror(char *fmt, ...)
{
	va_list vargs;
	va_start(vargs, fmt);
	(void) vprintf(fmt, vargs);
	va_end(vargs);
	printf(" -- unexpected '%s' at line %d\n", yytext, getYYLineNumber());
}

