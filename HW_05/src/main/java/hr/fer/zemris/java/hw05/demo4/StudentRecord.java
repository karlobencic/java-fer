package hr.fer.zemris.java.hw05.demo4;

import java.util.Comparator;

/**
 * The class StudentRecord represents a single database record in the
 * {@code StudentDatabase}. It has four properties: jmbag, lastName, firstName
 * and finalGrade which also represents the columns in the student database.
 * Students are compared using their jmbag.
 * 
 * @author Karlo Bencic
 * 
 */
public class StudentRecord implements Comparable<StudentRecord> {

	/** The constant BY_TOTAL_POINTS compares students by their total score, descending. */
	public static final Comparator<StudentRecord> BY_TOTAL_POINTS = (s1, s2) -> -Float.compare(s1.getTotalScore(), s2.getTotalScore());

	/** The jmbag. */
	private final String jmbag;

	/** The last name. */
	private final String lastName;

	/** The first name. */
	private final String firstName;

	/** The middle exam score. */
	private final float middleExamScore;

	/** The final exam score. */
	private final float finalExamScore;

	/** The lab score. */
	private final float labScore;

	/** The grade. */
	private final int grade;

	/**
	 * Instantiates a new student record.
	 *
	 * @param jmbag
	 *            the jmbag
	 * @param lastName
	 *            the last name
	 * @param firstName
	 *            the first name
	 * @param middleExamScore
	 *            the middle exam score
	 * @param finalExamScore
	 *            the final exam score
	 * @param labScore
	 *            the lab score
	 * @param grade
	 *            the grade
	 */
	public StudentRecord(String jmbag, String lastName, String firstName, float middleExamScore, float finalExamScore,
			float labScore, int grade) {
		this.jmbag = jmbag;
		this.lastName = lastName;
		this.firstName = firstName;
		this.middleExamScore = middleExamScore;
		this.finalExamScore = finalExamScore;
		this.labScore = labScore;
		this.grade = grade;
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
	 * Gets the middle exam score.
	 *
	 * @return the middle exam score
	 */
	public float getMiddleExamScore() {
		return middleExamScore;
	}

	/**
	 * Gets the final exam score.
	 *
	 * @return the final exam score
	 */
	public float getFinalExamScore() {
		return finalExamScore;
	}

	/**
	 * Gets the lab score.
	 *
	 * @return the lab score
	 */
	public float getLabScore() {
		return labScore;
	}

	/**
	 * Gets the grade.
	 *
	 * @return the grade
	 */
	public int getGrade() {
		return grade;
	}

	/**
	 * Gets the total score.
	 *
	 * @return the total score
	 */
	public float getTotalScore() {
		return middleExamScore + finalExamScore + labScore;
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
		return String.format("%s %s (%.2f)", firstName, lastName, getTotalScore());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	@Override
	public int compareTo(StudentRecord o) {
		return this.jmbag.compareTo(o.jmbag);
	}

}