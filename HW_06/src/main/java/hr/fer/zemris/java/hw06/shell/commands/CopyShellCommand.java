package hr.fer.zemris.java.hw06.shell.commands;

import java.io.*;
import java.nio.file.*;
import java.util.List;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellIOException;
import hr.fer.zemris.java.hw06.shell.ShellStatus;
import hr.fer.zemris.java.hw06.shell.ShellUtil;

/**
 * The {@code CopyShellCommand} is an implementation of the 'copy' command. It
 * copies the content of the given file to the another file or directory. This
 * command expects two arguments. First argument is a path to the source file.
 * Second argument is a path to the destination file.
 * 
 * @author Karlo Bencic
 * 
 */
public class CopyShellCommand implements ShellCommand {

	/** The Constant COMMAND_NAME. */
	private static final String COMMAND_NAME = "copy";

	/** The Constant BUFFER_SIZE. */
	private static final int BUFFER_SIZE = 4096;

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

		if (args.size() == 2) {
			Path source;
			try {
				source = Paths.get(args.get(0));
				if (!Files.exists(source)) {
					env.writeln(String.format("File %s does not exist", source));
					return ShellStatus.CONTINUE;
				} else if (Files.isDirectory(source)) {
					env.writeln(String.format("%s is not a file", source));
					return ShellStatus.CONTINUE;
				}
			} catch (InvalidPathException e) {
				env.writeln("Invalid path: " + args.get(0));
				return ShellStatus.CONTINUE;
			}

			Path destination;
			try {
				String destPath = args.get(1);
				if (Files.isDirectory(Paths.get(args.get(1)))) {
					destPath += File.separator + source.toString();
				}

				destination = Paths.get(destPath);
				if (Files.exists(destination)) {
					env.write("Do you want to overwrite file? Y/N: ");
					if (!env.readLine().toUpperCase().equals("Y")) {
						return ShellStatus.CONTINUE;
					}
				}
			} catch (InvalidPathException e) {
				env.writeln("Invalid path: " + args.get(1));
				return ShellStatus.CONTINUE;
			}

			try {
				copy(source.toFile(), destination.toFile());
			} catch (IOException e) {
				env.writeln("Unable to copy file, check your destination");
				return ShellStatus.CONTINUE;
			}
			env.writeln("File successfully copied");
			return ShellStatus.CONTINUE;
		}

		env.writeln(String.format("Invalid arguments for command %s: %s", getCommandName(), arguments));
		return ShellStatus.CONTINUE;
	}

	/**
	 * Copies the source file into destination file.
	 *
	 * @param source
	 *            the source file
	 * @param destination
	 *            the destination file
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	private void copy(File source, File destination) throws IOException {
		InputStream is = new FileInputStream(source);
		OutputStream os = new FileOutputStream(destination);

		byte[] buffer = new byte[BUFFER_SIZE];
		int readBytes = is.read(buffer);
		while (readBytes > -1) {
			os.write(buffer, 0, readBytes);
			readBytes = is.read(buffer);
		}

		is.close();
		os.close();
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
