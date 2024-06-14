package hr.fer.zemris.cmdapps.jvdraw.drawing.objects;

import java.awt.Component;
import java.awt.Graphics;
import java.awt.Point;

import javax.swing.JComponent;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import hr.fer.zemris.cmdapps.jvdraw.JVDraw;

/**
 * This class is an super class of all objects that can be drawn in
 * {@link JVDraw}.
 * 
 * @author Karlo Bencic
 * 
 */
public abstract class GeometricalObject extends JComponent {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/**
	 * This action is performed when an object is clicked.
	 *
	 * @param parent
	 *            parent component from which the dialog will be shown
	 * @param comp
	 *            the component
	 */
	public void onClick(Component parent, JComponent comp) {
		JPanel panel = createMessage();
		if (JOptionPane.showConfirmDialog(parent, panel, "Change object", JOptionPane.OK_CANCEL_OPTION,
				JOptionPane.QUESTION_MESSAGE) == JOptionPane.OK_OPTION) {
			updateObject(comp);
		}
	}
	
	/**
	 * Creates message that will be shown in the dialog.
	 * 
	 * @return message
	 */
	public abstract JPanel createMessage();

	/**
	 * Updates the given object.
	 *
	 * @param comp
	 *            the comp
	 */
	public abstract void updateObject(JComponent comp);

	/**
	 * Used to change objects using the ending point.
	 * 
	 * @param ending
	 *            end point
	 */
	public abstract void changeEndPoint(Point ending);

	/**
	 * Draws the given object shifted to the left for x and up for y.
	 *
	 * @param g
	 *            graphics used
	 * @param x
	 *            horizontal shift
	 * @param y
	 *            vertical shift
	 */
	public abstract void drawShifted(Graphics g, int x, int y);

	@Override
	public abstract void paintComponent(Graphics g);

}