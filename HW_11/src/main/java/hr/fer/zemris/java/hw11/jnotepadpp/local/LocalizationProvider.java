package hr.fer.zemris.java.hw11.jnotepadpp.local;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Localization is the process of adapting internationalized software for a
 * specific region or language by adding locale-specific components and
 * translating text. Localization (which is potentially performed multiple
 * times, for different locales) uses the infrastructure or flexibility provided
 * by internationalization (which is ideally performed only once, or as an
 * integral part of ongoing development).
 * 
 * @author Karlo Bencic
 *
 */
public class LocalizationProvider extends AbstractLocalizationProvider {

	/** Current package, used for getting .properties files */
	private static final String PACKAGE = LocalizationProvider.class.getPackage().getName();

	/** Display language */
	private String language;
	/** Language resource bundle */
	private ResourceBundle bundle;
	/** Single instance of Localization provider */
	private static LocalizationProvider instance;

	/**
	 * Creates a new singleton localization provider, with default language
	 * english.
	 */
	private LocalizationProvider() {
		setLanguage("en");
	}

	/**
	 * Gets a singleton instance of LocalizationProvider
	 * 
	 * @return language localization provider
	 */
	public static LocalizationProvider getInstance() {
		if (instance == null) {
			instance = new LocalizationProvider();
		}
		return instance;
	}

	/**
	 * Changes the localization language.
	 * 
	 * @param language
	 *            language tag
	 */
	public void setLanguage(String language) {
		if (language.equals(this.language)) {
			return;
		}
		this.language = language;
		bundle = ResourceBundle.getBundle(PACKAGE + ".lang", Locale.forLanguageTag(this.language));

		fire();
	}

	@Override
	public String getString(String key) {
		return bundle.getString(key);
	}

}
