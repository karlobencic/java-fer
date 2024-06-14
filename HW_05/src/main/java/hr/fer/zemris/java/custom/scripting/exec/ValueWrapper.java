package hr.fer.zemris.java.custom.scripting.exec;

/**
 * The class ValueWrapper stores object value and offerts simple operations on
 * it. Accepted value types are {@code null}, {@code Integer}, {@code Double}
 * and {@code String} which should be parsed into the two previously mentioned
 * types. If the value is {@code null}, it behaves like an integer with value 0.
 * 
 * @author Karlo Bencic
 * 
 */
public class ValueWrapper {

	/** The value. */
	private Object value;

	/**
	 * Instantiates a new value wrapper.
	 *
	 * @param value
	 *            the value
	 */
	public ValueWrapper(Object value) {
		this.value = value;
	}

	/**
	 * Adds the {@code incValue} to the current value.
	 *
	 * @param incValue
	 *            the increment value
	 */
	public void add(Object incValue) {
		performOperation(incValue, ValueOperation.ADD);
	}

	/**
	 * Subtract the {@code decValue} from the current value.
	 *
	 * @param decValue
	 *            the decrement value
	 */
	public void subtract(Object decValue) {
		performOperation(decValue, ValueOperation.SUBSTRACT);
	}

	/**
	 * Multiplies the current value with the {@code mulValue}.
	 *
	 * @param mulValue
	 *            the multiplication value
	 */
	public void multiply(Object mulValue) {
		performOperation(mulValue, ValueOperation.MULTIPLY);
	}

	/**
	 * Divides the current value with the {@code divValue}.
	 *
	 * @param divValue
	 *            the division value
	 */
	public void divide(Object divValue) {
		performOperation(divValue, ValueOperation.DIVIDE);
	}

	/**
	 * Performs the operation defined with {@link ValueOperation} type.
	 *
	 * @param value
	 *            the value
	 * @param operationType
	 *            the operation type
	 */
	private void performOperation(Object value, ValueOperation operationType) {
		if (getType(this.value) == ValueType.DOUBLE || getType(value) == ValueType.DOUBLE) {
			this.value = calculate(Double.parseDouble(parseNull(this.value).toString()),
					Double.parseDouble(parseNull(value).toString()), operationType);
		} else {
			this.value = calculate(Integer.parseInt(parseNull(this.value).toString()),
					Integer.parseInt(parseNull(value).toString()), operationType);
		}
	}

	/**
	 * Calculates the operation defined with {@link ValueOperation} type.
	 *
	 * @param first
	 *            the first operand
	 * @param second
	 *            the second operand
	 * @param operationType
	 *            the operation type
	 * @return the integer result
	 * @throws IllegalArgumentException
	 *             if trying to divide by zero
	 */
	private int calculate(int first, int second, ValueOperation operationType) {
		switch (operationType) {
		case ADD:
			return first + second;
		case SUBSTRACT:
			return first - second;
		case MULTIPLY:
			return first * second;
		case DIVIDE:
			if (second == 0) {
				throw new ArithmeticException("Cannot divide by zero.");
			}
			return first / second;
		default:
			return 0;
		}
	}

	/**
	 * Calculates the operation defined with {@link ValueOperation} type.
	 *
	 * @param first
	 *            the first operand
	 * @param second
	 *            the second operand
	 * @param operationType
	 *            the operation type
	 * @return the decimal result
	 * @throws IllegalArgumentException
	 *             if trying to divide by zero
	 */
	private double calculate(double first, double second, ValueOperation operationType) {
		switch (operationType) {
		case ADD:
			return first + second;
		case SUBSTRACT:
			return first - second;
		case MULTIPLY:
			return first * second;
		case DIVIDE:
			if (second == 0) {
				throw new ArithmeticException("Cannot divide by zero.");
			}
			return first / second;
		default:
			return 0.0;
		}
	}

	/**
	 * If the value is null, it will be parsed to integer value zero.
	 *
	 * @param value
	 *            the value
	 * @return the object value
	 */
	private Object parseNull(Object value) {
		return value == null ? 0 : value;
	}

	/**
	 * Compares another value with the value from this object numerically.
	 *
	 * @param withValue
	 *            the value to be compared
	 * @return 0 if this value is equal to the argument, -1 if this value is
	 *         numerically less than the argument and 1 if this value is
	 *         numerically greater than the argument
	 */
	public int numCompare(Object withValue) {
		if (this.value == null && withValue == null) {
			return 0;
		}

		if (getType(value) == ValueType.DOUBLE || getType(withValue) == ValueType.DOUBLE) {
			Double first = Double.parseDouble(parseNull(value).toString());
			Double second = Double.parseDouble(parseNull(withValue).toString());
			final double epsilon = 1E-8;
			if (second - first <= epsilon) {
				return 0;
			} else if (first < second) {
				return -1;
			} else {
				return 1;
			}
		}

		Integer first = Integer.parseInt(parseNull(value).toString());
		Integer second = Integer.parseInt(parseNull(withValue).toString());
		return first.compareTo(second);
	}

	/**
	 * Gets the value.
	 *
	 * @return the value
	 */
	public Object getValue() {
		return value;
	}

	/**
	 * Sets the value. Supported types are {@code null}, {@code Integer},
	 * {@code Double} and {@code String} which should be parsed into the two
	 * previously mentioned types.
	 *
	 * @param value
	 *            the new value
	 */
	public void setValue(Object value) {
		this.value = value;
	}

	/**
	 * Gets the type of the value.
	 *
	 * @param value
	 *            the value
	 * @return the type
	 * @throws RuntimeException
	 *             if value type is not supported
	 */
	private ValueType getType(Object value) {
		if (value == null || value instanceof Integer)
			return ValueType.INTEGER;
		if (value instanceof Double)
			return ValueType.DOUBLE;
		if (value instanceof String)
			return parseString(value);

		throw new RuntimeException(String.format("Value type '%s' not supported.", value.getClass().getName()));
	}

	/**
	 * Checks if a given string is a number, i.e. if it's an Integer or a
	 * Double.
	 *
	 * @param value
	 *            the value
	 * @return the value type
	 */
	private ValueType parseString(Object value) {
		final String intRegex = "([\\+-]?\\d+)([eE][\\+-]?\\d+)?";
		final String doubleRegex = "([\\+-]?\\d+(\\.\\d*)?|\\.\\d+)([eE][\\+-]?(\\d+(\\.\\d*)?|\\.\\d+))?";

		if (value.toString().matches(intRegex)) {
			return ValueType.INTEGER;
		} else if (value.toString().matches(doubleRegex)) {
			return ValueType.DOUBLE;
		}
		return ValueType.STRING;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return value == null ? "null" : value.toString();
	}
}
