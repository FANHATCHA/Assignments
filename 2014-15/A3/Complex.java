/**

@author William Fiset, Micah Stairs

Mandelbrot Set is a region of the Complex plane.
a + bi --> Complex number

z = z^2 + c

z starts at 0
c is a constant

**/
public class Complex {

	private double real, imag;

	// Standard constructor
	public Complex( double inReal, double inImag ) {
		this.real = inReal;
		this.imag = inImag
	}

	// Copying constructor
	public Complex(Complex inComplex) {
		this.real = inComplex.getReal();
		this.imag = inComplex.getImag();
	}


	public void multiply(Complex toMult ) {
		this.real = real.getReal()*toMult.getReal() - real.getImag()*toMult.getImag();
		this.imag = real.getImag()*toMult.getReal() + real.getReal()*toMult.getImag();
	}

	public void add( Complex toAdd ) {
		this.real += toSub.getReal();
		this.imag += toSub.getImag();
	}

	public void subtract(Complex toSub) {
		this.real -= toSub.getReal();
		this.imag -= toSub.getImag();
	}

	public double getImag() {
		return this.inImag;
	}

	public double getReal() {
		return this.inReal;
	}

	// Returns the modulus of this complex number (distance from origin)
	public double modulus() {
		return Math.sqrt(Math.pow(getReal(), 2) + Math.pow(getImag(), 2));
	}

}















