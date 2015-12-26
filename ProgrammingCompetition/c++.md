### Compiling 
``` shell
# Standard compile:
make cpp_file_name_without_extension

# Compile with c++11 and warnings on:
g++ -Wall -g -std=c++11 file.cpp -o executable_name

# If the above gives you trouble try the following:
g++ -std=c++0x your_file.cpp -o your_program
```
### Template

``` c++
#include <iostream> // cin, cout
#include <stdio.h>  // printf
using namespace std;
   
int main() {
 
  string a;
  int b;
  long c;
  cin >> a >> b >> c;
 
  // Output c style
  printf("%s %d %ld\n", a.c_str(), b, c);
  // Output c++ style
  cout << a << " " << b << " " << c << endl;
}
```

### Input/Ouput Optimizations:

Place `ios_base::sync_with_stdio(false);` in the beginning of the program, before any input/output. This command turns off iostreams and stdio synchronization (description). It is on by default, which means that calls to iostreams and stdio functions can be freely interleaved even for the same underlying stream. When synchronization is turned off, mixing calls is no longer allowed, but iostreams can potentially operate faster.

Untie cin from cout using `cin.tie(NULL);`. By default, cin is tied to cout, which means that cout is flushed before any operation on cin (description). Turning this feature off allows iostreams, again, to operate faster.

Frequent use of `endl` also negatively affects iostreams performance, because `endl` not only outputs a newline character, but also flushes the stream's buffer (decription). You can simply output `\n` instead of `endl`.

**Standard Input (stdin):**

### getline
``` c++
string line;
while ( getline(cin, line) ) {
  printf("%s\n", line.c_str());
}
```

###cin
``` c++
int n;
string s, s1;
long a;

cin >> n;
while (n-- > 0) {
 cin >> s >> s1 >> a; // reads a line with a string, another string and a long.
}

```

###\<climits> (integer min/max values)
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

###\<cfloat> (floating point number min/max values)
``` c++
- FLT_MAX 
- DBL_MAX 
- FLT_MIN 
- DBL_MIN
```
