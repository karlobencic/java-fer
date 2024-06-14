package hr.fer.zemris.bf.lexer;

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
	 * @param tokenType
	 *            the token type
	 * @param tokenValue
	 *            the token value
	 */
	public Token(TokenType tokenType, Object tokenValue) {
		value = tokenValue;
		type = tokenType;
	}

	/**
	 * Gets the token type.
	 *
	 * @return the token type
	 */
	public TokenType getTokenType() {
		return type;
	}

	/**
	 * Gets the token value.
	 *
	 * @return the token value
	 */
	public Object getTokenValue() {
		return value;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		if (value != null) {
			return String.format("Type: %s, Value: %s, Value is instance of: %s", String.valueOf(type),
					String.valueOf(value), value.getClass());
		}
		return String.format("Type: %s, Value: %s", String.valueOf(type), String.valueOf(value));
	}

}
