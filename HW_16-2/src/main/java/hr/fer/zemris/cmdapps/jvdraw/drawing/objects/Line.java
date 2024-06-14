package hr.fer.zemris.cmdapps.jvdraw.drawing.objects;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.Rectangle;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import hr.fer.zemris.cmdapps.jvdraw.JVDraw;
import hr.fer.zemris.cmdapps.jvdraw.util.Colors;

/**
 * Used to draw lines in {@link JVDraw}.
 * 
 * @author Karlo Bencic
 * 
 */
public class Line extends GeometricalObject {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The start X. */
	private int startX;

	/** The start Y. */
	private int startY;

	/** The end X. */
	private int endX;

	/** The end Y. */
	private int endY;

	/** The color. */
	private Color color;

	/** The start X text field. */
	private JTextField startXTextField;

	/** The start Y text field. */
	private JTextField startYTextField;

	/** The end X text field. */
	private JTextField endXTextField;

	/** The end Y text field. */
	private JTextField endYTextField;

	/** The color text field. */
	private JTextField colorTextField;

	/**
	 * Creates a new line.
	 *
	 * @param startX
	 *            the start X
	 * @param startY
	 *            the start Y
	 * @param endX
	 *            the end X
	 * @param endY
	 *            the end Y
	 * @param color
	 *            color of the line
	 */
	public Line(int startX, int startY, int endX, int endY, Color color) {
		this.startX = startX;
		this.startY = startY;
		this.endX = endX;
		this.endY = endY;
		this.color = color;
	}

	/**
	 * Reads from text fields to update the line.
	 */
	@Override
	public void updateObject(JComponent comp) {
		try {
			startX = Integer.parseInt(startXTextField.getText());
			startY = Integer.parseInt(startYTextField.getText());
			endX = Integer.parseInt(endXTextField.getText());
			endY = Integer.parseInt(endYTextField.getText());
		} catch (NumberFormatException ex) {
			JOptionPane.showMessageDialog(null, "Invalid coordinate format", "Error", JOptionPane.ERROR_MESSAGE);
		}

		try {
			color = Colors.getColor(colorTextField.getText());
		} catch (NumberFormatException ex) {
			JOptionPane.showMessageDialog(null, "Invalid color format", "Error", JOptionPane.ERROR_MESSAGE);
		}

		comp.repaint();
	}

	/**
	 * Changes the endpoint of the line.
	 */
	@Override
	public void changeEndPoint(Point ending) {
		endX = ending.x;
		endY = ending.y;
	}

	/**
	 * Gets the bounds of this line.
	 */
	@Override
	public Rectangle getBounds() {
		Point p = new Point(Math.min(startX, endX), Math.min(startY, endY));
		Dimension d = new Dimension(Math.abs(startX - endX), Math.abs(startY - endY));
		return new Rectangle(p, d);
	}

	@Override
	public void drawShifted(Graphics g, int x, int y) {
		drawLine(g, color, startX - x, startY - y, endX - x, endY - y);
	}

	@Override
	public void paintComponent(Graphics g) {
		drawLine(g, color, startX, startY, endX, endY);

	}

	/**
	 * Draws a line in given graphics with the given color.
	 * 
	 * @param g
	 *            graphics used
	 * @param c
	 *            color of the line
	 * @param sx
	 *            starting x
	 * @param sy
	 *            starting y
	 * @param ex
	 *            ending x
	 * @param ey
	 *            ending y
	 */
	private void drawLine(Graphics g, Color c, int sx, int sy, int ex, int ey) {
		g.setColor(c);
		g.drawLine(sx, sy, ex, ey);
	}

	@Override
	public JPanel createMessage() {
		JPanel main = new JPanel(new BorderLayout());
		JPanel left = new JPanel(new GridLayout(5, 1));
		JPanel right = new JPanel(new GridLayout(5, 1));

		main.add(left, BorderLayout.LINE_START);
		main.add(right, BorderLayout.CENTER);

		left.add(new JLabel("Start x-coor: "));
		left.add(new JLabel("Start y-coor: "));
		left.add(new JLabel("End x-coor: "));
		left.add(new JLabel("End y-coor: "));
		left.add(new JLabel("Color RGB: "));

		startXTextField = new JTextField(String.valueOf(startX));
		startYTextField = new JTextField(String.valueOf(startY));
		endXTextField = new JTextField(String.valueOf(endX));
		endYTextField = new JTextField(String.valueOf(endY));
		colorTextField = new JTextField(Colors.toRgbString(color));

		right.add(startXTextField);
		right.add(startYTextField);
		right.add(endXTextField);
		right.add(endYTextField);
		right.add(colorTextField);

		return main;
	}

	@Override
	public String toString() {
		return "LINE " + startX + " " + startY + " " + endX + " " + endY + " " + color.getRed() + " " + color.getGreen()
				+ " " + color.getBlue();
	}
}