package hr.fer.zemris.bf.model;

/**
 * The {@code NodeVisitor} interface represents a node visitor described in the
 * visitor pattern. Visitor's primary purpose is to abstract functionality that
 * can be applied to an aggregate hierarchy of nodes.
 * 
 * @author Karlo Bencic
 * 
 */
public interface NodeVisitor {

	/**
	 * This method is called from the {@code accept} method in the
	 * {@code ConstantNode} subclass.
	 *
	 * @param node
	 *            the node
	 */
	void visit(ConstantNode node);

	/**
	 * This method is called from the {@code accept} method in the
	 * {@code VariableNode} subclass.
	 *
	 * @param node
	 *            the node
	 */
	void visit(VariableNode node);

	/**
	 * This method is called from the {@code accept} method in the
	 * {@code UnaryOperatorNode} subclass.
	 *
	 * @param node
	 *            the node
	 */
	void visit(UnaryOperatorNode node);

	/**
	 * This method is called from the {@code accept} method in the
	 * {@code BinaryOperatorNode} subclass.
	 *
	 * @param node
	 *            the node
	 */
	void visit(BinaryOperatorNode node);
}
