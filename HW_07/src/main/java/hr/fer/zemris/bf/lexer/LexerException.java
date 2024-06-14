package hr.fer.zemris.bf.lexer;

/**
 * This class represents a lexer exception. It is thrown when the lexer fails to
 * get the next token.
 * 
 * @author Karlo Bencic
 * 
 */
public class LexerException extends RuntimeException {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;


	/**
	 * Instantiates a new lexer exception.
	 */
	public LexerException() {
	}

	/**
	 * Instantiates a new lexer exception.
	 *
	 * @param message
	 *            the exception message
	 */
	public LexerException(String message) {
		super(message);
	}

	/**
	 * Instantiates a new lexer exception.
	 *
	 * @param cause
	 *            the exception cause
	 */
	public LexerException(Throwable cause) {
		super(cause);
	}

	/**
	 * Instantiates a new lexer exception.
	 *
	 * @param message
	 *            the exception message
	 * @param cause
	 *            the exception cause
	 */
	public LexerException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * Instantiates a new lexer exception.
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
	public LexerException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
