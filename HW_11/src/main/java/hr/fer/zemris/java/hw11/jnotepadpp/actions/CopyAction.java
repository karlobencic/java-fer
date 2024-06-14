package hr.fer.zemris.java.hw11.jnotepadpp.actions;

import java.awt.event.ActionEvent;

import javax.swing.Action;
import javax.swing.KeyStroke;

import hr.fer.zemris.java.hw11.jnotepadpp.JNotepadPP;
import hr.fer.zemris.java.hw11.jnotepadpp.icons.Icons;
import hr.fer.zemris.java.hw11.jnotepadpp.local.ILocalizationProvider;

/**
 * The {@code CopyAction} places the selected text into a clipboard.
 * 
 * @author Karlo Bencic
 *
 */
public class CopyAction extends ClipboardAction {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The Constant ACTION_NAME. */
	private static final String ACTION_NAME = "action_copy";

	/**
	 * Instantiates a new copy action.
	 *
	 * @param provider
	 *            the provider
	 * @param notepad
	 *            the notepad
	 */
	public CopyAction(ILocalizationProvider provider, JNotepadPP notepad) {
		super(ACTION_NAME, provider, notepad);
		putValue(Action.SMALL_ICON, Icons.COPY);
		putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control C"));
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		copyAction(false);
	}

}
