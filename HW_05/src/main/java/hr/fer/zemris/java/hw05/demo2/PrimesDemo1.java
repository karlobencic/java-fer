package hr.fer.zemris.java.hw05.demo2;

/**
 * This program represents a demo usage of the {@code PrimesCollection} class in
 * a simple foreach loop.
 * 
 * @author Karlo Bencic
 * 
 */
public class PrimesDemo1 {

	/**
	 * The main method.
	 *
	 * @param args
	 *            the arguments
	 */
	public static void main(String[] args) {
		PrimesCollection primesCollection = new PrimesCollection(5);
		for (Integer prime : primesCollection) {
			System.out.println("Got prime: " + prime);
		}
	}
}
