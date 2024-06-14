package hr.fer.zemris.java.custom.scripting.nodes;

import hr.fer.zemris.java.custom.scripting.elems.Element;
import hr.fer.zemris.java.custom.scripting.elems.ElementVariable;

/**
 * The class ForLoopNode represents a single node constructed by the parser. It
 * inherits {@code Node} class.
 * 
 * @author Karlo Bencic
 * 
 */
public class ForLoopNode extends Node {

	/** The variable. */
	private final ElementVariable variable;

	/** The start expression. */
	private final Element startExpression;

	/** The end expression. */
	private final Element endExpression;

	/** The step expression. */
	private final Element stepExpression;

	/**
	 * Instantiates a new for loop node.
	 *
	 * @param variable
	 *            the variable
	 * @param startExpression
	 *            the start expression
	 * @param endExpression
	 *            the end expression
	 * @param stepExpression
	 *            the step expression
	 */
	public ForLoopNode(ElementVariable variable, Element startExpression, Element endExpression,
			Element stepExpression) {
		this.variable = variable;
		this.startExpression = startExpression;
		this.endExpression = endExpression;
		this.stepExpression = stepExpression;
	}

	/**
	 * Gets the variable.
	 *
	 * @return the variable
	 */
	public ElementVariable getVariable() {
		return variable;
	}

	/**
	 * Gets the start expression.
	 *
	 * @return the start expression
	 */
	public Element getStartExpression() {
		return startExpression;
	}

	/**
	 * Gets the end expression.
	 *
	 * @return the end expression
	 */
	public Element getEndExpression() {
		return endExpression;
	}

	/**
	 * Gets the step expression.
	 *
	 * @return the step expression
	 */
	public Element getStepExpression() {
		return stepExpression;
	}

	@Override
	public String asText() {
		StringBuilder output = new StringBuilder();
		output.append("{$ FOR ").append(variable.asText()).append(" ").append(startExpression.asText()).append(" ").append(endExpression.asText());

		if (stepExpression != null) {
			output.append(" ").append(stepExpression.asText());
		}
		output.append(" $}");
		for (int i = 0, n = numberOfChildren(); i < n; i++) {
			output.append(getChild(i).asText());
		}
		output.append("{$END$}");
		return output.toString();
	}

	@Override
	public void accept(INodeVisitor visitor) {
		visitor.visitForLoopNode(this);
	}
}
