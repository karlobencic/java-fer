package hr.fer.zemris.java.hw05.observer2;

/**
 * The class {@code ChangeCounter} prints the amount of changes of the observed
 * value.
 * 
 * @author Karlo Bencic
 * 
 */
public class ChangeCounter implements IntegerStorageObserver {

	/** The number of changes. */
	private int numOfChanges;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * hr.fer.zemris.java.hw05.observer2.IntegerStorageObserver#valueChanged(hr.
	 * fer.zemris.java.hw05.observer2.IntegerStorageChange)
	 */
	@Override
	public void valueChanged(IntegerStorageChange istorage) {
		numOfChanges++;
		System.out.printf("Number of value changes since tracking: %d%n", numOfChanges);
	}

}
