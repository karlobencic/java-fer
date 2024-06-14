package hr.fer.zemris.java.hw04.db;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * This program gets the database file and creates a StudentDatabase with the
 * given data. The database is interactible which means that the user can
 * execute simple queries which should start with the word "query" then follows
 * the query itself. Valid database values are 'jmbag', 'lastname' and
 * 'firstname'. Valid logical operator is 'and'. See {@link ComparisonOperators}
 * for the list of valid comparison operators.
 * 
 * @author Karlo Bencic
 * 
 */
public class StudentDB {

	/**
	 * The main method.
	 *
	 * @param args
	 *            the arguments
	 */
	public static void main(String[] args) {
		List<String> data = new ArrayList<>();

		try {
			File databaseFile = new File(new StudentDB().getClass().getResource("/database.txt").getFile());
			data = Files.readAllLines(Paths.get(databaseFile.toString()));
		} catch (IOException e) {
			System.err.println("Can't open file!");
			return;
		}	

		try(Scanner sc = new Scanner(System.in)) {
			while (true) {
				System.out.print("> ");
				try {
					String input = sc.nextLine();
					if (input.toLowerCase().startsWith("query")) {
						String query = input.substring("query".length());
						QueryParser parser = new QueryParser(query);
						TablePrinter table = new TablePrinter("jmbag", "lastname", "firstname", "finalgrade");
						int numOfRecords = 0;
						
						StudentDatabase db = new StudentDatabase(data);

						if (parser.isDirectQuery()) {
							System.out.println("Using index for record retrieval.");
							StudentRecord r = db.forJMBAG(parser.getQueriedJMBAG());
							if (r != null) {
								numOfRecords++;
								table.addRow(r.getJmbag(), r.getLastName(), r.getFirstName(),
										String.valueOf(r.getFinalGrade()));
							}
						} else {
							for (StudentRecord r : db.filter(new QueryFilter(parser.getQuery()))) {
								numOfRecords++;
								table.addRow(r.getJmbag(), r.getLastName(), r.getFirstName(),
										String.valueOf(r.getFinalGrade()));
							}
						}

						if (numOfRecords != 0) {
							table.print();
						}

						System.out.printf("Records selected: %d%n", numOfRecords);
					} else {
						if (input.equals("exit")) {
							System.out.println("Goodbye!");
							break;
						}
						System.out.format("'%s' is not a valid query!", input);
					}
				} catch (QueryParserException ex) {
					System.out.println(ex.getMessage());
				}
			}
		}
	}
}
