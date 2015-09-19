
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
// Gets all primes up to, but not including limit
static ArrayList<Integer> seive(int limit) {

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
		d = gcf( x - y, n);
		if( d == n) break;
	}
    return d;
}

// Returns an UNSORTED prime factorization of a number n >= 2
static ArrayList<Integer> primeFactorization(int n) {

	ArrayList<Integer> factors = new ArrayList<Integer> ();
	if (n <= 0) throw new IllegalArgumentException();
	else if (n == 1) return factors;

	PriorityQueue <Integer> divisorQueue = new PriorityQueue<Integer>();
	divisorQueue.add(n);

	while (!divisorQueue.isEmpty()) {
		int divisor = divisorQueue.remove();
		if (isprime(divisor)) {
			factors.add(divisor);
			continue;
		}
		int next_divisor = pollard_rho(divisor);
		if (next_divisor == divisor) {
			divisorQueue.add(divisor);
		} else {
			divisorQueue.add(next_divisor);
			divisorQueue.add( divisor/next_divisor );
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

**Convex Hull:**

``` java

import java.util.*;
import java.awt.geom.*;

public class ConvexHull {
    
    static Stack<Point2D> hull;
    static Scanner sc = new Scanner(System.in);

    static void convexHull(Point2D[] pts) {

        hull = new Stack<Point2D>();

        int N = pts.length;
        Point2D[] points = new Point2D.Double[N];
        for (int i = 0; i < N; i++) points[i] = pts[i];

        Arrays.sort(points, new PointOrder());
        Arrays.sort(points, 1, N, new PolarOrder(points[0]));

        hull.push(points[0]);

        int k1;
        for (k1 = 1; k1 < N; k1++)
            if (!points[0].equals(points[k1])) break;
        if (k1 == N) return;

        int k2;
        for (k2 = k1 + 1; k2 < N; k2++)
            if (ccw(points[0], points[k1], points[k2]) != 0) break;
        hull.push(points[k2-1]); 

        for (int i = k2; i < N; i++) {
            Point2D top = hull.pop();
            while (ccw(hull.peek(), top, points[i]) <= 0) 
                top = hull.pop();
            hull.push(top);
            hull.push(points[i]);
        }

    }

    // compare other points relative to polar angle (between 0 and 2pi) they make with this Point
    static class PolarOrder implements Comparator<Point2D> {
        
        Point2D pt;

        public PolarOrder(Point2D pt) {
            this.pt = pt;
        }

        @Override public int compare(Point2D q1, Point2D q2) {
            double dx1 = q1.getX() - pt.getX();
            double dy1 = q1.getY() - pt.getY();
            double dx2 = q2.getX() - pt.getX();
            double dy2 = q2.getY() - pt.getY();

            if      (dy1 >= 0 && dy2 < 0) return -1;    
            else if (dy2 >= 0 && dy1 < 0) return +1;    
            else if (dy1 == 0 && dy2 == 0) {            
                if      (dx1 >= 0 && dx2 < 0) return -1;
                else if (dx2 >= 0 && dx1 < 0) return +1;
                else                          return  0;
            }
            else return -ccw(pt, q1, q2);
        }
    }

    // put lower Y co-ordinates first, with lower X in the case of ties
    static class PointOrder implements Comparator<Point2D> {
        
        @Override public int compare(Point2D q1, Point2D q2) {

            if (q1.getY() < q2.getY()) return -1;

            if (q1.getY() == q2.getY()) {
                if (q1.getX() < q2.getX()) return -1;
                else if (q1.getX() > q2.getX()) return 1;
                else return 0;
            }

            return 1;

        }
    }

    static int ccw(Point2D a, Point2D b, Point2D c) {
        double area = (b.getX() - a.getX())*(c.getY() - a.getY()) - (b.getY() - a.getY())*(c.getX() - a.getX());
        return (int) Math.signum(area);
    }

    // check that boundary of hull is strictly convex
    static boolean isConvex() {
        int N = hull.size();
        if (N <= 2) return true;

        Point2D[] points = new Point2D.Double[N];
        int n = 0;
        for (Point2D p : hull) 
            points[n++] = p;

        for (int i = 0; i < N; i++) 
            if (ccw(points[i], points[(i+1) % N], points[(i+2) % N]) <= 0) 
                return false;
        return true;
    }

    // test client
    public static void main(String[] args) {
        
        while (true) {

            int N = sc.nextInt();
            Point2D[] points = new Point2D.Double[N];
            if (N == 0) break;
            for (int i = 0; i < N; i++) {
                int x = sc.nextInt();
                int y = sc.nextInt();
                points[i] = new Point2D.Double(x, y);
            }
            
            convexHull(points);
    
            System.out.println(hull.size());
            for (Point2D p : hull)
                System.out.println( (int)p.getX() + " " + (int)p.getY());
        }
    }

}
```
