#include <stdio.h>
#include <strings.h>
#include "LLNode.h"



/* forward declarations */
static void printMyData(LLNode *p, void *arg);


/*
 * test data, declared statically so that we can
 * access it in the function below, but not outside
 * this file
 */
static struct {
	int ordering;
	char *tag;
	int value;
} sData[] = {
		{	1, "Not Quite Pi",	3	},
		{	0, "The Answer",	42	},
		{	4, "A Small Postitive Integer",	1	},
		{	7, "Positive Ten",	10	},
		{	6, "Negative Ten",	-10	},
		{	2, "One Hundred",	100	},
		{	3, "One Million",	1000000	},
		{	-1, NULL }
	};


/*
 * doQueries: lookup various values in the list, some we expect
 * to find, and some we do not
 */
void
doQueries(LLNode *linkedList)
{
	char *key;
	LLNode *node;


	key = "The Answer";
	node = llLookupKey(linkedList, key);
	if (node != NULL)
	{
		printf("Found '%s', value=%d\n", key, node->data.value);
	} else {
		printf("Did not find '%s'\n", key);
	}



	key = "The Question";
	node = llLookupKey(linkedList, key);
	if (node != NULL)
	{
		printf("Found '%s', value=%d\n", key, node->data.value);
	} else {
		printf("Did not find '%s'\n", key);
	}


	printf("\n");
}

/*
 * load the data in the sData structure into a list,
 * appending each element
 */
LLNode *
loadExampleData()
{
	LLNode *listHead = NULL;
	int i;

	for (i = 0; sData[i].tag != NULL; i++) {
		MyData data;

		data.key = sData[i].tag;
		data.index = sData[i].ordering;
		data.value = sData[i].value;

		/** we could also "prepend" instead */
		listHead = llAppend(listHead, llNewNode(&data));
	}

	return listHead;
}

/*
 * load the data in the sData structure into a list, in order
 */
LLNode *
loadOrderedExampleData()
{
	LLNode *listHead = NULL;
	int i;

	for (i = 0; sData[i].tag != NULL; i++) {
		MyData data;

		data.key = sData[i].tag;
		data.index = sData[i].ordering;
		data.value = sData[i].value;

		listHead = llInsertByIndexOrder(listHead, llNewNode(&data));
	}

	return listHead;
}

int main()
{
	LLNode *aList;

	/** load the data into the list */
	aList = loadExampleData();

	/* do a couple of lookups */
	doQueries(aList);

	printf("\n\nDumping unordered list\n");
	llApplyFn(aList, printMyData, "unordered");

	/** delete our data */
	llFree(aList);


	/* now repeat, but with an ordered list */
	/** load the data into the list */
	aList = loadOrderedExampleData();
	doQueries(aList);
	printf("\n\nDumping ORDERED list\n");
	llApplyFn(aList, printMyData, "in-order");
	llFree(aList);

	return 0;
}

/*
 * used in llApplyFn to print the list values.  The
 * argument here is assumed to point at a (char *) tag
 */
static void printMyData(LLNode *p, void *arg)
{
	printf("  . %2d : '%16s' %d (%s)\n",
			p->data.index, p->data.key, p->data.value,
			(char *) arg);
}

