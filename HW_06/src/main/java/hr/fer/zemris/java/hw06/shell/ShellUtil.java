package hr.fer.zemris.java.hw06.shell;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * The {@code ShellUtil} class contains helper methods for the shell.
 * 
 * @author Karlo Bencic
 * 
 */
public class ShellUtil {

	/**
	 * Splits the arguments into the list delimited by space.
	 *
	 * @param arguments
	 *            the arguments
	 * @return the list
	 */
	public static List<String> splitArguments(String arguments) {
		List<String> args = new ArrayList<>();
		int lastStored = 0;
		for (int i = 0; i < arguments.length(); i++) {
			if (arguments.charAt(i) == '\"') {
				int closingQuoteIndex = arguments.indexOf('\"', i + 1);
				if (closingQuoteIndex == -1) {
					throw new ShellIOException("Unescaped quote");
				}
				addNonEmpty(args, arguments.substring(lastStored, i));
				addNonEmpty(args, arguments.substring(i + 1, closingQuoteIndex));
				lastStored = closingQuoteIndex + 1;
				i = closingQuoteIndex + 1;
			} else if (arguments.charAt(i) == ' ') {
				addNonEmpty(args, arguments.substring(lastStored, i));
				lastStored = i;
			}
		}
		addNonEmpty(args, arguments.substring(lastStored));
		return args;
	}

	/**
	 * Adds the non empty strings to a list.
	 *
	 * @param list
	 *            the list
	 * @param value
	 *            the string
	 */
	private static void addNonEmpty(List<String> list, String value) {
		if (!value.trim().equals("")) {
			list.add(value.trim());
		}
	}

	/**
	 * Gets the command description.
	 *
	 * @param loader
	 *            the class loader
	 * @param command
	 *            the command
	 * @return the description
	 */
	public static List<String> getDescription(Class<?> loader, String command) {
		try {
			File description = new File(loader.getResource(String.format("/%s.txt", command)).getFile());
			return Files.readAllLines(Paths.get(description.toString()));
		} catch (IOException e) {
			System.err.println("Failed to load description for: " + command);
			return null;
		}
	}
}
