package hr.fer.zemris.bf.lexer;

/**
 * The enum TokenType represent the type of the token used by the lexer. Types
 * can be EOF, VARIABLE, CONSTANT, OPERATOR, OPEN_BRACKET and CLOSED_BRACKET.
 * 
 * @author Karlo Bencic
 * 
 */
public enum TokenType {

	/** The end of line. */
	EOF,
	/** The variable. */
	VARIABLE,
	/** The constant. */
	CONSTANT,
	/** The operator. */
	OPERATOR,
	/** The open bracket. */
	OPEN_BRACKET,
	/** The closed bracket. */
	CLOSED_BRACKET
}
