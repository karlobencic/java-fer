package hr.fer.zemris.java.custom.scripting.exec;

import java.util.HashMap;
import java.util.Map;

/**
 * The class {@code ObjectMultistack} allows you to store multiple values for
 * the same key where each value acts like a stack collection.
 * 
 * @author Karlo Bencic
 * 
 */
public class ObjectMultistack {

	/**
	 * Struct that acts like a node of single-linked list.
	 * 
	 * @author Karlo Bencic
	 * 
	 */
	private static class MultistackEntry {
		/** The item. */
		private final ValueWrapper value;

		/** The next entry. */
		private final MultistackEntry next;

		/**
		 * Instantiates a new multistack entry.
		 *
		 * @param value
		 *            the value
		 * @param next
		 *            the next entry
		 */
		public MultistackEntry(ValueWrapper value, MultistackEntry next) {
			this.value = value;
			this.next = next;
		}
	}

	/** The map of stacks. */
	private final Map<String, MultistackEntry> stack = new HashMap<>();

	/**
	 * Pushes the {@code value} on the stack with the given {@code name} as key.
	 *
	 * @param name
	 *            the key of the stack, not null
	 * @param valueWrapper
	 *            the value to be pushed, not null
	 * @throws IllegalArgumentException
	 *             if name is null or valueWrapper is null
	 */
	public void push(String name, ValueWrapper valueWrapper) {
		if (name == null || valueWrapper == null) {
			throw new IllegalArgumentException("Arguments can't be null.");
		}

		MultistackEntry next = stack.containsKey(name) ? stack.get(name) : null;
		MultistackEntry entry = new MultistackEntry(valueWrapper, next);;
		stack.put(name, entry);
	}

	/**
	 * Pops the {@code value} on the stack with the given {@code name} as key.
	 *
	 * @param name
	 *            the key of the stack
	 * @return the {@link ValueWrapper} value that is popped from the stack.
	 * @throws IllegalArgumentException
	 *             if stack is empty
	 */
	public ValueWrapper pop(String name) {
		if (isEmpty(name)) {
			throw new IllegalArgumentException("Stack '" + name + "' is empty.");
		}

		ValueWrapper value = stack.get(name).value;
		MultistackEntry entry = stack.get(name);

		if (entry.next != null) {
			stack.put(name, entry.next);
		} else {
			stack.remove(name);
		}
		return value;
	}

	/**
	 * Peeks at the last {@code value} on the stack with the given {@code name}
	 * as key.
	 *
	 * @param name
	 *            the key of the stack
	 * @return the {@link ValueWrapper} value that is peeked from the stack.
	 */
	public ValueWrapper peek(String name) {
		if (isEmpty(name)) {
			throw new IllegalArgumentException("Stack '" + name + "' is empty.");
		}

		return stack.get(name).value;
	}

	/**
	 * Checks if the stack with the given key is empty.
	 *
	 * @param name
	 *            the key of the stack
	 * @return true, if is empty
	 */
	public boolean isEmpty(String name) {
		return !stack.containsKey(name);
	}

}
