package hr.fer.zemris.java.hw11.jnotepadpp.actions;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.Action;
import javax.swing.KeyStroke;

import hr.fer.zemris.java.hw11.jnotepadpp.JNotepadPP;
import hr.fer.zemris.java.hw11.jnotepadpp.icons.Icons;
import hr.fer.zemris.java.hw11.jnotepadpp.local.ILocalizationProvider;

/**
 * The {@code SaveAsDocumentAction} prompts user for location to of a file to save
 * currently edited document to.
 * 
 * @author Karlo Bencic
 *
 */
public class SaveAsDocumentAction extends SaveAction {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The Constant ACTION_NAME. */
	private static final String ACTION_NAME = "action_save_as";

	/**
	 * Instantiates a new save as document action.
	 *
	 * @param provider
	 *            the provider
	 * @param notepad
	 *            the notepad
	 */
	public SaveAsDocumentAction(ILocalizationProvider provider, JNotepadPP notepad) {
		super(ACTION_NAME, provider, notepad);
		putValue(Action.SMALL_ICON, Icons.SAVEAS);
		putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control shift S"));
		putValue(Action.MNEMONIC_KEY, KeyEvent.VK_A);
		putValue(Action.SHORT_DESCRIPTION, "Save file to disk");
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		save(true);
	}

}
