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
 * Used to draw filled circles in {@link JVDraw}.
 * 
 * @author Karlo Bencic
 * 
 */
public class FCircle extends GeometricalObject {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The x. */
	private int x;

	/** The y. */
	private int y;

	/** The radius. */
	private int radius;

	/** The foreground color. */
	private Color foregroundColor;

	/** The background color. */
	private Color backgroundColor;

	/** The x t ext field. */
	private JTextField xTextField;

	/** The y text field. */
	private JTextField yTextField;

	/** The radius text field. */
	private JTextField radiusTextField;

	/** The color text field. */
	private JTextField fColorTextField;

	/** The b color text field. */
	private JTextField bColorTextField;

	/**
	 * Creates a new drawable circle.
	 *
	 * @param x
	 *            x-coor of the center
	 * @param y
	 *            y-coor of the center
	 * @param radius
	 *            the radius
	 * @param fColor
	 *            color of the border
	 * @param bColor
	 *            color of the inner circle
	 */
	public FCircle(int x, int y, int radius, Color foregroundColor, Color backgroundColor) {
		this.x = x;
		this.y = y;
		this.radius = radius;
		this.foregroundColor = foregroundColor;
		this.backgroundColor = backgroundColor;
	}

	@Override
	public void paintComponent(Graphics g) {
		drawCircle(g, foregroundColor, backgroundColor, x, y, radius);
	}

	@Override
	public void drawShifted(Graphics g, int x, int y) {
		drawCircle(g, foregroundColor, backgroundColor, this.x - x, this.y - y, radius);
	}

	/**
	 * Draws a filled circle.
	 * 
	 * @param g
	 *            used graphics
	 * @param f
	 *            foreground color
	 * @param b
	 *            background color
	 * @param x
	 *            center x
	 * @param y
	 *            center y
	 * @param radius
	 *            radius
	 */
	private void drawCircle(Graphics g, Color f, Color b, int x, int y, int radius) {
		g.setColor(f);
		g.drawOval(x - radius, y - radius, 2 * radius, 2 * radius);
		g.setColor(b);
		g.fillOval(x - radius, y - radius, 2 * radius, 2 * radius);
	}

	@Override
	public Rectangle getBounds() {
		Point p = new Point(x - radius, y - radius);
		Dimension d = new Dimension(2 * radius, 2 * radius);
		return new Rectangle(p, d);
	}

	/**
	 * Creates a custom message to present.
	 */
	@Override
	public JPanel createMessage() {
		JPanel main = new JPanel(new BorderLayout());
		JPanel left = new JPanel(new GridLayout(5, 1));
		JPanel right = new JPanel(new GridLayout(5, 1));

		main.add(left, BorderLayout.LINE_START);
		main.add(right, BorderLayout.CENTER);

		left.add(new JLabel("X-coordinate: "));
		left.add(new JLabel("Y-coordinate: "));
		left.add(new JLabel("Radius: "));
		left.add(new JLabel("Foreground color: "));
		left.add(new JLabel("Background color: "));

		xTextField = new JTextField(String.valueOf(x));
		yTextField = new JTextField(String.valueOf(y));
		radiusTextField = new JTextField(String.valueOf(radius));
		fColorTextField = new JTextField(Colors.toRgbString(foregroundColor));
		bColorTextField = new JTextField(Colors.toRgbString(backgroundColor));

		right.add(xTextField);
		right.add(yTextField);
		right.add(radiusTextField);
		right.add(fColorTextField);
		right.add(bColorTextField);

		return main;
	}

	@Override
	public void updateObject(JComponent comp) {
		try {
			x = Integer.parseInt(xTextField.getText());
			y = Integer.parseInt(yTextField.getText());
			radius = Integer.parseInt(radiusTextField.getText());
		} catch (Exception ex) {
			JOptionPane.showMessageDialog(null, "Invalid coordinate format", "Error", JOptionPane.ERROR_MESSAGE);
		}

		try {
			foregroundColor = Colors.getColor(fColorTextField.getText());
			backgroundColor = Colors.getColor(bColorTextField.getText());
		} catch (Exception ex) {
			JOptionPane.showMessageDialog(null, "Invalid color format", "Error", JOptionPane.ERROR_MESSAGE);
		}

		comp.repaint();
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
		return "FCIRCLE " + x + " " + y + " " + radius + " " + foregroundColor.getRed() + " "
				+ foregroundColor.getGreen() + " " + foregroundColor.getBlue() + " " + backgroundColor.getRed() + " "
				+ backgroundColor.getGreen() + " " + backgroundColor.getBlue();
	}
}