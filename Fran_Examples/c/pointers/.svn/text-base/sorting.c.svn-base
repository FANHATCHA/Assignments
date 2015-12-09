/*
 * Dump out memory, to show memory mapping
 */
#include <stdio.h>
#include <stdlib.h>
#include <string.h>

typedef struct SC_Character {
	char firstname[32];
	char lastname[32];
	char appelation[6];
} SC_Character;

#define		NUM_CHARACTERS	13

static SC_Character sCharacterList[NUM_CHARACTERS] = {
		{	"Hiroaki (Hiro)",	"Protagonist",	""	},
		{	"Yours (Y.T.)", 	"Truly",	""	},
		{	"Juanita",		"Marquez",	""	},
		{	"Da5id",		"Meier",	""	},
		{	"Emanuel",		 "Lagos",	"Dr."	},
		{	"\"Uncle\"",		"Enzo",		""	},
		{	"Librarian",		"",		""	},
		{	"",			"Lee",		"Mr."	},
		{	"Ng",			"",		"Mr."	},
		{	"Fido",			"",		 ""	},
		{	"L. Bob",		 "Rife",	""	},
		{	"Dmitri (Raven)",	"Ravinoff",	""	},
		{	"Wayne",		"Bedford",	"Rev."	}
	};

int
compLastWStruct(const void *va, const void*vb)
{
	SC_Character *a;
	SC_Character *b;
	int status;

	a = (SC_Character *) va;
	b = (SC_Character *) vb;

	status = strcmp(a->lastname, b->lastname);
	if (status != 0)
		return status;

	status = strcmp(a->firstname, b->firstname);
	return status;
}


int
compAppWStructPtr(const void *va, const void*vb)
{
	SC_Character **aPtr;
	SC_Character **bPtr;
	SC_Character *a;
	SC_Character *b;
	int status;

	aPtr = (SC_Character **) va;
	bPtr = (SC_Character **) vb;
	a = *aPtr;
	b = *bPtr;

	status = strcmp(a->appelation, b->appelation);
	if (status != 0)
		return status;

	return compLastWStruct(va, vb);
}

void
printArray(SC_Character characters[], int nCharacters)
{
	int i;

	for (i = 0; i < nCharacters; i++) {
		printf("character[%2d] : %s, %s; %s\n", 
			i,
			characters[i].lastname,
			characters[i].firstname,
			characters[i].appelation);
	}
}

void
printPointers(SC_Character **characters, int nCharacters)
{
	int i;

	for (i = 0; i < nCharacters; i++) {
		printf("%2d : ", i);
		if (characters[i]->appelation[0] != 0)
			printf("%s ", characters[i]->appelation);
		if (characters[i]->firstname[0] != 0)
			printf("%s ", characters[i]->firstname);
		if (characters[i]->lastname[0] != 0)
			printf("%s ", characters[i]->lastname);
		printf("\n");
	}
}

int
main()
{
	SC_Character **ptrArray;
	int i;

	ptrArray = (SC_Character **)
				malloc(sizeof(SC_Character *) * NUM_CHARACTERS);
	for (i = 0; i < NUM_CHARACTERS; i++)
	{
		ptrArray[i] = &sCharacterList[i];
	}

	printf("Original ordering:\n");
	printArray(sCharacterList, NUM_CHARACTERS);

	qsort(sCharacterList, NUM_CHARACTERS, sizeof(SC_Character),
				compLastWStruct);
	printf("After Sort:\n");
	printArray(sCharacterList, NUM_CHARACTERS);


	qsort(ptrArray, NUM_CHARACTERS, sizeof(SC_Character *),
				compAppWStructPtr);
	printf("After Ptr Sort:\n");
	printPointers(ptrArray, NUM_CHARACTERS);

	printf("And finally the arrray again:\n");
	printArray(sCharacterList, NUM_CHARACTERS);

	return 0;
}


