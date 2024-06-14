package hr.fer.zemris.java.custom.collections;

/**
 * This class represents a collection of objects. It can't be instantiated on
 * it's own.
 * 
 * @author Karlo Bencic
 * 
 */
public class Collection {

	/**
	 * Instantiates a new collection.
	 */
	protected Collection() {
	}

	/**
	 * Checks if this collection is empty.
	 *
	 * @return true, if is empty
	 */
	public boolean isEmpty() {
		return size() == 0;
	}

	/**
	 * Gets the size of this collection.
	 *
	 * @return the size
	 */
	public int size() {
		return 0;
	}

	/**
	 * Adds the given object to the collection.
	 *
	 * @param value
	 *            the value to be added, not null
	 */
	public void add(Object value) {
	}

	/**
	 * Checks if the collection contains the given value.
	 *
	 * @param value
	 *            the value to be checked
	 * @return true, if successful
	 */
	public boolean contains(Object value) {
		return false;
	}

	/**
	 * Removes the given value from the collection.
	 *
	 * @param value
	 *            the value to be removed
	 * @return true, if successful
	 */
	public boolean remove(Object value) {
		return false;
	}

	/**
	 * Converts the current collection to the array of objects.
	 *
	 * @return the object[] array
	 * @throws UnsupportedOperationException
	 *             if called from the {@code Collection} class
	 */
	public Object[] toArray() {
		throw new UnsupportedOperationException();
	}

	/**
	 * Iterates through all of the elements of the collection and applies the
	 * action delegated from the provided processor.
	 *
	 * @param processor
	 *            the processor, not null
	 */
	public void forEach(Processor processor) {
	}

	/**
	 * Adds all of the elements of the passed collection to the current one.
	 *
	 * @param other
	 *            the other collection, not null
	 */
	public void addAll(Collection other) {
		if (other == null) {
			return;
		}

		other.forEach(new Processor() {
			public void process(Object value) {
				add(value);
			}
		});
	}

	/**
	 * Clears the collection.
	 */
	public void clear() {
	}
}