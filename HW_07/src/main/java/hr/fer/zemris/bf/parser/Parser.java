package hr.fer.zemris.bf.parser;

import java.util.ArrayList;
import java.util.List;

import hr.fer.zemris.bf.lexer.Lexer;
import hr.fer.zemris.bf.lexer.LexerException;
import hr.fer.zemris.bf.lexer.Token;
import hr.fer.zemris.bf.lexer.TokenType;
import hr.fer.zemris.bf.model.BinaryOperatorNode;
import hr.fer.zemris.bf.model.ConstantNode;
import hr.fer.zemris.bf.model.Node;
import hr.fer.zemris.bf.model.UnaryOperatorNode;
import hr.fer.zemris.bf.model.VariableNode;

/**
 * The {@code Parser} class represents a boolean expression parser. Internally
 * it uses {@link Lexer} to get the tokens from the expression. The parser is
 * implemented as the recursive descent parser which is a kind of top-down
 * parser built from a set of mutually recursive procedures where each such
 * procedure usually implements one of the productions of the grammar. The
 * grammar used for this parser is the following:
 * <ul>
 * <li>S -> E1</li>
 * <li>E1 -> E2 (OR E2)*</li>
 * <li>E2 -> E3 (XOR E3)*</li>
 * <li>E3 -> E4 (AND E4)*</li>
 * <li>E4 -> NOT E4 | E5</li>
 * <li>E5 -> VAR | KONST | '(' E1 ')'</li>
 * </ul>
 * 
 * @author Karlo Bencic
 * 
 */
public class Parser {

	/** The Constant AND. */
	private static final String AND = "and";

	/** The Constant XOR. */
	private static final String XOR = "xor";

	/** The Constant OR. */
	private static final String OR = "or";

	/** The Constant NOT. */
	private static final String NOT = "not";

	/** The boolean expression. */
	private Node expression;

	/** The lexer. */
	private Lexer lexer;

	/** The previous token. */
	private Token previous;

	/**
	 * Instantiates a new parser.
	 *
	 * @param expression
	 *            the boolean expression, not null
	 * @throws IllegalArgumentException
	 *             if expression is null
	 * @throws ParserException
	 *             if unable to parse or a syntax error is present
	 */
	public Parser(String expression) {
		if (expression == null) {
			throw new IllegalArgumentException("Expression can't be null.");
		}

		lexer = new Lexer(expression);
		try {
			parse();
		} catch (LexerException ex) {
			throw new ParserException("Lexer has thrown exception: " + ex.getMessage());
		} catch (IllegalArgumentException ex) {
			throw new ParserException(ex.getMessage());
		}
	}

	/**
	 * Gets the boolean expression.
	 *
	 * @return the expression
	 */
	public Node getExpression() {
		return expression;
	}

	/**
	 * Parses the boolean expression using the {@link Lexer} class.
	 * 
	 * @throws ParserException
	 *             if unable to parse or a syntax error is present
	 */
	private void parse() throws ParserException {
		lexer.nextToken();
		expression = E1();

		if (lexer.hasNextToken()) {
			throw new ParserException("Unexcpeted token");
		}
	}

	/**
	 * First grammar method which checks if the current token is {@code OR}
	 * operator, and builds {@link BinaryOperatorNode} nodes in a loop while the
	 * matching that operator.
	 *
	 * @return the node
	 */
	private Node E1() {
		Node node = E2();

		for (int i = 0; match(OR); i++) {
			Node right = E2();
			List<Node> children = getChildren(node, right, i > 0);
			node = new BinaryOperatorNode(OR, children, (v1, v2) -> Boolean.logicalOr(v1, v2));
		}

		return node;
	}

	/**
	 * Second grammar method which checks if the current token is {@code XOR}
	 * operator, and builds {@link BinaryOperatorNode} nodes in a loop while the
	 * matching that operator.
	 *
	 * @return the node
	 */
	private Node E2() {
		Node node = E3();

		for (int i = 0; match(XOR); i++) {
			Node right = E3();
			List<Node> children = getChildren(node, right, i > 0);
			node = new BinaryOperatorNode(XOR, children, (v1, v2) -> Boolean.logicalXor(v1, v2));
		}

		return node;
	}

	/**
	 * Third grammar method which checks if the current token is {@code AND}
	 * operator, and builds {@link BinaryOperatorNode} nodes in a loop while the
	 * matching that operator.
	 *
	 * @return the node
	 */
	private Node E3() {
		Node node = E4();

		for (int i = 0; match(AND); i++) {
			Node right = E4();
			List<Node> children = getChildren(node, right, i > 0);
			node = new BinaryOperatorNode(AND, children, (v1, v2) -> Boolean.logicalAnd(v1, v2));
		}

		return node;
	}

	/**
	 * Fourth grammar method which checks if the current token is {@code NOT}
	 * operator and builds a {@link UnaryOperatorNode} node.
	 *
	 * @return the node
	 */
	private Node E4() {
		if (match(NOT)) {
			Node right = E4();
			return new UnaryOperatorNode(NOT, right, v -> !v);
		}

		return E5();
	}

	/**
	 * Fifth and final grammar method checks if the current token is a variable,
	 * then constant, then open bracket, and builds a {@link VariableNode},
	 * {@link ConstantNode} or calls {@code E1} method, respectively.
	 *
	 * @return the node
	 * @throws ParserException
	 *             if found unexpected token
	 */
	private Node E5() {
		if (match(TokenType.VARIABLE)) {
			return new VariableNode(previous());
		}
		if (match(TokenType.CONSTANT)) {
			return new ConstantNode(Boolean.parseBoolean(previous()));
		}
		if (match(TokenType.OPEN_BRACKET)) {
			Node node = E1();
			consume(TokenType.CLOSED_BRACKET);
			return node;
		}

		throw new ParserException("Unexpected token found: " + lexer.nextToken());
	}

	/**
	 * Gets the node children.
	 *
	 * @param left
	 *            the left node
	 * @param right
	 *            the right node
	 * @param checkLevel
	 *            the check level flag, true if called from the operator which
	 *            contains children nodes
	 * @return the children list
	 */
	private List<Node> getChildren(Node left, Node right, boolean checkLevel) {
		List<Node> children = new ArrayList<>();

		addChildren(children, left, 0, checkLevel);
		addChildren(children, right, 0, checkLevel);

		return children;
	}

	/**
	 * Adds the children nodes recursively into the list.
	 *
	 * @param children
	 *            the children nodes list
	 * @param node
	 *            the root node
	 * @param level
	 *            the depth level
	 * @param checkLevel
	 *            the check level flag, true if called from the operator which
	 *            contains children nodes
	 */
	private void addChildren(List<Node> children, Node node, int level, boolean checkLevel) {
		if (isCheckingLevel(checkLevel, level) && node.numberOfChildren() == 0) {
			children.add(node);
		}

		for (int i = 0; i < node.numberOfChildren() && isCheckingLevel(checkLevel, level); i++) {
			if (node instanceof UnaryOperatorNode) {
				UnaryOperatorNode child = (UnaryOperatorNode) node;
				if (!checkLevel) {
					children.add(child);
				} else if (node.numberOfChildren() == 0) {
					children.add(child);
				}
				addChildren(children, child.getChild(), ++level, checkLevel);
			} else if (node instanceof BinaryOperatorNode) {
				BinaryOperatorNode child = (BinaryOperatorNode) node;
				if (!checkLevel) {
					children.add(child);
				} else if (node.numberOfChildren() == 0) {
					children.add(child);
				}
				addChildren(children, child.getChildren().get(i), ++level, checkLevel);
			}
		}
	}

	/**
	 * Checks if is checking level of recursion.
	 *
	 * @param checkLevel
	 *            the check level flag
	 * @param level
	 *            the level
	 * @return true if called from the operator which contains children nodes
	 */
	private boolean isCheckingLevel(boolean checkLevel, int level) {
		return checkLevel == false ? level == 0 : true;
	}

	/**
	 * Checks if the operator matches the current token and advances on the next
	 * token.
	 *
	 * @param operator
	 *            the operator
	 * @return true, if successful
	 */
	private boolean match(String operator) {
		if (check(operator)) {
			advance();
			return true;
		}
		return false;
	}

	/**
	 * Checks if the operator matches the current token and advances on the next
	 * token.
	 *
	 * @param type
	 *            the token type
	 * @return true, if successful
	 */
	private boolean match(TokenType type) {
		if (check(type)) {
			advance();
			return true;
		}
		return false;
	}

	/**
	 * Checks if the operator value is equal to the current token value.
	 *
	 * @param operator
	 *            the operator
	 * @return true, if successful
	 */
	private boolean check(String operator) {
		return value().equals(operator);
	}

	/**
	 * Checks if the token type is equal to the current token type.
	 *
	 * @param type
	 *            the token type
	 * @return true, if successful
	 */
	private boolean check(TokenType type) {
		return peek().getTokenType() == type;
	}

	/**
	 * Advances onto next token and returns the previous token.
	 *
	 * @return the previous token
	 */
	private Token advance() {
		previous = peek();
		if (lexer.hasNextToken()) {
			lexer.nextToken();
		}
		return previous;
	}

	/**
	 * Checks if the token type matches the current token and advances on the
	 * next token.
	 *
	 * @param type
	 *            the token type
	 * @return the previous token
	 * @throws ParserException
	 *             if found {@code EOF}
	 */
	private Token consume(TokenType type) {
		if (check(type)) {
			return advance();
		}

		throw new ParserException("Expected ')' but found EOF.");
	}

	/**
	 * Gets the current token without advancing onto next token.
	 *
	 * @return the current token
	 */
	private Token peek() {
		return lexer.getToken();
	}

	/**
	 * Gets the value of current token as a {@code String}.
	 *
	 * @return the current token value
	 */
	private String value() {
		return String.valueOf(peek().getTokenValue());
	}

	/**
	 * Gets the value of previous token as a {@code String}.
	 *
	 * @return the previous token value
	 */
	private String previous() {
		return String.valueOf(previous.getTokenValue());
	}
}
