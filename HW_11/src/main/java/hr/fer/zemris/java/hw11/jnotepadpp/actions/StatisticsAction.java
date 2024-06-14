package hr.fer.zemris.java.hw11.jnotepadpp.actions;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.Action;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;

import hr.fer.zemris.java.hw11.jnotepadpp.JNotepadPP;
import hr.fer.zemris.java.hw11.jnotepadpp.JTab;
import hr.fer.zemris.java.hw11.jnotepadpp.icons.Icons;
import hr.fer.zemris.java.hw11.jnotepadpp.local.ILocalizationProvider;

/**
 * The {@code StatisticsAction} class shows the statistics for currently showed
 * document.
 * 
 * @author Karlo Bencic
 * 
 */
public class StatisticsAction extends NotepadAction {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The Constant ACTION_NAME. */
	private static final String ACTION_NAME = "action_statistics";

	/** Dialog display message. */
	private String message;

	/**
	 * Instantiates a new statistics action.
	 *
	 * @param provider
	 *            the provider
	 * @param notepad
	 *            the notepad
	 */
	public StatisticsAction(ILocalizationProvider provider, JNotepadPP notepad) {
		super(ACTION_NAME, provider, notepad);
		provider.addLocalizationListener(this::update);
		putValue(Action.SMALL_ICON, Icons.STATISTICS);
		putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control B"));
		putValue(Action.MNEMONIC_KEY, KeyEvent.VK_B);
		putValue(Action.SHORT_DESCRIPTION, "Statistics for current document");
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		update();
		JOptionPane.showMessageDialog(notepad, message, provider.getString(ActionKeys.STATISTICS_TITLE),
				JOptionPane.INFORMATION_MESSAGE);
	}

	/**
	 * Updates language dependent elements for this action.
	 */
	private void update() {
		JTab selected = (JTab) tabs.getSelectedComponent();
		if (selected == null) {
			JOptionPane.showMessageDialog(notepad, "No tabs opened", "Info", JOptionPane.INFORMATION_MESSAGE);
			return;
		}

		message = String.format("%s %d %s, %d %s, %d %s", provider.getString(ActionKeys.STATISTICS_LINE),
				selected.getTotalNum(), provider.getString(ActionKeys.CHARACTERS), selected.getCharNum(),
				provider.getString(ActionKeys.NON_BLANK_CHARACTERS), selected.getLines(),
				provider.getString(ActionKeys.LINES));
	}
}
