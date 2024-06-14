package hr.fer.zemris.java.hw04.db;

import java.util.ArrayList;
import java.util.List;

/**
 * The class QueryFilter checks if the expression is satisfied for each record
 * in the database, and filters the result.
 * 
 * @author Karlo Bencic
 * 
 */
public class QueryFilter implements IFilter {

	/** The conditional expressions. */
	private List<ConditionalExpression> expressions = new ArrayList<>();

	/**
	 * Instantiates a new query filter.
	 *
	 * @param expressions
	 *            the conditional expressions, not null
	 * @throws IllegalArgumentException
	 *             if expressions is null
	 */
	public QueryFilter(List<ConditionalExpression> expressions) {
		if (expressions == null) {
			throw new IllegalArgumentException("Argument can't be null.");
		}

		this.expressions = expressions;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * hr.fer.zemris.java.hw04.db.IFilter#accepts(hr.fer.zemris.java.hw04.db.
	 * StudentRecord)
	 */
	@Override
	public boolean accepts(StudentRecord record) {
		if (record == null) {
			return false;
		}

		for (ConditionalExpression expression : expressions) {
			if (!expression.getComparisonOperator().satisfied(expression.getValueGetter().get(record),
					expression.getLiteral())) {
				return false;
			}
		}
		return true;
	}
}
