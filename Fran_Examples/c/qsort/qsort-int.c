/**
 * This example is taken from
 *	 B.W. Kernighan & D.M. Ritchie (1988). /The C programming language/,
 *      second edition, Prentice Hall,  ISBN 0-131103-628, pp. 87.
 *
 * This example sorts only integers; the example in the companion
 * file qsort-any sorts any item by means of allowing the calling
 * code to supply the swap routine
 */

/** swap:  interchange v[i] and v[j] */
static void
swap(int v[], int i, int j)
{
	int temp;

	temp = v[i];
	v[i] = v[j];
	v[j] = temp;
}

/** qsort: sort v[left]...v[right] into increasing order */
void
qsortint(int v[], int left, int right)
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
		if (v[i] < v[left])
			swap(v, ++last, i);

	/* restore partition element */
	swap(v, left, last);
	qsortint(v, left, last-1);
	qsortint(v, last+1, right);
}

