package hr.fer.zemris.cmdapps.trazilica;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Scanner;

/**
 * This program represents simple search engine that receives user queries
 * through the command line. Search engine performs search through files present
 * in root directory whose absolute path is given as a command line argument.
 * 
 * Keywords are:
 * <ul>
 * <li>query - searches through available files for similarity</li>
 * <li>type {0} - displays file mentioned in result list; first argument is an
 * index of mentioned result file</li>
 * <li>results - repeats previously asked query</li>
 * <li>exit - terminates current search engine session</li>
 * </ul>
 * 
 * @author Karlo Bencic
 * 
 */
public class Main {

	/** Path to file which contains stopping words for vocabulary. */
	public static final String RELATIVE_PATH_TO_STOPPING_WORDS = "./src/main/resources/hrvatski_stoprijeci.txt";

	/** Path to directory which contains files that can be searched. */
	private static Path rootDirectory;

	/** The vocabulary used by this search engine. */
	private static Vocabulary vocabulary;

	/** Cache of previously returned results. */
	private static List<VectorUtil.QueryResult> results;

	/**
	 * The main method.
	 *
	 * @param args
	 *            the arguments
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	public static void main(String[] args) throws IOException {
		if (args.length != 1) {
			System.err.println("One command line argument expected - absolute path to directory with search files.");
			return;
		}

		String path = args[0];
		vocabulary = VocabularyBuilder.buildVocabulary(path);
		rootDirectory = Paths.get(path);

		Scanner sc = new Scanner(System.in);
		while (true) {
			System.out.print("Enter command > ");
			String line = sc.nextLine();

			if (line.equals("exit")) {
				break;
			}
			if (line.startsWith("query")) {
				line = line.substring("query".length()).trim();
				executeQuery(line);
			} else if (line.startsWith("type")) {
				line = line.substring("type".length()).trim();
				displayFile(line);
			} else if (line.equals("results")) {
				line = line.substring("results".length());
				displayResults();
			} else {
				System.err.println("Unknown command: " + line);
			}
		}
		sc.close();
	}

	/**
	 * Searches through files present in root directory. Top 10 search results
	 * are displayed in order of similarity with the given query.
	 * 
	 * @param query
	 *            the user's query
	 */
	private static void executeQuery(String query) {
		List<String> keywords = DocumentParser.parseText(query, vocabulary.getStoppingWords());
		DocumentNode node = new DocumentNode(null, keywords);

		System.out.println("Query is: " + node);
		node.calculateTfIdfVector(vocabulary);

		results = VectorUtil.performComputation(node, vocabulary.getDocuments());
		displayResults();
	}

	/**
	 * Displays the file mentioned in query result list.
	 * 
	 * @param query
	 *            the user's query
	 */
	private static void displayFile(String query) {
		int index = 0;
		try {
			index = Integer.parseInt(query);
		} catch (NumberFormatException e) {
			System.err.println("Given value is not an integer.");
			return;
		}

		if (index < 0 || index > 9) {
			System.err.println("Index is out of range.");
			return;
		}

		if (results == null) {
			System.err.println("No query was executed.");
			return;
		}

		VectorUtil.QueryResult result = null;
		try {
			result = results.get(index);
		} catch (IndexOutOfBoundsException e) {
			System.err.println("Index is out of range.");
			return;
		}

		Path filePath = Paths.get(rootDirectory.toString() + File.separatorChar + result.getDocument().getFileName());
		try {
			List<String> lines = Files.readAllLines(filePath, StandardCharsets.UTF_8);
			String headline = rootDirectory.toString() + File.separatorChar + result.getDocument().getFileName();

			printHeadline(headline.length());
			System.out.print("\n" + headline + "\n");
			printHeadline(headline.length());

			lines.forEach(System.out::println);
		} catch (IOException e) {
			System.err.println("File does not exist.");
		}
	}

	private static void printHeadline(int length) {
		for (int i = 0, j = length; i < j; i++) {
			System.out.print("-");
		}
	}

	/**
	 * Display formated search engine results.
	 */
	private static void displayResults() {
		if (results == null) {
			System.err.println("No results to be shown.");
		} else if (!results.isEmpty() && results.get(0).getSimilarity() == 0.0) {
			System.out.println("Query returned no results.");
		} else {
			for (int i = 0, j = results.size(); i < j && i < 10; i++) {
				VectorUtil.QueryResult result = results.get(i);
				if (result.getSimilarity() == 0)
					break;
				System.out.printf("[%2d] (%.4f) %s\n", i, result.getSimilarity(),
						rootDirectory.toString() + File.separatorChar + result.getDocument().getFileName());
			}
		}
	}
}