package hr.fer.zemris.bf.model;

import java.util.function.UnaryOperator;

/**
 * The {@code UnaryOperatorNode} class represents a unary operator node. It
 * contains the logical operator which will be appliead on the child node.
 * 
 * @author Karlo Bencic
 * 
 */
public class UnaryOperatorNode implements Node {

	/** The node name. */
	private final String name;

	/** The child node. */
	private final Node child;

	/** The operator. */
	private final UnaryOperator<Boolean> operator;

	/**
	 * Instantiates a new unary operator node.
	 *
	 * @param name
	 *            the node name
	 * @param child
	 *            the node's child
	 * @param operator
	 *            the unary operator
	 */
	public UnaryOperatorNode(String name, Node child, UnaryOperator<Boolean> operator) {
		this.name = name;
		this.child = child;
		this.operator = operator;
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

	/**
	 * Gets the child of this node.
	 *
	 * @return the child
	 */
	public Node getChild() {
		return child;
	}

	/**
	 * Gets the operator of this node.
	 *
	 * @return the operator
	 */
	public UnaryOperator<Boolean> getOperator() {
		return operator;
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
		return 1;
	}
}
