package hr.fer.zemris.java.hw06.shell.commands;

import java.util.List;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellStatus;

/**
 * The {@code ShellCommand} interface is an interface for shell commands.
 * 
 * @author Karlo Bencic
 * 
 */
public interface ShellCommand {

	/**
	 * Executes a command.
	 *
	 * @param env
	 *            the command environment
	 * @param arguments
	 *            the command arguments
	 * @return the shell status
	 */
	ShellStatus executeCommand(Environment env, String arguments);

	/**
	 * Gets the command name.
	 *
	 * @return the command name
	 */
	String getCommandName();

	/**
	 * Gets the command description.
	 *
	 * @return the command description
	 */
	List<String> getCommandDescription();
}
