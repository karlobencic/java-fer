package hr.fer.zemris.java.hw11.jnotepadpp.local;

/**
 * The {@code ILocalizationProvider} interface is implemented by classes that
 * enable you to change language inside your application.
 * 
 * @author Karlo Bencic
 */
public interface ILocalizationProvider {

	/**
	 * Adds the localization listener.
	 *
	 * @param listener
	 *            the listener
	 */
	void addLocalizationListener(ILocalizationListener listener);

	/**
	 * Removes the localization listener.
	 *
	 * @param listener
	 *            the listener
	 */
	void removeLocalizationListener(ILocalizationListener listener);

	/**
	 * Gets the string from properties files associated with provided key.
	 *
	 * @param key
	 *            the key in properties file
	 * @return the string associated with a key in properties files
	 */
	String getString(String key);
}
