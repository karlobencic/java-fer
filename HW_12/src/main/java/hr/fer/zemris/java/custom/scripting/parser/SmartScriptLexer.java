package hr.fer.zemris.java.custom.scripting.parser;

import hr.fer.zemris.java.custom.scripting.elems.ElementConstantDouble;
import hr.fer.zemris.java.custom.scripting.elems.ElementConstantInteger;
import hr.fer.zemris.java.custom.scripting.elems.ElementFunction;
import hr.fer.zemris.java.custom.scripting.elems.ElementOperator;
import hr.fer.zemris.java.custom.scripting.elems.ElementString;
import hr.fer.zemris.java.custom.scripting.elems.ElementVariable;

/**
 * This class represents a tool to tokenize input and atomically divide it into
 * smaller lexical parts to generate tokens for {@link SmartScriptParser}. This
 * lexer has two states: TEXT and TAG state. While inside TEXT state everything
 * is treated as text and generated as 1 String token. This behavior is default
 * and it is changed when lexer encounters "{$" tag when enters TAG state. While
 * in tag state lexer has a set of rules to further tokenize the text. Lexer
 * stays in TAG state until it encounters closing tag "$}".
 * 
 * @author Karlo Bencic
 *
 */
public class SmartScriptLexer {

	/** Loaded text as an char data array. */
	private char[] data;

	/** Current index in text. */
	private int currentIndex;

	/** Current state of lexer */
	private SmartScriptLexerState state = SmartScriptLexerState.TEXT;

	/** Variable to store lexing tokens. */
	private Token token;

	/**
	 * Public constructor for this lexer, only argument is the text that you
	 * want to lexically analyze.
	 * 
	 * @param text
	 *            Text to be lexically analyzed.
	 */
	public SmartScriptLexer(String text) {
		if (text == null) {
			throw new IllegalArgumentException("Provided text cannot be null");
		}
		this.data = text.trim().toCharArray();
	}

	/**
	 * Sets this lexer state, allowed states are TEXT and TAG. While in TEXT
	 * state lexer treats everything as text and the TAG state is entered when
	 * "{$" is encountered in text.
	 * 
	 * @param state
	 *            the next state
	 */
	public void setState(SmartScriptLexerState state) {
		if (state == null) {
			throw new IllegalArgumentException("Invalid state provided, state cannot be null");
		}
		this.state = state;
	}

	/**
	 * Depending on the current state of this lexer this method analyzes word
	 * until next whitespace and returns according ScriptToken
	 * 
	 * @return next token from given text
	 */
	public Token nextToken() {
		if (token != null && token.getType() == TokenType.EOF) {
			throw new SmartScriptLexerException("No more tokens to be generated.");
		}
		if (currentIndex == data.length) {
			return new Token(TokenType.EOF, null);
		}

		switch (state) {
		case TEXT:
			token = processText();
			break;
		case TAG:
			token = processTag();
			break;
		}

		return token;
	}

	/**
	 * When in TEXT state nextToken method delegates work to this method which
	 * treats everything until "{$" tag as text and generates textTokens from
	 * given text.
	 * 
	 * @return processed script token with according {@code Element}.
	 */
	private Token processText() {
		try {
			if (data[currentIndex] == '{' && data[currentIndex + 1] == '$') {
				setState(SmartScriptLexerState.TAG);
				currentIndex += 2;
				token = new Token(TokenType.TAG, new ElementString("TAG"));
			} else {
				token = textToken();
			}
		} catch (Exception e) {
			throw new SmartScriptLexerException("Invalid opening tag.");
		}

		return token;
	}

	/**
	 * When in TAG state nextToken method delegates work to this method which
	 * analyzes language tags and returns according script tokens that represent
	 * elements from given text.
	 * 
	 * @return processed script token with according Element.
	 */
	private Token processTag() {
		while (Character.toString(data[currentIndex]).matches("\\s+") && currentIndex < data.length - 1) {
			currentIndex++;
		}
		if (data[currentIndex] == '$' && data[currentIndex + 1] == '}') {
			setState(SmartScriptLexerState.TEXT);
			currentIndex += 2;
			return new Token(TokenType.TAG, new ElementString("TAG"));
		}

		if (data[currentIndex] == '=' && token.getType() == TokenType.TAG) {
			currentIndex++;
			return new Token(TokenType.STRING, new ElementString("="));
		} else if (data[currentIndex] == '@') {
			return functionToken();
		} else if (data[currentIndex] == '"') {
			return stringToken();
		} else if (Character.isLetter(data[currentIndex])) {
			return variableToken();
		} else if (Character.isDigit(data[currentIndex])) {
			return numberToken();
		}
		return symbolToken();
	}

	/**
	 * Method which generates tokens which represent mathematical operators
	 * power(^), division(/), multiplication(*), addition(+) and subtraction(-).
	 * 
	 * @return descriptive {@code Token} with according {@code ElementOperator}
	 *         from analyzed text.
	 */
	private Token symbolToken() {
		char symbol = data[currentIndex];

		if (symbol == '-' && currentIndex + 1 < data.length && Character.isDigit(data[currentIndex + 1])) {
			return numberToken();
		} else if (symbol == '-' || symbol == '+' || symbol == '*' || symbol == '/' || symbol == '^') {
			return new Token(TokenType.OPERATOR, new ElementOperator(String.valueOf(data[currentIndex++])));
		} else {
			throw new SmartScriptLexerException("Symbol (operator) is invalid.");
		}
	}

	/**
	 * Method which generates number tokens that can be integer or double
	 * precision values. If number is invalid an exception is thrown.
	 * 
	 * @return descriptive {@code Token} with {@code ElementConstantDouble} or
	 *         {@code ElementConstantInteger} value.
	 */
	private Token numberToken() {
		StringBuilder sb = new StringBuilder();
		if (data[currentIndex] == '-') {
			sb.append(data[currentIndex++]);
		}

		int dotCounter = 0;
		while (currentIndex < data.length && (Character.isDigit(data[currentIndex]) || data[currentIndex] == '.')) {
			if (data[currentIndex] == '.') {
				dotCounter++;
			}
			sb.append(data[currentIndex++]);
		}

		if (dotCounter > 1) {
			throw new SmartScriptLexerException("Watch it with those dots, numbers only contain 1 dot, no more.");
		}
		String value = sb.toString();
		if (value.contains(".")) {
			try {
				return new Token(TokenType.CONSTANT_DOUBLE, new ElementConstantDouble(Double.parseDouble(value)));
			} catch (Exception e) {
				throw new SmartScriptLexerException("Couldnt parse double");
			}
		} else {
			try {
				return new Token(TokenType.CONSTANT_DOUBLE, new ElementConstantInteger(Integer.parseInt(value)));
			} catch (Exception e) {
				throw new SmartScriptLexerException("Couldnt parse integer");
			}
		}

	}

	/**
	 * This method analyzes variables within document inside tag state and
	 * returns according variable token.
	 * 
	 * @return descriptive {@code Token} with according {@code ElementVariable}.
	 */
	private Token variableToken() {
		if (!Character.isLetter(data[currentIndex])) {
			throw new SmartScriptLexerException("Your variable name is not valid, it is invalid");
		}

		StringBuilder sb = new StringBuilder();
		while ((Character.isLetter(data[currentIndex]) || Character.isDigit(data[currentIndex])
				|| data[currentIndex] == '_') && currentIndex < data.length) {
			sb.append(data[currentIndex++]);
		}

		return new Token(TokenType.VARIABLE, new ElementVariable(sb.toString()));
	}

	/**
	 * This method analyzes given function within given document inside tag
	 * state and returns according token.
	 * 
	 * @return descriptive {@code Token} with given {@code ElementFunction} inside.
	 */
	private Token functionToken() {
		StringBuilder sb = new StringBuilder();
		currentIndex++;
		while ((Character.isLetter(data[currentIndex]) || Character.isDigit(data[currentIndex])
				|| data[currentIndex] == '_') && currentIndex < data.length) {
			sb.append(data[currentIndex++]);
		}
		return new Token(TokenType.FUNCTION, new ElementFunction(sb.toString()));
	}

	/**
	 * This method generates text tokens in text state.
	 * 
	 * @return descriptive {@code Token} containing {@code ElementString} in text state.
	 */
	private Token textToken() {
		StringBuilder sb = new StringBuilder();
		while (currentIndex < data.length && data[currentIndex] != '{') {
			if (hasEscape(sb)) {
				sb.append(data[currentIndex++]);
			}
		}

		return new Token(TokenType.TEXT, new ElementString(sb.toString()));
	}

	/**
	 * This method generates string tokens in tag state.
	 * 
	 * @return descriptive ScriptToken containing {@code ElementString}
	 */
	private Token stringToken() {
		StringBuilder sb = new StringBuilder();
		currentIndex++;
		while (currentIndex < data.length && data[currentIndex] != '"') {
			if (hasEscape(sb)) {
				sb.append(data[currentIndex++]);
			}
		}
		currentIndex++;
		return new Token(TokenType.STRING, new ElementString(sb.toString()));
	}

	/**
	 * Helper method that check if the escape sequence in text is correct and
	 * throws an exception to notify the user that invalid escape sequence has
	 * been provided and the document does not comply with lexing rules.
	 *
	 * @throws SmartScriptLexerException
	 *             If escape sequence is incorrect.
	 */
	private boolean hasEscape(StringBuilder sb) {
		if (currentIndex + 1 >= data.length) {
			throw new SmartScriptLexerException("Invalid escape sequence. Sequence out of bounds.");
		}
		if (data[currentIndex] != '\\') {
			return true;
		}
		char next = data[currentIndex + 1];
		switch (state) {
		case TEXT:
			if (next == '\\')
				sb.append("\\");
			else if (next == '{')
				sb.append("{");
			else
				throw new SmartScriptLexerException("Invalid TEXT escape sequence.");
			currentIndex += 2;
			return false;
		case TAG:
			if (next == '\\')
				sb.append("\\");
			else if (next == '"')
				sb.append("\"");
			else if (next == 'r')
				sb.append("\r");
			else if (next == 'n')
				sb.append("\n");
			else
				throw new SmartScriptLexerException("Invalid TAG escape sequence.");
			currentIndex += 2;
			return false;
		default:
			throw new SmartScriptLexerException("You provided invalid escape sequence cannot lex the text ");
		}
	}

}
