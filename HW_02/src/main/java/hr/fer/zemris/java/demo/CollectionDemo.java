package hr.fer.zemris.java.demo;

import java.util.Arrays;

import hr.fer.zemris.java.custom.collections.*;

/**
 * This program is a simple usage demo of the {@code ArrayIndexedCollection} and
 * {@code LinkedListIndexedCollection} classes.
 * 
 * @author Karlo Bencic
 * 
 */
public class CollectionDemo {

	/**
	 * The main method.
	 *
	 * @param args
	 *            the arguments
	 */
	public static void main(String[] args) {
		ArrayIndexedCollection col1 = new ArrayIndexedCollection(2);
		col1.add(new Integer(20));
		col1.add("New York");
		col1.add("San Francisco"); // here the internal array is reallocated to 4
		System.out.println(col1.contains("New York")); // writes: true
		col1.remove(1); // removes "New York"; shifts "San Francisco" to position 1
		System.out.println(col1.get(1)); // writes: "San Francisco"
		System.out.println(col1.size()); // writes: 2
		col1.add("Los Angeles");
		LinkedListIndexedCollection col2 = new LinkedListIndexedCollection(col1);

		class P extends Processor {
			public void process(Object o) {
				System.out.println(o);
			}
		};

		System.out.println("col1 elements:");
		col1.forEach(new P());
		System.out.println("col1 elements again:");
		System.out.println(Arrays.toString(col1.toArray()));
		System.out.println("col2 elements:");
		col2.forEach(new P());
		System.out.println("col2 elements again:");
		System.out.println(Arrays.toString(col2.toArray()));
		System.out.println(col1.contains(col2.get(1))); // true
		System.out.println(col2.contains(col1.get(1))); // true
		col1.remove(new Integer(20)); // removes 20 from collection (at position 0).
	}
}
