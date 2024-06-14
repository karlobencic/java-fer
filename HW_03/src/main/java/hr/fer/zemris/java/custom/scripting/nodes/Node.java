package hr.fer.zemris.java.custom.scripting.nodes;

import hr.fer.zemris.java.custom.collections.ArrayIndexedCollection;

/**
 * The class Node represents a single node constructed by the parser. This is
 * the base class for other specific nodes.
 * 
 * @author Karlo Bencic
 * 
 */
public class Node {

	/** The collection of child nodes. */
	private ArrayIndexedCollection collection;

	/**
	 * Adds the child node.
	 *
	 * @param child
	 *            the child
	 */
	public void addChildNode(Node child) {
		if (child == null) {
			throw new IllegalArgumentException("Child can't be null.");
		}

		if (collection == null) {
			collection = new ArrayIndexedCollection();
		}

		collection.add(child);
	}

	/**
	 * Gets the number of (direct) children.
	 *
	 * @return the number of children
	 */
	public int numberOfChildren() {
		return collection == null ? 0 : collection.size();
	}

	/**
	 * Gets the child.
	 *
	 * @param index
	 *            the index
	 * @return the child
	 */
	public Node getChild(int index) {
		if (index < 0 || index > collection.size()) {
			throw new IndexOutOfBoundsException();
		}
		return (Node) collection.get(index);
	}
}
