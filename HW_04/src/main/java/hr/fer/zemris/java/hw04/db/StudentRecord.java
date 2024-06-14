package hr.fer.zemris.java.hw04.db;

/**
 * The class StudentRecord represents a single database record in the
 * {@code StudentDatabase}. It has four properties: jmbag, lastName, firstName
 * and finalGrade which also represents the columns in the student database.
 * 
 * @author Karlo Bencic
 * 
 */
public class StudentRecord {

	/** The jmbag. */
	private final String jmbag;

	/** The last name. */
	private final String lastName;

	/** The first name. */
	private final String firstName;

	/** The final grade. */
	private final int finalGrade;

	/**
	 * Instantiates a new student record.
	 *
	 * @param jmbag
	 *            the jmbag
	 * @param lastName
	 *            the last name
	 * @param firstName
	 *            the first name
	 * @param finalGrade
	 *            the final grade
	 */
	public StudentRecord(String jmbag, String lastName, String firstName, int finalGrade) {
		this.jmbag = jmbag;
		this.lastName = lastName;
		this.firstName = firstName;
		this.finalGrade = finalGrade;
	}

	/**
	 * Gets the jmbag.
	 *
	 * @return the jmbag
	 */
	public String getJmbag() {
		return jmbag;
	}

	/**
	 * Gets the last name.
	 *
	 * @return the last name
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * Gets the first name.
	 *
	 * @return the first name
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * Gets the final grade.
	 *
	 * @return the final grade
	 */
	public int getFinalGrade() {
		return finalGrade;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((jmbag == null) ? 0 : jmbag.hashCode());
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof StudentRecord)) {
			return false;
		}
		StudentRecord other = (StudentRecord) obj;
		if (jmbag == null) {
			if (other.jmbag != null) {
				return false;
			}
		} else if (!jmbag.equals(other.jmbag)) {
			return false;
		}
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "StudentRecord [jmbag=" + jmbag + ", lastName=" + lastName + ", firstName=" + firstName + ", finalGrade="
				+ finalGrade + "]";
	}
}
