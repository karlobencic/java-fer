package hr.fer.zemris.java.gui.charts;

/**
 * The {@code XYValue} class represents an XY coordinate. It has two read-only
 * properties: x and y integer value.
 * 
 * @author Karlo Bencic
 * 
 */
public class XYValue {

	/** The x. */
	private final int x;

	/** The y. */
	private final int y;

	/**
	 * Instantiates a new XY value.
	 *
	 * @param x
	 *            the x value
	 * @param y
	 *            the y value
	 */
	public XYValue(int x, int y) {
		this.x = x;
		this.y = y;
	}

	/**
	 * Gets the x.
	 *
	 * @return the x
	 */
	public int getX() {
		return x;
	}

	/**
	 * Gets the y.
	 *
	 * @return the y
	 */
	public int getY() {
		return y;
	}
}
