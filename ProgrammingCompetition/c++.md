**Standard Input (stdin)**

Get known finite input:

``` c++
#include <stdio.h>

// In this example we are reading in a word with a maximum of 9 characters, a single character and an integer
char word[10];
char c;
int num;
 
scanf("%s %c %d", word, &c, &num);
printf("%s - %c - %d\n", word, c, num);
```

Grab next token:
``` c++
string token;
while( getline(cin, token, ' ')){
  cout << token << endl;
}
```

Read Lines:
``` c++
string line;
while ( getline(cin, line) ) {
  printf("%s\n", line.c_str());
}
```
