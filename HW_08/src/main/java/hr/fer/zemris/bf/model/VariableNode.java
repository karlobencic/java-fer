package hr.fer.zemris.bf.model;

/**
 * The {@code VariableNode} class represents a variable node. It contains the
 * name of this variable and doesn't have any children nodes.
 * 
 * @author Karlo Bencic
 * 
 */
public class VariableNode implements Node {

	/** The node name. */
	private final String name;

	/**
	 * Instantiates a new variable node.
	 *
	 * @param name
	 *            the node name
	 */
	public VariableNode(String name) {
		this.name = name;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * hr.fer.zemris.bf.model.Node#accept(hr.fer.zemris.bf.model.NodeVisitor)
	 */
	public void accept(NodeVisitor visitor) {
		visitor.visit(this);
	}

	/**
	 * Gets the name of this node.
	 *
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return getName();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see hr.fer.zemris.bf.model.Node#numberOfChildren()
	 */
	@Override
	public int numberOfChildren() {
		return 0;
	}
}
