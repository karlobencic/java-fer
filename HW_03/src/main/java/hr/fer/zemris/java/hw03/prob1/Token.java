package hr.fer.zemris.java.hw03.prob1;

/**
 * The class Token represents a single token used by the lexer.
 * 
 * @author Karlo Bencic
 * 
 */
public class Token {
	
	/** The value object. */
	private Object value;
	
	/** The token type. */
	private TokenType type;

	/**
	 * Instantiates a new token.
	 *
	 * @param type
	 *            the token type
	 * @param value
	 *            the value object
	 */
	public Token(TokenType type, Object value) {
		this.type = type;
		this.value = value;
	}

	/**
	 * Gets the value object.
	 *
	 * @return the value object
	 */
	public Object getValue() {
		return value;
	}

	/**
	 * Gets the token type.
	 *
	 * @return the token type
	 */
	public TokenType getType() {
		return type;
	}
}
