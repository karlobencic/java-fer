package hr.fer.zemris.java.hw11.jnotepadpp.actions;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

import javax.swing.Action;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;

import hr.fer.zemris.java.hw11.jnotepadpp.JNotepadPP;
import hr.fer.zemris.java.hw11.jnotepadpp.JTab;
import hr.fer.zemris.java.hw11.jnotepadpp.icons.Icons;
import hr.fer.zemris.java.hw11.jnotepadpp.local.ILocalizationProvider;

/**
 * The {@code OpenDocumentAction} opens an existing document from disk.
 * 
 * @author Karlo Bencic
 *
 */
public class OpenDocumentAction extends NotepadAction {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The Constant ACTION_NAME. */
	private static final String ACTION_NAME = "action_open_existing_document";

	/**
	 * Instantiates a new open document action.
	 *
	 * @param provider
	 *            the provider
	 * @param notepad
	 *            the notepad
	 */
	public OpenDocumentAction(ILocalizationProvider provider, JNotepadPP notepad) {
		super(ACTION_NAME, provider, notepad);
		putValue(Action.SMALL_ICON, Icons.OPEN_EXISTING);
		putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control O"));
		putValue(Action.MNEMONIC_KEY, KeyEvent.VK_O);
		putValue(Action.SHORT_DESCRIPTION, "Open file from disk");
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		JFileChooser fc = new JFileChooser();
		fc.setDialogTitle("Open file");
		if (fc.showOpenDialog(notepad) != JFileChooser.APPROVE_OPTION) {
			return;
		}
		
		File fileName = fc.getSelectedFile();
		Path filePath = fileName.toPath();
		if (!Files.isReadable(filePath)) {
			JOptionPane.showMessageDialog(notepad, "File " + fileName.getAbsolutePath() + " does not exist!", "Error",
					JOptionPane.ERROR_MESSAGE);
			return;
		}
		
		byte[] bytes;
		try {
			bytes = Files.readAllBytes(filePath);
		} catch (Exception ex) {
			JOptionPane.showMessageDialog(notepad, "An error occurred while reading file " + fileName.getAbsolutePath(),
					"Error", JOptionPane.ERROR_MESSAGE);
			return;
		}
		
		String text = new String(bytes, StandardCharsets.UTF_8);
		JTab tab = new JTab(filePath, text);
		tabs.add(tab);
		refreshTabs();
		tabs.setSelectedComponent(tab);
	}

}
