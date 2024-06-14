package hr.fer.zemris.java.hw04.db;

/**
 * The class FieldValueGetters contains constants for getting the
 * {@code StudentRecord} properties.
 * 
 * @author Karlo Bencic
 * 
 */
public class FieldValueGetters {

	/** The Constant FIRST_NAME returns the first name of the student. */
	public static final IFieldValueGetter FIRST_NAME = r -> r.getFirstName();

	/** The Constant LAST_NAME returns the last name of the student. */
	public static final IFieldValueGetter LAST_NAME = r -> r.getLastName();

	/** The Constant JMBAG returns the jmbag of the student. */
	public static final IFieldValueGetter JMBAG = r -> r.getJmbag();
}
