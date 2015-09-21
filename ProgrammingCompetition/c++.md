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

#include <climits>
``` c++
- CHAR_MIN	Minimum value for an object of type char	either SCHAR_MIN or 0
- CHAR_MAX	Maximum value for an object of type char	either SCHAR_MAX or UCHAR_MAX
- SHRT_MIN	Minimum value for an object of type short int	-32767 (-215+1) or less*
- SHRT_MAX	Maximum value for an object of type short int	32767 (215-1) or greater*
- USHRT_MAX	Maximum value for an object of type unsigned short int	65535 (216-1) or greater*
- INT_MIN	Minimum value for an object of type int	-32767 (-215+1) or less*
- INT_MAX	Maximum value for an object of type int	32767 (215-1) or greater*
- UINT_MAX	Maximum value for an object of type unsigned int	65535 (216-1) or greater*
- LONG_MIN	Minimum value for an object of type long int	-2147483647 (-231+1) or less*
- LONG_MAX	Maximum value for an object of type long int	2147483647 (231-1) or greater*
- ULONG_MAX	Maximum value for an object of type unsigned long int	4294967295 (232-1) or greater*
- LLONG_MIN	Minimum value for an object of type long long int	-9223372036854775807 (-263+1) or less*
- LLONG_MAX	Maximum value for an object of type long long int	9223372036854775807 (263-1) or greater*
- ULLONG_MAX	Maximum value for an object of type unsigned long long int	18446744073709551615 (264-1) or greater*

```
