package hr.fer.zemris.java.custom.collections;

import java.util.Arrays;

/**
 * This class reprensents a collection of objects using an array as internal
 * storage. Default capacity of the collection is 16 unless instantiated with
 * the provided initial capacity.
 * 
 * @author Karlo Bencic
 * 
 */
public class ArrayIndexedCollection extends Collection {

	/** The collection size. */
	private int size;

	/** The collection capacity. */
	private int capacity;

	/** The collection elements. */
	private Object[] elements;

	/** The default size. */
	private final int DEFAULT_SIZE = 16;

	/**
	 * Instantiates a new array indexed collection whose capacity is 16.
	 */
	public ArrayIndexedCollection() {
		capacity = DEFAULT_SIZE;
		elements = new Object[DEFAULT_SIZE];
	}

	/**
	 * Instantiates a new array indexed collection with a given capacity
	 *
	 * @param initialCapacity
	 *            the initial capacity
	 */
	public ArrayIndexedCollection(int initialCapacity) {
		if (initialCapacity < 1) {
			throw new IllegalArgumentException();
		}

		capacity = initialCapacity;
		elements = new Object[initialCapacity];
	}

	/**
	 * Instantiates a new array indexed collection and copies all the elements
	 * of other collection to this one.
	 *
	 * @param other
	 *            the other collection, not null
	 */
	public ArrayIndexedCollection(Collection other) {
		this();
		addAll(other);
	}

	/**
	 * Instantiates a new array indexed collection with a given capacity and
	 * copies all the elements of other collection to this one.
	 *
	 * @param other
	 *            the other collection, not null
	 * @param initialCapacity
	 *            the initial capacity
	 */
	public ArrayIndexedCollection(Collection other, int initialCapacity) {
		this(initialCapacity);
		addAll(other);
	}

	/**
	 * Adds the given object to the collection. The average complexity of this
	 * method is O(1).
	 *
	 * @param value
	 *            the value to be added, not null
	 * @throws IllegalArgumentException
	 *             if value is null
	 */
	@Override
	public void add(Object value) {
		if (value == null) {
			throw new IllegalArgumentException();
		}

		if (size == capacity) {
			increaseCapacity(2);
		}

		elements[size++] = value;
	}

	/**
	 * Gets the element on the given position in the collection. The average
	 * complexity of this method is O(1).
	 *
	 * @param index
	 *            the index, position
	 * @return the object element
	 * @throws IndexOutOfBoundsException
	 *             if index is out of bounds
	 */
	public Object get(int index) {
		if (index < 0 || index >= size) {
			throw new IndexOutOfBoundsException();
		}

		return elements[index];
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see hr.fer.zemris.java.custom.collections.Collection#clear()
	 */
	@Override
	public void clear() {
		for (int i = 0; i < size; i++) {
			elements[i] = null;
		}
		size = 0;
	}

	/**
	 * Inserts the value to the collection at the given position. All elements
	 * from that position onwards are shifted one place right. The average
	 * complexity of this method is O(n).
	 *
	 * @param value
	 *            the value, not null
	 * @param position
	 *            the position
	 * @throws IllegalArgumentException
	 *             if value is null
	 * @throws IndexOutOfBoundsException
	 *             if position is out of bounds
	 */
	public void insert(Object value, int position) {
		if (value == null) {
			throw new IllegalArgumentException();
		}
		if (position < 0 || position > size) {
			throw new IndexOutOfBoundsException();
		}

		if (size == capacity) {
			increaseCapacity(2);
		}

		for (int i = size; i > position; i--) {
			elements[i] = elements[i - 1];
		}
		elements[position] = value;
		size++;
	}

	/**
	 * Returns the index of a given value inside the collection. The average
	 * complexity of this method is O(n).
	 *
	 * @param value
	 *            the value
	 * @return the index of the object inside the collection, -1 otherwise
	 */
	public int indexOf(Object value) {
		if (value == null) {
			return -1;
		}
		for (int i = 0; i < size; i++) {
			if (elements[i].equals(value)) {
				return i;
			}
		}

		return -1;
	}

	/**
	 * Removes the element at a given position inside the collection.
	 *
	 * @param index
	 *            the index, position
	 * @throws IndexOutOfBoundsException
	 *             if index is out of bounds
	 */
	public void remove(int index) {
		if (index < 0 || index >= size) {
			throw new IndexOutOfBoundsException();
		}

		for (int i = index; i < size; i++) {
			elements[i] = elements[i + 1];
		}
		elements[size--] = null;
	}

	@Override
	public int size() {
		return size;
	}

	@Override
	public boolean contains(Object value) {
		return indexOf(value) != -1;
	}

	@Override
	public boolean remove(Object value) {
		if (value == null) {
			return false;
		}

		for (int i = 0; i < size; i++) {
			if (elements[i].equals(value)) {
				remove(i);
				return true;
			}
		}

		return false;
	}

	@Override
	public Object[] toArray() {
		return Arrays.copyOf(elements, size);
	}

	/**
	 * Iterates through all of the elements of the collection and applies the
	 * action delegated from the provided processor.
	 *
	 * @param processor
	 *            the processor, not null
	 * @throws IllegalArgumentException
	 *             if processor is null
	 */
	@Override
	public void forEach(Processor processor) {
		if (processor == null) {
			throw new IllegalArgumentException();
		}

		for (int i = 0; i < size; i++) {
			processor.process(elements[i]);
		}
	}

	/**
	 * Increases the array capacity.
	 *
	 * @param factor
	 *            the factor
	 * @throws IllegalArgumentException
	 *             if factor is less than 2
	 */
	private void increaseCapacity(int factor) {
		if (factor < 2) {
			throw new IllegalArgumentException();
		}

		capacity = elements.length * factor;
		elements = Arrays.copyOf(elements, capacity);
	}
}
