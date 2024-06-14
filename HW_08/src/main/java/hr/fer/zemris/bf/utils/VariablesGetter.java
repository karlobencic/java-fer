package hr.fer.zemris.bf.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import hr.fer.zemris.bf.model.BinaryOperatorNode;
import hr.fer.zemris.bf.model.ConstantNode;
import hr.fer.zemris.bf.model.Node;
import hr.fer.zemris.bf.model.NodeVisitor;
import hr.fer.zemris.bf.model.UnaryOperatorNode;
import hr.fer.zemris.bf.model.VariableNode;

/**
 * The {@code VariablesGetter} class gets all the variables that are mentioned
 * in a given boolean expression and are returned as a list which is sorted
 * alphabetically. All variables are unique.
 * 
 * @author Karlo Bencic
 * 
 */
public class VariablesGetter implements NodeVisitor {

	/** The variables. */
	private Set<String> variables = new TreeSet<>();

	/**
	 * Gets the variables.
	 *
	 * @return the variables
	 */
	public List<String> getVariables() {
		return new ArrayList<>(variables);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see hr.fer.zemris.bf.model.NodeVisitor#visit(hr.fer.zemris.bf.model.
	 * ConstantNode)
	 */
	@Override
	public void visit(ConstantNode node) {
		getNodes(node);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see hr.fer.zemris.bf.model.NodeVisitor#visit(hr.fer.zemris.bf.model.
	 * VariableNode)
	 */
	@Override
	public void visit(VariableNode node) {
		getNodes(node);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see hr.fer.zemris.bf.model.NodeVisitor#visit(hr.fer.zemris.bf.model.
	 * UnaryOperatorNode)
	 */
	@Override
	public void visit(UnaryOperatorNode node) {
		getNodes(node);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see hr.fer.zemris.bf.model.NodeVisitor#visit(hr.fer.zemris.bf.model.
	 * BinaryOperatorNode)
	 */
	@Override
	public void visit(BinaryOperatorNode node) {
		getNodes(node);
	}

	/**
	 * Gets the nodes and all it's children recursively. If the node is a
	 * {@code VariableNode} then it's name is added in the variables list.
	 *
	 * @param parent
	 *            the parent node
	 * @return the nodes
	 */
	private void getNodes(Node parent) {
		if (parent instanceof VariableNode) {
			VariableNode node = (VariableNode) parent;
			variables.add(node.getName());
		}

		for (int i = 0, count = parent.numberOfChildren(); i < count; i++) {
			if (parent instanceof BinaryOperatorNode) {
				BinaryOperatorNode node = (BinaryOperatorNode) parent;
				getNodes(node.getChildren().get(i));
			} else if (parent instanceof UnaryOperatorNode) {
				UnaryOperatorNode node = (UnaryOperatorNode) parent;
				getNodes(node.getChild());
			}
		}
	}
}
