package hr.fer.zemris.java.hw11.jnotepadpp;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.Action;
import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JTabbedPane;
import javax.swing.SwingUtilities;

import hr.fer.zemris.java.hw11.jnotepadpp.actions.*;
import hr.fer.zemris.java.hw11.jnotepadpp.local.LocalizationProvider;
import hr.fer.zemris.java.hw11.jnotepadpp.local.ui.*;

/**
 * The {@code JNotepadPP} is a a simple text editor with a few text editing
 * commands and possibilites to save current docuemnent or edit multiple
 * documents at the same time.
 * 
 * @author Karlo Bencic
 * 
 */
public class JNotepadPP extends JFrame {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The language. */
	public String language = "en";

	/** The tabs. */
	public final JTabbedPane tabs = new JTabbedPane();

	/** The status bar. */
	private JStatusBar statusBar;

	/** The flp. */
	private final FormLocalizationProvider flp = new FormLocalizationProvider(LocalizationProvider.getInstance(), this);

	/**
	 * The main method.
	 *
	 * @param args
	 *            the arguments
	 */
	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> new JNotepadPP().setVisible(true));
	}

	/**
	 * Creates and initializes the user interface.
	 */
	private JNotepadPP() {
		JTab tab = new JTab();
		tabs.add(tab);
		tabs.setToolTipTextAt(0, tab.getToolTipText());
		tabs.setTitleAt(0, tab.getName());
		tabs.setIconAt(0, tab.getIcon());

		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		setSize(800, 600);
		setLocationRelativeTo(null);

		initGUI();
	}

	/**
	 * Initializes the GUI.
	 */
	private void initGUI() {
		getContentPane().setLayout(new BorderLayout());
		getContentPane().add(tabs, BorderLayout.CENTER);

		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				exitAction.actionPerformed(null);
			}
		});

		createMenus();
		createToolbars();
		createStatusBar();
		setActions();
	}

	/**
	 * Enables/disables mark text dependent actions.
	 */
	public void setActions() {
		for (Action aMarkDependent : markDependent) {
			aMarkDependent.setEnabled(getTab().hasMarkedText());
		}
	}

	/**
	 * Sets the status bar up.
	 */
	public void setStatusBar() {
		if (tabs.getTabCount() == 0) {
			remove(statusBar);
			return;
		}
		if (tabs.getTabCount() == 1) {
			createStatusBar();
			return;
		}
		statusBar.setStatusBar(getTab());
	}

	/**
	 * Refreshes all tabs information.
	 */
	public void refresh() {
		for (int i = 0; i < tabs.getComponentCount(); i++) {
			JTab tab = (JTab) tabs.getComponentAt(i);
			tabs.setToolTipTextAt(i, tab.getToolTipText());
			tabs.setTitleAt(i, tab.getName());
			tabs.setIconAt(i, tab.getIcon());
		}
	}

	/**
	 * Creates a new status bar.
	 */
	private void createStatusBar() {
		statusBar = new JStatusBar(getTab());
		add(statusBar, BorderLayout.SOUTH);
	}

	/**
	 * Gets the tab.
	 *
	 * @return the tab
	 */
	private JTab getTab() {
		return (JTab) tabs.getSelectedComponent();
	}

	/**
	 * Creates a toolbar with actions.
	 */
	private void createToolbars() {
		LJToolBar toolBar = new LJToolBar(flp);
		toolBar.setFloatable(true);

		toolBar.add(blankDocumentAction);
		toolBar.add(openDocumentAction);
		toolBar.add(saveDocumentAction);
		toolBar.add(saveAsDocumentAction);

		toolBar.addSeparator();

		toolBar.add(cutAction);
		toolBar.add(copyAction);
		toolBar.add(pasteAction);
		toolBar.add(uniqueAction);

		toolBar.addSeparator();

		toolBar.add(closeTabAction);
		toolBar.add(statisticsAction);

		toolBar.addSeparator();

		toolBar.add(upperCaseAction);
		toolBar.add(lowerCaseAction);
		toolBar.add(invertCaseAction);

		getContentPane().add(toolBar, BorderLayout.PAGE_START);
	}

	/**
	 * Creates menus with actions for this window.
	 */
	private void createMenus() {
		JMenuBar menuBar = new JMenuBar();

		LJMenu fileMenu = new LJMenu(ActionKeys.FILE, flp);
		menuBar.add(fileMenu);

		fileMenu.add(blankDocumentAction);
		fileMenu.add(openDocumentAction);
		fileMenu.add(saveDocumentAction);
		fileMenu.add(saveAsDocumentAction);
		fileMenu.addSeparator();
		fileMenu.add(statisticsAction);
		fileMenu.add(closeTabAction);
		fileMenu.add(exitAction);

		LJMenu editMenu = new LJMenu(ActionKeys.EDIT, flp);
		menuBar.add(editMenu);
		editMenu.add(copyAction);
		editMenu.add(cutAction);
		editMenu.add(pasteAction);

		LJMenu toolsMenu = new LJMenu(ActionKeys.TOOLS, flp);
		menuBar.add(toolsMenu);

		LJMenu sort = new LJMenu(ActionKeys.SORT, flp);
		toolsMenu.add(sort);
		sort.add(new JMenuItem(sortAscAction));
		sort.add(new JMenuItem(sortDescAction));

		LJMenu changeMenu = new LJMenu(ActionKeys.CHANGE_CASE, flp);
		toolsMenu.add(changeMenu);
		changeMenu.add(lowerCaseAction);
		changeMenu.add(upperCaseAction);
		changeMenu.add(invertCaseAction);
		toolsMenu.add(uniqueAction);

		LJMenu langMenu = new LJMenu(ActionKeys.LANGUAGES, flp);
		menuBar.add(langMenu);
		langMenu.add(new JMenuItem(langEn));
		langMenu.add(new JMenuItem(langHr));
		langMenu.add(new JMenuItem(langDe));

		setJMenuBar(menuBar);
	}

	/** English language setting action. */
	private final Action langEn = new LocalizableAction("english", flp) {
		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			setLanguage("en");
		}
	};

	/** Croatian language setting action. */
	private final Action langHr = new LocalizableAction("croatian", flp) {
		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			setLanguage("hr");
		}
	};

	/** German language setting action. */
	private final Action langDe = new LocalizableAction("german", flp) {
		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			setLanguage("de");
		}
	};

	/**
	 * Sets the language.
	 *
	 * @param language
	 *            the new language
	 */
	private void setLanguage(String language) {
		this.language = language;
		LocalizationProvider.getInstance().setLanguage(language);
	}

	/** The blank document action. */
	private final Action blankDocumentAction = new BlankDocumentAction(flp, this);

	/** The open document action. */
	private final Action openDocumentAction = new OpenDocumentAction(flp, this);

	/** The save document action. */
	private final Action saveDocumentAction = new SaveDocumentAction(flp, this);

	/** The save as document action. */
	private final Action saveAsDocumentAction = new SaveAsDocumentAction(flp, this);

	/** The close tab action. */
	private final Action closeTabAction = new CloseTabAction(flp, this);

	/** The exit action. */
	private final Action exitAction = new ExitAction(flp, this);

	/** The statistics action. */
	private final Action statisticsAction = new StatisticsAction(flp, this);

	/** The cut action. */
	private final Action cutAction = new CutAction(flp, this);

	/** The copy action. */
	private final Action copyAction = new CopyAction(flp, this);

	/** The paste action. */
	private final Action pasteAction = new PasteAction(flp, this);

	/** The upper case action. */
	private final Action upperCaseAction = new UpperCaseAction(flp, this);

	/** The lower case action. */
	private final Action lowerCaseAction = new LowerCaseAction(flp, this);

	/** The invert case action. */
	private final Action invertCaseAction = new InvertCaseAction(flp, this);

	/** The sort asc action. */
	private final Action sortAscAction = new SortAscendingAction(flp, this);

	/** The sort desc action. */
	private final Action sortDescAction = new SortDescendingAction(flp, this);

	/** The unique action. */
	private final Action uniqueAction = new UniqueAction(flp, this);

	/** The mark dependent actions. */
	private final Action[] markDependent = { upperCaseAction, lowerCaseAction, invertCaseAction, sortAscAction,
			sortDescAction, uniqueAction };
}
