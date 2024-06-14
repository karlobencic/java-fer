package hr.fer.zemris.java.hw04.db;

/**
 * The interface IFilter contains a single method, which is used to determine if
 * the given student record satisfies the condition.
 * 
 * @author Karlo Bencic
 * 
 */
public interface IFilter {

	/**
	 * Checks if the given student record satisfies the contidion.
	 *
	 * @param record
	 *            the student record, not null
	 * @return true, if satisfies
	 */
	public boolean accepts(StudentRecord record);
}
