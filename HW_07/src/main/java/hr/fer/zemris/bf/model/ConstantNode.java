package hr.fer.zemris.bf.model;

/**
 * The {@code ConstantNode} class represents a constant node. It contains the
 * boolean value of this variable and doesn't have any children nodes.
 * 
 * @author Karlo Bencic
 * 
 */
public class ConstantNode implements Node {
	
	/** The node value. */
	private final boolean value;
	
	/**
	 * Instantiates a new constant node.
	 *
	 * @param value
	 *            the node value
	 */
	public ConstantNode(boolean value) {
		this.value = value;
	}

	/* (non-Javadoc)
	 * @see hr.fer.zemris.bf.model.Node#accept(hr.fer.zemris.bf.model.NodeVisitor)
	 */
	public void accept(NodeVisitor visitor) {
		visitor.visit(this);
	}

	/**
	 * Gets the value of this node.
	 *
	 * @return the value
	 */
	public boolean getValue() {
		return value;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return String.valueOf(getValue());
	}
	
	/* (non-Javadoc)
	 * @see hr.fer.zemris.bf.model.Node#numberOfChildren()
	 */
	@Override
	public int numberOfChildren() {
		return 0;
	}
}
