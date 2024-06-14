package hr.fer.zemris.java.hw11.jnotepadpp.actions;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.Action;
import javax.swing.KeyStroke;

import hr.fer.zemris.java.hw11.jnotepadpp.JNotepadPP;
import hr.fer.zemris.java.hw11.jnotepadpp.icons.Icons;
import hr.fer.zemris.java.hw11.jnotepadpp.local.ILocalizationProvider;

/**
 * The {@code UpperCaseAction} is a mark dependent action that transforms all selected
 * text to upper case letters.
 * 
 * @author Karlo Bencic
 *
 */
public class UpperCaseAction extends CaseAction {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The Constant ACTION_NAME. */
	private static final String ACTION_NAME = "action_to_upper_case";

	/**
	 * Instantiates a new upper case action.
	 *
	 * @param provider
	 *            the localization provider
	 * @param notepad
	 *            the notepad
	 */
	public UpperCaseAction(ILocalizationProvider provider, JNotepadPP notepad) {
		super(ACTION_NAME, provider, notepad);
		putValue(Action.SMALL_ICON, Icons.UPPER);
		putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control U"));
		putValue(Action.MNEMONIC_KEY, KeyEvent.VK_U);
		putValue(Action.SHORT_DESCRIPTION, "Changes selected text to uppercase");
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		caseAction(Character::toUpperCase);
	}

}
