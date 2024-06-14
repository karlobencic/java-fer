package hr.fer.zemris.java.hw04.db;

import java.text.Collator;
import java.util.Locale;

/**
 * The class ComparisonOperators offers constants for basic operators such as
 * <, <=, >, >=, =, != and LIKE. All operators use HR locale for comparison.
 * 
 * @author Karlo Bencic
 * 
 */
public class ComparisonOperators {

	/** The Constant LESS '<' */
	public static final IComparisonOperator LESS;

	/** The Constant LESS_OR_EQUALS '<=' */
	public static final IComparisonOperator LESS_OR_EQUALS;

	/** The Constant GREATER '>' */
	public static final IComparisonOperator GREATER;

	/** The Constant GREATER_OR_EQUALS '>=' */
	public static final IComparisonOperator GREATER_OR_EQUALS;

	/** The Constant EQUALS '=' */
	public static final IComparisonOperator EQUALS;

	/** The Constant NOT_EQUALS '!=' */
	public static final IComparisonOperator NOT_EQUALS;

	/** The Constant LIKE. */
	public static final IComparisonOperator LIKE;

	static {
		Collator collator = Collator.getInstance(new Locale("hr", "HR"));

		LESS = (v1, v2) -> collator.compare(v1, v2) < 0;
		LESS_OR_EQUALS = (v1, v2) -> collator.compare(v1, v2) <= 0;
		GREATER = (v1, v2) -> collator.compare(v1, v2) > 0;
		GREATER_OR_EQUALS = (v1, v2) -> collator.compare(v1, v2) >= 0;
		EQUALS = (v1, v2) -> collator.compare(v1, v2) == 0;
		NOT_EQUALS = (v1, v2) -> collator.compare(v1, v2) != 0;
		LIKE = (v1, v2) -> v1.matches(v2.replace("*", ".*").replace("?", "."));
	}
}
