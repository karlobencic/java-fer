package hr.fer.zemris.java.hw11.jnotepadpp.actions;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.swing.Action;
import javax.swing.JTextArea;
import javax.swing.KeyStroke;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;

import hr.fer.zemris.java.hw11.jnotepadpp.JNotepadPP;
import hr.fer.zemris.java.hw11.jnotepadpp.JTab;
import hr.fer.zemris.java.hw11.jnotepadpp.icons.Icons;
import hr.fer.zemris.java.hw11.jnotepadpp.local.ILocalizationProvider;

/**
 * The {@code UniqueAction} removes all duplicate lines from selected text.
 * 
 * @author Karlo Bencic
 *
 */
public class UniqueAction extends NotepadAction {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The Constant ACTION_NAME. */
	private static final String ACTION_NAME = "action_unique";

	/**
	 * Instantiates a new unique action.
	 *
	 * @param provider
	 *            the provider
	 * @param notepad
	 *            the notepad
	 */
	public UniqueAction(ILocalizationProvider provider, JNotepadPP notepad) {
		super(ACTION_NAME, provider, notepad);
		putValue(Action.SMALL_ICON, Icons.UNIQUE);
		putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control shift U"));
		putValue(Action.MNEMONIC_KEY, KeyEvent.VK_I);
		putValue(Action.SHORT_DESCRIPTION, "Remove selected duplicate lines");
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		JTextArea editor = ((JTab) tabs.getSelectedComponent()).getEditor();
		Document doc = editor.getDocument();
		int length = Math.abs(editor.getCaret().getDot() - editor.getCaret().getMark());
		int offset = length == 0 ? doc.getLength() : Math.min(editor.getCaret().getDot(), editor.getCaret().getMark());
		
		try {
			length = editor.getLineEndOffset(editor.getLineOfOffset(length + offset));
			offset = editor.getLineStartOffset(editor.getLineOfOffset(offset));
			String text = doc.getText(offset, length - offset);
			Set<String> sorted = new LinkedHashSet<>(Arrays.asList(text.split("\\r?\\n")));
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
