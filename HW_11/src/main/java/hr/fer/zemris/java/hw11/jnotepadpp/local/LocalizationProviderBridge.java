package hr.fer.zemris.java.hw11.jnotepadpp.local;

/**
 * The {@code LocalizationProviderBridge} is an intermediate class between a localizable
 * application component and localization provider to isolate the localizable
 * elements of the application so when a language change occurs replacement
 * elements are created for appropriate language.
 * 
 * @author Karlo Bencic
 *
 */
public class LocalizationProviderBridge extends AbstractLocalizationProvider {

	/** The connected flag. */
	private boolean connected;

	/** The parent component. */
	private final ILocalizationProvider parent;

	/** The listener. */
	private final ILocalizationListener listener = this::fire;

	/**
	 * Instantiates a new localization provider bridge.
	 *
	 * @param parent
	 *            the parent localization provider
	 */
	protected LocalizationProviderBridge(ILocalizationProvider parent) {
		this.parent = parent;
	}

	/**
	 * Disconnects the component from localization.
	 */
	protected void disconnect() {
		if (!connected) {
			return;
		}
		connected = false;
		parent.removeLocalizationListener(listener);
	}

	/**
	 * Connects the component for localization.
	 */
	protected void connect() {
		if (connected) {
			return;
		}
		connected = true;
		parent.addLocalizationListener(listener);
	}

	@Override
	public String getString(String key) {
		return parent.getString(key);
	}

}
