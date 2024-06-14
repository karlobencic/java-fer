package hr.fer.zemris.java.hw06.shell.commands;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.*;
import java.util.List;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellIOException;
import hr.fer.zemris.java.hw06.shell.ShellStatus;
import hr.fer.zemris.java.hw06.shell.ShellUtil;

/**
 * The {@code HexdumpShellCommand} is an implementation of the 'hexdump'
 * command. It writes a binary view of the file in hexadecimal notation. This
 * command expects one argument, path to the file.
 * 
 * @author Karlo Bencic
 * 
 */
public class HexdumpShellCommand implements ShellCommand {

	/** The Constant COMMAND_NAME. */
	private static final String COMMAND_NAME = "hexdump";

	/** The Constant BUFFER_SIZE. */
	private static final int BUFFER_SIZE = 16;

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
			File file;
			try {
				Path path = Paths.get(args.get(0));
				if (!Files.isReadable(path) || Files.isDirectory(path)) {
					env.writeln("Unable to read file: " + args.get(0));
					return ShellStatus.CONTINUE;
				}

				file = path.toFile();
			} catch (InvalidPathException e) {
				env.writeln("Invalid path: " + args.get(0));
				return ShellStatus.CONTINUE;
			}

			try (InputStream inputStream = new FileInputStream(file)) {
				byte[] buffer = new byte[BUFFER_SIZE];
				int counter = 0;
				int readBytes = inputStream.read(buffer);
				while (readBytes > -1) {
					String memoryLocation = String.format("%08X", counter);
					env.writeln(String.format("%s: %s| %s| %s", memoryLocation, bufferRange(buffer, 0, 8, readBytes),
							bufferRange(buffer, 8, 16, readBytes), bufferToChars(buffer, readBytes)));

					readBytes = inputStream.read(buffer);
					counter += 16;
				}
			} catch (IOException e) {
				env.writeln("Unable to open file: " + file.toString());
			}

			return ShellStatus.CONTINUE;
		}

		env.writeln(String.format("Invalid arguments for command %s: %s", getCommandName(), arguments));
		return ShellStatus.CONTINUE;
	}

	/**
	 * Converts bytes to characters on a given range.
	 *
	 * @param buffer
	 *            the buffer
	 * @param start
	 *            the start index
	 * @param end
	 *            the end index
	 * @param valid
	 *            the buffer size limit
	 * @return the characters
	 */
	private String bufferRange(byte[] buffer, int start, int end, int valid) {
		String output = "";
		for (int i = start; i < Math.min(buffer.length, end); i++) {
			output += (i < valid ? String.format(("%02X "), buffer[i]) : "   ");
		}
		return output;
	}

	/**
	 * Converts bytes to characters.
	 *
	 * @param buffer
	 *            the buffer
	 * @param valid
	 *            the buffer size limit
	 * @return the characters
	 */
	private String bufferToChars(byte[] buffer, int valid) {
		String output = "";
		for (int i = 0; i < buffer.length; i++) {
			if (i >= valid) {
				break;
			}
			output += (buffer[i] >= 32) && (buffer[i] <= 127) ? String.valueOf((char) buffer[i]) : ".";
		}
		return output;
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
