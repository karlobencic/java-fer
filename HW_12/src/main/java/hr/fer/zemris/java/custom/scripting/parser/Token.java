package hr.fer.zemris.java.custom.scripting.parser;

import hr.fer.zemris.java.custom.scripting.elems.Element;

/**
 * This class represents a token generated by {@link SmartScriptLexer} to
 * process given text and encapsulate elements of the document.
 * 
 * @author Karlo Bencic
 *
 */
public class Token {

	/**
	 * Defines the type of token.
	 */
	private TokenType type;
	/**
	 * Element that it encapsulates.
	 */
	private Element element;

	/**
	 * Public constructor that takes two arguments, TokenType which describes
	 * what this token holds and an Element that it encapsulates.
	 * 
	 * @param type
	 *            Type of this token.
	 * @param element
	 *            Element to encapsulate.
	 */
	public Token(TokenType type, Element element) {
		if (type == null) {
			throw new IllegalArgumentException("Value given cannot be null.");
		}
		this.type = type;
		this.element = element;
	}

	/**
	 * Returns this tokens type.
	 * 
	 * @return {@link TokenType} of this token.
	 */
	public TokenType getType() {
		return type;
	}

	/**
	 * Returns this tokens element.
	 * 
	 * @return {@link Element} that it encapsulates.
	 */
	public Element getValue() {
		return element;
	}

	@Override
	public String toString() {
		return "(" + type + ", " + element.asText() + ")";
	}
}
