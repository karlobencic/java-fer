package hr.fer.zemris.java.hw03.prob2;

import static org.junit.Assert.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.Test;

import hr.fer.zemris.java.custom.scripting.lexer.Lexer;
import hr.fer.zemris.java.custom.scripting.lexer.Token;
import hr.fer.zemris.java.custom.scripting.lexer.TokenType;
import hr.fer.zemris.java.custom.scripting.lexer.LexerState;

public class LexerTest {

	@Test
	public void testSampleText() {
		Path path = Paths.get("examples", "doc1.txt");
		String text = null;
		try {
			byte[] data = Files.readAllBytes(path);
			text = new String(data);
		} catch (IOException e) {
			System.out.println(e);
		}

		Lexer lexer = new Lexer(text);

		checkToken(lexer.nextToken(), new Token(TokenType.TEXT, "This is sample text.\r\n"));
		lexer.setState(LexerState.EXTENDED);
		checkToken(lexer.nextToken(), new Token(TokenType.TAG, "{$ FOR i 1 10 1 $}"));
		lexer.setState(LexerState.BASIC);
		checkToken(lexer.nextToken(), new Token(TokenType.TEXT, "\r\n This is "));
		lexer.setState(LexerState.EXTENDED);
		checkToken(lexer.nextToken(), new Token(TokenType.TAG, "{$= i $}"));
		lexer.setState(LexerState.BASIC);
		checkToken(lexer.nextToken(), new Token(TokenType.TEXT, "-th time this message is generated.\r\n"));
		lexer.setState(LexerState.EXTENDED);
		checkToken(lexer.nextToken(), new Token(TokenType.TAG, "{$END$}"));
		lexer.setState(LexerState.BASIC);
		checkToken(lexer.nextToken(), new Token(TokenType.TEXT, "\r\n"));
		lexer.setState(LexerState.EXTENDED);
		checkToken(lexer.nextToken(), new Token(TokenType.TAG, "{$FOR i 0 10 2 $}"));
		lexer.setState(LexerState.BASIC);
		checkToken(lexer.nextToken(), new Token(TokenType.TEXT, "\r\n sin("));
		lexer.setState(LexerState.EXTENDED);
		checkToken(lexer.nextToken(), new Token(TokenType.TAG, "{$=i$}"));
		lexer.setState(LexerState.BASIC);
		checkToken(lexer.nextToken(), new Token(TokenType.TEXT, "^2) = "));
		lexer.setState(LexerState.EXTENDED);
		checkToken(lexer.nextToken(), new Token(TokenType.TAG, "{$= i i * @sin \"0.000\" @decfmt $}"));
		lexer.setState(LexerState.BASIC);
		checkToken(lexer.nextToken(), new Token(TokenType.TEXT, "\r\n"));
		lexer.setState(LexerState.EXTENDED);
		checkToken(lexer.nextToken(), new Token(TokenType.TAG, "{$END$}"));
	}
	
	@Test
	public void testSampleText2() {
		Path path = Paths.get("examples", "doc3.txt");
		String text = null;
		try {
			byte[] data = Files.readAllBytes(path);
			text = new String(data);
		} catch (IOException e) {
			System.out.println(e);
		}

		Lexer lexer = new Lexer(text);

		lexer.setState(LexerState.EXTENDED);
		checkToken(lexer.nextToken(), new Token(TokenType.TAG, "{$= \"text/plain\" @setMimeType $}"));
		lexer.setState(LexerState.BASIC);
		checkToken(lexer.nextToken(), new Token(TokenType.TEXT, "\r\nOvaj dokument pozvan je sljedeći broj puta:\r\n"));
		lexer.setState(LexerState.EXTENDED);
		checkToken(lexer.nextToken(), new Token(TokenType.TAG, "{$= \"brojPoziva\" \"1\" @pparamGet @dup 1 + \"brojPoziva\" @pparamSet $}"));
	}
	
	@Test
	public void testEscape() {
		Lexer lexer = new Lexer("A tag follows {$= \"Joe \\\"Long\\\" Smith\"$}.");

		checkToken(lexer.nextToken(), new Token(TokenType.TEXT, "A tag follows "));
		lexer.setState(LexerState.EXTENDED);
		checkToken(lexer.nextToken(), new Token(TokenType.TAG, "{$= \"Joe \\\"Long\\\" Smith\"$}"));
		lexer.setState(LexerState.BASIC);
		checkToken(lexer.nextToken(), new Token(TokenType.TEXT, "."));
	}

	private void checkToken(Token actual, Token expected) {
		String msg = "Token are not equal.";
		assertEquals(msg, expected.getType(), actual.getType());
		assertEquals(msg, expected.getValue(), actual.getValue());
	}
}
