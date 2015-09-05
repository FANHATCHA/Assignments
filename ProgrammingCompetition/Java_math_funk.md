
**isprime**
```java
static boolean isPrime(final long n) {
     if (n < 2) return false;
     if (n == 2 || n == 3) return true;
     if (n % 2 == 0 || n % 3 == 0) return false;

     int limit = (int) Math.sqrt(n);
     for (int i = 5; i <= limit; i += 6)
     	if (n % i == 0 || n % (i + 2) == 0)
     		return false;
     return true;
} 
```

**Sieve of Eratosthenes (Prime Seive)**
```java

```

**GCF (Greatest Common Factor)**
``` java
static int gcf( int a, int b ) {
    if (b == 0) return a;
    return gcf( b, a % b );
} 
```
**PHI**
``` java
public static HashSet<Integer> getDistinctPrimeFactors( int n ) {
	
	HashSet <Integer> set = new HashSet<Integer>();
	for(int f = 2; f < Math.pow(n, 0.5) + 1; f++ ) {
		if (n % f == 0) {
			int c = n / f;
			if (c != f) {
				if (isprime(f)) set.add(f);
				if (isprime(c)) set.add(c);
			} else {
				if (isprime(f)) set.add(f);
			}
		}
	}
	return set;
}

public static int phi(int n) {
	// Phi of a prime is prime-1
	if (isprime(n)) return n-1;
	int phi = n;
	for (int p : getDistinctPrimeFactors(n)) 
		phi -= (phi / p);
	return phi;
}
```

**Pascals Triangle**
```java
// Note: Switch to BigInteger if you want to generate more than 67 rows
static long[][] generatePascalTriangle(int nRows) {

    long[][] arr = new long[nRows][nRows];
    arr[0][0] = 1;

    for (int y = 1; y < nRows; y++) {
    	arr[y][0] = arr[y - 1][0];
    	for (int x = 1; x <= y; x++)
    		arr[y][x] = arr[y - 1][x - 1] + arr[y - 1][x];
    }
    return arr;
}
```


