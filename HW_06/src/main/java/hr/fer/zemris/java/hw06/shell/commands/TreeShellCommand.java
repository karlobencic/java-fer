package hr.fer.zemris.java.hw06.shell.commands;

import java.io.File;
import java.nio.file.*;
import java.util.List;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellIOException;
import hr.fer.zemris.java.hw06.shell.ShellStatus;
import hr.fer.zemris.java.hw06.shell.ShellUtil;

/**
 * The {@code TreeShellCommand} is an implementation of the 'tree' command. It
 * lists the content of a directory as a tree. This command expects one
 * argument, path to directory.
 * 
 * @author Karlo Bencic
 * 
 */
public class TreeShellCommand implements ShellCommand {

	/** The Constant COMMAND_NAME. */
	private static final String COMMAND_NAME = "tree";

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
			try {
				Path path = Paths.get(args.get(0));
				directoryTree(env, path.toFile(), 0);
			} catch (InvalidPathException e) {
				env.writeln("Invalid path: " + args.get(0));
			} catch (IllegalArgumentException e) {
				env.writeln(e.getMessage());
			}
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

	/**
	 * Recursively prints the directory tree.
	 *
	 * @param env
	 *            the environment
	 * @param root
	 *            the root
	 * @param level
	 *            the level
	 */
	private static void directoryTree(Environment env, File root, int level) {
		File[] files = root.listFiles();
		if (files == null) {
			throw new IllegalArgumentException("Not a directory: " + root.toString());
		}

		print(env, level, root.getName(), false);
		for (File file : files) {
			if (file.isDirectory()) {
				directoryTree(env, file, level + 1);
			} else {
				print(env, level + 1, file.getName(), true);
			}
		}
	}

	/**
	 * Prints the file or directory name.
	 *
	 * @param env
	 *            the environment
	 * @param level
	 *            the level
	 * @param name
	 *            the name
	 * @param isFile
	 *            the is file flag
	 */
	private static void print(Environment env, int level, String name, boolean isFile) {
		for (int i = 0; i < level; i++) {
			env.write(isFile ? "  " : "--");
		}
		env.writeln(name);
	}
}
