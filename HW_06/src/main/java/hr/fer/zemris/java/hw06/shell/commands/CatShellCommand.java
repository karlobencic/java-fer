package hr.fer.zemris.java.hw06.shell.commands;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.IllegalCharsetNameException;
import java.nio.file.*;
import java.util.List;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellIOException;
import hr.fer.zemris.java.hw06.shell.ShellStatus;
import hr.fer.zemris.java.hw06.shell.ShellUtil;

/**
 * The {@code CatShellCommand} is an implementation of the 'cat' command. It
 * writes the content of the given file to the console. This command expects one
 * or two arguments. First argument is path to a file. Second argument is
 * optional and represents a charset which should be used for reading a file.
 * 
 * @author Karlo Bencic
 * 
 */
public class CatShellCommand implements ShellCommand {

	/** The Constant COMMAND_NAME. */
	private static final String COMMAND_NAME = "cat";

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

		if (args.size() == 1 || args.size() == 2) {
			Path filePath;
			try {
				filePath = Paths.get(args.get(0));
			} catch (InvalidPathException e) {
				env.writeln("Invalid path: " + args.get(0));
				return ShellStatus.CONTINUE;
			}

			Charset charset;
			try {
				charset = args.size() == 1 ? Charset.defaultCharset() : Charset.forName(args.get(1));
			} catch (IllegalCharsetNameException e) {
				env.writeln("Invalid charset name: " + args.get(1));
				return ShellStatus.CONTINUE;
			}

			try {
				if (Files.isReadable(filePath)) {
					try {
						for (String line : Files.readAllLines(filePath, charset)) {
							env.writeln(line);
						}
					} catch (IOException e) {
						env.writeln(String.format("%s is not a file", filePath));
					}
				} else {
					env.writeln("Unable to read file: " + filePath);
				}
			} catch (SecurityException e) {
				env.writeln("You don't have permission to read file: " + filePath);
				return ShellStatus.CONTINUE;
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
}
