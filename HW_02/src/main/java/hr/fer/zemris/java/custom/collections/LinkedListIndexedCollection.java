package hr.fer.zemris.java.custom.collections;

/**
 * This class reprensents a collection of objects using a doubly linked list as
 * internal storage.
 * 
 * @author Karlo Bencic
 * 
 */
public class LinkedListIndexedCollection extends Collection {

	/**
	 * A struct which represents a list node.
	 * 
	 * @author Karlo Bencic
	 * 
	 */
	private static class ListNode {

		/** The item. */
		private Object item;

		/** The next node. */
		private ListNode next;

		/** The previous node. */
		private ListNode prev;

		/**
		 * Instantiates a new list node.
		 *
		 * @param item
		 *            the item value
		 * @param next
		 *            the next node
		 * @param prev
		 *            the previous node
		 */
		public ListNode(Object item, ListNode next, ListNode prev) {
			this.item = item;
			this.next = next;
			this.prev = prev;
		}
	}

	/** The collection size. */
	private int size;

	/** The first node of the collection. */
	private ListNode first;

	/** The last node of the collection. */
	private ListNode last;

	/**
	 * Instantiates a new linked list indexed collection.
	 */
	public LinkedListIndexedCollection() {
	}

	/**
	 * Instantiates a new linked list indexed collection and copies all the
	 * elements of other collection to this one.
	 *
	 * @param other
	 *            the other collection, not null
	 */
	public LinkedListIndexedCollection(Collection other) {
		addAll(other);
	}

	/**
	 * Gets the element on the given position in the collection. The average
	 * complexity of this method is O(n/2 + 1).
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

		return node(index).item;
	}

	/**
	 * Inserts the value to the collection at the given position. All elements
	 * from that position onwards are shifted one place right. The average
	 * complexity of this method is O(1).
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

		if (position == size) {
			add(value);
			return;
		}

		ListNode node = node(position);
		ListNode prev = node.prev;
		ListNode newNode = new ListNode(value, node, prev);
		node.prev = newNode;

		if (prev == null) {
			first = newNode;
		} else {
			prev.next = newNode;
		}

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

		int index = 0;
		for (ListNode node = first; node != null; node = node.next, index++) {
			if (value.equals(node.item)) {
				return index;
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

		unlink(node(index));
		size--;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see hr.fer.zemris.java.custom.collections.Collection#size()
	 */
	@Override
	public int size() {
		return size;
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

		ListNode temp = new ListNode(value, null, last);
		if (last != null) {
			last.next = temp;
		}
		last = temp;
		if (first == null) {
			first = temp;
		}

		size++;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see hr.fer.zemris.java.custom.collections.Collection#contains(java.lang.
	 * Object)
	 */
	@Override
	public boolean contains(Object value) {
		return indexOf(value) != -1;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * hr.fer.zemris.java.custom.collections.Collection#remove(java.lang.Object)
	 */
	@Override
	public boolean remove(Object value) {
		if (value == null) {
			return false;
		}

		for (ListNode node = first; node != null; node = node.next) {
			if (value.equals(node.item)) {
				unlink(node);
				size--;
				return true;
			}
		}
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see hr.fer.zemris.java.custom.collections.Collection#toArray()
	 */
	@Override
	public Object[] toArray() {
		Object[] result = new Object[size];

		int i = 0;
		for (ListNode node = first; node != null; node = node.next) {
			result[i++] = node.item;
		}

		return result;
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

		for (ListNode node = first; node != null; node = node.next) {
			processor.process(node.item);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see hr.fer.zemris.java.custom.collections.Collection#clear()
	 */
	@Override
	public void clear() {
		for (ListNode node = first; node != null;) {
			ListNode next = node.next;
			node.item = null;
			node.next = null;
			node.prev = null;
			node = next;
		}
		first = last = null;
		size = 0;
	}

	/**
	 * Unlinks the node inside this collection.
	 *
	 * @param node
	 *            the node
	 */
	private void unlink(ListNode node) {
		if (node.prev == null) {
			first = node.next;
			first.prev = null;
		} else {
			node.prev.next = node.next;
			node.next.prev = node.prev;
		}
	}

	/**
	 * Gets the node inside this collection at the given position.
	 *
	 * @param index
	 *            the index, position
	 * @return the list node
	 */
	private ListNode node(int index) {
		ListNode temp;
		if (index < size / 2) {
			temp = first;
			for (int i = 0; i < index; i++) {
				temp = temp.next;
			}
		} else {
			temp = last;
			for (int i = size - 1; i > index; i--) {
				temp = temp.prev;
			}
		}
		return temp;
	}
}