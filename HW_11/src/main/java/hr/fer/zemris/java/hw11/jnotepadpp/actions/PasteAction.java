package hr.fer.zemris.java.hw11.jnotepadpp.actions;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.event.ActionEvent;
import java.io.IOException;

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
 * The {@code PasteAction} inserts the text, from clipboard to current location of caret in
 * editor.
 * 
 * @author Karlo Bencic
 *
 */
public class PasteAction extends NotepadAction {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The Constant ACTION_NAME. */
	private static final String ACTION_NAME = "action_paste";

	/**
	 * Instantiates a new paste action.
	 *
	 * @param provider
	 *            the provider
	 * @param notepad
	 *            the notepad
	 */
	public PasteAction(ILocalizationProvider provider, JNotepadPP notepad) {
		super(ACTION_NAME, provider, notepad);
		putValue(Action.SMALL_ICON, Icons.PASTE);
		putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control V"));
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		JTab selected = (JTab) tabs.getSelectedComponent();
		JTextArea editor = selected.getEditor();
		Document doc = editor.getDocument();

		Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
		Transferable contents = clipboard.getContents(null);

		if (contents != null && contents.isDataFlavorSupported(DataFlavor.stringFlavor)) {
			try {
				String result = (String) contents.getTransferData(DataFlavor.stringFlavor);
				doc.insertString(editor.getCaretPosition(), result, null);
			} catch (UnsupportedFlavorException | IOException | BadLocationException ignorable) {
			}
		}
	}

}
