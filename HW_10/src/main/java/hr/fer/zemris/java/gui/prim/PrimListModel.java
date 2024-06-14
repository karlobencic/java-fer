package hr.fer.zemris.java.gui.prim;

import java.util.ArrayList;
import java.util.List;

import javax.swing.ListModel;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;

/**
 * The {@code PrimListModel} class represents a model for the GUI list. When
 * called, it writes the next prime number on the subscribed list, starting from
 * 1. All numbers are calculated dynamicaly and added into internal integer
 * list.
 * 
 * @author Karlo Bencic
 * 
 */
public class PrimListModel implements ListModel<Integer> {

	/** The calculated prime numbers list. */
	private List<Integer> primList = new ArrayList<>();

	/** The listeners list. */
	private List<ListDataListener> listeners = new ArrayList<>();

	/** The current iteration. */
	private int current = 0;

	/**
	 * Calculates the next element in the iteration and notifies all listeners
	 * about it.
	 */
	public void next() {
		int pos = primList.size();
		int next = getPrime(current++);
		primList.add(next);
		ListDataEvent event = new ListDataEvent(this, ListDataEvent.INTERVAL_ADDED, pos, pos);
		listeners.forEach(l -> l.intervalAdded(event));
	}

	/**
	 * Gets the nth prime number.
	 *
	 * @param n
	 *            the n
	 * @return the prime number
	 */
	private int getPrime(int n) {
		int candidate = 2;
		for (int i = 0; i < n; candidate++) {
			if (isPrime(candidate)) {
				i++;
			}
		}
		return candidate - 1;
	}

	/**
	 * Checks if is the number is prime.
	 *
	 * @param n
	 *            the number
	 * @return true, if is prime
	 */
	private boolean isPrime(int n) {
		for (int i = 2; i < n; i++) {
			if (n % i == 0) {
				return false;
			}
		}
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.ListModel#getSize()
	 */
	@Override
	public int getSize() {
		return primList.size();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.ListModel#getElementAt(int)
	 */
	@Override
	public Integer getElementAt(int index) {
		return primList.get(index);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.ListModel#addListDataListener(javax.swing.event.
	 * ListDataListener)
	 */
	@Override
	public void addListDataListener(ListDataListener l) {
		listeners.add(l);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.ListModel#removeListDataListener(javax.swing.event.
	 * ListDataListener)
	 */
	@Override
	public void removeListDataListener(ListDataListener l) {
		listeners.remove(l);
	}
}
