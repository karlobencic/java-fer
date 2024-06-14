package hr.fer.zemris.java.custom.scripting.lexer;

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
		int startIndex = currentIndex;
		currentIndex++;
		
		if (currentState == LexerState.EXTENDED) {
			while (currentIndex < data.length) {
				currentIndex++;
				if (currentIndex > 0 && data[currentIndex - 1] == '$' && data[currentIndex] == '}') {
					currentIndex++;
					break;
				}
			}

			String value = new String(data, startIndex, currentIndex - startIndex).replaceAll("\\s+"," ");
			token = new Token(TokenType.TAG, value);
			return token;
		}
		
		while (currentIndex < data.length) {		
			if(currentIndex > 0 && data[currentIndex - 1] == '\\' && data[currentIndex] != '{' && data[currentIndex] != '\\') {
				throw new LexerException("Invalid escape.");
			}
			
			if (currentIndex > 0 && data[currentIndex - 1] == '{' && data[currentIndex] == '$') {
				if(currentIndex > 1 && data[currentIndex - 2] == '\\') {
					currentIndex++;
					continue;
				}
				currentIndex--;
				break;
			}		
			currentIndex++;
		}

		String value = new String(data, startIndex, currentIndex - startIndex);
		token = new Token(TokenType.TEXT, value);
		return token;
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
	
	/**
	 * Checks if next token exists.
	 *
	 * @return true, if successful
	 */
	public boolean hasNextToken() {
		return currentIndex < data.length;
	}
}
