package hr.fer.zemris.java.hw11.jnotepadpp.actions;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;

import hr.fer.zemris.java.hw11.jnotepadpp.JNotepadPP;
import hr.fer.zemris.java.hw11.jnotepadpp.JTab;
import hr.fer.zemris.java.hw11.jnotepadpp.local.ILocalizationProvider;

/**
 * The {@code SaveAction} class is an abstract class which can save component
 * text on the disk.
 * 
 * @author Karlo Bencic
 * 
 */
public abstract class SaveAction extends NotepadAction {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/**
	 * Instantiates a new save action.
	 *
	 * @param key
	 *            the key
	 * @param provider
	 *            the provider
	 * @param notepad
	 *            the notepad
	 */
	public SaveAction(String key, ILocalizationProvider provider, JNotepadPP notepad) {
		super(key, provider, notepad);
	}

	/**
	 * Save action implementation.
	 *
	 * @param newPath
	 *            true if the user should be prompted to set new path
	 */
	protected void save(boolean newPath) {
		JTab tab = (JTab) tabs.getSelectedComponent();
		if (tab == null) {
			JOptionPane.showMessageDialog(notepad, "No tabs opened", "Info", JOptionPane.INFORMATION_MESSAGE);
			return;
		}

		Path openedFilePath = tab.getFilePath();
		if (newPath) {
			JFileChooser jfc = new JFileChooser();
			jfc.setDialogTitle("Save document");
			if (jfc.showSaveDialog(notepad) != JFileChooser.APPROVE_OPTION) {
				JOptionPane.showMessageDialog(notepad, nothingSaved, "Warning", JOptionPane.WARNING_MESSAGE);
				return;
			}
			if (jfc.getSelectedFile().exists()) {
				if (JOptionPane.showConfirmDialog(notepad, overwrite, "Are you sure?",
						JOptionPane.YES_NO_OPTION) != JOptionPane.YES_OPTION) {
					return;
				}
			}
			openedFilePath = jfc.getSelectedFile().toPath();
			tab.setFilePath(openedFilePath);
		}

		JTextArea editor = tab.getEditor();
		byte[] data = editor.getText().getBytes(StandardCharsets.UTF_8);
		try {
			Files.write(openedFilePath, data);
		} catch (IOException ex) {
			JOptionPane.showMessageDialog(notepad, saveError + openedFilePath.toAbsolutePath(), "Error",
					JOptionPane.ERROR_MESSAGE);
			return;
		}

		JOptionPane.showMessageDialog(notepad, saveSuccess, "Info", JOptionPane.INFORMATION_MESSAGE);
		((JTab) tabs.getSelectedComponent()).setEdited(false);
		setFrameTitle();
		notepad.refresh();
	}

}
