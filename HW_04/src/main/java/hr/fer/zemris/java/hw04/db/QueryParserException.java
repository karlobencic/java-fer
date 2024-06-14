package hr.fer.zemris.java.hw04.db;

/**
 * This class represents a parser exception. It is thrown when the parser fails to
 * parse the query.
 * 
 * @author Karlo Bencic
 * 
 */
public class QueryParserException extends RuntimeException {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/**
	 * Instantiates a new query parser exception.
	 */
	public QueryParserException() {
		super();
	}

	/**
	 * Instantiates a new query parser exception.
	 *
	 * @param message
	 *            the message
	 * @param cause
	 *            the cause
	 */
	public QueryParserException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * Instantiates a new query parser exception.
	 *
	 * @param message
	 *            the message
	 */
	public QueryParserException(String message) {
		super(message);
	}

	/**
	 * Instantiates a new query parser exception.
	 *
	 * @param cause
	 *            the cause
	 */
	public QueryParserException(Throwable cause) {
		super(cause);
	}
}
