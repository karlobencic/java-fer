package hr.fer.zemris.java.hw05.demo4;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * The class {@code StudentDemo} represents the usage of Java's Stream API on
 * {@code StudentRecord}. It shows how to get students with more than 25 score,
 * the number of best students, the list of best students, sorted list of best
 * students, the list of students who failed, mapped students by their grades,
 * mapped number of students by their grades and mapped students with their
 * status(passed or failed).
 * 
 * @author Karlo Bencic
 * 
 */
public class StudentDemo {

	/**
	 * The main method.
	 *
	 * @param args
	 *            the arguments
	 */
	public static void main(String[] args) {
		List<String> lines = new ArrayList<>();
			
		try {
			File databaseFile = new File(new StudentDemo().getClass().getResource("/studenti.txt").getFile());
			lines = Files.readAllLines(Paths.get(databaseFile.toString()));
		} catch (IOException e) {
			System.err.println("Can't open file!");
			return;
		}
		
		List<StudentRecord> records;
		try {
			records = convert(lines);
		} catch(IllegalArgumentException ex) {
			System.err.println(ex.getMessage());
			return;
		}	

		long broj = vratiBodovaViseOd25(records);
		System.out.println(String.format("Broj studenata sa više od 25 bodova: %d", broj));

		System.out.println();

		long broj5 = vratiBrojOdlikasa(records);
		System.out.println(String.format("Broj odlikaša: %d", broj5));

		System.out.println();

		List<StudentRecord> odlikasi = vratiListuOdlikasa(records);
		System.out.println("Odlikaši:");
		odlikasi.forEach(System.out::println);

		System.out.println();

		List<StudentRecord> odlikasiSortirano = vratiSortiranuListuOdlikasa(records);
		System.out.println("Odlikaši sortirano po bodovima:");
		odlikasiSortirano.forEach(System.out::println);

		System.out.println();

		List<String> nepolozeniJMBAGovi = vratiPopisNepolozenih(records);
		System.out.println("Studenti koji nisu položili:");
		nepolozeniJMBAGovi.forEach(s -> System.out.printf("%s, ", s));

		System.out.println();
		System.out.println();

		Map<Integer, List<StudentRecord>> mapaPoOcjenama = razvrstajStudentePoOcjenama(records);
		System.out.println("Studenti po ocjenama:");
		mapaPoOcjenama.forEach((k, v) -> System.out.printf("Ocjena %d: %s%n", k, v));

		System.out.println();

		Map<Integer, Integer> mapaPoOcjenama2 = vratiBrojStudenataPoOcjenama(records);
		System.out.println("Koliko studenata ima koju ocjenu:");
		mapaPoOcjenama2.forEach((k, v) -> System.out.printf("Ocjena %d: %d%n", k, v));

		System.out.println();

		Map<Boolean, List<StudentRecord>> prolazNeprolaz = razvrstajProlazPad(records);
		System.out.println("Rezultati prolaska studenata:");
		prolazNeprolaz.forEach((k, v) -> System.out.printf("Prolaz %s: %s%n", k == false ? "NE" : "DA", v));
	}

	/**
	 * Gets the amount of students with more than 25 total score.
	 *
	 * @param records
	 *            the list of student records
	 * @return the amount of students
	 */
	private static long vratiBodovaViseOd25(List<StudentRecord> records) {
		return records.stream().filter(s -> s.getTotalScore() > 25).count();
	}

	/**
	 * Gets the amount of students with the best grade.
	 *
	 * @param records
	 *            the list of student records
	 * @return the amount of students
	 */
	private static long vratiBrojOdlikasa(List<StudentRecord> records) {
		return records.stream().filter(s -> s.getGrade() == 5).count();
	}

	/**
	 * Gets the list of student records with the best grade.
	 *
	 * @param records
	 *            the list of student records
	 * @return the list of student records
	 */
	private static List<StudentRecord> vratiListuOdlikasa(List<StudentRecord> records) {
		return records.stream().filter(s -> s.getGrade() == 5).collect(Collectors.toList());
	}

	/**
	 * Gets the list of student records with the best grade sorted by their
	 * total points.
	 *
	 * @param records
	 *            the list of student records
	 * @return the sorted list of student records
	 */
	private static List<StudentRecord> vratiSortiranuListuOdlikasa(List<StudentRecord> records) {
		return records.stream().filter(s -> s.getGrade() == 5)
				.sorted(Comparator.comparing(s -> s, StudentRecord.BY_TOTAL_POINTS)).collect(Collectors.toList());
	}

	/**
	 * Gets the list of student's jmbags who have failed(their grade is 1).
	 *
	 * @param records
	 *            the list of student records
	 * @return the list of jmbags
	 */
	private static List<String> vratiPopisNepolozenih(List<StudentRecord> records) {
		return records.stream().filter(s -> s.getGrade() == 1).sorted(Comparator.naturalOrder()).map(s -> s.getJmbag())
				.collect(Collectors.toList());
	}

	/**
	 * Maps the students by their grades where key is student's grade and value
	 * is the list of student records with that grade.
	 *
	 * @param records
	 *            the list of student records
	 * @return the mapped student records by grades
	 */
	private static Map<Integer, List<StudentRecord>> razvrstajStudentePoOcjenama(List<StudentRecord> records) {
		return records.stream().collect(Collectors.groupingBy(StudentRecord::getGrade));
	}

	/**
	 * Maps the students by their grades where key is student's grade and value
	 * is the amount of students with that grade.
	 *
	 * @param records
	 *            the list of student records
	 * @return the mapped student records by grades
	 */
	private static Map<Integer, Integer> vratiBrojStudenataPoOcjenama(List<StudentRecord> records) {
		return records.stream().collect(Collectors.toMap(StudentRecord::getGrade, s -> 1, Integer::sum));
	}

	/**
	 * Maps the students by their status(passed or failed) where key is
	 * student's status and value is the list of student records with that
	 * status.
	 *
	 * @param records
	 *            the list of student records
	 * @return the mapped student records by status(passed = true, failed =
	 *         false)
	 */
	private static Map<Boolean, List<StudentRecord>> razvrstajProlazPad(List<StudentRecord> records) {
		return records.stream().collect(Collectors.partitioningBy(s -> s.getGrade() > 1));
	}

	/**
	 * Converts the list of text rows to the list of student records.
	 *
	 * @param rows
	 *            the rows
	 * @return the list
	 */
	private static List<StudentRecord> convert(List<String> rows) {
		if (rows == null) {
			throw new IllegalArgumentException("Argument can't be null.");
		}

		List<StudentRecord> records = new ArrayList<>(rows.size());

		for (String row : rows) {
			String[] record = row.split("\t");

			if (record.length != 7) {
				throw new IllegalArgumentException("Invalid records file - there should be 7 columns.");
			}

			String jmbag = record[0];
			String lastName = record[1];
			String firstName = record[2];
			float middleExamScore;
			float finalExamScore;
			float labScore;
			int grade;
			
			try {
				middleExamScore = Float.parseFloat(record[3]);
				finalExamScore = Float.parseFloat(record[4]);
				labScore = Float.parseFloat(record[5]);
				grade = Integer.parseInt(record[6]);
			} catch (NumberFormatException ex) {
				throw new IllegalArgumentException("Invalid records file - unable to parse record for row: " + row);
			}

			StudentRecord studentRecord = new StudentRecord(jmbag, lastName, firstName, middleExamScore, finalExamScore,
					labScore, grade);
			records.add(studentRecord);
		}

		return records;
	}

}
