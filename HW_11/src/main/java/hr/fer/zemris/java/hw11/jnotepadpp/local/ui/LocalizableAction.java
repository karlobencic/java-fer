package hr.fer.zemris.java.hw11.jnotepadpp.local.ui;

import javax.swing.AbstractAction;

import hr.fer.zemris.java.hw11.jnotepadpp.local.ILocalizationProvider;

/**
 * The {@code LocalizableAction} class is a localized wrapper for
 * {@link AbstractAction}, so each time language change occurs action name is
 * updated dynamically.
 * 
 * @author Karlo Bencic
 *
 */
public abstract class LocalizableAction extends AbstractAction {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The key. */
	private final String key;

	/** The provider. */
	private final ILocalizationProvider provider;

	/**
	 * Creates a new localizable action with name under the key and and
	 * localization provider that will fetch the value under the key.
	 *
	 * @param key
	 *            localization key
	 * @param provider
	 *            the provider
	 */
	public LocalizableAction(String key, ILocalizationProvider provider) {
		this.key = key;
		this.provider = provider;

		update();
		provider.addLocalizationListener(this::update);
	}

	/**
	 * Updates the action with the provided language.
	 */
	private void update() {
		putValue(NAME, provider.getString(key));
	}
}
