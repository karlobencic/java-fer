package hr.fer.zemris.java.hw11.jnotepadpp;

import java.nio.file.Path;

import javax.swing.Icon;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.Document;

import hr.fer.zemris.java.hw11.jnotepadpp.icons.Icons;

/**
 * The {@code JTab} class holds all the elements that a single tab can have and
 * its info.
 * 
 * @author Karlo Bencic
 * 
 */
public class JTab extends JScrollPane {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The editor. */
	private final JTextArea editor = new JTextArea();

	/** The file path. */
	private Path filePath;

	/** The file name. */
	private String fileName;

	/** The total num. */
	private int totalNum;

	/** The char num. */
	private int charNum;

	/** The lines. */
	private int lines;

	/** The edited. */
	private boolean edited;

	/** The has marked text. */
	private boolean hasMarkedText;

	/** The Constant UNSAVED. */
	private static final String UNSAVED = "Untitled";

	/** The Constant DEFAULT_FILENAME. */
	private static final String DEFAULT_FILENAME = "New Document";

	/**
	 * Instantiates a new custom tab for tabbed pane.
	 */
	public JTab() {
		fileName = DEFAULT_FILENAME;
		setUpEditor();
	}

	/**
	 * Instantiates a new custom tab for tabbed pane with provided path to file
	 * and text to be set in the editor.
	 *
	 * @param filePath
	 *            the file path to opened file
	 * @param text
	 *            the text to be set in editor
	 */
	public JTab(Path filePath, String text) {
		setFilePath(filePath);
		editor.setText(text);
		setUpEditor();
	}

	/**
	 * Sets the up editor for this tab.
	 */
	private void setUpEditor() {
		setViewportView(editor);

		editor.getDocument().addDocumentListener(new DocumentListener() {
			@Override
			public void removeUpdate(DocumentEvent e) {
				update(e);
			}

			@Override
			public void insertUpdate(DocumentEvent e) {
				update(e);
			}

			@Override
			public void changedUpdate(DocumentEvent e) {
				update(e);
			}

			private void update(DocumentEvent e) {
				Document doc = e.getDocument();
				if (!isEdited()) {
					setEdited(true);
				}
				((JNotepadPP) JTab.this.getTopLevelAncestor()).refresh();
				totalNum = doc.getLength();
				lines = editor.getLineCount();
				charNum = editor.getText().replaceAll("\\s+", "").length();
			}
		});

		editor.addCaretListener(new CaretListener() {
			@Override
			public void caretUpdate(CaretEvent e) {
				if (hasMarkedText != checkForMarks()) {
					hasMarkedText = checkForMarks();
					((JNotepadPP) JTab.this.getTopLevelAncestor()).setActions();
				}
			}

			private boolean checkForMarks() {
				return Math.abs(editor.getCaret().getDot() - editor.getCaret().getMark()) != 0;
			}
		});
	}

	/**
	 * Returns the editor from this tab.
	 *
	 * @return the text area
	 */
	public JTextArea getEditor() {
		return editor;
	}

	/**
	 * Gets the file path.
	 *
	 * @return the file path
	 */
	public Path getFilePath() {
		return filePath;
	}

	/**
	 * Sets the file path.
	 *
	 * @param filePath
	 *            the new file path
	 */
	public void setFilePath(Path filePath) {
		this.filePath = filePath;
		this.fileName = filePath.toAbsolutePath().getFileName().toString();
	}

	@Override
	public String getToolTipText() {
		return filePath != null ? filePath.toAbsolutePath().toString() : UNSAVED;
	}

	/**
	 * Gets the total number of characters in this tabs editor.
	 *
	 * @return the total num
	 */
	public int getTotalNum() {
		return totalNum;
	}

	/**
	 * Gets the char number in this tabs editor.
	 *
	 * @return the char num
	 */
	public int getCharNum() {
		return charNum;
	}

	/**
	 * Gets the number of lines in this tabs editor.
	 *
	 * @return the lines
	 */
	public int getLines() {
		return lines;
	}

	@Override
	public String getName() {
		return fileName;
	}

	/**
	 * Signals that the editor has been edited.
	 *
	 * @param edited
	 *            does this editor have any unsaved changes
	 */
	public void setEdited(boolean edited) {
		this.edited = edited;
	}

	/**
	 * Gets the icon for this tabs editor status.
	 *
	 * @return the icon
	 */
	public Icon getIcon() {
		return edited ? Icons.RED : Icons.GREEN;
	}

	/**
	 * Checks if is edited.
	 *
	 * @return true, if it is edited
	 */
	public boolean isEdited() {
		return edited;
	}

	/**
	 * Checks for marked text.
	 *
	 * @return true, if successful
	 */
	public boolean hasMarkedText() {
		return hasMarkedText;
	}
}
