package hr.fer.zemris.math;

/**
 * The {@code ComplexRootedPolynomial} class represents a {@link Complex} number
 * by its roots.
 * 
 * @author Karlo Bencic
 * 
 */
public class ComplexRootedPolynomial {

	/** The complex roots of this polynom. */
	private Complex[] roots;

	/**
	 * Instantiates a new complex rooted polynomial.
	 *
	 * @param roots
	 *            the defined roots
	 */
	public ComplexRootedPolynomial(Complex... roots) {
		this.roots = roots;
	}

	/**
	 * Computes polynomial value at given point z.
	 *
	 * @param z
	 *            the point for which you want to calculate polynomial value.
	 * @return polynomial value at given point z.
	 */
	public Complex apply(Complex z) {
		Complex result = Complex.ONE;
		for (Complex root : roots) {
			result = result.multiply(z.sub(root));
		}
		return result;
	}

	/**
	 * Converts this {@code ComplexRootedPolynomial} to
	 * {@code ComplexPolynomial} type
	 *
	 * @return {@link ComplexRootedPolynomial} in {@link ComplexPolynomial} type
	 */
	public ComplexPolynomial toComplexPolynom() {
		ComplexPolynomial result = new ComplexPolynomial(Complex.ONE);
		for (Complex root : roots) {
			result = result.multiply(new ComplexPolynomial(Complex.ONE, root.negate()));
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
		if (roots.length == 0) {
			return "(no roots)";
		}

		StringBuilder result = new StringBuilder();
		for (Complex root : roots) {
			result.append("[z-(" + root + ")]");
		}

		return result.toString();
	}

	/**
	 * Finds the index of the closest root for given complex number z that is
	 * within specified threshold.
	 *
	 * @param z
	 *            the complex number for which you want to calculate closest
	 *            root.
	 * @param threshold
	 *            the threshold inside which roots will be calculated.
	 * @return the index of the closest root if it's within threshold, -1
	 *         otherwise.
	 */
	public int indexOfClosestRootFor(Complex z, double threshold) {
		int index = -1;
		double minDistance = Double.MAX_VALUE;

		for (int i = 0; i < roots.length; i++) {
			double distance = z.sub(roots[i]).module();
			if (Double.compare(distance, threshold) < 0 && Double.compare(distance, minDistance) < 0) {
				minDistance = distance;
				index = i;
			}
		}

		return index;
	}

	/**
	 * Returns the order of this polynom.
	 *
	 * @return the order of this polynom
	 */
	public int order() {
		return roots.length;
	}
}
