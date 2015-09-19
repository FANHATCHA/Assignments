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
