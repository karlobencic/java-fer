package hr.fer.zemris.java.hw11.jnotepadpp.actions;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.Action;
import javax.swing.KeyStroke;

import hr.fer.zemris.java.hw11.jnotepadpp.JNotepadPP;
import hr.fer.zemris.java.hw11.jnotepadpp.icons.Icons;
import hr.fer.zemris.java.hw11.jnotepadpp.local.ILocalizationProvider;

/**
 * The {@code SortDescendingAction} is a mark dependent action that sorts the selected lines of
 * text in descending order.
 * 
 * @author Karlo Bencic
 *
 */
public class SortDescendingAction extends SortAction {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The Constant ACTION_NAME. */
	private static final String ACTION_NAME = "action_sort_descending";

	/**
	 * Instantiates a new sort descending action.
	 *
	 * @param provider
	 *            the provider
	 * @param notepad
	 *            the notepad
	 */
	public SortDescendingAction(ILocalizationProvider provider, JNotepadPP notepad) {
		super(ACTION_NAME, provider, notepad);
		putValue(Action.SMALL_ICON, Icons.SORT_DESC);
		putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control shift Y"));
		putValue(Action.MNEMONIC_KEY, KeyEvent.VK_D);
		putValue(Action.SHORT_DESCRIPTION, "Sort selected text in descending order");
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		sort(false);
	}

}
