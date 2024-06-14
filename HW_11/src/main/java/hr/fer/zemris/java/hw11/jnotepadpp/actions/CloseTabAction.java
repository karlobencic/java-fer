package hr.fer.zemris.java.hw11.jnotepadpp.actions;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.Action;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;

import hr.fer.zemris.java.hw11.jnotepadpp.JNotepadPP;
import hr.fer.zemris.java.hw11.jnotepadpp.JTab;
import hr.fer.zemris.java.hw11.jnotepadpp.icons.Icons;
import hr.fer.zemris.java.hw11.jnotepadpp.local.ILocalizationProvider;

/**
 * The {@code CloseTabAction} closes currently opened tab.
 * 
 * @author Karlo Bencic
 *
 */
public class CloseTabAction extends SaveAction {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The Constant ACTION_NAME. */
	private final static String ACTION_NAME = "action_close_current_file";

	/** The dialog message. */
	private String message;

	/** The dialog title. */
	private String title;

	/**
	 * Instantiates a new close tab action.
	 *
	 * @param provider
	 *            the provider
	 * @param notepad
	 *            the notepad
	 */
	public CloseTabAction(ILocalizationProvider provider, JNotepadPP notepad) {
		super(ACTION_NAME, provider, notepad);
		putValue(Action.SMALL_ICON, Icons.CLOSE);
		putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control W"));
		putValue(Action.MNEMONIC_KEY, KeyEvent.VK_W);
		putValue(Action.SHORT_DESCRIPTION, "Closes the current tab");
		update();
		provider.addLocalizationListener(this::update);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		JTab tab = (JTab) tabs.getSelectedComponent();
		if (tab == null) {
			JOptionPane.showMessageDialog(notepad, "No tabs opened", "Info", JOptionPane.INFORMATION_MESSAGE);
			return;
		}
		
		if (tab.isEdited()) {
			int option = JOptionPane.showConfirmDialog(notepad, message, title, JOptionPane.YES_NO_CANCEL_OPTION);
			if (option == JOptionPane.YES_OPTION) {
				save(true);
			} else if (option == JOptionPane.CANCEL_OPTION) {
				return;
			}
		}
		tabs.remove(tab);
	}

	/**
	 * Updates language dependent elements of this action.
	 */
	private void update() {
		message = provider.getString(ActionKeys.UNSAVED_WORK);
		title = provider.getString(ActionKeys.WARNING_TITLE);
	}
}
