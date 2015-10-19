
**Prime Checker:**
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

**Sieve of Eratosthenes (Prime Sieve):**
```java
// Gets all primes up to, but not including limit, return as a list of primes
static ArrayList<Integer> sieve(int limit) {

	// See: http://mathworld.wolfram.com/PrimeCountingFunction.html
    final int numPrimes = (limit > 1 ? (int)(1.25506 * limit / Math.log((double)limit)) : 0);
    ArrayList<Integer> primes = new ArrayList<Integer>(numPrimes);

    boolean [] isComposite = new boolean [limit];   // all false
    final int sqrtLimit    = (int)Math.sqrt(limit); // floor
    for (int i = 2; i <= sqrtLimit; i++) {
        if (!isComposite [i]) {
            primes.add(i);
            for (int j = i*i; j < limit; j += i) // `j+=i` can overflow
                isComposite [j] = true;
        }
    }
    for (int i = sqrtLimit + 1; i < limit; i++)
        if (!isComposite [i])
            primes.add(i);
    return primes;
}
```

```java
// Gets all primes up to, but not including limit, return as a boolean array
static boolean[] sieve(int limit) {

  boolean[] isPrime = new boolean[limit];
  Arrays.fill(isPrime, true);
  if (limit >= 1)
    isPrime[0] = false;
  if (limit >= 2)
    isPrime[1] = false;

  final int sqrtLimit = (int)Math.sqrt(limit);
  for (int i = 2; i <= sqrtLimit; i++) {
      if (isPrime[i])
        for (int j = i*i; j < limit; j += i)
          isPrime[j] = false;
  }

  return isPrime;
  
}
```

**GCF (Greatest Common Factor):**
``` java
static int gcf( int a, int b ) {
    if (b == 0) return a;
    return gcf( b, a % b );
} 
```

**Co-Prime:**
```java
// Co-prime is a fancy way of saying that two numbers share no factors 
static boolean areCoprime(int a, int b) {
	return gcf(a, b) == 1;
}
```

**Factorization:**
```java
// Returns the factors of a given number UNSORTED. 
// Where n >= 0, does not account for negative numbers!
static ArrayList<Integer> factors(int n) {
    	
	ArrayList<Integer> divs = new ArrayList<Integer>();
	divs.add(1); 
	
	if (n > 1) {
	    divs.add(n); 
	    for (int f = 2; f < ((int)Math.pow(n, 0.5))+1; f++) {
	    	if (n % f == 0) {
	    		int c = n / f;
	    		if (c != f) {
					divs.add(f);
					divs.add(c);
	    		} else {
	    			divs.add(f);
	    		}
	    	} 
	    }
	}
	
	return divs;
}
```

**Prime Factorization:**
```java
static int pollard_rho(int n) {
    if (n % 2 == 0) return 2;
    // Get a number between [2, 10^6] inclusive
    int x = 2 + (int) ( ((1000000-2)+1) * Math.random());
    int c = 2 + (int) ( ((1000000-2)+1) * Math.random());
    int y = x;
    int d = 1;
    while (d == 1) {
        x = (x * x + c) % n;
        y = (y * y + c) % n;
        y = (y * y + c) % n;
        d = gcf(Math.abs(x - y), n);
        if (d == n) break;
    }
    return d;
}

static ArrayList<Integer> primeFactorization(int n) {

    ArrayList<Integer> factors = new ArrayList<Integer> ();
    if (n <= 0) throw new IllegalArgumentException();
    else if (n == 1) return factors;

    PriorityQueue <Integer> divisorQueue = new PriorityQueue<Integer>();
    divisorQueue.add(n);

    while (!divisorQueue.isEmpty()) {
        int divisor = divisorQueue.remove();
        if (isPrime(divisor)) {
            factors.add(divisor);
            continue;
        }
        int next_divisor = pollard_rho(divisor);
        if (next_divisor == divisor) {
            divisorQueue.add(divisor);
        } else {
            divisorQueue.add(next_divisor);
            divisorQueue.add(divisor/next_divisor);
        }
    }

    return factors;

}
```

**Euler's phi function (aka Euler's totient function):**
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

**Pascal's Triangle:**
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
