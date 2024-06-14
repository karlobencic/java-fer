package hr.fer.zemris.java.hw02;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

/**
 * This class represents the data type for complex numbers. It includes basic
 * operations that can be performed on complex numbers such as: addition,
 * subtraction, multiplication, division, reciprocal and roots.
 * 
 * @author Karlo Bencic
 * 
 */
public class ComplexNumber {

	/** The real part of this complex number. */
	private double real;

	/** The imaginary part of this complex number. */
	private double imaginary;

	/**
	 * Instantiates a new complex number.
	 *
	 * @param real
	 *            the real part
	 * @param imaginary
	 *            the imaginary part
	 */
	public ComplexNumber(double real, double imaginary) {
		this.real = real;
		this.imaginary = imaginary;
	}

	/**
	 * Creates a new complex number where imaginary part is zero.
	 *
	 * @param real
	 *            the real part
	 * @return the complex number
	 */
	public static ComplexNumber fromReal(double real) {
		return new ComplexNumber(real, 0);
	}

	/**
	 * Creates a new complex number where real part is zero.
	 *
	 * @param imaginary
	 *            the imaginary part
	 * @return the complex number
	 */
	public static ComplexNumber fromImaginary(double imaginary) {
		return new ComplexNumber(0, imaginary);
	}

	/**
	 * Creates a new complex number from magnitude and angle.
	 *
	 * @param magnitude
	 *            the magnitude(absolute)
	 * @param angle
	 *            the angle(phase)
	 * @return the complex number
	 */
	public static ComplexNumber fromMagnitudeAndAngle(double magnitude, double angle) {
		double re = magnitude * Math.cos(angle);
		double im = magnitude * Math.sin(angle);
		return new ComplexNumber(re, im);
	}

	/**
	 * Parses the given string to complex number of type x+yi.
	 *
	 * @param s
	 *            the input string, not null
	 * @return the parsed complex number if input is valid, null if not
	 *         processed
	 * @throws IllegalArgumentException
	 *             if s is null
	 */
	public static ComplexNumber parse(String s) {
		if (s == null) {
			throw new IllegalArgumentException();
		}

		s = s.replaceAll(" ", "").toLowerCase();

		try {
			if (s.contains(String.valueOf("+")) || (s.contains(String.valueOf("-")) && s.lastIndexOf('-') > 0)) {
				s = s.replaceAll("i", "");
				String re, im;

				if (s.indexOf('+') > 0) {
					re = s.substring(0, s.indexOf('+'));
					im = s.substring(s.indexOf('+') + 1, s.length());
					return new ComplexNumber(Double.parseDouble(re), Double.parseDouble(im));
				} else if (s.lastIndexOf('-') > 0) {
					re = s.substring(0, s.lastIndexOf('-'));
					im = s.substring(s.lastIndexOf('-') + 1, s.length());
					return new ComplexNumber(Double.parseDouble(re), -Double.parseDouble(im));
				}
			} else {
				if (s.endsWith("i")) {
					s = s.replaceAll("i", "");
					double value = s.isEmpty() ? 1 : Double.parseDouble(s);
					return ComplexNumber.fromImaginary(value);
				}
				return ComplexNumber.fromReal(Double.parseDouble(s));
			}
		} catch (NumberFormatException e) {
			return null;
		}

		return null;
	}

	/**
	 * Gets the real part of this complex number.
	 *
	 * @return the real part
	 */
	public double getReal() {
		return real;
	}

	/**
	 * Gets the imaginary part of this complex number.
	 *
	 * @return the imaginary part
	 */
	public double getImaginary() {
		return imaginary;
	}

	/**
	 * Gets the magnitude, modulus or the absolute value of this complex number.
	 *
	 * @return the magnitude
	 */
	public double getMagnitude() {
		return Math.hypot(real, imaginary);
	}

	/**
	 * Gets the angle, argument or phase of this complex number.
	 *
	 * @return arg(z) - the angle
	 */
	public double getAngle() {
		return Math.atan2(imaginary, real);
	}

	/**
	 * Adds another complex number to the current complex number.
	 *
	 * @param c
	 *            the complex number to be added to the current complex number,
	 *            not null
	 * @return the new complex number, null if not processed
	 * @throws IllegalArgumentException
	 *             if c is null
	 */
	public ComplexNumber add(ComplexNumber c) {
		if (c == null) {
			throw new IllegalArgumentException();
		}
		
		return new ComplexNumber(real + c.real, imaginary + c.imaginary);
	}

	/**
	 * Substracts another complex number from the current complex number.
	 *
	 * @param c
	 *            the complex number to be substracted from the current complex
	 *            number, not null
	 * @return the new complex number, null if not processed
	 * @throws IllegalArgumentException
	 *             if c is null
	 */
	public ComplexNumber sub(ComplexNumber c) {
		if (c == null) {
			throw new IllegalArgumentException();
		}
		
		return new ComplexNumber(real - c.real, imaginary - c.imaginary);
	}

	/**
	 * Multiplies another complex number with the current complex number.
	 *
	 * @param c
	 *            the complex number to be multiplied to the current complex
	 *            number, not null
	 * @return the new complex number, null if not processed
	 * @throws IllegalArgumentException
	 *             if c is null
	 */
	public ComplexNumber mul(ComplexNumber c) {
		if (c == null) {
			throw new IllegalArgumentException();
		}
		
		double re = real * c.real - imaginary * c.imaginary;
		double im = real * c.imaginary + imaginary * c.real;
		return new ComplexNumber(re, im);
	}

	/**
	 * Divides another complex number with the current complex number.
	 *
	 * @param c
	 *            the divisor, not null
	 * @return the new complex number, null if not processed
	 * @throws IllegalArgumentException
	 *             if c is null
	 */
	public ComplexNumber div(ComplexNumber c) {
		if (c == null) {
			throw new IllegalArgumentException();
		}
		
		return mul(c.reciprocal());
	}

	/**
	 * Creates a reciprocal value of this complex number.
	 *
	 * @return the reciprocal complex number
	 */
	public ComplexNumber reciprocal() {
		double scale = real * real + imaginary * imaginary;
		return new ComplexNumber(real / scale, -imaginary / scale);
	}

	/**
	 * Calculates the complex number to the passed integer power.
	 *
	 * @param n
	 *            the power, should be positive
	 * @return the complex number which is (z)^power
	 * @throws IllegalArgumentException
	 *             if n is negative
	 */
	public ComplexNumber power(int n) {
		if (n < 0) {
			throw new IllegalArgumentException();
		}

		ComplexNumber output = new ComplexNumber(real, imaginary);
		for (int i = 1; i < n; i++) {
			double re = real * output.real - imaginary * output.imaginary;
			double im = imaginary * output.real + real * output.imaginary;
			output = new ComplexNumber(re, im);
		}
		
		return output;
	}

	/**
	 * Calculates the complex number roots to the passed integer root.
	 *
	 * @param n
	 *            the root, should be positive
	 * @return the array of complex solutions for the roots of this complex
	 *         number
	 * @throws IllegalArgumentException
	 *             if n is negative
	 */
	public ComplexNumber[] root(int n) {
		if (n <= 0) {
			throw new IllegalArgumentException();
		}

		ComplexNumber[] roots = new ComplexNumber[n];
		for (int i = 0; i < n; i++) {
			roots[i] = sqrt(i);
		}

		return roots;
	}

	/**
	 * Calculates the square root of this complex number
	 *
	 * @param n
	 *            the root iteration
	 * @return sqrt(z)
	 */
	private ComplexNumber sqrt(int k) {
		double re = Math.sqrt(getMagnitude()) * Math.cos((getAngle() + 2 * k * Math.PI) / 2);
		double im = Math.sqrt(getMagnitude()) * Math.sin((getAngle() + 2 * k * Math.PI) / 2);
		return new ComplexNumber(re, im);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		DecimalFormat formatter = new DecimalFormat("#.#####", new DecimalFormatSymbols(Locale.ROOT));

		String re = formatter.format(real);
		String im = formatter.format(imaginary);

		if (real != 0 && imaginary > 0) {
			return re + " + " + im + "i";
		}
		if (real != 0 && imaginary < 0) {
			return re + " - " + formatter.format(-imaginary) + "i";
		}
		if (imaginary == 0) {
			return re;
		}
		if (real == 0) {
			return im + "i";
		}

		return re + " + i*" + im;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(imaginary);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(real);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		return result;
	}

	/**
	 * Checks if two complex numbers are equal. They are equal if their real and
	 * imaginary part are equal.
	 * 
	 * @param obj
	 *            complex number object
	 * @return true if equal, false otherwise
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof ComplexNumber)) {
			return false;
		}
		ComplexNumber other = (ComplexNumber) obj;
		final double epsilon = 1E-8;
		if (Math.abs(imaginary - other.imaginary) > epsilon) {
			return false;
		}
		if (Math.abs(real - other.real) > epsilon) {
			return false;
		}
		return true;
	}
}
