package hr.fer.zemris.java.custom.scripting.nodes;

/**
 * The {@code NodeVisitor} interface represents a node visitor described in the
 * visitor pattern. Visitor's primary purpose is to abstract functionality that
 * can be applied to an aggregate hierarchy of nodes.
 * 
 * @author Karlo Bencic
 * 
 */
public interface INodeVisitor {

	/**
	 * This method is called from the {@code accept} method in the
	 * {@code TextNode} subclass.
	 *
	 * @param node
	 *            the node
	 */
	void visitTextNode(TextNode node);

	/**
	 * This method is called from the {@code accept} method in the
	 * {@code ForLoopNode} subclass.
	 *
	 * @param node
	 *            the node
	 */
	void visitForLoopNode(ForLoopNode node);

	/**
	 * This method is called from the {@code accept} method in the
	 * {@code EchoNode} subclass.
	 *
	 * @param node
	 *            the node
	 */
	void visitEchoNode(EchoNode node);

	/**
	 * This method is called from the {@code accept} method in the
	 * {@code DocumentNode} subclass.
	 *
	 * @param node
	 *            the node
	 */
	void visitDocumentNode(DocumentNode node);
}
