package hr.fer.zemris.java.demo;

import hr.fer.zemris.java.hw02.ComplexNumber;

/**
 * This program is a simple usage demo of the {@code ComplexNumber} class.
 * 
 * @author Karlo Bencic
 * 
 */
public class ComplexDemo {

	/**
	 * The main method.
	 *
	 * @param args
	 *            the arguments
	 */
	public static void main(String[] args) {
		ComplexNumber c1 = new ComplexNumber(2, 3);
		ComplexNumber c2 = ComplexNumber.parse("2.5-3i");
		ComplexNumber c3 = c1.add(ComplexNumber.fromMagnitudeAndAngle(2, 1.57)).div(c2).power(3).root(2)[1];

		System.out.println(c3);
	}
}
