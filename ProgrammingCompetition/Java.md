**Command line flags:**
 - Set maximum Java heap size: `-Xmx<size>` (**For example:** `-Xmx1024k`, `-Xmx512m`, `-Xmx8g`)
 - Set maxmimum Java thread stack size: `-Xss<size>`

**Input:**
 * Fastest known way to read a large number of space-separated integers from a line (was tested with up to 200000 integers on one lines). NOTE: This method of using a BufferedReader is faster than Scanner for other uses too.
```java
BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
StringTokenizer token  = new StringTokenizer(br.readLine());
for (int i = 0; i < n; i++)
    int n = Integer.parseInt(token.nextToken());
```

**Conversions:**

* Character to Integer: `int val = Character.getNumericValue(char c);`
* Integer to Character: `Character c = i + '0';`
* ArrayList to Set: `Set<Foo> listName = new HashSet<Foo>(arrayListName);`
* Set to ArrayList: `ArrayList<Integer> arrayListName = new ArrayList<Integer>(mySet);`
* Base x to Base 10 (where 2 <= x <= 36): `int base10 = Integer.parseInt(strBaseX, x);`

**Comparator:**
``` java
// Example: Sort YourObjects by ID
class YourComparator implements Comparator<YourObject> {
    @Override public int compare(YourObject a, YourObject b) {
        return (new Integer(a.id)).compareTo(b.id);
    }
}
```
