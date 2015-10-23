**Edit Distance:**
Using dynamic programming, computes the edit distance between from string A to string B. The complexity is O(nm) when n is the length of the first string, and m is the length of the second string. NOTE: This technique can also be used to find the edit distance for other things, not just strings.
```java
static int editDistance(String a, String b, int insertionCost, int deletionCost, int substitutionCost) {

	int[][] arr = new int[a.length()+1][b.length()+1];
	
	for (int i = 0; i <= a.length(); i++)
		for (int j = (i == 0 ? 1 : 0); j <= b.length(); j++) {

			int min = Integer.MAX_VALUE;

			// Substitution
			if (i > 0 && j > 0)
				min = arr[i-1][j-1] + (a.charAt(i-1) == b.charAt(j-1) ? 0 : substitutionCost);
			// Deletion
			if (i > 0)
				min = Math.min(min, arr[i-1][j] + deletionCost);
			// Insertion
			if (j > 0)
				min = Math.min(min, arr[i][j-1] + insertionCost);
				
			arr[i][j] = min;
		}

	for (int i = 0; i <= a.length(); i++)
		System.out.println(Arrays.toString(arr[i]));
	
	return arr[a.length()][b.length()];

}
```

**Number Of Power Strings:**

Given a string `str`, find `n` such that `substr^n = str`, where `substr` is some substring of `str`. Example of string exponentiation: `"abf"^4 = "abfabfabfabf"`. The following algorithm (which I came up with) is O(n). The naive solution is O(n^2).

``` java
static int findMaxNumberOfPowerStrings(String str) {
  char[] arr = str.toCharArray();
  int end = 1, len = 1;
  while (end < arr.length) {
    if (arr[end] == arr[end-len])
      end++;
    else if (len == end) {
      len++;
      end++;
    } else
      len = end;
  }
  return arr.length/len;
}
```

**Knuth–Morris–Pratt algorithm:**

Given string `str` and substring `substr`, count the number of occurences of `substr` in `str`, which are allowed to be overlapping (NOTE: The below algorithm returns the index of the first occurence of the pattern, however it can easily be modified to return the count of occurences instead). The naive solution is O(n*m) where n is the length of `str` and m is the length of `substr`. The KMP algorithm is difficult to understand, but it is able to solve this problem in O(n).

NOTE: This can easily be modified to work with other things such as integers, for example, instead of characters. For example, I used KMP as part of my solution for the following problem: https://open.kattis.com/problems/clockpictures. It would be good, however, to update this method so that it uses generics.

```java
// Returns -1 if not found, otherwise the start index of the pattern in the string
static int kmp(String string, String pattern) {
  char[] str = string.toCharArray();
  char[] pat = pattern.toCharArray();
  int[] arr = kmpHelper(pat);
  int i = 0, j = 0;
  while (i < str.length) {
    if (str[i] == pat[j]) {
      i++;
      j++;
    } else if (j == 0)
      i++;
    else
      j = arr[j-1];
    if (j == pat.length)
      return i - j;
  }
  return -1;
}

static int[] kmpHelper(char[] ch) {
  int[] arr = new int[ch.length];
  int i = 1, j = 0;
  while (i < ch.length) {
    if (ch[i] == ch[j]) arr[i++] = j++ + 1;
    else if (j == 0) i++;
    else j = arr[j-1];
  }
  return arr;
}
```

**Suffix Tree:**

```java
class Node {
  Node[] nodes = new Node[26];
  void add(String str, int start) {
    if (start == str.length()) return;
    int index = str.charAt(start) - 'a';
    if (nodes[index] == null) nodes[index] = new Node();
    nodes[index].add(str, start + 1);
  }
```
Setup:
```java
BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
String str = br.readLine();
Node root = new Node();
for (int i = 0; i < str.length(); i++)
  root.add(str, i);
```
