package hr.fer.zemris.java.hw11.jnotepadpp.actions;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.Action;
import javax.swing.KeyStroke;

import hr.fer.zemris.java.hw11.jnotepadpp.JNotepadPP;
import hr.fer.zemris.java.hw11.jnotepadpp.icons.Icons;
import hr.fer.zemris.java.hw11.jnotepadpp.local.ILocalizationProvider;

/**
 * The {@code InvertCaseAction} is a mark dependent action that transforms all selected
 * text to opposite case letters.
 * 
 * @author Karlo Bencic
 *
 */
public class InvertCaseAction extends CaseAction {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The Constant ACTION_NAME. */
	private static final String ACTION_NAME = "action_toggle_case";

	/**
	 * Instantiates a new invert case action.
	 *
	 * @param provider
	 *            the provider
	 * @param notepad
	 *            the notepad
	 */
	public InvertCaseAction(ILocalizationProvider provider, JNotepadPP notepad) {
		super(ACTION_NAME, provider, notepad);
		putValue(Action.SMALL_ICON, Icons.INVERT);
		putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control I"));
		putValue(Action.MNEMONIC_KEY, KeyEvent.VK_I);
		putValue(Action.SHORT_DESCRIPTION, "Inverts casing of selected text");
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		caseAction(c -> Character.isUpperCase(c) ? Character.toLowerCase(c) : Character.toUpperCase(c));
	}
}
