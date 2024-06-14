package hr.fer.zemris.java.hw06.shell.commands;

import java.util.List;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellStatus;
import hr.fer.zemris.java.hw06.shell.ShellUtil;

/**
 * The {@code ExitShellCommand} is an implementation of the 'exit' command. It
 * closes the shell. This command expects no arguments.
 * 
 * @author Karlo Bencic
 * 
 */
public class ExitShellCommand implements ShellCommand {
	
	/** The Constant COMMAND_NAME. */
	private static final String COMMAND_NAME = "exit";
	
    /** The command description. */
    private List<String> commandDescription;

	/* (non-Javadoc)
	 * @see hr.fer.zemris.java.hw06.shell.commands.ShellCommand#executeCommand(hr.fer.zemris.java.hw06.shell.Environment, java.lang.String)
	 */
	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		return ShellStatus.TERMINATE;
	}

	/* (non-Javadoc)
	 * @see hr.fer.zemris.java.hw06.shell.commands.ShellCommand#getCommandName()
	 */
	@Override
	public String getCommandName() {
		return COMMAND_NAME;
	}

	/* (non-Javadoc)
	 * @see hr.fer.zemris.java.hw06.shell.commands.ShellCommand#getCommandDescription()
	 */
	@Override
	public List<String> getCommandDescription() {
		if (commandDescription == null) {
			commandDescription = ShellUtil.getDescription(getClass(), COMMAND_NAME);
		}
		return commandDescription;
	}

}
