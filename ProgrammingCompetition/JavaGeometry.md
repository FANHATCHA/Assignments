**NOTE:** Java's Math class has methods for toRadians(double degrees) and toDegrees(double radians). It also has a method called hypot(double x, double y), which returns sqrt(x^2 +y^2).

**Convert Polar Co-ordinate to Cartesian Co-ordinate:**
```java
static Point2D polarToCartesian(double degrees, double radius) {
  double radians = Math.toRadians(degrees);
  return new Point2D.Double(radius*Math.cos(radians), radius*Math.sin(radians));
}
```
**Angle from Point A to B**
```java
// Find the angle from point A to point B in radians
// NOTE: Not entirely tested yet
static double findAngleBetweenPoints(Point2D a, Point2D b) {
  return Math.atan2(b.getY() - a.getY(), b.getX() - a.getX());
}
```

**Area of Triangle:**
```java
static double area(Point2D a, Point2D b, Point2D c) {
  double temp1 = a.getX()*(b.getY() - c.getY());
  double temp2 = b.getX()*(c.getY() - a.getY());
  double temp3 = c.getX()*(a.getY() - b.getY());
  return Math.abs(temp1 + temp2 + temp3)/2.0;
}
```

**Triangle Contains Point:**
```java
static boolean containsPoint(Point2D a, Point2D b, Point2D c, double area, double x, double y) {
  double ABC = Math.abs (a.getX() * (b.getY() - c.getY()) + b.getX() * (c.getY() - a.getY()) + c.getX() * (a.getY() - b.getY()));
  double ABP = Math.abs (a.getX() * (b.getY() - y) + b.getX()  * (y - a.getY()) + x * (a.getY() - b.getY()));
  double APC = Math.abs (a.getX() * (y - c.getY()) + x   * (c.getY() - a.getY()) + c.getX() * (a.getY() - y));
  double PBC = Math.abs (x  * (b.getY() - c.getY()) + b.getX() * (c.getY() - y) + c.getX() * (y - b.getY()));
  return ABP + APC + PBC == ABC;
}
```
**Area of Polygon:**

```java
// Points must be ordered (either clockwise or counter-clockwise)
static double findAreaOfPolygon(Point2D[] pts) {

  double area = 0;

  for (int i = 1; i + 1 < pts.length; i++)
    area += areaOfTriangulation(pts[0], pts[i], pts[i+1]);

  return Math.abs(area);

}

// May return positive or negative value (important for polygon method)
static double areaOfTriangulation(Point2D a, Point2D b, Point2D c) {
  return crossProduct(subtract(a, b), subtract(a, c))/2.0;
}

// Find the difference between two points
static Point2D subtract(Point2D a, Point2D b) {
  return new Point2D.Double(a.getX() - b.getX(), a.getY() - b.getY());
}

static double crossProduct(Point2D a, Point2D b) {
  return a.getX()*b.getY() - a.getY()*b.getX();
}
```

For readability purposes, here is what is going on:
```
ABC = abs(x1 * (y2 - y3) + x2 * (y3 - y1) + x3 * (y1 - y2));
ABP = abs(x1 * (y2 - y)  + x2 * (y - y1)  + x  * (y1 - y2));
APC = abs(x1 * (y  - y3) + x  * (y3 - y1) + x3 * (y1 - y));
PBC = abs(x  * (y2 - y3) + x2 * (y3 - y)  + x3 * (y - y2));
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

    // Sample Usage
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
    
            for (Point2D p : hull)
                System.out.println( p.getX() + " " + p.getY());
        }
    }

}
```
