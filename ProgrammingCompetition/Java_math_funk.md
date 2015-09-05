
** GCF (Greatest Common Factor) **
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



