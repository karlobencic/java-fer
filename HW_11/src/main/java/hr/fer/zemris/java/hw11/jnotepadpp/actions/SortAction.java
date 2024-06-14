package hr.fer.zemris.java.hw11.jnotepadpp.actions;

import java.util.Arrays;
import java.util.List;

import javax.swing.JTextArea;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;

import hr.fer.zemris.java.hw11.jnotepadpp.JNotepadPP;
import hr.fer.zemris.java.hw11.jnotepadpp.JTab;
import hr.fer.zemris.java.hw11.jnotepadpp.local.ILocalizationProvider;

/**
 * The {@code SortAction} class is an abstract class which can sort component
 * text in a given order.
 * 
 * @author Karlo Bencic
 * 
 */
public abstract class SortAction extends NotepadAction {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/**
	 * Instantiates a new sort action.
	 *
	 * @param key
	 *            the key
	 * @param provider
	 *            the provider
	 * @param notepad
	 *            the notepad
	 */
	public SortAction(String key, ILocalizationProvider provider, JNotepadPP notepad) {
		super(key, provider, notepad);
	}

	/**
	 * Sorts the selected lines of text.
	 * 
	 * @param ascending
	 *            true if text should be sorted in ascending order, false
	 *            otherwise
	 */
	protected void sort(boolean ascending) {
		JTextArea editor = ((JTab) tabs.getSelectedComponent()).getEditor();
		Document doc = editor.getDocument();
		int length = Math.abs(editor.getCaret().getDot() - editor.getCaret().getMark());
		int offset = length == 0 ? doc.getLength() : Math.min(editor.getCaret().getDot(), editor.getCaret().getMark());

		try {
			offset = editor.getLineStartOffset(editor.getLineOfOffset(offset));
			length = editor.getLineEndOffset(editor.getLineOfOffset(length + offset));
			String text = doc.getText(offset, length - offset);
			List<String> sorted = Arrays.asList(text.split("\\r?\\n"));
			sorted.sort(ascending ? (o1, o2) -> col.compare(o1, o2) : (o1, o2) -> col.reversed().compare(o1, o2));
			int lines = editor.getLineCount();
			doc.remove(offset, length - offset);

			for (String string : sorted) {
				doc.insertString(offset, string + (--lines > 0 ? "\n" : ""), null);
				offset += string.length() + 1;
			}
		} catch (BadLocationException ignorable) {
		}
	}
}
