package hr.fer.zemris.java.custom.scripting.parser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import hr.fer.zemris.java.custom.collections.ObjectStack;
import hr.fer.zemris.java.custom.scripting.elems.*;
import hr.fer.zemris.java.custom.scripting.lexer.*;
import hr.fer.zemris.java.custom.scripting.nodes.*;

/**
 * The class SmartScriptParser represents a script parser. Internally it uses
 * {@code Lexer} to get the tokens from the script.
 * 
 * @author Karlo Bencic
 * 
 */
public class SmartScriptParser {

	/** The document root node. */
	private DocumentNode documentNode = new DocumentNode();

	/** The stack. */
	private ObjectStack stack = new ObjectStack();

	/** The operators. */
	private final String[] operators = new String[] { "+", "-", "*", "/", "^" };

	/**
	 * Instantiates a new smart script parser.
	 *
	 * @param input
	 *            the input
	 * @throws IllegalArgumentException
	 *             if input is null
	 */
	public SmartScriptParser(String input) {
		if (input == null) {
			throw new IllegalArgumentException("Input can't be null.");
		}

		Lexer lexer = new Lexer(input);
		parse(lexer);
	}

	/**
	 * Parses the script using the {@code Lexer} class.
	 *
	 * @param lexer
	 *            the lexer
	 * @throws SmartScriptParserException
	 *             if unable to parse
	 */
	private void parse(Lexer lexer) {
		stack.push(documentNode);

		while (lexer.hasNextToken()) {
			Token token = lexer.nextToken();

			LexerState nextState = token.getType() == TokenType.TAG ? LexerState.BASIC : LexerState.EXTENDED;
			lexer.setState(nextState);

			if (token.getType() == TokenType.TEXT) {
				TextNode newNode = new TextNode(token.getValue().toString());
				lastPushedDocument().addChildNode(newNode);
				continue;
			}

			String regex = Pattern.quote("{$") + "(.*?)" + Pattern.quote("$}");
			Matcher matcher = Pattern.compile(regex, Pattern.DOTALL).matcher(token.getValue().toString());

			if (!matcher.find()) {
				throw new SmartScriptParserException("Invalid tag.");
			}

			String tag = matcher.group(1).trim();
			if (tag.startsWith("=")) {
				String[] params = tag.substring(1).trim().split("\\s+");
				Element[] elements = new Element[params.length];

				for (int i = 0; i < params.length; i++) {
					if (isOperator(params[i])) {
						elements[i] = new ElementOperator(params[i]);
						continue;
					}
					if (isVariable(params[i])) {
						elements[i] = new ElementVariable(params[i]);
						continue;
					}
					if (isFunction(params[i])) {
						elements[i] = new ElementFunction(params[i]);
						continue;
					}
					if (isConstantInteger(params[i])) {
						elements[i] = new ElementConstantInteger(Integer.parseInt(params[i]));
						continue;
					}
					if (isConstantDouble(params[i])) {
						elements[i] = new ElementConstantDouble(Double.parseDouble(params[i]));
						continue;
					}
					if (isString(params[i])) {
						elements[i] = new ElementString(params[i]);
						continue;
					}
				}

				if (elements.length == 0) {
					throw new SmartScriptParserException("Invalid tag.");
				}

				EchoNode newNode = new EchoNode(elements);
				lastPushedDocument().addChildNode(newNode);
				continue;
			}

			if (tag.toUpperCase().startsWith("FOR")) {
				String[] params = tag.substring(3).trim().split("\\s+");

				if (!isVariable(params[0])) {
					throw new SmartScriptParserException(params[0] + " is not a variable name.");
				}

				if (params.length < 3 || params.length > 4) {
					throw new SmartScriptParserException("Invalid number of arguments in 'for' tag.");
				}

				for (int i = 1; i < params.length; i++) {
					if (!isConstantInteger(params[i]) && !isString(params[i])) {
						throw new SmartScriptParserException(params[i] + " is not a valid parameter in 'for' tag.");
					}
				}

				ElementVariable var = new ElementVariable(params[0]);
				Element startExpression = new ElementConstantInteger(Integer.parseInt(params[1]));
				Element endExpression = new ElementConstantInteger(Integer.parseInt(params[2]));

				Element stepExpression = null;
				if (params.length == 4) {
					stepExpression = new ElementConstantInteger(Integer.parseInt(params[3]));
				}

				ForLoopNode newNode = new ForLoopNode(var, startExpression, endExpression, stepExpression);
				lastPushedDocument().addChildNode(newNode);
				stack.push(newNode);
				continue;
			}

			if (tag.equals("END")) {
				stack.pop();
			}

			if (stack.isEmpty()) {
				throw new SmartScriptParserException();
			}
		}
	}

	/**
	 * Checks if a given input is a operator.
	 *
	 * @param var
	 *            the string
	 * @return true, if is operator
	 */
	private boolean isOperator(String var) {
		for (String op : operators) {
			if (var.equals(op)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Checks if a given input is a variable.
	 *
	 * @param var
	 *            the string
	 * @return true, if is variable
	 */
	private boolean isVariable(String var) {
		if (!Character.isLetter(var.charAt(0))) {
			return false;
		}

		for (char c : var.toCharArray()) {
			if (Character.isLetterOrDigit(c) || c == '_') {
				continue;
			}
			return false;
		}

		return true;
	}

	/**
	 * Checks if a given input is a function.
	 *
	 * @param var
	 *            the string
	 * @return true, if is function
	 */
	private boolean isFunction(String var) {
		if (var.charAt(0) != '@') {
			return false;
		}
		if (!Character.isLetter(var.charAt(1))) {
			return false;
		}

		for (int i = 2; i < var.length(); i++) {
			char c = var.charAt(i);
			if (Character.isLetterOrDigit(c) || c == '_') {
				continue;
			}
			return false;
		}

		return true;
	}

	/**
	 * Checks if a given input is a string.
	 *
	 * @param var
	 *            the string
	 * @return true, if is string
	 */
	private boolean isString(String var) {
		return var.startsWith("\"") && var.endsWith("\"");
	}

	/**
	 * Checks if a given input is a constant double.
	 *
	 * @param var
	 *            the string
	 * @return true, if is constant double
	 */
	private boolean isConstantDouble(String var) {
		try {
			Double.parseDouble(var);
		} catch (NumberFormatException e) {
			return false;
		}

		return true;
	}

	/**
	 * Checks if a given input is a constant integer.
	 *
	 * @param var
	 *            the string
	 * @return true, if is constant integer
	 */
	private boolean isConstantInteger(String var) {
		try {
			Integer.parseInt(var);
		} catch (NumberFormatException e) {
			return false;
		}

		return true;
	}

	/**
	 * Gets the document node.
	 *
	 * @return the document node
	 */
	public DocumentNode getDocumentNode() {
		return documentNode;
	}

	/**
	 * Gets the last pushed document on the stack.
	 *
	 * @return the last pushed node
	 */
	private Node lastPushedDocument() {
		return (Node) stack.peek();
	}
}
