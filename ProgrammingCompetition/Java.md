**Input:**
 * Fastest known way to read a large number of space-separated integers from a line (was tested with up to 200000 integers on one lines). NOTE: This method of using a BufferedReader is faster than Scanner for other uses too.
```java
BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
StringTokenizer token  = new StringTokenizer(br.readLine());
for (int i = 0; i < n; i++)
    int n = Integer.parseInt(token.nextToken());
```

**Conversions:**

* Character → Integer
```java    
Character.getNumericValue(char c);
```
* Integer → Character 
```java    
Character c = i + ‘0’
```
* ArrayList → Set
```java 
Set<Foo> listName = new HashSet<Foo>(arrayListName);
```
* Set → ArrayList
```java 
ArrayList<Integer> arrayListName = new ArrayList<Integer>(mySet);
```  
* Base x → Base 10 where 2 <= x <= 36
```java  
int base10 = Integer.parseInt(strBaseX, x);

// Examples:
parseInt("0", 10) returns 0
parseInt("473", 10) returns 473
parseInt("+42", 10) returns 42
parseInt("-0", 10) returns 0
parseInt("-FF", 16) returns -255
parseInt("1100110", 2) returns 102
parseInt("2147483647", 10) returns 2147483647
parseInt("-2147483648", 10) returns -2147483648
parseInt("2147483648", 10) throws a NumberFormatException
parseInt("99", 8) throws a NumberFormatException
parseInt("Kona", 10) throws a NumberFormatException
parseInt("Kona", 27) returns 411787
```

**Comparator:**

``` java
// Orders Planet objects by ID
class PlanetIDOrder implements Comparator<Planet> {
    @Override public int compare(Planet p1, Planet p2) {
        Integer i1 = new Integer(p1.id);
        Integer i2 = new Integer(p2.id);
        return  i1.compareTo(i2);
    }
}
```

Usage:

``` java
Collections.sort( my_planets_arraylist, new PlanetIDOrder() );
```


