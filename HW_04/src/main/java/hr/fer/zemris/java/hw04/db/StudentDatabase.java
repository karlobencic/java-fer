package hr.fer.zemris.java.hw04.db;

import java.util.ArrayList;
import java.util.List;

import hr.fer.zemris.java.hw04.collections.SimpleHashtable;

/**
 * The class StudentDatabase represents a database of students. The primary key
 * of the database is student's JMBAG. The database is created from the given
 * list of rows which contain the properties of the {@link StudentRecord}.
 * 
 * @author Karlo Bencic
 * 
 */
public class StudentDatabase {

	/** The student records list. */
	private List<StudentRecord> records;

	/** The student records map with jmbag as key. */
	private SimpleHashtable<String, StudentRecord> studentRecords;

	/**
	 * Instantiates a new student database.
	 *
	 * @param rows
	 *            the database rows
	 */
	public StudentDatabase(List<String> rows) {
		if(rows == null) {
			throw new IllegalArgumentException("Argument can't be null.");
		}
		
		records = new ArrayList<>(rows.size());
		studentRecords = new SimpleHashtable<>(rows.size());

		for (String row : rows) {
			String[] record = row.split("\\s+");

			String jmbag = record[0];
			String lastName = extractLastName(record);
			String firstName = record[record.length - 2];
			int finalGrade = Integer.parseInt(record[record.length - 1]);

			StudentRecord studentRecord = new StudentRecord(jmbag, lastName, firstName, finalGrade);
			studentRecords.put(jmbag, studentRecord);
			records.add(studentRecord);
		}
	}

	/**
	 * Gets the student mapped with the given JMBAG.
	 *
	 * @param jmbag
	 *            the jmbag
	 * @return the student record
	 */
	public StudentRecord forJMBAG(String jmbag) {
		return studentRecords.get(jmbag);
	}

	/**
	 * Filters the students list with the given filter.
	 *
	 * @param filter
	 *            the filter
	 * @return the filterred student list
	 */
	public List<StudentRecord> filter(IFilter filter) {
		if(filter == null) {
			throw new IllegalArgumentException("Filter can't be null.");
		}
		
		List<StudentRecord> students = new ArrayList<>();

		for (StudentRecord record : records) {
			if (filter.accepts(record)) {
				students.add(record);
			}
		}

		return students;
	}

	/**
	 * Extracts the last name from the database record.
	 *
	 * @param record
	 *            the record
	 * @return the last name
	 */
	private String extractLastName(String[] record) {
		if (record.length == 4) {
			return record[1];
		}

		StringBuilder sb = new StringBuilder();
		for (int i = 1; i < record.length - 2; i++) {
			sb.append(record[i]).append(" ");
		}

		return sb.deleteCharAt(sb.length() - 1).toString();
	}

	/**
	 * Gets the size of the database.
	 *
	 * @return the size
	 */
	public int size() {
		return records == null ? 0 : records.size();
	}
}
