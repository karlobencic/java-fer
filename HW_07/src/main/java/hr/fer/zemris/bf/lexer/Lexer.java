package hr.fer.zemris.bf.lexer;

import java.util.Arrays;
import java.util.List;

/**
 * This class represents a tool to tokenize input.
 * 
 * @author Karlo Bencic
 * 
 */
public class Lexer {

	/** The data. */
	private char[] data;

	/** The token. */
	private Token token;

	/** The current index. */
	private int currentIndex;

	/** The Constant AND. */
	private static final String AND = "and";

	/** The Constant XOR. */
	private static final String XOR = "xor";

	/** The Constant OR. */
	private static final String OR = "or";

	/** The Constant NOT. */
	private static final String NOT = "not";

	/** The Constant TRUE. */
	private static final String TRUE = "true";

	/** The Constant FALSE. */
	private static final String FALSE = "false";

	/** The operators list. */
	private static final List<String> operators = Arrays.asList(AND, XOR, OR, NOT);

	/** The constants list. */
	private static final List<String> constants = Arrays.asList(TRUE, FALSE);

	/**
	 * Instantiates a new lexer.
	 *
	 * @param expression
	 *            the expression, not null
	 * @throws IllegalArgumentException
	 *             if expression is null
	 */
	public Lexer(String expression) {
		if (expression == null) {
			throw new IllegalArgumentException("Text can't be null.");
		}

		data = expression.toCharArray();
	}

	/**
	 * This method extracts the next token from the source text.
	 *
	 * @return the token
	 * @throws LexerException
	 *             if the token is invalid
	 */
	public Token nextToken() {
		if (token != null && token.getTokenType() == TokenType.EOF) {
			throw new LexerException("No tokens available.");
		}

		skipBlanks();

		if (currentIndex >= data.length) {
			token = new Token(TokenType.EOF, null);
			return token;
		}

		char currentValue = Character.valueOf(data[currentIndex]);
		int startIndex = currentIndex;
		currentIndex++;

		if (checkLetter(currentValue, startIndex)) {
			return token;
		}

		if (checkDigit(currentValue, startIndex)) {
			return token;
		}

		if (currentValue == '(') {
			token = new Token(TokenType.OPEN_BRACKET, currentValue);
			return token;
		}

		if (currentValue == ')') {
			token = new Token(TokenType.CLOSED_BRACKET, currentValue);
			return token;
		}

		if (currentValue == '*') {
			token = new Token(TokenType.OPERATOR, AND);
			return token;
		}

		if (currentValue == '+') {
			token = new Token(TokenType.OPERATOR, OR);
			return token;
		}

		if (currentValue == '!') {
			token = new Token(TokenType.OPERATOR, NOT);
			return token;
		}

		if (currentValue == ':') {
			if (currentIndex < data.length - 1 && data[currentIndex] == '+' && data[currentIndex + 1] == ':') {
				currentIndex += 2;
				token = new Token(TokenType.OPERATOR, XOR);
				return token;
			}
		}

		throw new LexerException(
				String.format("Invalid character found at index %d: '%c'.", currentIndex, data[currentIndex]));
	}

	/**
	 * Checks if the current character is a letter and moves the index while
	 * there are next tokens and the next token is a letter or a digit and sets
	 * the token to that value.
	 *
	 * @param currentValue
	 *            the current character
	 * @param startIndex
	 *            the start index
	 * @return true, if successful
	 */
	private boolean checkLetter(char currentValue, int startIndex) {
		if (!Character.isLetter(currentValue)) {
			return false;
		}

		while (hasNextToken() && Character.isLetterOrDigit(data[currentIndex])) {
			currentIndex++;
		}

		String ident = new String(data, startIndex, currentIndex - startIndex);

		if (operators.contains(ident.toLowerCase())) {
			token = new Token(TokenType.OPERATOR, ident.toLowerCase());
			return true;
		}

		if (constants.contains(ident.toLowerCase())) {
			boolean value = ident.toLowerCase().equals(FALSE) ? false : true;
			token = new Token(TokenType.CONSTANT, value);
			return true;
		}

		token = new Token(TokenType.VARIABLE, ident.toUpperCase());
		return true;
	}

	/**
	 * Checks if the current character is a digit and moves the index while
	 * there are next tokens and the next token is a digit and sets the token to
	 * that value.
	 *
	 * @param currentValue
	 *            the current character
	 * @param startIndex
	 *            the start index
	 * @return true, if successful
	 * @throws LexerException
	 *             if digit is not 0 or 1
	 */
	private boolean checkDigit(char currentValue, int startIndex) {
		if (!Character.isDigit(currentValue)) {
			return false;
		}

		while (hasNextToken() && Character.isDigit(data[currentIndex])) {
			currentIndex++;
		}

		String ident = new String(data, startIndex, currentIndex - startIndex);
		if (!ident.equals("0") && !ident.equals("1")) {
			throw new LexerException(String.format("Unexpected number: %s.", ident));
		}

		boolean value = Integer.valueOf(ident) == 0 ? false : true;
		token = new Token(TokenType.CONSTANT, value);
		return true;
	}

	/**
	 * Returns last generated token; can be called multiple times. Does not
	 * start generation of a new token.
	 * 
	 * @return last generated roken
	 */
	public Token getToken() {
		return token;
	}

	/**
	 * Checks if next token exists.
	 *
	 * @return true, if successful
	 */
	public boolean hasNextToken() {
		return currentIndex < data.length;
	}

	/**
	 * This method moves the current character index so it skips all empty
	 * characters (spaces, new lines, tabs).
	 */
	private void skipBlanks() {
		while (currentIndex < data.length) {
			char c = data[currentIndex];
			if (c == ' ' || c == '\t' || c == '\r' || c == '\n') {
				currentIndex++;
				continue;
			}
			if (c == '\\') {
				currentIndex++;
				continue;
			}
			break;
		}
	}
}
