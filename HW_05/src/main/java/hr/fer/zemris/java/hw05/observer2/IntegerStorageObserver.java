package hr.fer.zemris.java.hw05.observer2;

/**
 * An asynchronous update interface for receiving notifications about
 * IntegerStorage information as the IntegerStorage is constructed.
 * 
 * @author Karlo Bencic
 * 
 */
public interface IntegerStorageObserver {
	
	/**
	 * This method is called when information about an IntegerStorage which was
	 * previously requested using an asynchronous interface becomes available.
	 *
	 * @param istorage
	 *            the integer storage
	 */
	public void valueChanged(IntegerStorageChange istorage);
}
