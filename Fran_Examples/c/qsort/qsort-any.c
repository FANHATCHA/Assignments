/**
 * This example is taken from
 *	 B.W. Kernighan & D.M. Ritchie (1988). /The C programming language/,
 *      second edition, Prentice Hall,  ISBN 0-131103-628, pp. 120.
 *
 * This example sorts a list of pointers to any data type; compare with
 * the example in the companion file qsort-int that sorts only integers
 * to see what benefits the pointer-to-function based comparator provides
 * here.
 */

/** swap:  interchange v[i] and v[j] */
static void
swap(void *v[], int i, int j)
{
	void *temp;

	temp = v[i];
	v[i] = v[j];
	v[j] = temp;
}


/** qsort: sort v[left]...v[right] into increasing order */
void
qsortany(void *v[], int left, int right,
		int (*comp)(void *, void *)
	)
{
	int i, last;

	/* do nothing if array contains fewer than two elements */
	if (left >= right)
		return;

	/* move partition element to v[0] */
	swap(v, left, (left+right)/2);
	last = left;

	/* partition */
	for (i = left+1; i <= right; i++)
		if ((*comp)(v[i], v[left]) < 0)	/* the big change! */
			swap(v, ++last, i);

	/* restore partition element */
	swap(v, left, last);
	qsortany(v, left, last-1, comp);
	qsortany(v, last+1, right, comp);
}

