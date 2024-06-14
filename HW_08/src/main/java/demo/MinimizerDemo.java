package demo;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import hr.fer.zemris.bf.qmc.Minimizer;

/**
 * This program represents a simple demo usage of the {@link Minimizer} class.
 * It also prints the log of the minimization process in the console.
 * 
 * @author Karlo Bencic
 * 
 */
public class MinimizerDemo {

	/**
	 * The main method.
	 *
	 * @param args
	 *            the arguments
	 */
	public static void main(String[] args) {
		Set<Integer> minterms = new HashSet<>(Arrays.asList(0, 1, 3, 10, 11, 14, 15));
		Set<Integer> dontcares = new HashSet<>(Arrays.asList(4, 6));
		new Minimizer(minterms, dontcares, Arrays.asList("A", "B", "C", "D"));
	}

}
