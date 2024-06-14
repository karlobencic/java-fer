package hr.fer.zemris.java.gui.charts;

import java.util.List;

/**
 * The {@code BarChart} class represents a chart bar. It has 6 read-only
 * properties: XY coordinates, x-axis and y-axis description, minimum and
 * maximum Y value and offset on y-axis.
 * 
 * @author Karlo Bencic
 * 
 */
public class BarChart {

	/** The coordinates. */
	private final List<XYValue> values;

	/** The x-axis description. */
	private final String xDescription;

	/** The y-axis description. */
	private final String yDescription;

	/** The minimum y. */
	private final int yMin;

	/** The maximum y. */
	private final int yMax;

	/** The y-axis offset. */
	private final int yOffset;

	/**
	 * Instantiates a new bar chart.
	 *
	 * @param values
	 *            the XY coordinates
	 * @param xDescription
	 *            the x-axisdescription
	 * @param yDescription
	 *            the y-axis description
	 * @param yMin
	 *            the minimum y
	 * @param yMax
	 *            the maximum y
	 * @param yOffset
	 *            the y-axis offset
	 */
	public BarChart(List<XYValue> values, String xDescription, String yDescription, int yMin, int yMax, int yOffset) {
		this.values = values;
		this.xDescription = xDescription;
		this.yDescription = yDescription;
		this.yMin = yMin;
		this.yMax = yMax;
		this.yOffset = yOffset;
	}

	/**
	 * Gets the XY coordinates.
	 *
	 * @return the coordinates
	 */
	public List<XYValue> getValues() {
		return values;
	}

	/**
	 * Gets the x-axis description.
	 *
	 * @return the x-axis description
	 */
	public String getxDescription() {
		return xDescription;
	}

	/**
	 * Gets the y-axis description.
	 *
	 * @return the y-axis description
	 */
	public String getyDescription() {
		return yDescription;
	}

	/**
	 * Gets the minimum y.
	 *
	 * @return the minimum y
	 */
	public int getMinY() {
		return yMin;
	}

	/**
	 * Gets the maximum y.
	 *
	 * @return the maximum y
	 */
	public int getMaxY() {
		return yMax;
	}

	/**
	 * Gets the y-axis offset.
	 *
	 * @return the y-axis offset
	 */
	public int getyOffset() {
		return yOffset;
	}
}
