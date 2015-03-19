public class TestMandelbrot {

    public static void main(String[] args) {
	
    	Complex [] points = {
    		new Complex(0,0),
    		new Complex(-1, 0),
    		new Complex(1.0/8.0,0),
    		new Complex(1.0/4.0,0),

    		new Complex(1,0),

    	};
    	
    	boolean [] withinFractal = {
    	
    		true,
    		true,
    		true,
    		true,
    	
    		false,
    	};

    	int cases = 0;
    	int passes = 0;
    	cases += points.length;

    	for (int i =0; i < points.length; i++) {
    		if (isWithinFractal(points[i]) == withinFractal[i] ) {
    			passes++;
    		}
    	}

    	System.out.printf("%d/%d TESTCASES PASSED\n", passes, cases );

    }

    private static boolean isWithinFractal(Complex c) {
    	return doIterations( c ) == MandelbrotGUI.MAX_ITERATIONS;
    }

	private static int doIterations(Complex c) {

		Complex z = new Complex(0, 0);
	    	
    	for (int i = 0; i < MandelbrotGUI.MAX_ITERATIONS; i++) {
    		z.multiply(z);
    		z.add(c);

    		// Not within Mandelbrot set
    		if (z.modulus() > 2.0) return i;
    	}

    	// Within Mandelbrot set
	    return MandelbrotGUI.MAX_ITERATIONS;

	}


}