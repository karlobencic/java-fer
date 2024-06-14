package hr.fer.zemris.bf.model;

import java.util.List;
import java.util.function.BinaryOperator;

/**
 * The {@code BinaryOperatorNode} class represents a binary operator node. It
 * contains the logical operator which will be appliead on the children nodes.
 * There should be at least 2 children nodes in order to instantiate this
 * operator.
 * 
 * @author Karlo Bencic
 * 
 */
public class BinaryOperatorNode implements Node {

	/** The node name. */
	private final String name;

	/** The children nodes. */
	private final List<Node> children;

	/** The operator. */
	private final BinaryOperator<Boolean> operator;

	/**
	 * Instantiates a new binary operator node.
	 *
	 * @param name
	 *            the node name
	 * @param children
	 *            the node's children, at least 2
	 * @param operator
	 *            the binary operator
	 * @throws IllegalArgumentException
	 *             if there are less than 2 children in the list
	 */
	public BinaryOperatorNode(String name, List<Node> children, BinaryOperator<Boolean> operator) {
		if (children.size() < 2) {
			throw new IllegalArgumentException("There should be at least 2 nodes in the children list.");
		}

		this.name = name;
		this.children = children;
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
	 * Gets the children of this node.
	 *
	 * @return the children
	 */
	public List<Node> getChildren() {
		return children;
	}

	/**
	 * Gets the operator of this node.
	 *
	 * @return the operator
	 */
	public BinaryOperator<Boolean> getOperator() {
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
		return children.size();
	}
}
