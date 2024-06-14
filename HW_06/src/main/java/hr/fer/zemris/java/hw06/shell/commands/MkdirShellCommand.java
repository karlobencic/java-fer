package hr.fer.zemris.java.hw06.shell.commands;

import java.nio.file.*;
import java.util.List;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellIOException;
import hr.fer.zemris.java.hw06.shell.ShellStatus;
import hr.fer.zemris.java.hw06.shell.ShellUtil;

/**
 * The {@code MkdirShellCommand} is an implementation of the 'mkdir' command. It
 * creates a directory at the specified path. This command expects one argument.
 * The argument is path to the directory which should be created.
 * 
 * @author Karlo Bencic
 * 
 */
public class MkdirShellCommand implements ShellCommand {

	/** The Constant COMMAND_NAME. */
	private static final String COMMAND_NAME = "mkdir";

	/** The command description. */
	private List<String> commandDescription;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * hr.fer.zemris.java.hw06.shell.commands.ShellCommand#executeCommand(hr.fer
	 * .zemris.java.hw06.shell.Environment, java.lang.String)
	 */
	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		List<String> args;
		try {
			args = ShellUtil.splitArguments(arguments);
		} catch (ShellIOException e) {
			env.writeln(e.getMessage());
			return ShellStatus.CONTINUE;
		}

		if (args.size() == 1) {
			Path path;
			try {
				path = Paths.get(args.get(0));
			} catch (InvalidPathException e) {
				env.writeln("Invalid path: " + args.get(0));
				return ShellStatus.CONTINUE;
			}

			if (path.toFile().mkdirs()) {
				env.writeln("Sucessfuly created directory: " + path);
				return ShellStatus.CONTINUE;
			}
			env.writeln("Failed creating directory: " + path);
			return ShellStatus.CONTINUE;
		}

		env.writeln(String.format("Invalid arguments for command %s: %s", getCommandName(), arguments));
		return ShellStatus.CONTINUE;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see hr.fer.zemris.java.hw06.shell.commands.ShellCommand#getCommandName()
	 */
	@Override
	public String getCommandName() {
		return COMMAND_NAME;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * hr.fer.zemris.java.hw06.shell.commands.ShellCommand#getCommandDescription
	 * ()
	 */
	@Override
	public List<String> getCommandDescription() {
		if (commandDescription == null) {
			commandDescription = ShellUtil.getDescription(getClass(), COMMAND_NAME);
		}
		return commandDescription;
	}
}
