package hr.fer.zemris.java.demo;

import hr.fer.zemris.java.custom.collections.EmptyStackException;
import hr.fer.zemris.java.custom.collections.ObjectStack;

/**
 * This program accepts a single command-line argument, which is a simple math
 * expressions and calculates it using the Stack data type. Expression must be
 * in postfix representation.
 * 
 * @author Karlo Bencic
 * 
 */
public class StackDemo {

	/**
	 * The main method.
	 *
	 * @param args
	 *            the arguments
	 */
	public static void main(String[] args) {
		if (args.length != 1) {
			return;
		}

		ObjectStack stack = new ObjectStack();

		String[] expressions = args[0].split("\\s+");
		for (String expression : expressions) {
			try {
				int number = Integer.parseInt(expression);
				stack.push(number);
			} catch (NumberFormatException e) {
				int right, left;
				try {
					right = (int) stack.pop();
					left = (int) stack.pop();
				} catch (EmptyStackException ex) {
					System.err.println("Invalid expression.");
					return;
				}

				int result = calculateExpression(expression, left, right);
				stack.push(result);
			}
		}

		if (stack.size() != 1) {
			System.out.println("An error occurred.");
			return;
		}

		System.out.printf("Expression evaluates to %d.", stack.pop());
	}

	/**
	 * Calculates a simple math expression. Supported operations are addition,
	 * substraction, multiplication, division and modulus.
	 *
	 * @param operation
	 *            the operation
	 * @param left
	 *            the left operand
	 * @param right
	 *            the right operand
	 * @return the integer result
	 */
	private static int calculateExpression(String operation, int left, int right) {
		int result = 0;
		try {
			switch (operation) {
			case "+":
				result = left + right;
				break;
			case "-":
				result = left - right;
				break;
			case "/":
				result = left / right;
				break;
			case "*":
				result = left * right;
				break;
			case "%":
				result = left % right;
				break;
			}
		} catch (ArithmeticException ex) {
			System.out.println("Cannot divide by zero.");
			System.exit(0);
		}

		return result;
	}
}
