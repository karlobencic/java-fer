package hr.fer.zemris.java.custom.collections;

/**
 * This class represents an empty stack exception. It is thrown when the stack
 * has no elements but you try to access it.
 * 
 * @author Karlo Bencic
 * 
 */
public class EmptyStackException extends RuntimeException {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/**
	 * Instantiates a new empty stack exception.
	 *
	 * @param message
	 *            the exception message
	 */
	public EmptyStackException(String message) {
		super(message);
	}
}
