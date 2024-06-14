package hr.fer.zemris.java.hw11.jnotepadpp.actions;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;

import javax.swing.JTextArea;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;

import hr.fer.zemris.java.hw11.jnotepadpp.JNotepadPP;
import hr.fer.zemris.java.hw11.jnotepadpp.JTab;
import hr.fer.zemris.java.hw11.jnotepadpp.local.ILocalizationProvider;

/**
 * The {@code ClipboardAction} class is an abstract class which can save copy
 * text from the clipboard to the current component.
 * 
 * @author Karlo Bencic
 * 
 */
public abstract class ClipboardAction extends NotepadAction {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/**
	 * Instantiates a new clipboard action.
	 *
	 * @param key
	 *            the key
	 * @param provider
	 *            the provider
	 * @param notepad
	 *            the notepad
	 */
	public ClipboardAction(String key, ILocalizationProvider provider, JNotepadPP notepad) {
		super(key, provider, notepad);
	}

	/**
	 * Copy action implementation.
	 * 
	 * @param cut
	 *            true if a text should be cut, false otherwise
	 */
	protected void copyAction(boolean cut) {
		JTab selected = (JTab) tabs.getSelectedComponent();
		JTextArea editor = selected.getEditor();
		Document doc = editor.getDocument();
		int length = Math.abs(editor.getCaret().getDot() - editor.getCaret().getMark());
		if (length == 0) {
			return;
		}
		int offset = Math.min(editor.getCaret().getDot(), editor.getCaret().getMark());

		try {
			StringSelection stringSelection = new StringSelection(doc.getText(offset, length));
			Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
			clipboard.setContents(stringSelection, null);
			if (cut) {
				doc.remove(offset, length);
			}
		} catch (BadLocationException ignorable) {
		}
	}

}
