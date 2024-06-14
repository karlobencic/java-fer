package hr.fer.zemris.cmdapps.jvdraw.color;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JColorChooser;
import javax.swing.JComponent;

import hr.fer.zemris.cmdapps.jvdraw.JVDraw;

/**
 * Used for color changing in {@link JVDraw}.
 * 
 * @author Karlo Bencic
 * 
 */
public class JColorArea extends JComponent implements IColorProvider {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** Default size of this component. */
	private static final Dimension DIM = new Dimension(20, 20);

	/** The prefered size of this component. */
	private static final int SIZE = 30;

	/** The color of this component. */
	private Color color;

	/** The is foreground flag. */
	private boolean isForeground;

	/** A list of listeners (observers). */
	private List<ColorChangeListener> listeners = new ArrayList<>();

	/**
	 * When there's a click on this component, a {@link JColorChooser} is opened
	 * to choose a new color.
	 */
	private MouseAdapter myMouse = new MouseAdapter() {
		public void mouseClicked(MouseEvent e) {
			Color c = JColorChooser.showDialog(JColorArea.this, "Choose a color", color);
			if (c != null) {
				setNewColor(c);
			}
		};
	};

	/**
	 * Instantiates a new {@link JColorArea} with given color.
	 *
	 * @param color
	 *            initial color
	 * @param isFgColor
	 *            is foreground color flag
	 */
	public JColorArea(Color color, boolean isFgColor) {
		this.color = color;
		this.isForeground = isFgColor;
		addMouseListener(myMouse);
	}

	@Override
	public boolean isForegroundColor() {
		return isForeground;
	}

	/**
	 * Sets the new color.
	 * 
	 * @param c
	 *            new color
	 */
	private void setNewColor(Color c) {
		listeners.forEach(l -> l.newColorSelected(this, color, c));
		color = c;
		repaint();
	}

	@Override
	public Color getCurrentColor() {
		return color;
	}

	@Override
	public void addColorChangeListener(ColorChangeListener l) {
		if (!listeners.contains(l)) {
			listeners.add(l);
		}
	}

	@Override
	public void removeColorChangeListener(ColorChangeListener l) {
		listeners.remove(l);
	}

	@Override
	protected void paintComponent(Graphics g) {
		g.setColor(color);
		Insets ins = getInsets();
		Dimension size = getSize();
		g.fillRect(ins.left + (size.width - (DIM.width)) / 2, ins.top + (size.height - (DIM.height)) / 2, DIM.width,
				DIM.height);
	}

	@Override
	public Dimension getPreferredSize() {
		return new Dimension(SIZE, SIZE);
	}

	@Override
	public Dimension getMinimumSize() {
		return getPreferredSize();
	}

	@Override
	public Dimension getMaximumSize() {
		return getPreferredSize();
	}

}