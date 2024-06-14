package hr.fer.zemris.bf.parser;

/**
 * This class represents a parser exception. It is thrown when the parser fails to
 * parse the script.
 * 
 * @author Karlo Bencic
 * 
 */
public class ParserException extends RuntimeException {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/**
	 * Instantiates a new parser exception.
	 */
	public ParserException() {
	}

	/**
	 * Instantiates a new parser exception.
	 *
	 * @param message
	 *            the exception message
	 */
	public ParserException(String message) {
		super(message);
	}

	/**
	 * Instantiates a new parser exception.
	 *
	 * @param cause
	 *            the exception cause
	 */
	public ParserException(Throwable cause) {
		super(cause);
	}

	/**
	 * Instantiates a new parser exception.
	 *
	 * @param message
	 *            the exception message
	 * @param cause
	 *            the exception cause
	 */
	public ParserException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * Instantiates a new parser exception.
	 *
	 * @param message
	 *            the exception message
	 * @param cause
	 *            the exception cause
	 * @param enableSuppression
	 *            the enable suppression
	 * @param writableStackTrace
	 *            the writable stack trace
	 */
	public ParserException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}