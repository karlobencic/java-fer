package hr.fer.zemris.java.hw04.db;

/**
 * The class ConditionalExpression represents a conditional expression in a
 * query. It has three properties: the operator, the value and the literal.
 * 
 * @author Karlo Bencic
 * 
 */
public class ConditionalExpression {

	/** The comparison operator. */
	private final IComparisonOperator comparisonOperator;

	/** The value getter. */
	private final IFieldValueGetter valueGetter;

	/** The literal. */
	private final String literal;

	/**
	 * Instantiates a new conditional expression.
	 *
	 * @param comparisonOperator
	 *            the comparison operator
	 * @param valueGetter
	 *            the value getter
	 * @param literal
	 *            the literal
	 */
	public ConditionalExpression(IComparisonOperator comparisonOperator, IFieldValueGetter valueGetter,
			String literal) {
		this.comparisonOperator = comparisonOperator;
		this.valueGetter = valueGetter;
		this.literal = literal;
	}

	/**
	 * Gets the comparison operator. See {@link IComparisonOperator}
	 *
	 * @return the comparison operator
	 */
	public IComparisonOperator getComparisonOperator() {
		return comparisonOperator;
	}

	/**
	 * Gets the value getter. See {@link IFieldValueGetter}
	 *
	 * @return the value getter
	 */
	public IFieldValueGetter getValueGetter() {
		return valueGetter;
	}

	/**
	 * Gets the literal.
	 *
	 * @return the literal
	 */
	public String getLiteral() {
		return literal;
	}
}
