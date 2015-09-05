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

 **Number Series:**
 
* Fibonacci:
 
    1,1,2,3,5,8,13,21,34,55,89…
 
* Catalan:
 
    1, 1, 2, 5, 14, 42, 132, 429, 1430, 4862…

![Catalan Series Formula](/ProgrammingCompetition/Images/CatalanSeries.png)
     
* Lazy Caterer:

Formally also known as the central polygonal numbers, it describes the maximum number of pieces (or bounded/unbounded regions) of a circle (a pancake or pizza is usually used to describe the situation) that can be made with a given number of straight cuts. For example, three cuts across a pancake will produce six pieces if the cuts all meet at a common point, but seven if they do not.

![Lazy Caterer Series Formula](/ProgrammingCompetition/Images/LazyCatererSeries.png)

* Trianglar:

    0, 1, 3, 6, 10, 15, 21, 28, 36, 45, 55...

![Triangular Series Formula](/ProgrammingCompetition/Images/TriangleNumberSeries.png)

![Triangular Series Visualization](/ProgrammingCompetition/Images/TriangleNumberSeriesVisualization.png)

* Hexagonal:

    1, 6, 15, 28, 45, 66, 91, 120, 153, 190, 231...

![Hexagonal Series Formula](/ProgrammingCompetition/Images/HexagonalNumberSeries.png)

![Hexagonal Series Visualization](/ProgrammingCompetition/Images/HexagonalNumberSeriesVisualization.png)
