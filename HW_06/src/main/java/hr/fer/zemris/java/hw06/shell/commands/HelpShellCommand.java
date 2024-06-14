package hr.fer.zemris.java.hw06.shell.commands;

import java.util.List;
import java.util.Map.Entry;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellIOException;
import hr.fer.zemris.java.hw06.shell.ShellStatus;
import hr.fer.zemris.java.hw06.shell.ShellUtil;

/**
 * The {@code HelpShellCommand} is an implementation of the 'help' command. This
 * command has one optional argument, command name. It prints all the available
 * commands if no arguments are given. If argument is given it prints the
 * description of the command.
 * 
 * @author Karlo Bencic
 * 
 */
public class HelpShellCommand implements ShellCommand {

	/** The Constant COMMAND_NAME. */
	private static final String COMMAND_NAME = "help";

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

		if (args.size() == 0) {
			for (String command : env.commands().keySet()) {
				env.writeln(command);
			}
			return ShellStatus.CONTINUE;
		}
		if (args.size() == 1) {
			for (Entry<String, ShellCommand> entry : env.commands().entrySet()) {
				if (!entry.getKey().equals(args.get(0))) {
					continue;
				}
				env.writeln(String.format("%s description: ", args.get(0)));
				for (String line : entry.getValue().getCommandDescription()) {
					env.writeln(line);
				}
				return ShellStatus.CONTINUE;
			}
			env.writeln(String.format("Command %s does not exist", args.get(0)));
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
