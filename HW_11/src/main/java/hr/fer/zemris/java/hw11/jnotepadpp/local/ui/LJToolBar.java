package hr.fer.zemris.java.hw11.jnotepadpp.local.ui;

import javax.swing.JToolBar;

import hr.fer.zemris.java.hw11.jnotepadpp.actions.ActionKeys;
import hr.fer.zemris.java.hw11.jnotepadpp.local.ILocalizationProvider;

/**
 * The {@code LJToolBar} class is a localized wrapper for {@link JToolBar}, so
 * each time language change occurs toolbar name is updated dynamically.
 * 
 * @author Karlo Bencic
 *
 */
public class LJToolBar extends JToolBar {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The provider. */
	private final ILocalizationProvider provider;

	/**
	 * Creates a localizable {@link JToolBar}.
	 *
	 * @param provider
	 *            the provider
	 */
	public LJToolBar(ILocalizationProvider provider) {
		this.provider = provider;

		update();
		provider.addLocalizationListener(this::update);
	}

	/**
	 * Updates the name of this component using the provided language.
	 */
	private void update() {
		setName(provider.getString(ActionKeys.TOOLS));
	}
}
