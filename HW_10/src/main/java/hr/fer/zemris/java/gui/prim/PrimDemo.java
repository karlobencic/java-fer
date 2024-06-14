package hr.fer.zemris.java.gui.prim;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;

/**
 * The {@code PrimDemo} program is start from the command line without
 * arguments. When started, it opens a GUI windows which has two lists and a
 * button "next". When pressed, {@link PrimListModel} next method is called and
 * calculates the next prime number(starting from 1) and updates the lists with
 * the result.
 * 
 * @author Karlo Bencic
 * 
 */
public class PrimDemo extends JFrame {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/**
	 * The main method.
	 *
	 * @param args
	 *            the arguments
	 */
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				new PrimDemo().setVisible(true);
			}
		});
	}

	/**
	 * Instantiates a new prim demo and starts initializing the GUI.
	 */
	public PrimDemo() {
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setSize(200, 200);
		setLocationRelativeTo(null);
		initGUI();
	}

	/**
	 * Initializes the GUI.
	 */
	private void initGUI() {
		Container cp = getContentPane();
		cp.setLayout(new BorderLayout());

		PrimListModel model = new PrimListModel();

		JPanel lists = new JPanel(new GridLayout(1, 2));
		lists.add(new JScrollPane(new JList<>(model)));
		lists.add(new JScrollPane(new JList<>(model)));
		cp.add(lists);

		JButton btn = new JButton("Sljedeći");
		btn.addActionListener(e -> model.next());
		cp.add(btn, BorderLayout.SOUTH);
	}
}
