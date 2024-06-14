package hr.fer.zemris.math;

import java.util.ArrayList;
import java.util.List;

/**
 * The {@code ComplexPolynomial} class represents a {@link Complex} number in
 * polynomial form.
 * 
 * @author Karlo Bencic
 * 
 */
public class ComplexPolynomial {

	/** The complex factors. */
	private Complex[] factors;

	/**
	 * Instantiates a new complex polynomial.
	 *
	 * @param factors
	 *            the defined factors
	 */
	public ComplexPolynomial(Complex... factors) {
		this.factors = factors;
	}

	/**
	 * Returns the order of this polynom.
	 *
	 * @return the order
	 */
	public short order() {
		return (short) (factors.length - 1);
	}

	/**
	 * Multiplies this polynomial with {@code p}.
	 *
	 * @param p
	 *            the multiplier
	 * @return the result of multiplication
	 */
	public ComplexPolynomial multiply(ComplexPolynomial p) {
		Complex[] multipliedFactors = new Complex[this.factors.length + p.factors.length - 1];

		for (int i = 0; i < multipliedFactors.length; i++) {
			multipliedFactors[i] = Complex.ZERO;
		}

		for (int i = 0; i < this.factors.length; i++) {
			for (int j = 0; j < p.factors.length; j++) {
				Complex multiplicationResult = this.factors[i].multiply(p.factors[j]);
				multipliedFactors[i + j] = multipliedFactors[i + j].add(multiplicationResult);
			}
		}

		return new ComplexPolynomial(multipliedFactors);
	}

	/**
	 * Computes first derivative of this {@code ComplexPolynomial}.
	 *
	 * @return the first derivative
	 */
	public ComplexPolynomial derive() {
		Complex[] derivedFactors = new Complex[factors.length - 1];

		for (int i = 0, length = order(); i < length; i++) {
			Complex factor = new Complex(length - i, 0);
			derivedFactors[i] = factors[i].multiply(factor);
		}

		return new ComplexPolynomial(derivedFactors);
	}

	/**
	 * Computes polynomial value at given point z.
	 *
	 * @param z
	 *            the point for which you want to calculate polynomial value.
	 * @return the polynomial value at given point z.
	 */
	public Complex apply(Complex z) {
		Complex result = Complex.ZERO;
		Complex exponent = Complex.ONE;

		for (int i = factors.length - 1; i >= 0; i--) {
			result = result.add(exponent.multiply(factors[i]));
			exponent = exponent.multiply(z);
		}

		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		int order = order();
		List<String> polynomFactors = new ArrayList<String>();

		for (Complex factor : factors) {
			if (factor.equals(Complex.ZERO)) {
				continue;
			}

			StringBuilder factorString = new StringBuilder("(" + factor + ")");

			if (order == 1) {
				factorString.append("z");
			} else if (order > 1) {
				factorString.append("z^" + order);
			}

			polynomFactors.add(factorString.toString());
			order--;
		}

		if (polynomFactors.size() == 0) {
			return "(no factors)";
		}

		StringBuilder result = new StringBuilder(polynomFactors.get(0));
		for (int i = 1; i < polynomFactors.size(); i++) {
			result.append(" + ");
			result.append(polynomFactors.get(i));
		}

		return result.toString();
	}
}
