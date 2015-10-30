import java.util.*;
import java.awt.geom.*; 

public class LineIntersection {

    static Scanner sc = new Scanner(System.in);
    
    public static void main(String[] args) {
		
    	System.out.println();

    	// Two non intersecting vertical lines:
    	Point2D.Double pt1 = new Point2D.Double(0, 0);
    	Point2D.Double pt2 = new Point2D.Double(0, 1);
    	Point2D.Double pt3 = new Point2D.Double(1, 0);
    	Point2D.Double pt4 = new Point2D.Double(1, 1);
    	System.out.println("Two non intersecting vertical lines: " + intersection(pt1,pt2,pt3,pt4,true));
    	
		// Two stacked vertical lines
    	pt1 = new Point2D.Double(0, 0);
    	pt2 = new Point2D.Double(0, 1);
    	pt3 = new Point2D.Double(0, 0);
    	pt4 = new Point2D.Double(0, 1);
		System.out.println("Stacked Vertical Lines: " + intersection(pt1,pt2,pt3,pt4,true));

		// Two non intersecting horizontal lines:
    	pt1 = new Point2D.Double(0, 0);
    	pt2 = new Point2D.Double(1, 0);
    	pt3 = new Point2D.Double(0, 1);
    	pt4 = new Point2D.Double(1, 1);
    	System.out.println("Two non intersecting horizontal lines: " + intersection(pt1,pt2,pt3,pt4,true));
    
		// Two stacked horizontal lines
    	pt1 = new Point2D.Double(0, 0);
    	pt2 = new Point2D.Double(1, 0);
    	pt3 = new Point2D.Double(0, 0);
    	pt4 = new Point2D.Double(1, 0);
		System.out.println("Stacked horizontal Lines: " + intersection(pt1,pt2,pt3,pt4,true));

		// Half box
    	pt1 = new Point2D.Double(0, 1);
    	pt2 = new Point2D.Double(1, 1);
    	pt3 = new Point2D.Double(1, 1);
    	pt4 = new Point2D.Double(1, 0);
		System.out.println("Half box: " + intersection(pt1,pt2,pt3,pt4,true));

		// Half box flipped
    	pt1 = new Point2D.Double(0, 0);
    	pt2 = new Point2D.Double(1, 0);
    	pt3 = new Point2D.Double(0, 0);
    	pt4 = new Point2D.Double(0, 1);
		System.out.println("Half box flipped: " + intersection(pt1,pt2,pt3,pt4,true));

		// Diagonal Parallel Lines:
    	pt1 = new Point2D.Double(0, 1);
    	pt2 = new Point2D.Double(-1, 0);
    	pt3 = new Point2D.Double(1, 0);
    	pt4 = new Point2D.Double(0, -1);
		System.out.println("Diagonal Parallel Lines: " + intersection(pt1,pt2,pt3,pt4,true));


		// horizontal line intersects diagonal line:
    	pt1 = new Point2D.Double(-10, 0);
    	pt2 = new Point2D.Double(10, 0);
    	pt3 = new Point2D.Double(5, 5);
    	pt4 = new Point2D.Double(2, -1);
		System.out.println("horizontal line intersects diagonal line: " + intersection(pt1,pt2,pt3,pt4,true));

		// vertical line intersects diagonal line:
    	pt1 = new Point2D.Double(2.5, 10);
    	pt2 = new Point2D.Double(2.5, -10);
    	pt3 = new Point2D.Double(5, 5);
    	pt4 = new Point2D.Double(2, -1);
		System.out.println("vertical line intersects diagonal line: " + intersection(pt1,pt2,pt3,pt4,true));

		// two diagonals intersect:
    	pt1 = new Point2D.Double(1, 0);
    	pt2 = new Point2D.Double(2, 1);
    	pt3 = new Point2D.Double(1, 1);
    	pt4 = new Point2D.Double(3, -1);
		System.out.println("two diagonals intersect: " + intersection(pt1,pt2,pt3,pt4,true));

		// two diagonals intersect at a T-intersection:
    	pt1 = new Point2D.Double(-0.5, -0.5);
    	pt2 = new Point2D.Double(0.5, 0.5);
    	pt3 = new Point2D.Double(0, 1);
    	pt4 = new Point2D.Double(1, 0);
		System.out.println("two diagonals intersect at a T-intersection: " + intersection(pt1,pt2,pt3,pt4,true));

		// two diagonals don't quite intersect at a T-intersection:
    	pt1 = new Point2D.Double(-0.5, -0.5);
    	pt2 = new Point2D.Double(0.49, 0.49);
    	pt3 = new Point2D.Double(0, 1);
    	pt4 = new Point2D.Double(1, 0);
		System.out.println("two diagonals don't quite intersect at a T-intersection: " + intersection(pt1,pt2,pt3,pt4,true));

		// two inifinite lines intersection, but not the segments:
    	pt1 = new Point2D.Double(-0.4, 0);
    	pt2 = new Point2D.Double(-0.1, 1);
    	pt3 = new Point2D.Double(0.1, 1);
    	pt4 = new Point2D.Double(0.4, 0);
		System.out.println("two inifinite lines intersection, but not the segments: " + intersection(pt1,pt2,pt3,pt4,false));
		System.out.println("two inifinite lines intersection, but not the segments: " + intersection(pt1,pt2,pt3,pt4,true));


		System.out.println();

    }

    // If the lines are stacked on top of each other one of the two points is returned, this may not be desired
    // Returns null if there is no intersection or the lines are parallel to each other
    public static Point2D intersection(Point2D a, Point2D b, Point2D c, Point2D d, boolean isSegment) {

		double x1 = a.getX(), y1 = a.getY(), x2 = b.getX(), y2 = b.getY();
		double x3 = c.getX(), y3 = c.getY(), x4 = d.getX(), y4 = d.getY();
    	double D = (x1-x2)*(y3-y4) - (y1-y2)*(x3-x4);

    	// Returns null if lines are parallel
    	if (D == 0)
    		return null;

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


}