package hr.fer.zemris.java.hw05.demo2;

/**
 * This program represents a demo usage of the {@code PrimesCollection} class in
 * a nested foreach loops.
 * 
 * @author Karlo Bencic
 * 
 */
public class PrimesDemo2 {

	/**
	 * The main method.
	 *
	 * @param args
	 *            the arguments
	 */
	public static void main(String[] args) {
		PrimesCollection primesCollection = new PrimesCollection(2);
		for (Integer prime : primesCollection) {
			for (Integer prime2 : primesCollection) {
				System.out.println("Got prime pair: " + prime + ", " + prime2);
			}
		}
	}

}
