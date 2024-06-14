package hr.fer.zemris.bf.utils;

/**
 * The {@code Tuple} class represents a triple value.
 *
 * @param <T>
 *            the first type
 * @param <U>
 *            the second type
 * @param <V>
 *            the third type
 *            
 * @author Karlo Bencic
 */
public class Tuple<T, U, V> {

	/** The first. */
	private final T first;
	
	/** The second. */
	private final U second;
	
	/** The third. */
	private final V third;
	
	/**
	 * Instantiates a new tuple.
	 *
	 * @param first
	 *            the first
	 * @param second
	 *            the second
	 * @param third
	 *            the third
	 */
	public Tuple(T first, U second, V third) {
		this.first = first;
		this.second = second;
		this.third = third;
	}

	/**
	 * Gets the first.
	 *
	 * @return the first
	 */
	public T getFirst() {
		return first;
	}

	/**
	 * Gets the second.
	 *
	 * @return the second
	 */
	public U getSecond() {
		return second;
	}

	/**
	 * Gets the third.
	 *
	 * @return the third
	 */
	public V getThird() {
		return third;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((first == null) ? 0 : first.hashCode());
		result = prime * result + ((second == null) ? 0 : second.hashCode());
		result = prime * result + ((third == null) ? 0 : third.hashCode());
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
		if (!(obj instanceof Tuple)) {
			return false;
		}
		Tuple<?, ?, ?> other = (Tuple<?, ?, ?>) obj;
		if (first == null) {
			if (other.first != null) {
				return false;
			}
		} else if (!first.equals(other.first)) {
			return false;
		}
		if (second == null) {
			if (other.second != null) {
				return false;
			}
		} else if (!second.equals(other.second)) {
			return false;
		}
		if (third == null) {
			if (other.third != null) {
				return false;
			}
		} else if (!third.equals(other.third)) {
			return false;
		}
		return true;
	}
}