package hr.fer.zemris.java.hw06.shell;

import java.util.Collections;
import java.util.Scanner;
import java.util.SortedMap;
import java.util.TreeMap;

import hr.fer.zemris.java.hw06.shell.commands.*;

/**
 * The {@code MyShell} program is a command line program. It has the following
 * features:
 * <ul>
 * <li><code>symbol</code> - change <code>PROMPT</code>, <code>MULTILINE</code>
 * or <code>MORELINES</code> symbol.</li>
 * <li><code>charsets</code> - get the list of the all available charsets on
 * this machine.</li>
 * <li><code>cat</code> - write the content of the given file to the
 * console.</li>
 * <li><code>ls</code> - lists the provided directory.</li>
 * <li><code>tree</code> - print a tree-list of provided directory.</li>
 * <li><code>copy</code> - copy file to another file or directory.</li>
 * <li><code>mkdir</code> - make a new directory.</li>
 * <li><code>hexdump</code> - dumps file content as a hex-values.</li>
 * <li><code>help</code> - lists all the available commands.</li>
 * <li><code>exit</code> - exits the console.</li>
 * </ul>
 * 
 * @author Karlo Bencic
 * 
 */
public class MyShell {

	/**
	 * The {@code EnvironmentImpl} class is an implementation of the
	 * {@link Environment}.
	 * 
	 * @author Karlo Bencic
	 * 
	 */
	private static class EnvironmentImpl implements Environment {

		/** The morelines symbol. */
		private char morelinesSymbol = '\\';

		/** The multiline symbol. */
		private char multilineSymbol = '|';

		/** The prompt symbol. */
		private char promptSymbol = '>';

		/*
		 * (non-Javadoc)
		 * 
		 * @see hr.fer.zemris.java.hw06.shell.Environment#readLine()
		 */
		@Override
		public String readLine() throws ShellIOException {
			if(sc == null) {
				throw new ShellIOException();
			}
			
			String line = "";
			if (sc.hasNextLine()) {
				line = sc.nextLine();
				while (line.endsWith(" " + Character.toString(morelinesSymbol))) {
					System.out.print(multilineSymbol + " ");
					String newLine = sc.nextLine();
					line = line.substring(0, line.length() - 1);
					line += newLine;
				}
			}
			return line.trim();
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * hr.fer.zemris.java.hw06.shell.Environment#write(java.lang.String)
		 */
		@Override
		public void write(String text) throws ShellIOException {
			System.out.print(text);
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * hr.fer.zemris.java.hw06.shell.Environment#writeln(java.lang.String)
		 */
		@Override
		public void writeln(String text) throws ShellIOException {
			System.out.println(text);
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see hr.fer.zemris.java.hw06.shell.Environment#commands()
		 */
		@Override
		public SortedMap<String, ShellCommand> commands() {
			return Collections.unmodifiableSortedMap(commands);
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see hr.fer.zemris.java.hw06.shell.Environment#getMultilineSymbol()
		 */
		@Override
		public Character getMultilineSymbol() {
			return multilineSymbol;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * hr.fer.zemris.java.hw06.shell.Environment#setMultilineSymbol(java.
		 * lang.Character)
		 */
		@Override
		public void setMultilineSymbol(Character symbol) {
			multilineSymbol = symbol;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see hr.fer.zemris.java.hw06.shell.Environment#getPromptSymbol()
		 */
		@Override
		public Character getPromptSymbol() {
			return promptSymbol;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * hr.fer.zemris.java.hw06.shell.Environment#setPromptSymbol(java.lang.
		 * Character)
		 */
		@Override
		public void setPromptSymbol(Character symbol) {
			promptSymbol = symbol;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see hr.fer.zemris.java.hw06.shell.Environment#getMorelinesSymbol()
		 */
		@Override
		public Character getMorelinesSymbol() {
			return morelinesSymbol;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * hr.fer.zemris.java.hw06.shell.Environment#setMorelinesSymbol(java.
		 * lang.Character)
		 */
		@Override
		public void setMorelinesSymbol(Character symbol) {
			morelinesSymbol = symbol;
		}
	}

	/** The version constant. */
	private static final String VERSION = "1.0";
	
	/** The scanner. */
	private static Scanner sc = new Scanner(System.in);

	/**
	 * The commands map where key is command name and value is command wrapper.
	 */
	private static SortedMap<String, ShellCommand> commands = new TreeMap<>();

	static {
		commands.put("symbol", new SymbolShellCommand());
		commands.put("cat", new CatShellCommand());
		commands.put("charsets", new CharsetsShellCommand());
		commands.put("ls", new LsShellCommand());
		commands.put("tree", new TreeShellCommand());
		commands.put("mkdir", new MkdirShellCommand());
		commands.put("copy", new CopyShellCommand());
		commands.put("hexdump", new HexdumpShellCommand());
		commands.put("help", new HelpShellCommand());
		commands.put("exit", new ExitShellCommand());
	}

	/**
	 * The main method.
	 *
	 * @param args
	 *            the arguments
	 */
	public static void main(String[] args) {
		System.out.printf("Welcome to MyShell v %s%n", VERSION);

		Environment env = new EnvironmentImpl();
		ShellStatus status = ShellStatus.CONTINUE;
		while (status != ShellStatus.TERMINATE) {
			System.out.printf("%s ", env.getPromptSymbol());
			String line;
			try {
				line = parseCommand(env);
			} catch(ShellIOException ex) {
				System.out.println("An error occurred during IO.");
				status = ShellStatus.TERMINATE;
				return;
			}
			
			int separator = line.indexOf(' ');
			String commandName = separator == -1 ? line : line.substring(0, separator);
			String arguments = separator == -1 ? "" : line.substring(separator + 1);

			ShellCommand command = commands.get(commandName);
			if(command == null) {
				System.out.printf("Command '%s' does not exists%n", commandName);
				continue;
			}
			status = command.executeCommand(env, arguments);
		}

		sc.close();
	}

	/**
	 * Parses the command.
	 *
	 * @param env
	 *            the environment
	 * @return the command
	 */
	private static String parseCommand(Environment env) throws ShellIOException {
		String command = "";
		String line = env.readLine();

		if (line.endsWith(env.getMorelinesSymbol().toString()) && line.charAt(line.length() - 1) == ' ') {
			command += line.substring(0, line.length() - 1);
			String nextLine = env.readLine();
			while (nextLine != null && nextLine.startsWith(String.valueOf(env.getMultilineSymbol()))) {
				if (nextLine.endsWith(String.valueOf(env.getMorelinesSymbol()))
						&& nextLine.charAt(nextLine.length() - 1) == ' ') {
					command += nextLine.substring(1, nextLine.length() - 1);
					nextLine = env.readLine();
				} else {
					command += nextLine.substring(1);
					nextLine = "";
				}			
			}
		} else {
			command += line;
		}

		return command;
	}

}
