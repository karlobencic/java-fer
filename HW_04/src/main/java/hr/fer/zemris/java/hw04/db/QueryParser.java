package hr.fer.zemris.java.hw04.db;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * The class QueryParser parses the query string as conditional expressions. The
 * query is valid if it has three tokens(value, operator and literal).
 * 
 * @author Karlo Bencic
 * 
 */
public class QueryParser {

	/** The query string. */
	private String query;

	/**
	 * Instantiates a new query parser.
	 *
	 * @param query
	 *            the query string, not null
	 * @throws IllegalArgumentException
	 *             if query is null
	 */
	public QueryParser(String query) {
		if (query == null) {
			throw new IllegalArgumentException("Argument can't be null.");
		}

		this.query = query.trim();
	}

	/**
	 * Checks if is direct query.
	 *
	 * @return true, if is direct query
	 */
	public boolean isDirectQuery() {
		return query.replaceAll("\\s+", "").matches("jmbag=\"\\w+\"");
	}

	/**
	 * Gets the queried JMBAG.
	 *
	 * @return the queried JMBAG
	 * @throws IllegalStateException
	 *             if is not a direct query
	 */
	public String getQueriedJMBAG() {
		if (!isDirectQuery()) {
			throw new IllegalStateException("Not a direct query.");
		}

		Pattern p = Pattern.compile("\"([^\"]*)\"");
		Matcher m = p.matcher(query);

		return m.find() ? m.group(1) : null;
	}

	/**
	 * Gets the query. See {@link ConditionalExpression}
	 *
	 * @return the list of conditional expressions
	 * @throws QueryParserException
	 *             if the query is invalid
	 */
	public List<ConditionalExpression> getQuery() {
		String[] expressions = query.split("(?i)and");

		List<ConditionalExpression> condExpressions = new ArrayList<>(expressions.length);

		for (String expression : expressions) {
			final String operators = "=|>|>=|<|<=|!=|(?i)LIKE";
			String[] tokens = expression.split("(?<=" + operators + ")|(?=" + operators + ")");

			if (tokens.length != 3) {
				throw new QueryParserException("Invalid query.");
			}

			IFieldValueGetter value = getValue(tokens[0]);
			if (value == null) {
				throw new QueryParserException(String.format("Column '%s' does not exist.", tokens[0]));
			}

			IComparisonOperator operator = getOperator(tokens[1]);
			if (operator == null) {
				throw new QueryParserException(String.format("Operator '%s' is not supported.", tokens[1]));
			}

			String literal = tokens[2].trim();
			if (!literal.startsWith("\"")) {
				throw new QueryParserException("Invalid query.");
			}

			condExpressions.add(new ConditionalExpression(operator, value, literal.replaceAll("\"", "")));
		}

		return condExpressions;
	}

	/**
	 * Gets the value. See {@link IFieldValueGetter}
	 *
	 * @param value
	 *            the value string
	 * @return the value
	 */
	private IFieldValueGetter getValue(String value) {
		if (value == null) {
			return null;
		}

		switch (value.trim().toLowerCase()) {
		case "firstname":
			return FieldValueGetters.FIRST_NAME;
		case "lastname":
			return FieldValueGetters.LAST_NAME;
		case "jmbag":
			return FieldValueGetters.JMBAG;
		default:
			return null;
		}
	}

	/**
	 * Gets the operator. See {@link IComparisonOperator}
	 *
	 * @param operator
	 *            the operator string
	 * @return the operator
	 */
	private IComparisonOperator getOperator(String operator) {
		if (operator == null) {
			return null;
		}

		switch (operator.trim().toUpperCase()) {
		case "=":
			return ComparisonOperators.EQUALS;
		case ">":
			return ComparisonOperators.GREATER;
		case ">=":
			return ComparisonOperators.GREATER_OR_EQUALS;
		case "<":
			return ComparisonOperators.LESS;
		case "<=":
			return ComparisonOperators.LESS_OR_EQUALS;
		case "!=":
			return ComparisonOperators.NOT_EQUALS;
		case "LIKE":
			return ComparisonOperators.LIKE;
		default:
			return null;
		}
	}
}
