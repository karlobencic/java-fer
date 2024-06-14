package hr.fer.zemris.java.hw06.shell;

import java.util.SortedMap;

import hr.fer.zemris.java.hw06.shell.commands.ShellCommand;

/**
 * The {@code Environment} interface is an interface for shell commands.
 * 
 * @author Karlo Bencic
 * 
 */
public interface Environment {
	
	/**
	 * Reads a line from the shell.
	 *
	 * @return the read line
	 * @throws ShellIOException
	 *             if an IO exception occurs
	 */
	String readLine() throws ShellIOException;

	/**
	 * Writes a text to the shell.
	 *
	 * @param text
	 *            the text
	 * @throws ShellIOException
	 *             if an IO exception occurs
	 */
	void write(String text) throws ShellIOException;

	/**
	 * Writes a text followed by a newline into shell.
	 *
	 * @param text
	 *            the text
	 * @throws ShellIOException
	 *             if an IO exception occurs
	 */
	void writeln(String text) throws ShellIOException;

	/**
	 * Map of all supported commands.
	 *
	 * @return the sorted map of all commands
	 */
	SortedMap<String, ShellCommand> commands();

	/**
	 * Gets the multiline symbol.
	 *
	 * @return the multiline symbol
	 */
	Character getMultilineSymbol();

	/**
	 * Sets the multiline symbol.
	 *
	 * @param symbol
	 *            the new multiline symbol
	 */
	void setMultilineSymbol(Character symbol);

	/**
	 * Gets the prompt symbol.
	 *
	 * @return the prompt symbol
	 */
	Character getPromptSymbol();

	/**
	 * Sets the prompt symbol.
	 *
	 * @param symbol
	 *            the new prompt symbol
	 */
	void setPromptSymbol(Character symbol);

	/**
	 * Gets the morelines symbol.
	 *
	 * @return the morelines symbol
	 */
	Character getMorelinesSymbol();

	/**
	 * Sets the morelines symbol.
	 *
	 * @param symbol
	 *            the new morelines symbol
	 */
	void setMorelinesSymbol(Character symbol);

}
