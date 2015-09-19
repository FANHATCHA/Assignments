**Convert Polar Co-ordinate to Cartesian Co-ordinate:**
```java
static Point2D polarToCartesian(double degrees, double radius) {
  double radians = degreesToRadians(degrees);
  return new Point2D.Double(radius*Math.cos(radians), radius*Math.sin(radians));
}
```

**Convert Degrees to Radians:**
```java
static double degreesToRadians(double degrees) {
  return degrees*(Math.PI/180.0);
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

**Triangle Conatins Point:**
```java
static boolean containsPoint(Point2D a, Point2D b, Point2D c, double area, double x, double y) {
  double ABC = Math.abs (a.getX() * (b.getY() - c.getY()) + b.getX() * (c.getY() - a.getY()) + c.getX() * (a.getY() - b.getY()));
  double ABP = Math.abs (a.getX() * (b.getY() - y) + b.getX()  * (y - a.getY()) + x * (a.getY() - b.getY()));
  double APC = Math.abs (a.getX() * (y - c.getY()) + x   * (c.getY() - a.getY()) + c.getX() * (a.getY() - y));
  double PBC = Math.abs (x  * (b.getY() - c.getY()) + b.getX() * (c.getY() - y) + c.getX() * (y - b.getY()));
  return ABP + APC + PBC == ABC;
}
```

For readability purposes, here is what is going on:
```
ABC = abs(x1 * (y2 - y3) + x2 * (y3 - y1) + x3 * (y1 - y2));
ABP = abs(x1 * (y2 - y)  + x2 * (y - y1)  + x  * (y1 - y2));
APC = abs(x1 * (y  - y3) + x  * (y3 - y1) + x3 * (y1 - y));
PBC = abs(x  * (y2 - y3) + x2 * (y3 - y)  + x3 * (y - y2));
```
