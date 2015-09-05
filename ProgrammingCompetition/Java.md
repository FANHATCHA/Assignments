
  **CONVERSIONS**

  Character → Integer
    
    Character.getNumericValue(char c);
    
  Integer → Character 
    
    Character c = i + ‘0’
    
  ArrayList -> Set
    
    Set<Foo> listName = new HashSet<Foo>(arrayListName);

  Set to ArrayList
    
    ArrayList<Integer> arrayListName = new ArrayList<Integer>(mySet);
  
  **MATH**
  
  Greatest Common Factor:
    
    int gcf(int a, int b) {
        if (b == 0)
          return a;
        return gcf(b, a % b);
    }
