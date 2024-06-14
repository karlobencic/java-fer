package hr.fer.zemris.java.hw04.db;

/**
 * The interface IComparisonOperator contains a single method, which is used to
 * determine if two values satisfy a condition.
 * 
 * @author Karlo Bencic
 * 
 */
public interface IComparisonOperator {

	/**
	 * Does value1 satisfy value2.
	 *
	 * @param value1
	 *            the first value
	 * @param value2
	 *            the second value
	 * @return true, if satisfies
	 */
	public boolean satisfied(String value1, String value2);
}
