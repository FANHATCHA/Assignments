**Java's version of C++'s next_permutation:**

NOTE: This method accounts for duplicates (which is wherever compareTo() returns 0). By modifying the two lines indicated below, this method can also be used to get the previous permutation.

```java
static <T extends Comparable<? super T>> T[] nextPermutation(T[] c) {
  int first = getFirst(c);
  if (first == -1) return null;
  int toSwap = c.length - 1;
  while (c[first].compareTo(c[toSwap]) >= 0) // Change to <= for descending
    --toSwap;
  swap(c, first++, toSwap);
  toSwap = c.length - 1;
  while (first < toSwap)
  	swap(c, first++, toSwap--);
  return c;
}

static <T extends Comparable<? super T>> int getFirst(T[] c) {
  for (int i = c.length - 2; i >= 0; --i)
    if (c[i].compareTo(c[i + 1]) < 0) // Change to > for descending
      return i;
  return -1;
}

static <T extends Comparable<? super T>> void swap(T[] c, int i, int j) {
  T tmp = c[i];
  c[i] = c[j];
  c[j] = tmp;
}
```

**Find a particular permutation efficiently:**

Given a string, find a specified permutation based on its index (sorted lexographically), without generating all of the permutations. The complexity of this algorithm is O(nm), where is the length of the string, and m is the number of possible characters. The following implementation was designed for the characters A-Z, but it could clearly be modified for other character sets, or even other types of objects. NOTE: This algorithm ensures that each permutation is only counted once (so a string like "ALL" won't mess it up).

```java
static long[] factorial;        

// Finds the specified perm (1-based index)
static String findPerm(String str, long permIndex) {
    // Pre-compute factorial values
    factorial = new long[str.length() + 1];
    factorial[0] = 1;
    for (int i = 1; i < factorial.length; i++)
        factorial[i] = factorial[i-1]*i;
    // Count the occurrences of each character
    char[] arr = str.toCharArray();
    int[] count = new int[26];
    for (char ch : arr)
        count[ch - 'A']++;
    return findPermHelper(count, str.length(), permIndex);
}

static String findPermHelper(int[] count, int nLeft, long permIndex) {
	if (nLeft == 0) return "";
	// Try placing each character at the front, recurse once we've found the right one
	for (int i = 0; i < count.length; i++) {
		if (count[i] == 0) continue;
		count[i]--;
		long nCombinations = factorial[nLeft-1]/accountForRepeatedCharacters(count);
		if (nCombinations >= permIndex)
			return (char) ('A' + i) + findPermHelper(count, nLeft - 1, permIndex);
		count[i]++;
		permIndex -= nCombinations;
	}
	return null;
}

static long accountForRepeatedCharacters(int[] count) {
	long n = 1;
	for (int i : count)n *= factorial[i];
	return n;
}
```
