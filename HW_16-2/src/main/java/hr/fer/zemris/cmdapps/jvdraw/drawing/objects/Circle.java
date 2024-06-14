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
 * Used to draw circles in {@link JVDraw}.
 * 
 * @author Karlo Bencic
 * 
 */
public class Circle extends GeometricalObject {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The x. */
	private int x;

	/** The y. */
	private int y;

	/** The radius. */
	private int radius;

	/** The color. */
	private Color color;

	/** The x text field. */
	private JTextField xTextField;

	/** The y text field. */
	private JTextField yTextField;

	/** The radius text field. */
	private JTextField radiusTextField;

	/** The color text field. */
	private JTextField colorTextField;

	/**
	 * Creates a new drawable circle.
	 *
	 * @param x
	 *            x-coordinate
	 * @param y
	 *            y-coordinate
	 * @param radius
	 *            the radius
	 * @param color
	 *            color of the border
	 */
	public Circle(int x, int y, int radius, Color color) {
		this.x = x;
		this.y = y;
		this.radius = radius;
		this.color = color;
	}

	@Override
	public void paintComponent(Graphics g) {
		drawCircle(g, color, x, y, radius);
	}

	@Override
	public void drawShifted(Graphics g, int x, int y) {
		drawCircle(g, color, this.x - x, this.y - y, radius);
	}

	@Override
	public void updateObject(JComponent comp) {
		try {
			x = Integer.parseInt(xTextField.getText());
			y = Integer.parseInt(yTextField.getText());
			radius = Integer.parseInt(radiusTextField.getText());
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
	 * Draws a circle in the given graphics with given color.
	 * 
	 * @param g
	 *            graphics used
	 * @param c
	 *            color used
	 * @param x
	 *            center x
	 * @param y
	 *            center y
	 * @param radius
	 *            radius
	 */
	private void drawCircle(Graphics g, Color c, int x, int y, int radius) {
		g.setColor(c);
		g.drawOval(x - radius, y - radius, 2 * radius, 2 * radius);
	}

	@Override
	public Rectangle getBounds() {
		Point p = new Point(x - radius, y - radius);
		Dimension d = new Dimension(2 * radius, 2 * radius);
		return new Rectangle(p, d);
	}

	/**
	 * Creates a custom message.
	 */
	@Override
	public JPanel createMessage() {
		JPanel main = new JPanel(new BorderLayout());
		JPanel left = new JPanel(new GridLayout(4, 1));
		JPanel right = new JPanel(new GridLayout(4, 1));

		main.add(left, BorderLayout.LINE_START);
		main.add(right, BorderLayout.CENTER);

		left.add(new JLabel("Center x-coor "));
		left.add(new JLabel("Start y-coor: "));
		left.add(new JLabel("Radius: "));
		left.add(new JLabel("Color RGB: "));

		xTextField = new JTextField(String.valueOf(x));
		yTextField = new JTextField(String.valueOf(y));
		radiusTextField = new JTextField(String.valueOf(radius));
		colorTextField = new JTextField(Colors.toRgbString(color));

		right.add(xTextField);
		right.add(yTextField);
		right.add(radiusTextField);
		right.add(colorTextField);

		return main;
	}

	/**
	 * Changes some point on the circle that determines the radius.
	 */
	@Override
	public void changeEndPoint(Point ending) {
		radius = (int) new Point(x, y).distance(ending);
	}

	@Override
	public String toString() {
		return "CIRCLE " + x + " " + y + " " + radius + " " + color.getRed() + " " + color.getGreen() + " "
				+ color.getBlue();
	}
}