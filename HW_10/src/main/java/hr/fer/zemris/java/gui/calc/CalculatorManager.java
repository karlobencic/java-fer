package hr.fer.zemris.java.gui.calc;

import java.util.EmptyStackException;
import java.util.Stack;
import java.util.function.BinaryOperator;
import java.util.function.UnaryOperator;

/**
 * The {@code CalculatorManager} class represents a calculator based on a state
 * machine. Each key press on a GUI calls the keyPressed method which then
 * delegates the calculation work based on the key name and type.
 * 
 * @author Karlo Bencic
 * 
 */
public class CalculatorManager {

	/** The calculator's current value. */
	private String currentValue = "0";

	/** The calculator's previous value. */
	private String previousValue;

	/** The second operand. */
	private String secondOperand;

	/** The calculator state. */
	private CalculatorState state;

	/** The calculator stack. */
	private Stack<String> stack = new Stack<>();

	/** The arithmetic operator. */
	private BinaryOperator<Double> operator;

	/**
	 * Gets the display value.
	 *
	 * @return the display value
	 */
	public String getDisplayValue() {
		return currentValue;
	}

	/**
	 * On calculator key pressed action.
	 *
	 * @param binary
	 *            the binary operator
	 * @param unary
	 *            the unary operator
	 * @param key
	 *            the key name
	 * @param type
	 *            the key type
	 */
	public void keyPressed(BinaryOperator<Double> binary, UnaryOperator<Double> unary, String key, CalculatorKey type) {
		if (state == CalculatorState.CHAIN && !key.equals("=")) {
			state = CalculatorState.CLEAR;
		}

		switch (type) {
		case NUMBER:
			onNumberPressed(key);
			break;
		case DOT_SIGN:
			onSignDotPressed(key);
			break;
		case CLR_RESET:
			onClrResetPressed(key);
			break;
		case PUSH_POP:
			onPushPopPressed(key);
			break;
		case BINARY:
			onBinaryOperatorPressed(binary, key);
			break;
		case UNARY:
			onUnaryOperatorPressed(unary);
			break;
		}
	}

	/**
	 * On number key pressed action.
	 *
	 * @param key
	 *            the key name
	 */
	private void onNumberPressed(String key) {
		if (state == CalculatorState.CLEAR || state == CalculatorState.ERROR) {
			reset();
		}

		if (currentValue.equals("0") && !key.equals("0")) {
			currentValue = key;
		} else if (!key.equals("0") || !currentValue.equals("0")) {
			currentValue += key;
		}
	}

	/**
	 * Resets the calculator state.
	 */
	private void reset() {
		currentValue = "0";
		state = CalculatorState.INPUT;
	}

	/**
	 * On sign or dot key pressed action.
	 *
	 * @param key
	 *            the key name
	 */
	private void onSignDotPressed(String key) {
		if (state == CalculatorState.ERROR) {
			return;
		}
		if (state == CalculatorState.CLEAR) {
			reset();
		}
		if (key.equals(".")) {
			if (currentValue.indexOf(".") != -1) {
				return;
			}
			currentValue += key;
			return;
		}
		if (currentValue.startsWith("-")) {
			currentValue = currentValue.substring(1);
		} else if (!currentValue.equals("0") && currentValue.length() != 0) {
			currentValue = "-" + currentValue;
		}
	}

	/**
	 * On clear or reset key pressed action.
	 *
	 * @param key
	 *            the key name
	 */
	private void onClrResetPressed(String key) {
		reset();
		if (key.equals("res")) {
			stack.clear();
			previousValue = null;
			secondOperand = null;
		}
	}

	/**
	 * On push or pop key pressed action. Prints error if trying to pop an empty
	 * stack.
	 *
	 * @param key
	 *            the key name
	 */
	private void onPushPopPressed(String key) {
		if (state == CalculatorState.ERROR) {
			return;
		}
		if (key.equals("push")) {
			stack.push(currentValue);
			return;
		}
		try {
			currentValue = stack.pop();
		} catch (EmptyStackException e) {
			error();
		}

		if (state == CalculatorState.CLEAR) {
			state = CalculatorState.INPUT;
		}
	}

	/**
	 * Sets the error.
	 */
	private void error() {
		currentValue = "-ERROR-";
		state = CalculatorState.ERROR;
	}

	/**
	 * On binary operator key pressed action. Prints error if a calculation
	 * exceptions occures.
	 *
	 * @param operator
	 *            the binary operator
	 * @param key
	 *            the key name
	 */
	private void onBinaryOperatorPressed(BinaryOperator<Double> operator, String key) {
		if (state == CalculatorState.ERROR) {
			return;
		}

		if (previousValue == null) {
			previousValue = currentValue;
			this.operator = operator;
			state = CalculatorState.CLEAR;
			return;
		}

		if (state == CalculatorState.CLEAR) {
			this.operator = operator;
			return;
		}

		if (state != CalculatorState.CHAIN) {
			secondOperand = currentValue;
		} else {
			currentValue = secondOperand;
		}

		try {
			double prevValue = this.operator.apply(Double.valueOf(previousValue), Double.valueOf(currentValue));
			currentValue = previousValue = String.valueOf(prevValue);

			state = key.equals("=") ? CalculatorState.CHAIN : CalculatorState.CLEAR;
		} catch (Exception e) {
			error();
		}
	}

	/**
	 * On unary operator key pressed action. Prints error if a calculation
	 * exceptions occures.
	 *
	 * @param operator
	 *            the unary operator
	 */
	private void onUnaryOperatorPressed(UnaryOperator<Double> operator) {
		try {
			currentValue = String.valueOf(operator.apply(Double.valueOf(currentValue)));
		} catch (Exception e) {
			error();
		}
	}
}
