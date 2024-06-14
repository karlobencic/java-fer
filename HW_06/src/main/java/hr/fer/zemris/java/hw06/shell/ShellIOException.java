package hr.fer.zemris.java.hw06.shell;

/**
 * The {@code ShellIOException} class represents a shell exception. It is thrown
 * when the shell fails to execute a given command.
 * 
 * @author Karlo Bencic
 * 
 */
public class ShellIOException extends RuntimeException {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/**
	 * Instantiates a new shell IO exception.
	 */
	public ShellIOException() {
	}

	/**
	 * Instantiates a new shell IO exception.
	 *
	 * @param message
	 *            the exception message
	 */
	public ShellIOException(String message) {
		super(message);
	}

	/**
	 * Instantiates a new shell IO exception.
	 *
	 * @param cause
	 *            the exception cause
	 */
	public ShellIOException(Throwable cause) {
		super(cause);
	}

	/**
	 * Instantiates a new shell IO exception.
	 *
	 * @param message
	 *            the exception message
	 * @param cause
	 *            the exception cause
	 */
	public ShellIOException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * Instantiates a new shell IO exception.
	 *
	 * @param message
	 *            the exception message
	 * @param cause
	 *            the exception cause
	 * @param enableSuppression
	 *            the enable suppression flag
	 * @param writableStackTrace
	 *            the writable stack trace flag
	 */
	public ShellIOException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
