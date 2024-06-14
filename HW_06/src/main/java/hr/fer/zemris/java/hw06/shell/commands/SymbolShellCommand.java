package hr.fer.zemris.java.hw06.shell.commands;

import java.util.List;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellIOException;
import hr.fer.zemris.java.hw06.shell.ShellStatus;
import hr.fer.zemris.java.hw06.shell.ShellUtil;

/**
 * The {@code SymbolShellCommand} is an implementation of the 'symbol' command.
 * This command expects one or two arguments. First argument is a symbol
 * specifier. Available symbols are: PROMPT, MORELINES, MULTILINE. If only one
 * argument is specified, command writes which symbol is used in the shell
 * environment. If two arguments are specified, command sets a new symbol which
 * will be used in the shell environment.
 * 
 * @author Karlo Bencic
 * 
 */
public class SymbolShellCommand implements ShellCommand {

	/** The Constant COMMAND_NAME. */
	private static final String COMMAND_NAME = "symbol";

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
				String symbolName = args.get(0);
				char symbol = getSymbol(env, symbolName);
				env.writeln(String.format("Symbol for %s is '%s'", symbolName, symbol));
			} catch (IllegalArgumentException e) {
				env.writeln("Invalid symbol specifier: " + args.get(0));
			}
			return ShellStatus.CONTINUE;
		} else if (args.size() == 2 && args.get(1).length() == 1) {
			try {
				String symbolName = args.get(0);
				char newSymbol = args.get(1).charAt(0);
				char oldSymbol = setSymbol(env, symbolName, newSymbol);
				env.writeln(String.format("Symbol for %s changed from '%s' to '%s'", symbolName, oldSymbol, newSymbol));
			} catch (IllegalArgumentException e) {
				env.writeln("Invalid symbol length: " + args.get(0));
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
	 * Gets the symbol.
	 *
	 * @param env
	 *            the environment
	 * @param symbolName
	 *            the symbol name
	 * @return the symbol
	 */
	private char getSymbol(Environment env, String symbolName) {
		switch (symbolName.toUpperCase()) {
		case "PROMPT":
			return env.getPromptSymbol();
		case "MORELINES":
			return env.getMorelinesSymbol();
		case "MULTILINE":
			return env.getMultilineSymbol();
		default:
			throw new IllegalArgumentException();
		}
	}

	/**
	 * Sets the symbol.
	 *
	 * @param env
	 *            the environment
	 * @param symbolName
	 *            the symbol name
	 * @param symbol
	 *            the symbol
	 * @return the char
	 */
	private char setSymbol(Environment env, String symbolName, char symbol) {
		char oldSymbol;
		switch (symbolName.toUpperCase()) {
		case "PROMPT":
			oldSymbol = env.getPromptSymbol();
			env.setPromptSymbol(symbol);
			return oldSymbol;
		case "MORELINES":
			oldSymbol = env.getMorelinesSymbol();
			env.setMorelinesSymbol(symbol);
			return oldSymbol;
		case "MULTILINE":
			oldSymbol = env.getMultilineSymbol();
			env.setMultilineSymbol(symbol);
			return oldSymbol;
		default:
			throw new IllegalArgumentException();
		}
	}
}
