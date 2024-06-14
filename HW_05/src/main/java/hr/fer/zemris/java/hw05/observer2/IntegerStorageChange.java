package hr.fer.zemris.java.hw05.observer2;

/**
 * The class {@link IntegerStorageChange} encapsulates information for the
 * Observer interface as read-only properties.
 * 
 * @author Karlo Bencic
 * 
 */
public class IntegerStorageChange {

	/** The integer storage. */
	private final IntegerStorage integerStorage;

	/** The previous value. */
	private final int previousValue;

	/** The new value. */
	private final int newValue;

	/**
	 * Instantiates a new integer storage change.
	 *
	 * @param integerStorage
	 *            the integer storage
	 * @param previousValue
	 *            the previous value
	 * @param newValue
	 *            the new value
	 */
	public IntegerStorageChange(IntegerStorage integerStorage, int previousValue, int newValue) {
		this.integerStorage = integerStorage;
		this.previousValue = previousValue;
		this.newValue = newValue;
	}

	/**
	 * Gets the integer storage.
	 *
	 * @return the integer storage
	 */
	public IntegerStorage getIntegerStorage() {
		return integerStorage;
	}

	/**
	 * Gets the previous value.
	 *
	 * @return the previous value
	 */
	public int getPreviousValue() {
		return previousValue;
	}

	/**
	 * Gets the new value.
	 *
	 * @return the new value
	 */
	public int getNewValue() {
		return newValue;
	}

}
