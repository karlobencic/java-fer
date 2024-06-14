package hr.fer.zemris.java.custom.scripting.parser;

/**
 * This class represents a parser exception. It is thrown when the parser fails to
 * parse the script.
 * 
 * @author Karlo Bencic
 * 
 */
public class SmartScriptParserException extends RuntimeException {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/**
	 * Instantiates a new smart script parser exception.
	 */
	public SmartScriptParserException() {
		super();
	}

	/**
	 * Instantiates a new smart script parser exception.
	 *
	 * @param message
	 *            the message
	 * @param cause
	 *            the cause
	 */
	public SmartScriptParserException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * Instantiates a new smart script parser exception.
	 *
	 * @param message
	 *            the message
	 */
	public SmartScriptParserException(String message) {
		super(message);
	}

	/**
	 * Instantiates a new smart script parser exception.
	 *
	 * @param cause
	 *            the cause
	 */
	public SmartScriptParserException(Throwable cause) {
		super(cause);
	}
}
