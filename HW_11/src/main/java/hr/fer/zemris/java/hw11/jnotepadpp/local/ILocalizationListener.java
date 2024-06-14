package hr.fer.zemris.java.hw11.jnotepadpp.local;

/**
 * The listener interface for receiving localization events. The class that is
 * interested in processing an ILocalization event implements this interface,
 * and the object created with that class is registered with a component using
 * the component's {@code addLocalizationListener} method. When the localization
 * event occurs, that object's appropriate method is invoked.
 *
 * @see ILocalizationProvider
 * @author Karlo Bencic
 */
public interface ILocalizationListener {

	/**
	 * Notifies the listener that the localization has changed.
	 */
	void localizationChanged();
}
