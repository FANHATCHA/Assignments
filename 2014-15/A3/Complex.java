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
	public Complex(double real, double imag) {
		this.real = real;
		this.imag = imag;
	}

	// Copying constructor
	public Complex(Complex complex) {
		this.real = complex.getReal();
		this.imag = complex.getImag();
	}

	public void multiply(Complex complex) {
		Complex previousValue = new Complex(this);
		this.real = (previousValue.getReal()*complex.getReal()) - (previousValue.getImag()*complex.getImag());
		this.imag = (previousValue.getImag()*complex.getReal()) + (previousValue.getReal()*complex.getImag());
	}

	public void add(Complex complex) {
		this.real += complex.getReal();
		this.imag += complex.getImag();
	}

	public void subtract(Complex complex) {
		this.real -= complex.getReal();
		this.imag -= complex.getImag();
	}

	public double getImag() {
		return imag;
	}

	public double getReal() {
		return real;
	}

	// Returns the modulus of this complex number (distance from origin)
	public double modulus() {
		// return Math.sqrt(Math.pow(getReal(),2) + Math.pow(getImag(),2) );
		return Math.sqrt(getReal()*getReal() + getImag()*getImag());
	}

	@Override public String toString() {
		return "(" + getReal() + " + " + getImag() + "i)";
	}

}















