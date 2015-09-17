**Number Of Power Strings:**

Given a string `str`, find `n` such that `substr^n = str`, where `substr` is some substring of `str`. Example of string exponentiation: `"abf"^4 = "abfabfabfabf"`. The following algorithm (which I came up with) is O(n). The naive solution is O(n^2).

``` java
static int findMaxNumberOfPowerStrings(String str) {

  char[] arr = str.toCharArray();

  int end = 1;
  int len = 1;

  while (end < arr.length) {

    if (arr[end] == arr[end-len]) {
      end++;
    } else if (len == end) {
      len++;
      end++;
    } else {
      len = end;
    }

  }

  return arr.length/len;

}
```

**Knuth–Morris–Pratt algorithm:**

Given string `str` and substring `substr`, count the number of occurences of `substr` in `str` (which can be overlapping). The naive solution is O(n*m) where n is the length of `str` and m is the length of `substr`. The KMP algorithm is difficult to understand, but it is able to solve this problem in O(n).
