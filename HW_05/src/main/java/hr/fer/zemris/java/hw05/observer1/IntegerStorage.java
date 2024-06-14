package hr.fer.zemris.java.hw05.observer1;

import java.util.ArrayList;
import java.util.List;

/**
 * The class {@code IntegerStorage} represents the Subject class implemented in
 * The Observer pattern. This object is able to "talk" with your actions using
 * the {@link IntegerStorageObserver} interface. It also provides methods for
 * registering and unregistering observers which implement the Observer
 * interface, and is responsible for tracking internal collection of registered
 * objects. Every time the object's state changes, the object will invoke the
 * method prescribed by Observer interface on all of the registered actions.
 * 
 * @author Karlo Bencic
 * 
 */
public class IntegerStorage {

	/** The value. */
	private int value;

	/** The observers. */
	private List<IntegerStorageObserver> observers;

	/**
	 * Instantiates a new integer storage.
	 *
	 * @param initialValue
	 *            the initial value
	 */
	public IntegerStorage(int initialValue) {
		this.value = initialValue;
	}

	/**
	 * Adds the observer.
	 *
	 * @param observer
	 *            the observer
	 */
	public void addObserver(IntegerStorageObserver observer) {
		if (observers == null) {
			observers = new ArrayList<>();
		}

		if (!observers.contains(observer)) {
			observers.add(observer);
		}
	}

	/**
	 * Removes the observer.
	 *
	 * @param observer
	 *            the observer
	 */
	public void removeObserver(IntegerStorageObserver observer) {
		if (observers == null) {
			return;
		}

		observers.remove(observer);
	}

	/**
	 * Clear observers.
	 */
	public void clearObservers() {
		if (observers == null) {
			return;
		}

		observers.clear();
	}

	/**
	 * Gets the value.
	 *
	 * @return the value
	 */
	public int getValue() {
		return value;
	}

	/**
	 * Sets the value.
	 *
	 * @param value
	 *            the new value
	 */
	public void setValue(int value) {
		// Only if new value is different than the current value:
		if (this.value != value) {
			// Update current value
			this.value = value;
			// Notify all registered observers
			if (observers != null) {
				for (IntegerStorageObserver observer : observers) {
					observer.valueChanged(this);
				}
			}
		}
	}
}
