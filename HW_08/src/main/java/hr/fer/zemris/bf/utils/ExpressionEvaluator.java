package hr.fer.zemris.bf.utils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

import hr.fer.zemris.bf.model.BinaryOperatorNode;
import hr.fer.zemris.bf.model.ConstantNode;
import hr.fer.zemris.bf.model.Node;
import hr.fer.zemris.bf.model.NodeVisitor;
import hr.fer.zemris.bf.model.UnaryOperatorNode;
import hr.fer.zemris.bf.model.VariableNode;

/**
 * The {@code ExpressionEvaluator} is a node visitor which calculates the result
 * of the boolean expression for the given combination of variables.
 * 
 * @author Karlo Bencic
 * 
 */
public class ExpressionEvaluator implements NodeVisitor {

	/** The values. */
	private boolean[] values;

	/**
	 * The position map where key is variable name and value is the position of
	 * that variable.
	 */
	private Map<String, Integer> positions;

	/** The stack of boolean values. */
	private Stack<Boolean> stack = new Stack<>();

	/**
	 * Instantiates a new expression evaluator.
	 *
	 * @param variables
	 *            the variables list
	 */
	public ExpressionEvaluator(List<String> variables) {
		positions = new HashMap<>();
		for (int i = 0; i < variables.size(); i++) {
			positions.put(variables.get(i), i);
		}
	}

	/**
	 * Sets the boolean values and starts the evaluator.
	 *
	 * @param values
	 *            the boolean values
	 */
	public void setValues(boolean[] values) {
		this.values = values;
		start();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see hr.fer.zemris.bf.model.NodeVisitor#visit(hr.fer.zemris.bf.model.
	 * ConstantNode)
	 */
	@Override
	public void visit(ConstantNode node) {
		stack.push(node.getValue());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see hr.fer.zemris.bf.model.NodeVisitor#visit(hr.fer.zemris.bf.model.
	 * VariableNode)
	 */
	@Override
	public void visit(VariableNode node) {
		if (!positions.containsKey(node.getName())) {
			throw new IllegalStateException("Given variable not found in internal map.");
		}
		int position = positions.get(node.getName());
		stack.push(values[position]);
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
	 * Starts the evaluator by clearing the stack.
	 */
	public void start() {
		stack.clear();
	}

	/**
	 * Gets the expression result.
	 *
	 * @return the result
	 */
	public boolean getResult() {
		if (stack.size() != 1) {
			throw new IllegalStateException("There should be 1 value on the stack, was " + stack.size());
		}
		return stack.peek();
	}

	/**
	 * Gets the nodes and all it's children recursively. If the node is a
	 * {@link VariableNode} or {@link ConstantNode} it's name or value is pushed
	 * on the stack, and if it's an operator then the calculation is performed
	 * by popping 1 value for {@link UnaryOperatorNode} or 2 values for
	 * {@link BinaryOperatorNode} from the stack and pushing the result of the
	 * operation.
	 *
	 * @param parent
	 *            the parent node
	 * @return the nodes
	 */
	private void getNodes(Node parent) {
		if (parent instanceof VariableNode) {
			VariableNode node = (VariableNode) parent;
			visit(node);
		} else if (parent instanceof ConstantNode) {
			ConstantNode node = (ConstantNode) parent;
			visit(node);
		}

		for (int i = 0, count = parent.numberOfChildren(); i < count; i++) {
			if (parent instanceof BinaryOperatorNode) {
				BinaryOperatorNode node = (BinaryOperatorNode) parent;
				getNodes(node.getChildren().get(i));
				calculateBinary(node);
			} else if (parent instanceof UnaryOperatorNode) {
				UnaryOperatorNode node = (UnaryOperatorNode) parent;
				getNodes(node.getChild());
				calculateUnary(node);
			}
		}
	}

	/**
	 * Performs calculation on the binary node using it's binary operator.
	 *
	 * @param node
	 *            the node
	 */
	private void calculateBinary(BinaryOperatorNode node) {
		if (stack.size() < 2)
			return;

		boolean v1 = stack.pop();
		boolean v2 = stack.pop();
		boolean calculated = node.getOperator().apply(v1, v2);
		stack.push(calculated);
	}

	/**
	 * Performs calculation on the unary node using it's unary operator.
	 *
	 * @param node
	 *            the node
	 */
	private void calculateUnary(UnaryOperatorNode node) {
		if (stack.size() == 0)
			return;

		boolean value = stack.pop();
		boolean calculated = node.getOperator().apply(value);
		stack.push(calculated);
	}
}
