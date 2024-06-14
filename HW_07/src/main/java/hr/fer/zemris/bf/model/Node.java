package hr.fer.zemris.bf.model;

/**
 * The {@code Node} interface represents the model of a tree node. It contains
 * one method {@code accept} which uses the visitor pattern to perform an
 * operation on this type of node. It also offers a method which returns this
 * node's number of children.
 * 
 * @author Karlo Bencic
 * 
 */
public interface Node {

	/**
	 * This method causes flow of control to find the correct {@code Node}
	 * subclass. After the {@link NodeVisitor} method {@code visit} is invoked,
	 * flow of control is vectored to the correct {@code Node} subclass.
	 *
	 * @param visitor
	 *            the node visitor
	 */
	void accept(NodeVisitor visitor);

	/**
	 * Gets the number of children of this node. For {@link VariableNode} and
	 * {@link ConstantNode} this method always returns 0, and for
	 * {@link UnaryOperatorNode} always returns 1.
	 *
	 * @return the number of children
	 */
	int numberOfChildren();
}
