package hr.fer.zemris.math;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * The {@code Complex} class represents the data type for complex numbers. It
 * includes basic operations that can be performed on complex numbers such as:
 * addition, subtraction, multiplication, division, reciprocal and roots.
 * 
 * @author Karlo Bencic
 * 
 */
public class Complex {

	/** The real part. */
	private double re;

	/** The imaginary part. */
	private double im;

	/** The zero complex number. */
	public static final Complex ZERO = new Complex(0, 0);

	/** The one complex number. */
	public static final Complex ONE = new Complex(1, 0);

	/** The negative one complex number. */
	public static final Complex ONE_NEG = new Complex(-1, 0);

	/** The imaginary one complex number. */
	public static final Complex IM = new Complex(0, 1);

	/** The negative imaginary complex number. */
	public static final Complex IM_NEG = new Complex(0, -1);

	/**
	 * Instantiates a new complex number with real and imaginary part set to
	 * zero.
	 */
	public Complex() {
	}

	/**
	 * Instantiates a new complex number.
	 *
	 * @param re
	 *            the real part
	 * @param im
	 *            the imaginary part
	 */
	public Complex(double re, double im) {
		this.re = re;
		this.im = im;
	}

	/**
	 * Gets the magnitude, modulus or the absolute value of this complex number.
	 *
	 * @return the magnitude
	 */
	public double module() {
		return Math.hypot(re, im);
	}

	/**
	 * Gets the angle, argument or phase of this complex number.
	 *
	 * @return arg(z) - the angle
	 */
	public double getAngle() {
		return Math.atan2(im, re);
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
	public Complex multiply(Complex c) {
		if (c == null) {
			throw new IllegalArgumentException();
		}

		double real = re * c.re - im * c.im;
		double imaginary = re * c.im + im * c.re;
		return new Complex(real, imaginary);
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
	public Complex divide(Complex c) {
		if (c == null) {
			throw new IllegalArgumentException();
		}

		return multiply(c.reciprocal());
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
	public Complex add(Complex c) {
		if (c == null) {
			throw new IllegalArgumentException();
		}

		return new Complex(re + c.re, im + c.im);
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
	public Complex sub(Complex c) {
		if (c == null) {
			throw new IllegalArgumentException();
		}

		return new Complex(re - c.re, im - c.im);
	}

	/**
	 * Creates a negated value of this complex number.
	 *
	 * @return the negated complex number
	 */
	public Complex negate() {
		return multiply(ONE_NEG);
	}

	/**
	 * Creates a reciprocal value of this complex number.
	 *
	 * @return the reciprocal complex number
	 */
	public Complex reciprocal() {
		double scale = re * re + im * im;
		return new Complex(re / scale, -im / scale);
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
	public Complex power(int n) {
		if (n < 0) {
			throw new IllegalArgumentException();
		}

		Complex output = new Complex(re, im);
		for (int i = 1; i < n; i++) {
			double real = re * output.re - im * output.im;
			double imaginary = im * output.re + re * output.im;
			output = new Complex(real, imaginary);
		}
		return output;
	}

	/**
	 * Calculates the complex number roots to the passed integer root.
	 *
	 * @param n
	 *            the root, should be positive
	 * @return the list of complex solutions for the roots of this complex
	 *         number
	 * @throws IllegalArgumentException
	 *             if n is negative
	 */
	public List<Complex> root(int n) {
		if (n <= 0) {
			throw new IllegalArgumentException();
		}

		List<Complex> roots = new ArrayList<>(n);
		for (int i = 0; i < n; i++) {
			roots.add(sqrt(i));
		}

		return roots;
	}

	/**
	 * Calculates the square root of this complex number.
	 *
	 * @param k
	 *            the root iteration
	 * @return sqrt(z)
	 */
	private Complex sqrt(int k) {
		double re = Math.sqrt(module()) * Math.cos((getAngle() + 2 * k * Math.PI) / 2);
		double im = Math.sqrt(module()) * Math.sin((getAngle() + 2 * k * Math.PI) / 2);
		return new Complex(re, im);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		DecimalFormat formatter = new DecimalFormat("#.#####", new DecimalFormatSymbols(Locale.ROOT));

		String real = formatter.format(re);
		String imaginary = formatter.format(im);

		if (re != 0 && im > 0) {
			return real + " + " + imaginary + "i";
		}
		if (re != 0 && im < 0) {
			return real + " - " + formatter.format(-im) + "i";
		}
		if (im == 0) {
			return real;
		}
		if (re == 0) {
			return imaginary + "i";
		}

		return real + " + i*" + imaginary;
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
	public static Complex parse(String s) {
		if (s == null) {
			throw new IllegalArgumentException();
		}

		s = s.replaceAll(" ", "").toLowerCase();

		try {
			if (s.contains("+") || (s.contains("-") && s.lastIndexOf('-') > 0)) {
				s = s.replaceAll("i", "");
				String re, im;

				if (s.indexOf('+') > 0) {
					re = s.substring(0, s.indexOf('+'));
					im = s.substring(s.indexOf('+') + 1, s.length());
					return new Complex(Double.parseDouble(re), Double.parseDouble(im));
				} else if (s.lastIndexOf('-') > 0) {
					re = s.substring(0, s.lastIndexOf('-'));
					im = s.substring(s.lastIndexOf('-') + 1, s.length());
					return new Complex(Double.parseDouble(re), -Double.parseDouble(im));
				}
			} else {
				if (s.endsWith("i")) {
					s = s.replaceAll("i", "");
					double value = s.isEmpty() ? 1 : Double.parseDouble(s);
					return new Complex(0, value);
				}
				return new Complex(Double.parseDouble(s), 0);
			}
		} catch (NumberFormatException e) {
			return null;
		}

		return null;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(im);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(re);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof Complex)) {
			return false;
		}
		Complex other = (Complex) obj;
		final double epsilon = 1E-8;
		if (Math.abs(im - other.im) > epsilon) {
			return false;
		}
		if (Math.abs(re - other.re) > epsilon) {
			return false;
		}
		return true;
	}
}
