package hr.fer.zemris.java.hw11.jnotepadpp.local;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * The {@code AbstractLocalizationProvider} class is responsible for storing and
 * notifying the listeners of a provider.
 * 
 * @author Karlo Bencic
 * 
 */
public abstract class AbstractLocalizationProvider implements ILocalizationProvider {

	/** The listeners. */
	private final List<ILocalizationListener> listeners;

	/**
	 * Instantiates a new abstract localization provider.
	 */
	public AbstractLocalizationProvider() {
		listeners = new CopyOnWriteArrayList<>();
	}

	@Override
	public void addLocalizationListener(ILocalizationListener listener) {
		listeners.add(listener);
	}

	@Override
	public void removeLocalizationListener(ILocalizationListener listener) {
		listeners.remove(listener);
	}

	@Override
	public abstract String getString(String s);

	/**
	 * Notifies all listeners about a change in a provider.
	 */
	public void fire() {
		listeners.forEach(ILocalizationListener::localizationChanged);
	}

}
