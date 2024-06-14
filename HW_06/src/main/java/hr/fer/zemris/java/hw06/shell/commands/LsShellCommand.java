package hr.fer.zemris.java.hw06.shell.commands;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributeView;
import java.nio.file.attribute.BasicFileAttributes;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellIOException;
import hr.fer.zemris.java.hw06.shell.ShellStatus;
import hr.fer.zemris.java.hw06.shell.ShellUtil;

/**
 * The {@code LsShellCommand} is an implementation of the 'ls' command. It lists
 * the content of a directory, non recursive. This command expects one argument.
 * The argument is path to the directory.
 * 
 * @author Karlo Bencic
 * 
 */
public class LsShellCommand implements ShellCommand {

	/** The Constant COMMAND_NAME. */
	private static final String COMMAND_NAME = "ls";

	/** The number of attributes. */
	private static int NUMBER_OF_ATTRIBUTES = 4;

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
			Path directoryPath;
			try {
				directoryPath = Paths.get(args.get(0));
			} catch (InvalidPathException e) {
				env.writeln("Invalid path: " + args.get(0));
				return ShellStatus.CONTINUE;
			}

			if (Files.isDirectory(directoryPath)) {
				String[] details;
				try {
					details = getDirectoryContentDetails(directoryPath, directoryPath.toFile());
				} catch (IOException e) {
					env.writeln("Failed to get directory content");
					return ShellStatus.CONTINUE;
				}
				for (String line : details) {
					env.writeln(line);
				}
			} else {
				env.writeln(String.format("%s is not a directory", directoryPath.toString()));
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

	/**
	 * Gets the directory content details.
	 *
	 * @param directoryPath
	 *            the directory path
	 * @param directory
	 *            the directory
	 * @return the directory content details
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	private String[] getDirectoryContentDetails(Path directoryPath, File directory) throws IOException {
		String[] content = directory.list();
		String[] out = new String[content.length];
		String[][] details = new String[content.length][NUMBER_OF_ATTRIBUTES];

		SimpleDateFormat timeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		int maxSizeLength = 0;
		for (int i = 0; i < content.length; i++) {
			Path contentPath = Paths.get(directoryPath.toString(), content[i]);

			BasicFileAttributes fileAttributes = Files
					.getFileAttributeView(contentPath, BasicFileAttributeView.class, LinkOption.NOFOLLOW_LINKS)
					.readAttributes();

			String[] attributes = new String[NUMBER_OF_ATTRIBUTES];
			attributes[0] = getFileAttributes(contentPath);
			attributes[1] = String.valueOf(Files.size(contentPath));
			attributes[2] = timeFormat.format(new Date(fileAttributes.creationTime().toMillis()));
			attributes[3] = content[i];

			details[i] = attributes;

			maxSizeLength = Math.max(maxSizeLength, attributes[1].length());
		}

		for (int i = 0; i < content.length; i++) {
			String[] data = details[i];
			out[i] = String.format("%s %" + maxSizeLength + "s %s %s", data[0], data[1], data[2], data[3]);
		}

		return out;
	}

	/**
	 * Gets the file attributes.
	 *
	 * @param path
	 *            the path
	 * @return the file attributes
	 */
	private String getFileAttributes(Path path) {
		String directory = Files.isDirectory(path) ? "d" : "-";
		String readable = Files.isReadable(path) ? "r" : "-";
		String writable = Files.isWritable(path) ? "w" : "-";
		String executable = Files.isExecutable(path) ? "x" : "-";

		return directory + readable + writable + executable;
	}
}
