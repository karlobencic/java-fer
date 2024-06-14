package hr.fer.zemris.java.custom.collections;

/**
 * This class represents a last-in-first-out (LIFO) stack of objects. It
 * provides usual push and pop operations, as well as a method to peek at the
 * top object on the stack and a method to check for whether the stack is empty.
 * 
 * @author Karlo Bencic
 * 
 */
public class ObjectStack {

	/** The storage collection. */
	private final ArrayIndexedCollection collection = new ArrayIndexedCollection();

	/**
	 * Checks if the stack is empty empty.
	 *
	 * @return true, if is empty
	 */
	public boolean isEmpty() {
		return collection.isEmpty();
	}

	/**
	 * Gets the size of the stack.
	 *
	 * @return the size
	 */
	public int size() {
		return collection.size();
	}

	/**
	 * Pushes the object to the top of the stack.
	 *
	 * @param value
	 *            the value to be pushed on the stack, not null
	 */
	public void push(Object value) {
		if (value == null) {
			return;
		}

		collection.add(value);
	}

	/**
	 * Gets the object at the top of the stack and removes it.
	 *
	 * @return the object at the top of the stack
	 * @throws EmptyStackException
	 *             if the stack is empty
	 */
	public Object pop() {
		if (isEmpty()) {
			throw new EmptyStackException("Stack is empty.");
		}

		Object element = peek();
		collection.remove(getLastIndex());
		return element;
	}

	/**
	 * Gets the top object on the stack without removing it.
	 *
	 * @return the object at the top of the stack
	 * @throws EmptyStackException
	 *             if the stack is empty
	 */
	public Object peek() {
		if (isEmpty()) {
			throw new EmptyStackException("Stack is empty.");
		}

		return collection.get(getLastIndex());
	}

	/**
	 * Clears the stack.
	 */
	public void clear() {
		collection.clear();
	}

	/**
	 * Gets the index of the top object in the stack.
	 *
	 * @return the last index
	 */
	private int getLastIndex() {
		return size() - 1;
	}
}
