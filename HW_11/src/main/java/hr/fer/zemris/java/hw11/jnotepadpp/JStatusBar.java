package hr.fer.zemris.java.hw11.jnotepadpp;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
import javax.swing.border.BevelBorder;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.text.BadLocationException;

/**
 * The {@code JStatusBar} class represents an element with information about the
 * currently opened document.
 * 
 * @author Karlo Bencic
 * 
 */
public class JStatusBar extends JPanel {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The clock. */
	private JLabel clock;

	/** The line label. */
	private JLabel lnLabel;

	/** The coloumn label. */
	private JLabel colLabel;

	/** The selected label. */
	private JLabel selLabel;

	/** The length label. */
	private JLabel lenLabel;

	/** The tab. */
	private JTab tab;

	/** The editor. */
	private JTextArea editor;

	/** Date format for clock. */
	private final SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

	/**
	 * Instantiates a new notepad status bar.
	 *
	 * @param tab
	 *            the tab with editor to show the info about
	 */
	public JStatusBar(JTab tab) {
		setStatusBar(tab);
	}

	/**
	 * Sets the status bar.
	 *
	 * @param tab
	 *            the new status bar
	 */
	public void setStatusBar(JTab tab) {
		this.tab = tab;
		editor = tab.getEditor();
		lenLabel = new JLabel("Length: 0");
		lnLabel = new JLabel("Ln: 1");
		colLabel = new JLabel(" Col: 0");
		selLabel = new JLabel(" Sel: 0");
		clock = new JLabel();

		removeAll();
		setBorder(new BevelBorder(BevelBorder.LOWERED));
		setLayout(new BoxLayout(this, BoxLayout.X_AXIS));

		setUpLabel();
	}

	/**
	 * Sets the up labels.
	 */
	private void setUpLabel() {
		add(lenLabel);
		add(Box.createHorizontalGlue());
		add(lnLabel);
		add(colLabel);
		add(selLabel);
		add(Box.createHorizontalGlue());
		add(clock);

		editor.addCaretListener(new CaretListener() {
			@Override
			public void caretUpdate(CaretEvent e) {
				JTextArea editArea = (JTextArea) e.getSource();
				int linenum = 1;
				int columnnum = 1;
				try {
					int caretpos = editArea.getCaretPosition();
					linenum = editArea.getLineOfOffset(caretpos);
					columnnum = caretpos - editArea.getLineStartOffset(linenum);
					linenum += 1;
				} catch (BadLocationException ignorable) {
				}
				updateStatus(linenum, columnnum);
			}

			private void updateStatus(int linenum, int columnnum) {
				lenLabel.setText("Length: " + tab.getTotalNum());
				lnLabel.setText("Ln: " + linenum);
				colLabel.setText(" Col: " + columnnum);
				selLabel.setText(" Sel: " + Math.abs(editor.getCaret().getDot() - editor.getCaret().getMark()));
			}
		});

		keepPosted();
	}

	/**
	 * Method that keeps the clock refreshed.
	 */
	private void keepPosted() {
		Thread t = new Thread(() -> {
			while (true) {
				try {
					Thread.sleep(250);
					SwingUtilities.invokeLater(() -> clock.setText(sdf.format(new Date())));
				} catch (InterruptedException ignorable) {
				}
			}
		});
		t.setDaemon(true);
		t.start();
	}
}
