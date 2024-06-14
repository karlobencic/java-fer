package hr.fer.zemris.cmdapps.trazilica;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * 
 * The {@code DocumentParser} class is used to parse text documents for the
 * search engine. The result is a collection of string objects.
 * 
 * @author Karlo Bencic
 * 
 */
public class DocumentParser {

	/** Regular expression used for filtering and spliting document text. */
	public static final String FILTER_REGEX = "[^\\p{L}]";

	/**
	 * Parses text and generates words with a filter of removing unallowed
	 * words.
	 * 
	 * @param text
	 *            text to be parsed
	 * @param stoppingWords
	 *            words to be filtered out
	 * @return collection of words produced from text
	 */
	public static List<String> parseText(String text, Collection<String> stoppingWords) {
		return filterText(text, stoppingWords);
	}

	/**
	 * Parses text present in the given file and generates words with a filter
	 * of removing unallowed words.
	 * 
	 * @param file
	 *            file whose content is to be analyzed
	 * @param stoppingWords
	 *            words to be filtered out
	 * @return collection of words produced from text
	 */
	public static List<String> parseFile(Path file, Collection<String> stoppingWords) throws IOException {
		List<String> result = new ArrayList<>();	
		List<String> lines = Files.readAllLines(file, StandardCharsets.UTF_8);			
		lines.forEach(line -> result.addAll(filterText(line, stoppingWords)));	
		return result;
	}

	/**
	 * Filters text and generates words.
	 * 
	 * @param text
	 *            text to be analyzed
	 * @param stoppingWords
	 *            words to be filtered out
	 * @return collection of words produced from text
	 */
	private static List<String> filterText(String text, Collection<String> stoppingWords) {	
		List<String> result = new ArrayList<>();
		String[] tokens = text.trim().split(FILTER_REGEX);
		
		for (String word : tokens) {
			word = word.trim().toLowerCase();
			if (!word.isEmpty() && !stoppingWords.contains(word)) {
				result.add(word);
			}
		}

		return result;
	}
}