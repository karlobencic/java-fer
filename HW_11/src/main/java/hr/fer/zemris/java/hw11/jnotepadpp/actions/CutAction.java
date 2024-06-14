package hr.fer.zemris.java.hw11.jnotepadpp.actions;

import java.awt.event.ActionEvent;

import javax.swing.Action;
import javax.swing.KeyStroke;

import hr.fer.zemris.java.hw11.jnotepadpp.JNotepadPP;
import hr.fer.zemris.java.hw11.jnotepadpp.icons.Icons;
import hr.fer.zemris.java.hw11.jnotepadpp.local.ILocalizationProvider;

/**
 * The {@code CutAction} places the selected text into a clipboard and deletes it from
 * editor.
 * 
 * @author Karlo Bencic
 *
 */
public class CutAction extends ClipboardAction {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The Constant ACTION_NAME. */
	private static final String ACTION_NAME = "action_cut";

	/**
	 * Instantiates a new cut action.
	 *
	 * @param provider
	 *            the provider
	 * @param notepad
	 *            the notepad
	 */
	public CutAction(ILocalizationProvider provider, JNotepadPP notepad) {
		super(ACTION_NAME, provider, notepad);
		putValue(Action.SMALL_ICON, Icons.CUT);
		putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control X"));
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		copyAction(true);
	}

}
