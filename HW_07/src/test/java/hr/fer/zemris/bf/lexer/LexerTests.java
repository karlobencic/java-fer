package hr.fer.zemris.bf.lexer;

import static org.junit.Assert.*;

import org.junit.Test;

public class LexerTests {
	
	@Test(expected=IllegalArgumentException.class)
	public void testNullInput() {
		new Lexer(null);
	}
	
	@Test
	public void testEmptyInput() {
		Lexer lexer = new Lexer("");	
		checkToken(lexer.nextToken(), new Token(TokenType.EOF, null));
	}
	
	@Test(expected=LexerException.class)
	public void testReadAfterEOF() {
		Lexer lexer = new Lexer("");

		lexer.nextToken();
		lexer.nextToken();
	}

	@Test
	public void testSimpleExpression() {
		String expression = "a and b";
		Lexer lexer = new Lexer(expression);

		checkToken(lexer.nextToken(), new Token(TokenType.VARIABLE, "A"));
		checkToken(lexer.nextToken(), new Token(TokenType.OPERATOR, "and"));
		checkToken(lexer.nextToken(), new Token(TokenType.VARIABLE, "B"));
		checkToken(lexer.nextToken(), new Token(TokenType.EOF, null));
	}
	
	@Test
	public void testInvalidExpression() {
		String expression = "a mor b";
		Lexer lexer = new Lexer(expression);

		checkToken(lexer.nextToken(), new Token(TokenType.VARIABLE, "A"));
		checkToken(lexer.nextToken(), new Token(TokenType.VARIABLE, "MOR"));
		checkToken(lexer.nextToken(), new Token(TokenType.VARIABLE, "B"));
		checkToken(lexer.nextToken(), new Token(TokenType.EOF, null));
	}
	
	@Test
	public void testConstantExpression() {
		String expression = "1 * 1 + 0";
		Lexer lexer = new Lexer(expression);

		checkToken(lexer.nextToken(), new Token(TokenType.CONSTANT, true));
		checkToken(lexer.nextToken(), new Token(TokenType.OPERATOR, "and"));
		checkToken(lexer.nextToken(), new Token(TokenType.CONSTANT, true));
		checkToken(lexer.nextToken(), new Token(TokenType.OPERATOR, "or"));
		checkToken(lexer.nextToken(), new Token(TokenType.CONSTANT, false));
		checkToken(lexer.nextToken(), new Token(TokenType.EOF, null));
	}
	
	
	@Test
	public void testComplexExpression() {
		String expression = "(a and b + C * d) xor 1";
		Lexer lexer = new Lexer(expression);

		checkToken(lexer.nextToken(), new Token(TokenType.OPEN_BRACKET, '('));
		checkToken(lexer.nextToken(), new Token(TokenType.VARIABLE, "A"));
		checkToken(lexer.nextToken(), new Token(TokenType.OPERATOR, "and"));
		checkToken(lexer.nextToken(), new Token(TokenType.VARIABLE, "B"));
		checkToken(lexer.nextToken(), new Token(TokenType.OPERATOR, "or"));
		checkToken(lexer.nextToken(), new Token(TokenType.VARIABLE, "C"));
		checkToken(lexer.nextToken(), new Token(TokenType.OPERATOR, "and"));
		checkToken(lexer.nextToken(), new Token(TokenType.VARIABLE, "D"));
		checkToken(lexer.nextToken(), new Token(TokenType.CLOSED_BRACKET, ')'));
		checkToken(lexer.nextToken(), new Token(TokenType.OPERATOR, "xor"));
		checkToken(lexer.nextToken(), new Token(TokenType.CONSTANT, true));
		checkToken(lexer.nextToken(), new Token(TokenType.EOF, null));
	}

	private void checkToken(Token actual, Token expected) {
		String msg = "Token are not equal.";
		assertEquals(msg, expected.getTokenType(), actual.getTokenType());
		assertEquals(msg, expected.getTokenValue(), actual.getTokenValue());
	}
}
