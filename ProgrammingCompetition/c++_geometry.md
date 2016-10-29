**Circle line intersection**

```C++
inline double sign( double v ) {
	return v < 0 ? -1 : 1;
}

// http://mathworld.wolfram.com/Circle-LineIntersection.html
// Returns the points of intersection of a line (0,0) to (x2, y2) going
// through a circle centered at the origin.
// Return the discriminant to let you know what kind of intersection occurred.
double circleLineIntersection( double r, double x2, double y2, double *X1, double *Y1, double *X2, double *Y2 ) {

	// Circle is centered at the origin
	double x1 = 0, y1 = 0;

	double dx = x2 - x1;
	double dy = y2 - y1;
	double dr = sqrt(dx*dx + dy*dy);

	double D = x1*y2 - x2*y1;
	double discriminant = r*r*dr*dr - D*D;

	*X1 = D*dy  + sign(dy)*dx*sqrt(discriminant) / (dr*dr);
	*Y1 = -D*dy + fabs(dy)*sqrt(discriminant) / (dr*dr);

	*X2 = D*dy  - sign(dy)*dx*sqrt(discriminant) / (dr*dr);
	*Y2 = -D*dy - fabs(dy)*sqrt(discriminant) / (dr*dr);

	return discriminant;

}

// USAGE:
double r, x2, y2, X1, Y1, X2, Y2;
double discriminant = circleLineIntersection(r, x2, y2, &X1, &Y1, &X2, &Y2 );

if (discriminant < 0) {
	// No intersection
} else if (discriminant == 0) {
	// tangent
} else {
	// intersection
}

```
