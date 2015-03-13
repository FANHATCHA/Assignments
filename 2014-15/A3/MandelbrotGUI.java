/**

@author William Fiset, Micah Stairs

Each time you render the mandelbrot set, you will iterate though
all the pixels on the drawing surface (x,y) and convert those
to positions on the complex plane.
	
c = a + bi

*/


public class MandelbrotGUI {

    public static void main(String[] args) {
		
    }

    private int doIteractions( Complex c ) {

    	/*

		if this sequence goes to infinity, then it is NOT in the mandelbrot set
		otherwise it is in the mandelbrot. 

    	z_0 = 0
    	z_1 = 0^2 + c = 2
    	...
		z_n = (z_n-1)^2 + c

		Simplifications:
		- If sequence ever leaves a circle of radius two, then 
		sequence will go to infinity, so stop iterating.

		- If it never leaves, (MAX_ITER ~ 100) then original point
		c is in the mandelbrot set.

		NOTE ON COLOR: 
		- Use distance formula to calculate the distance from the
		origin: dist = (a^2 + b^2) <= 2.
		- Set ranges for the # of iterations, ex:
		0-10 purple, 10-20 pink, 20-30 red.... 
		
		^^^ 0-10 is the background color

    	*/

    }

}