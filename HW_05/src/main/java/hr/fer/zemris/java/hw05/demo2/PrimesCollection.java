package hr.fer.zemris.java.hw05.demo2;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * The class {@code PrimesCollection} represents a collection of prime numbers.
 * All numbers are calculated dynamicaly. The amount of prime numbers to be
 * calculated is given in the constructor of this object.
 * 
 * @author Karlo Bencic
 * 
 */
public class PrimesCollection implements Iterable<Integer> {

	/** The number of primes. */
	private int numOfPrimes;

	/**
	 * Instantiates a new primes collection.
	 *
	 * @param numOfPrimes
	 *            the number of primes
	 */
	public PrimesCollection(int numOfPrimes) {
		this.numOfPrimes = numOfPrimes;
	}

	/**
	 * Gets the nth prime number.
	 *
	 * @param n
	 *            the n
	 * @return the prime number
	 */
	private int getPrime(int n) {
		int candidate = 2;
		for (int i = 0; i < n; candidate++) {
			if (isPrime(candidate)) {
				i++;
			}
		}
		return candidate - 1;
	}

	/**
	 * Checks if is the number is prime.
	 *
	 * @param n
	 *            the number
	 * @return true, if is prime
	 */
	private boolean isPrime(int n) {
		for (int i = 2; i < n; i++) {
			if (n % i == 0) {
				return false;
			}
		}
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Iterable#iterator()
	 */
	@Override
	public Iterator<Integer> iterator() {
		return new PrimesCollectionIterator();
	}

	/**
	 * The class {@code PrimesCollectionIterator} represents the iterator
	 * implementation for the {@code PrimesCollection} class. It gets the next
	 * prime number dynamicaly.
	 * 
	 * @author Karlo Bencic
	 * 
	 */
	private class PrimesCollectionIterator implements Iterator<Integer> {

		/** The current iteration index. */
		private int current;

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.util.Iterator#hasNext()
		 */
		@Override
		public boolean hasNext() {
			return current < numOfPrimes;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.util.Iterator#next()
		 */
		@Override
		public Integer next() {
			if (!hasNext()) {
				throw new NoSuchElementException();
			}

			return getPrime(++current);
		}
	}
}
