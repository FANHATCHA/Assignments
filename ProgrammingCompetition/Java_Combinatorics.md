**Java's version of C++'s next_permutation:**

NOTE: This method accounts for duplicates (which is wherever compareTo() returns 0). By modifying the two lines indicated below, this method can also be used to get the previous permutation.

```java
private static <T extends Comparable<? super T>> T[] nextPermutation(final T[] c) {

  int first = getFirst(c);
  if (first == -1)
    return null;
  
  int toSwap = c.length - 1;
  while (c[first].compareTo(c[toSwap]) >= 0) // Change to <= for descending
    --toSwap;

  swap(c, first++, toSwap);

  toSwap = c.length - 1;
  while (first < toSwap)
    swap(c, first++, toSwap--);

  return c;
}

private static <T extends Comparable<? super T>> int getFirst(final T[] c) {
  for (int i = c.length - 2; i >= 0; --i)
    if (c[i].compareTo(c[i + 1]) < 0) // Change to > for descending
      return i;
  return -1;
}

private static <T extends Comparable<? super T>> void swap(final T[] c, final int i, final int j) {
  final T tmp = c[i];
  c[i] = c[j];
  c[j] = tmp;
}
```
Sample Usage:

```java
Integer[] c = new Integer[] {2, -5, 3, 3};
System.out.println(Arrays.toString(c));
do {
  System.out.println(Arrays.toString(c));
} while ((c = nextPermutation(c)) != null);
```

Output: 
```
[2, -5, 3, 3]
[2, 3, -5, 3]
[2, 3, 3, -5]
[3, -5, 2, 3]
[3, -5, 3, 2]
[3, 2, -5, 3]
[3, 2, 3, -5]
[3, 3, -5, 2]
[3, 3, 2, -5]
```
