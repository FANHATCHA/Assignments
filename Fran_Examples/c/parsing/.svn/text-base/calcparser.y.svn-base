%{
#include <stdio.h>

#include "calcvariables.h"
#include "y.tab.h"

	/**
	 * declaration of external functions to get data
	 * and report the current line number
	 */
	int yylex(void);
	int getYYLineNumber(void);

	/**
	 * declaration of external function to report error messages
	 * that is defined in lexical analyser source (.l file)
	 */
	void yyerror(char *, ...);

%}

/**
 * Declare the value of a grammatical construct as a union
 * so that we can handle both numeric values and strings
 */
%union {
	double numericValue;
	char *variableName;
};

/**
 * Declare our tokens and give types (fields from the union
 * above) to those that will have a value
 */
%token EOL KEYWORD_SET
%token <numericValue> NUMBER
%token <variableName> VARIABLE

/**
 * Once you have added * and /, these lines will give the
 * appropriate operator precedence
 */
/*
%left '+' '-'
%left '*' '/'
*/


/**
 * for entities above the token lexical entity, assign a
 * type for each one that will have a value (in our case,
 * these are all the same
 */
%type <numericValue> expression variableAssignment calculationLine


%%

/**
 * The top-level entity -- at this level we don't have any
 * particular work to do, so we do not supply a control block
 */
program:
		  /* "nothing" is valid input (ie; empty file, or 'nothing yet') */
		| program calculationLine
        | program variableAssignment 
        ;

/**
 * one of entities we can build out of tokens that make up a
 * program.  Note that an EOL tells us that we have come to
 * the end of this entity (and therefore of the underlying
 * expression)
 */
calculationLine:
		  expression EOL {
		  		printf("= %lg\n", $1);
			}
		;

/**
 * another entity we can build out of tokens.  Here EOL
 * again tells us that the expression has come to an end.
 */
variableAssignment:
		  KEYWORD_SET VARIABLE '=' expression EOL
			{
				$$ = setVariableValue($2, $4);
				printf("[variable '%s' now %lg]\n", $2, $4);
				free($2);
			}
		;

/**
 * A mid-level entity that is recursively defined and can
 * be part of other entities.  Note that NUMBER already has
 * a value, so no work need be done for it
 */
expression:
		  NUMBER
		| '-' expression {
				$$ = -($2);
			}
		| expression '+' expression	{
				$$ = $1 + $3;
				printf(" . +: %lg + %lg = %lg\n", $1, $3, $$);
			}
		| expression '-' expression	{
				$$ = $1 - $3;
				printf(" . -: %lg - %lg = %lg\n", $1, $3, $$);
			}
		| '$' VARIABLE	{
				double dval = 0.0;
				if (getVariableValue(&dval, $2) < 0)
					yyerror("Unknown variable '%s'", $2);
				free($2);
				$$ = dval;
			}
        ;

%%


/**
 * Vestigial main -- we will read from stdin and take no arguments
 */
int main(void)
{
    yyparse();
    return 0;
}

