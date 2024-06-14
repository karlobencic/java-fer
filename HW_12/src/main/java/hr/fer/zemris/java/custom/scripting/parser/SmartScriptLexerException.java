package hr.fer.zemris.java.custom.scripting.parser;

/**
 * This class represents a lexer exception. It is thrown when the lexer fails to
 * get the next token.
 * 
 * @author Karlo Bencic
 * 
 */
public class SmartScriptLexerException extends RuntimeException {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/**
	 * Instantiates a new lexer exception.
	 */
	public SmartScriptLexerException() {
		super();
	}

	/**
	 * Instantiates a new lexer exception.
	 *
	 * @param message
	 *            the exception message
	 * @param cause
	 *            the exception cause
	 */
	public SmartScriptLexerException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * Instantiates a new lexer exception.
	 *
	 * @param message
	 *            the exception message
	 */
	public SmartScriptLexerException(String message) {
		super(message);
	}

	/**
	 * Instantiates a new lexer exception.
	 *
	 * @param cause
	 *            the exception cause
	 */
	public SmartScriptLexerException(Throwable cause) {
		super(cause);
	}

}
