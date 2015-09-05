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
