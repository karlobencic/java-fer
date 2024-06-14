package hr.fer.zemris.bf.utils;

import hr.fer.zemris.bf.model.BinaryOperatorNode;
import hr.fer.zemris.bf.model.ConstantNode;
import hr.fer.zemris.bf.model.Node;
import hr.fer.zemris.bf.model.NodeVisitor;
import hr.fer.zemris.bf.model.UnaryOperatorNode;
import hr.fer.zemris.bf.model.VariableNode;

/**
 * The {@code ExpressionTreePrinter} is a node visitor which prints the node
 * tree using the following rule: each time an operator is printed, the output
 * is indented by 2 spaces.
 * 
 * @author Karlo Bencic
 * 
 */
public class ExpressionTreePrinter implements NodeVisitor {

	/** The indentation. */
	private int indentation;

	/*
	 * (non-Javadoc)
	 * 
	 * @see hr.fer.zemris.bf.model.NodeVisitor#visit(hr.fer.zemris.bf.model.
	 * ConstantNode)
	 */
	@Override
	public void visit(ConstantNode node) {
		System.out.println(parseConstant(node));
		System.out.print(print(node));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see hr.fer.zemris.bf.model.NodeVisitor#visit(hr.fer.zemris.bf.model.
	 * VariableNode)
	 */
	@Override
	public void visit(VariableNode node) {
		System.out.println(node.getName());
		System.out.print(print(node));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see hr.fer.zemris.bf.model.NodeVisitor#visit(hr.fer.zemris.bf.model.
	 * UnaryOperatorNode)
	 */
	@Override
	public void visit(UnaryOperatorNode node) {
		System.out.println(node.getName());
		System.out.print(print(node));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see hr.fer.zemris.bf.model.NodeVisitor#visit(hr.fer.zemris.bf.model.
	 * BinaryOperatorNode)
	 */
	@Override
	public void visit(BinaryOperatorNode node) {
		System.out.println(node.getName());
		System.out.print(print(node));
	}

	/**
	 * Indents the current line based on the current recursion depth.
	 *
	 * @param sb
	 *            the string builder
	 * @param i
	 *            the indent value
	 */
	private void indent(StringBuilder sb, int i) {
		for (int j = 0; j < i; j++) {
			sb.append(" ");
		}
	}

	/**
	 * Gets the node tree string.
	 *
	 * @param node
	 *            the node
	 * @return the string
	 */
	private String print(Node node) {
		if (node == null) {
			throw new IllegalArgumentException("Node can't be null.");
		}

		StringBuilder body = new StringBuilder();
		getNodeString(node, body);
		return body.toString();
	}

	/**
	 * Gets the node string recursively.
	 *
	 * @param parent
	 *            the parent node
	 * @param body
	 *            the body string
	 * @return the node string
	 */
	private void getNodeString(Node parent, StringBuilder body) {
		for (int i = 0, count = parent.numberOfChildren(); i < count; i++) {
			indent(body, indentation += 2);
			if (parent instanceof BinaryOperatorNode) {
				BinaryOperatorNode node = (BinaryOperatorNode) parent;
				body.append(String.format("%s%n", node.getChildren().get(i)));
				getNodeString(node.getChildren().get(i), body);
				indentation -= 2;
			} else if (parent instanceof UnaryOperatorNode) {
				UnaryOperatorNode node = (UnaryOperatorNode) parent;
				body.append(String.format("%s%n", node.getChild()));
				getNodeString(node.getChild(), body);
			}
		}
	}

	/**
	 * Parses the boolean constant.
	 *
	 * @param node
	 *            the node
	 * @return "0" if false, "1" otherwise
	 */
	private String parseConstant(ConstantNode node) {
		return node.getValue() == false ? "0" : "1";
	}

}
