package hr.fer.zemris.java.hw03.prob1;

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

	/** The current character index. */
	private int currentIndex;

	/** The current lexer state. */
	private LexerState currentState = LexerState.BASIC;

	/** The symbols. */
	private final char[] symbols = new char[] { ')', '(', '=', '.', ',', ';', '-', '?', '#', '!' };

	/**
	 * Instantiates a new lexer.
	 *
	 * @param text
	 *            the text to be parsed
	 */
	public Lexer(String text) {
		if (text == null) {
			throw new IllegalArgumentException("Text can't be null.");
		}

		data = text.toCharArray();
	}

	/**
	 * This method extracts the next token from the source text.
	 *
	 * @return the token
	 * @throws LexerException
	 *             if the token is invalid
	 */
	public Token nextToken() {
		if (token != null && token.getType() == TokenType.EOF) {
			throw new LexerException("No tokens available.");
		}

		skipBlanks();

		if (data.length > 0 && data[data.length - 1] == '\\') {
			throw new LexerException("Invalid escape.");
		}

		if (currentIndex > 0 && currentIndex < data.length && data[currentIndex - 1] == '\\'
				&& Character.isLetter(data[currentIndex])) {
			throw new LexerException("Invalid escape.");
		}

		if (currentIndex >= data.length) {
			token = new Token(TokenType.EOF, null);
			return token;
		}

		if (currentState == LexerState.EXTENDED) {
			int startIndex = currentIndex;
			currentIndex++;
			while (currentIndex < data.length && data[currentIndex] != ' ') {
				if (data[currentIndex] == '#' || data[startIndex] == '#') {
					break;
				}
				currentIndex++;
			}

			String value = new String(data, startIndex, currentIndex - startIndex);
			if (isSymbol(value.charAt(0))) {
				token = new Token(TokenType.SYMBOL, value.charAt(0));
				return token;
			}

			token = new Token(TokenType.WORD, value);
			return token;
		}

		char currentValue = Character.valueOf(data[currentIndex]);
		if (isSymbol(currentValue)) {
			token = new Token(TokenType.SYMBOL, currentValue);
			currentIndex++;
			return token;
		}

		if (Character.isLetter(currentValue)) {
			int startIndex = currentIndex;
			currentIndex++;
			while (currentIndex < data.length && (data[currentIndex] == '\\' || Character.isLetter(data[currentIndex])
					|| data[currentIndex - 1] == '\\' && Character.isDigit(data[currentIndex]))) {
				currentIndex++;
			}

			String value = new String(data, startIndex, currentIndex - startIndex);
			value = unescape(value);
			token = new Token(TokenType.WORD, value);
			return token;
		}

		if (Character.isDigit(currentValue)) {
			boolean isWord = false;
			if (currentIndex > 0 && data[currentIndex - 1] == '\\') {
				isWord = true;
			}

			int startIndex = currentIndex;
			currentIndex++;
			while (currentIndex < data.length
					&& (Character.isDigit(data[currentIndex]) || (isWord && Character.isLetter(data[currentIndex])))) {
				currentIndex++;
			}

			String value = new String(data, startIndex, currentIndex - startIndex);
			if (!isWord) {
				try {
					long number = Long.parseLong(value);
					token = new Token(TokenType.NUMBER, number);
					return token;
				} catch (NumberFormatException ex) {
					throw new LexerException("Invalid number format.");
				}
			}
			token = new Token(TokenType.WORD, value);
			return token;
		}

		throw new LexerException("Invalid character found: '" + data[currentIndex] + "'.");
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

	/**
	 * Removes all occurences of '\' character in a string.
	 *
	 * @param input
	 *            the string input
	 * @return the unescaped string
	 */
	private String unescape(String input) {
		if (input == null) {
			throw new IllegalArgumentException("Input can't be null.");
		}
		
		StringBuilder sb = new StringBuilder(input);
		for (int i = 0; i < sb.length() - 1; i++) {
			if (sb.charAt(i) == '\\' && sb.charAt(i + 1) != '\\') {
				sb.deleteCharAt(i);
			}
		}
		
		if (sb.charAt(sb.length() - 1) == '\\') {
			sb.deleteCharAt(sb.length() - 1);
		}
		
		return sb.toString();
	}

	/**
	 * Checks if the character is a symbol.
	 *
	 * @param c
	 *            the character
	 * @return true, if is symbol
	 */
	private boolean isSymbol(char c) {
		for (char symbol : symbols) {
			if (c == symbol) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Sets the lexer state.
	 *
	 * @param state
	 *            the new state
	 * @throws IllegalArgumentException
	 *             if state is null
	 */
	public void setState(LexerState state) {
		if (state == null) {
			throw new IllegalArgumentException("State can't be null.");
		}
		currentState = state;
	}
}