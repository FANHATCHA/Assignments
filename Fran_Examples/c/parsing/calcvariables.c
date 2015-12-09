#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include "calcvariables.h"

/**
 * define a linked-list structure which we can use to
 * manage our list of variables
 */
struct variable {
	struct variable *next;
	double value;
	char *name;
} *sVariableList = NULL;

int
getVariableValue(double *valueP, char *variableName)
{
	struct variable *curVariable;

	curVariable = sVariableList;
	while (curVariable != NULL && strcmp(curVariable->name, variableName) != 0)
	{
		curVariable = curVariable->next;
	}

	/** if search turned up nothing, return 0 */
	if (curVariable == NULL)
		return 0;

	/** set the variable value pointer, and return 1 */
	*valueP = curVariable->value;
	return 1;
}

int
setVariableValue(char *variableName, double value)
{
	struct variable *curVariable;

	/** first, see if we can find a variable with this name */
	curVariable = sVariableList;
	while (curVariable != NULL && strcmp(curVariable->name, variableName) != 0)
	{
		curVariable = curVariable->next;
	}

	/** if search turned up nothing, set up a new variable */
	if (curVariable == NULL)
	{
		/** we prepend, as this will handle the no-list condition easily */
		curVariable = (struct variable *) malloc(sizeof(struct variable));
		curVariable->name = strdup(variableName);
		curVariable->next = sVariableList;
		sVariableList = curVariable;
	}

	curVariable->value = value;
	return value;
}

