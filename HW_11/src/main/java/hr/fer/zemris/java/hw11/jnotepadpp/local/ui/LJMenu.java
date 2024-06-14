package hr.fer.zemris.java.hw11.jnotepadpp.local.ui;

import javax.swing.JMenu;

import hr.fer.zemris.java.hw11.jnotepadpp.local.ILocalizationProvider;

/**
 * The {@code LJMenu} class is a localized wrapper for {@link JMenu}, so each
 * time language change occurs text in menu is updated dynamically.
 * 
 * @author Karlo Bencic
 *
 */
public class LJMenu extends JMenu {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The key. */
	private final String key;

	/** The provider. */
	private final ILocalizationProvider provider;

	/**
	 * Creates a localizable {@link JMenu}.
	 *
	 * @param key
	 *            key for localizable menu name
	 * @param provider
	 *            localization provider
	 */
	public LJMenu(String key, ILocalizationProvider provider) {
		this.key = key;
		this.provider = provider;

		update();
		provider.addLocalizationListener(this::update);
	}

	/**
	 * Updates the text on this component using the provided language.
	 */
	private void update() {
		setText(provider.getString(key));
	}
}
