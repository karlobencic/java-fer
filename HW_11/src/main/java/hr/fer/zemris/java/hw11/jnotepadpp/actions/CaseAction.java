package hr.fer.zemris.java.hw11.jnotepadpp.actions;

import java.util.function.Function;

import javax.swing.JTextArea;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;

import hr.fer.zemris.java.hw11.jnotepadpp.JNotepadPP;
import hr.fer.zemris.java.hw11.jnotepadpp.JTab;
import hr.fer.zemris.java.hw11.jnotepadpp.local.ILocalizationProvider;

/**
 * The {@code CaseAction} class is an abstract class which can change the case
 * of the given component's text.
 * 
 * @author Karlo Bencic
 * 
 */
public abstract class CaseAction extends NotepadAction {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/**
	 * Instantiates a new case action.
	 *
	 * @param key
	 *            the key
	 * @param provider
	 *            the provider
	 * @param notepad
	 *            the notepad
	 */
	public CaseAction(String key, ILocalizationProvider provider, JNotepadPP notepad) {
		super(key, provider, notepad);
	}

	/**
	 * Changes case of given component's text.
	 *
	 * @param action
	 *            the action, to lower or to upper
	 */
	protected void caseAction(Function<Character, Character> action) {
		JTextArea editor = ((JTab) tabs.getSelectedComponent()).getEditor();
		Document doc = editor.getDocument();
		int length = Math.abs(editor.getCaret().getDot() - editor.getCaret().getMark());
		int offset = length != 0 ? Math.min(editor.getCaret().getDot(), editor.getCaret().getMark()) : doc.getLength();

		try {
			String text = doc.getText(offset, length);
			text = changeCase(text, action);
			doc.remove(offset, length);
			doc.insertString(offset, text, null);
		} catch (BadLocationException ignorable) {
		}
	}

	/**
	 * Changes case of the given text.
	 *
	 * @param text
	 *            the text
	 * @param action
	 *            the action, to lower or to upper
	 * @return the string
	 */
	private String changeCase(String text, Function<Character, Character> action) {
		char[] znakovi = text.toCharArray();
		for (int i = 0; i < znakovi.length; i++) {
			char c = znakovi[i];
			znakovi[i] = action.apply(c);
		}
		return new String(znakovi);
	}
}
