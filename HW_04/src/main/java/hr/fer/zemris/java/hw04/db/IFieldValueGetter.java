package hr.fer.zemris.java.hw04.db;

/**
 * The interface IFieldValueGetter contains a single method, which is used to
 * get the property of the student record.
 * 
 * @author Karlo Bencic
 * 
 */
public interface IFieldValueGetter {
	
	/**
	 * Gets the property of the student record.
	 *
	 * @param record
	 *            the student record
	 * @return the property value
	 */
	public String get(StudentRecord record);
}
