package hr.fer.zemris.java.custom.scripting.parser;

/**
 * 
 * The {@code TokenType} enum defines types of tokens that lexer can generate.
 * They are assigned to their corresponding element type.
 * 
 * @author Karlo Bencic
 *
 */
public enum TokenType {

	/** The function. */
	FUNCTION,
	/** The variable. */
	VARIABLE,
	/** The string. */
	STRING,
	/** The text. */
	TEXT,
	/** The constant integer. */
	CONSTANT_INTEGER,
	/** The constant double. */
	CONSTANT_DOUBLE,
	/** The operator. */
	OPERATOR,
	/** The end of line. */
	EOF,
	/** The tag. */
	TAG
}
