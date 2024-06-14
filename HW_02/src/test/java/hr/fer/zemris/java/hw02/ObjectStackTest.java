package hr.fer.zemris.java.hw02;

import static org.junit.Assert.*;

import org.junit.Test;

import hr.fer.zemris.java.custom.collections.ObjectStack;

public class ObjectStackTest {

	ObjectStack stack = new ObjectStack();

	@Test
	public void singleOp() {
		String expression = "8 2 /";
		int result = calculateResult(expression.split("\\s+"));
		assertEquals(4, result);
	}
	
	@Test
	public void multipleOp() {
		String expression = "-1 8 2 / +";
		int result = calculateResult(expression.split("\\s+"));
		assertEquals(3, result);
	}
	
	@Test
	public void multipleDigit() {
		String expression = "800 20 /";
		int result = calculateResult(expression.split("\\s+"));
		assertEquals(40, result);
	}
	
	private int calculateResult(String[] expressions) {
		for (String expression : expressions) {
			try {
				int number = Integer.parseInt(expression);
				stack.push(number);
			} catch (NumberFormatException e) {
				int right = (int) stack.pop();
				int left = (int) stack.pop();

				int result = calculateExpression(expression, left, right);
				stack.push(result);
			}
		}

		if (stack.size() != 1) {
			fail();
			return 0;
		}

		return (int) stack.pop();
	}

	private int calculateExpression(String expression, int left, int right) {
		int result = 0;

		switch (expression) {
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

		return result;
	}
}
