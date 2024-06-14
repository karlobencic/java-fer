package hr.fer.zemris.java.hw11.jnotepadpp.actions;

import java.text.Collator;
import java.util.Locale;

import javax.swing.JTabbedPane;

import hr.fer.zemris.java.hw11.jnotepadpp.JNotepadPP;
import hr.fer.zemris.java.hw11.jnotepadpp.JTab;
import hr.fer.zemris.java.hw11.jnotepadpp.local.ILocalizationProvider;
import hr.fer.zemris.java.hw11.jnotepadpp.local.ui.LocalizableAction;

/**
 * This class is the heart of every action serving for extending actions for
 * use.
 * 
 * @author Karlo Bencic
 *
 */
public abstract class NotepadAction extends LocalizableAction {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The notepad. */
	protected final JNotepadPP notepad;

	/** The tabs. */
	protected final JTabbedPane tabs;

	/** The localization provider. */
	protected final ILocalizationProvider provider;

	/** The collator for comparator. */
	protected Collator col;

	/** The save error. */
	protected String saveError;

	/** The save success. */
	protected String saveSuccess;

	/** The overwrite message text. */
	protected String overwrite;

	/** The nothing saved message text. */
	protected String nothingSaved;

	/**
	 * Instantiates a new notepad action.
	 *
	 * @param key
	 *            the key
	 * @param provider
	 *            the provider
	 * @param notepad
	 *            the notepad
	 */
	public NotepadAction(String key, ILocalizationProvider provider, JNotepadPP notepad) {
		super(key, provider);
		this.provider = provider;
		this.notepad = notepad;
		tabs = notepad.tabs;

		update();
		provider.addLocalizationListener(this::update);
		setFrameTitle();
		tabs.addChangeListener(e -> {
			setFrameTitle();
			notepad.setStatusBar();
		});
	}

	/**
	 * Updates language dependent elements.
	 */
	private void update() {
		Locale locale = Locale.forLanguageTag(notepad.language);
		col = Collator.getInstance(locale);

		saveError = provider.getString(ActionKeys.SAVE_UNSUCCESSFUL_TEXT);
		saveSuccess = provider.getString(ActionKeys.SAVE_SUCCESSFUL_TEXT);
		overwrite = provider.getString(ActionKeys.OVERWRITE_QUESTION);
		nothingSaved = provider.getString(ActionKeys.SAVE_CANCELED);
	}

	/**
	 * Refreshes tabs in notepad.
	 */
	protected void refreshTabs() {
		for (int i = 0; i < tabs.getComponentCount(); i++) {
			JTab tab = (JTab) tabs.getComponentAt(i);
			tabs.setToolTipTextAt(i, tab.getToolTipText());
			tabs.setTitleAt(i, tab.getName());
			tabs.setIconAt(i, tab.getIcon());
		}
	}

	/**
	 * Sets notepads frame title.
	 */
	protected void setFrameTitle() {
		if (tabs != null && tabs.getTabCount() == 0) {
			notepad.setTitle("No files opened");
			return;
		}
		JTab tab = (JTab) tabs.getSelectedComponent();
		notepad.setTitle((tab.getFilePath() == null ? "Untitled" : tab.getFilePath().toString()) + " - JNotepad++");
	}

}
