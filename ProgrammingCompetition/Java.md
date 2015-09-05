**~/.vimrc**


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
