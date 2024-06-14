package hr.fer.zemris.java.hw11.jnotepadpp.local.ui;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;

import hr.fer.zemris.java.hw11.jnotepadpp.local.ILocalizationProvider;
import hr.fer.zemris.java.hw11.jnotepadpp.local.LocalizationProviderBridge;

/**
 * The {@code FormLocalizationProvider} class represents a
 * {@link LocalizationProviderBridge} that connects itself to the provided frame
 * of thr application and registers itself to main localization provider.
 * 
 * @author Karlo Bencic
 *
 */
public class FormLocalizationProvider extends LocalizationProviderBridge {

	/**
	 * Creates a new form localization provider that is responsible for fetching
	 * and translating keys inside a provided frame.
	 * 
	 * @param parent
	 *            the localization provider
	 * @param frame
	 *            frame to be localized
	 */
	public FormLocalizationProvider(ILocalizationProvider parent, JFrame frame) {
		super(parent);

		frame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowOpened(WindowEvent e) {
				connect();
			}

			@Override
			public void windowClosed(WindowEvent e) {
				disconnect();
			}
		});
	}
}
