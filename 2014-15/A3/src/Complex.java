/**

Complex - 	The Complex class has two instance variables, both of type double (these hold the real and imaginary parts of a complex number).
			This class also contains some basic arithmetic operations.

@author William Fiset, Micah Stairs

**/

public class Complex {

	private double real, imag;

	/* Standard constructor */
	public Complex(double real, double imag) {
		this.real = real;
		this.imag = imag;
	}

	/* Copying constructor */
	public Complex(Complex complex) {
		this.real = complex.getReal();
		this.imag = complex.getImag();
	}

	/* Multiples this complex number by the complex number passed as an argument */
	public void multiply(Complex complex) {
		Complex previousValue = new Complex(this);
		this.real = (previousValue.getReal()*complex.getReal()) - (previousValue.getImag()*complex.getImag());
		this.imag = (previousValue.getImag()*complex.getReal()) + (previousValue.getReal()*complex.getImag());
	}

	/* Multiples this complex number to the complex number passed as an argument
	/* Stores the result back in this complex number */
	public void add(Complex complex) {
		this.real += complex.getReal();
		this.imag += complex.getImag();
	}

	/* Subtracts the complex number passed as an argument from this complex number
	/* Stores the result back in this complex number */
	public void subtract(Complex complex) {
		this.real -= complex.getReal();
		this.imag -= complex.getImag();
	}

	/* Returns the real part of this complex number */
	public double getReal() {
		return real;
	}

	/* Retuns the imaginary part of this complex number */
	public double getImag() {
		return imag;
	}

	/* Returns the modulus of this complex number (distance from origin) */
	public double modulus() {
		return Math.sqrt(getReal()*getReal() + getImag()*getImag());
	}

	/* Formats this complex number as a String: (a + bi) */
	@Override public String toString() {
		return "(" + getReal() + " + " + getImag() + "i)";
	}

}















