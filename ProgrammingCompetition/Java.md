**~/.vimrc**

    set expandtab
    set shiftwidth=2
    set tabstop=2
    set number
     
    command W w
    command Q q
    command WQ wq
    command Wq wq


**CONVERSIONS**

Character → Integer
    
    Character.getNumericValue(char c);
    
Integer → Character 
    
    Character c = i + ‘0’
    
ArrayList → Set
    
    Set<Foo> listName = new HashSet<Foo>(arrayListName);

Set → ArrayList
    
    ArrayList<Integer> arrayListName = new ArrayList<Integer>(mySet);
  
Base x → Base 10 where 2 <= x <= 36
  
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
  
  **MATH**
  
Greatest Common Factor:
    
    int gcf(int a, int b) {
        if (b == 0)
          return a;
        return gcf(b, a % b);
    }
  
Co-Prime:
  
Phi:
  
Factorizing:
  
Prime Factorization:
    
Prime Checker:
    
Prime Sieve:
  
Pythagorean Triplets:
  

 **Number Series**
 
 Fibonacci:
 
    1,1,2,3,5,8,13,21,34,55,89…
 
Catalan:
 
    1, 1, 2, 5, 14, 42, 132, 429, 1430, 4862…

![Catalan Series Formula](/ProgrammingCompetition/CatalanSeries.png)
     
Lazy Caterer: Formally also known as the central polygonal numbers, it describes the maximum number of pieces (or bounded/unbounded regions) of a circle (a pancake or pizza is usually used to describe the situation) that can be made with a given number of straight cuts. For example, three cuts across a pancake will produce six pieces if the cuts all meet at a common point, but seven if they do not.

![Lazy Caterer Series Formula](/ProgrammingCompetition/LazyCatererSeries.png)

Triangle: https://edublognss.wordpress.com/2013/04/16/famous-mathematical-sequences-and-series/

Pentagonal: https://edublognss.wordpress.com/2013/04/16/famous-mathematical-sequences-and-series/

Hexagonal: https://edublognss.wordpress.com/2013/04/16/famous-mathematical-sequences-and-series/
