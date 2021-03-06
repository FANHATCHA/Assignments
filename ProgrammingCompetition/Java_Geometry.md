**NOTE:** Java's Math class has methods for toRadians(double degrees) and toDegrees(double radians). It also has a method called hypot(double x, double y), which returns sqrt(x^2 +y^2).


**Find Point of Intersection:**

```java
// Returns null if there is no intersection or the lines are parallel to each other
public static Point2D intersection(Point2D a, Point2D b, Point2D c, Point2D d, boolean isSegment) {
	double x1 = a.getX(), y1 = a.getY(), x2 = b.getX(), y2 = b.getY();
	double x3 = c.getX(), y3 = c.getY(), x4 = d.getX(), y4 = d.getY();
	double D = (x1-x2)*(y3-y4) - (y1-y2)*(x3-x4);
	// Returns null if lines are parallel
	if (Math.abs(D) < EPS) return null; // Where EPS is something like 0.000001
	double u = (x1*y2 - y1*x2);
	double v = (x3*y4 - y3*x4);
	double x = (u*(x3-x4)-(x1-x2)*v)/D;
	double y = (u*(y3-y4)-(y1-y2)*v)/D;
	if (isSegment) {
		if ((new Line2D.Double(a,b)).intersectsLine(new Line2D.Double(c,d)))
			return new Point2D.Double(x,y);
		else return null;
	}
	return new Point2D.Double(x,y);
}
```

**Find Closest Point on Line to Point:**

```java
// Find the closest point on AB to the point C (assumes that A != B)
// If isSegment==true then it returns a point on the finite line, otherwise the infinite line
static Point2D findClosestPointToLine(Point2D a, Point2D b, Point2D c, boolean isSegment) {
	double dx = b.getX() - a.getX();
	double dy = b.getY() - a.getY();
	double u = ((c.getX() - a.getX())*dx + (c.getY() - a.getY())*dy)/(dx*dx + dy*dy);
	if (u < 0 && isSegment) return a;
	if (u > 1 && isSegment) return b;		
	return new Point2D.Double(a.getX() + u*dx, a.getY() + u*dy);
}
```

**Find Center of Circle given two points and a radius:**

``` java
// Note: plus=TRUE gives one possible point, plus=FALSE gives the other possible point
static Point2D findCenter(double a, double b, double c, double d, double r, boolean plus) {
  double q = Math.sqrt((a-c)*(a-c) + (b-d)*(b-d));
  double x3 = (a+c)/2.0;
  double y3 = (b+d)/2.0;
  double temp = Math.sqrt(r*r-(q/2.0)*(q/2.0));
  if (plus)
    return new Point2D.Double(x3 + temp*(b-d)/q, y3 + temp*(c-a)/q);
  return new Point2D.Double(x3 - temp*(b-d)/q, y3 - temp*(c-a)/q);
}
```

**Find Center of Circle given three points:**

```java
// Returns null if no circle exists (points are on the same line)
static Point2D findCenter(double x1, double y1, double x2, double y2, double x3, double y3) {
	if (x1 == x2 && x1 == x3) return null; 
	if (x2 == x1 || x3 == x2) return findCenter(x3, y3, x1, y1, x2, y2);
	double ma = (y2 - y1)/(x2 - x1);
	double mb = (y3 - y2)/(x3 - x2);
	if (ma == mb) return null;
	double x = ((ma*mb*(y1 - y3)) + (mb*(x1 + x2)) - (ma*(x2 + x3))) / (2.0*(mb - ma));
	double y;
	if (ma != 0) y = ((y1 + y2)/2.0) - ((x - (x1 + x2)/2.0)/ma);
	else y = ((y2 + y3)/2.0) - ((x - (x2 + x3)/2.0)/mb);
	return new Point2D.Double(x, y);
}
```

**Circle Line Intersection:**
<br>
<a href="https://github.com/micahstairs/Assignments/edit/master/ProgrammingCompetition/c++_geometry.md"> View in C++_geometry </a>

**Circle Arc Length/Sector Area**

![CircleSector](/ProgrammingCompetition/Images/circular_sector.jpg)

Area of Sector = θr²/2 <br>
Arc length = rθ

**Inradius:**

![Inradius](/ProgrammingCompetition/Images/Inradius.png)

**Incenter:**

![Incenter](/ProgrammingCompetition/Images/Incenter.png)

**Convert Polar Co-ordinate to Cartesian Co-ordinate:**
```java
static Point2D polarToCartesian(double degrees, double radius) {
  double radians = Math.toRadians(degrees);
  return new Point2D.Double(radius*Math.cos(radians), radius*Math.sin(radians));
}
```
**Angle from Point A to B:**
```java
// Find the angle from point A to point B in radians
static double angleBetweenPoints(Point2D a, Point2D b) {
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
static boolean containsPoint(Point2D a, Point2D b, Point2D c,
                             double area, double x, double y) {
  double ABC = Math.abs (a.getX()*(b.getY()-c.getY())
               + b.getX()*(c.getY()-a.getY()) + c.getX()*(a.getY()-b.getY()));
  double ABP = Math.abs (a.getX()*(b.getY()-y)
               + b.getX()*(y-a.getY()) + x*(a.getY()-b.getY()));
  double APC = Math.abs (a.getX()*(y-c.getY())
               + x*(c.getY()-a.getY()) + c.getX()*(a.getY()-y));
  double PBC = Math.abs (x*(b.getY()-c.getY())
               + b.getX()*(c.getY()-y) + c.getX()*(y-b.getY()));
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

**Determine if Polygon points are given CW or CCW**

``` java
	static boolean isCounterClockwise(Point2D polygon[]) {
		double sum = 0.0;
		for (int i = 0; i < polygon.length; i++) {
			Point2D p1 = polygon[i];
			Point2D p2 = polygon[(i+1)%N];
			sum += (p2.getX() - p1.getX())*(p2.getY() + p1.getY());
		}
		return sum <= 0.0;
	}
```

**Convex Hull:**

``` java
    static Stack<Point2D> createConvexHull(Point2D[] pts) {

        int k1, k2;
        int N = pts.length;
        Stack<Point2D> hull = new Stack<Point2D>();
        Point2D[] points = new Point2D.Double[N];
        for (int i = 0; i < N; i++) points[i] = pts[i];
        Arrays.sort(points, new PointOrder());
        Arrays.sort(points, 1, N, new PolarOrder(points[0]));
        hull.push(points[0]);
        for (k1 = 1; k1 < N; k1++)
            if (!points[0].equals(points[k1])) break;
        if (k1 == N) return null;
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
        return hull;
    }

    // Compare other points relative to polar angle (between 0 and 2pi) they make with this Point
    static class PolarOrder implements Comparator<Point2D> {
        Point2D pt;
        public PolarOrder(Point2D pt) { this.pt = pt; }
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

    // Put lower Y co-ordinates first, with lower X in the case of ties
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
    static boolean isConvex(Stack<Point2D> hull) {
        int N = hull.size();
        if (N <= 2) return true;
        Point2D[] points = new Point2D.Double[N];
        int n = 0;
        for (Point2D p : hull) points[n++] = p;
        for (int i = 0; i < N; i++) 
            if (ccw(points[i], points[(i+1) % N], points[(i+2) % N]) <= 0) 
                return false;
        return true;
    }
```
**Distance from point to plane:**
Given a plane of the form ax+by+cz+d=0 and point P=(u,v,w). The distance from the point P, to the plane is given by D=(au+bv+cw+d)/sqrt(a^2+b^2+c^2)
