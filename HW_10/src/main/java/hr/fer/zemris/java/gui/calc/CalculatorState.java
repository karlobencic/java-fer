package hr.fer.zemris.java.gui.calc;

/**
 * The {@code CalculatorState} enum defines the current state of the calculator.
 * 
 * @author Karlo Bencic
 * 
 */
public enum CalculatorState {

	/** The input state. */
	INPUT,

	/** The error state. */
	ERROR,

	/** The clear/reset state. */
	CLEAR,

	/** The chain input state. */
	CHAIN
}
