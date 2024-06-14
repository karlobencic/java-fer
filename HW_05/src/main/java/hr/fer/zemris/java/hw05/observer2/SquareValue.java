package hr.fer.zemris.java.hw05.observer2;

/**
 * The class {@code SquareValue} calculates and prints the observed
 * {@code value * value} when the value is changed.
 * 
 * @author Karlo Bencic
 * 
 */
public class SquareValue implements IntegerStorageObserver {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * hr.fer.zemris.java.hw05.observer2.IntegerStorageObserver#valueChanged(hr.
	 * fer.zemris.java.hw05.observer2.IntegerStorageChange)
	 */
	@Override
	public void valueChanged(IntegerStorageChange istorage) {
		int value = istorage.getNewValue();
		int squareValue = value * value;
		System.out.printf("Provided new value: %d. square is %d%n", value, squareValue);
	}

}
