package hr.fer.zemris.java.hw05.observer2;

/**
 * The class {@code DoubleValue} calculates and prints the observed
 * <code>value * 2</code> when the value is changed until it reaches the allowed
 * amount of changes provided from the constructor.
 * 
 * @author Karlo Bencic
 * 
 */
public class DoubleValue implements IntegerStorageObserver {

	/** The number of allowed changes. */
	private int allowedChanges;

	/**
	 * Instantiates a new double value.
	 *
	 * @param n
	 *            the allowed number of changes
	 */
	public DoubleValue(int n) {
		if (n < 0) {
			throw new IllegalArgumentException("Argument should be a positive integer.");
		}
		allowedChanges = n;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * hr.fer.zemris.java.hw05.observer2.IntegerStorageObserver#valueChanged(hr.
	 * fer.zemris.java.hw05.observer2.IntegerStorageChange)
	 */
	@Override
	public void valueChanged(IntegerStorageChange istorage) {
		if (allowedChanges == 0) {
			return;
		}

		allowedChanges--;
		int doubleValue = istorage.getNewValue() * 2;
		System.out.printf("Double value: %d%n", doubleValue);
	}

}
