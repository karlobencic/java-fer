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
 * The {@code ExitAction} prompts users if any of his work is unsaved and closes the
 * notepad application.
 * 
 * @author Karlo Bencic
 *
 */
public class ExitAction extends SaveAction {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The Constant ACTION_NAME. */
	private static final String ACTION_NAME = "action_exit";

	/** The dialog message. */
	private String message;

	/** The dialog title. */
	private String title;

	/**
	 * Instantiates a new exit action.
	 *
	 * @param provider
	 *            the provider
	 * @param notepad
	 *            the notepad
	 */
	public ExitAction(ILocalizationProvider provider, JNotepadPP notepad) {
		super(ACTION_NAME, provider, notepad);
		putValue(Action.SMALL_ICON, Icons.EXIT);
		putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control Q"));
		putValue(Action.MNEMONIC_KEY, KeyEvent.VK_X);
		putValue(Action.SHORT_DESCRIPTION, "Exit application");
		update();
		provider.addLocalizationListener(this::update);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		boolean anyEdited = false;
		for (int i = 0; i < tabs.getComponentCount(); i++) {
			JTab tab = (JTab) tabs.getComponentAt(i);
			if (anyEdited = tab.isEdited()) {
				break;
			}
		}
		
		if (tabs.getComponentCount() != 0 && anyEdited) {
			int option = JOptionPane.showConfirmDialog(notepad, message, title, JOptionPane.YES_NO_CANCEL_OPTION);
			if (option == JOptionPane.CANCEL_OPTION) {
				return;
			} else if (option == JOptionPane.YES_OPTION) {
				while (tabs.getComponentCount() != 0) {
					JTab tab = (JTab) tabs.getSelectedComponent();
					if (tab.isEdited()) {
						save(true);
					}
					tabs.remove(tab);
				}
			}
		}
		
		notepad.dispose();
		System.exit(0);
	}

	/**
	 * Updates language dependent elements of this action.
	 */
	private void update() {
		message = provider.getString(ActionKeys.UNSAVED_WORK);
		title = provider.getString(ActionKeys.WARNING_TITLE);
	}
}
