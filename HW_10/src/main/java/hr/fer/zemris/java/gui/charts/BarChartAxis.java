package hr.fer.zemris.java.gui.charts;

/**
 * The {@code BarChartAxis} class represents a chart bar axis. It has the
 * following read-only properties: primary and secondary increments step,
 * minimum and maximum value on the axis.
 * 
 * @author Karlo Bencic
 * 
 */
public class BarChartAxis {

	/** The primary increments step. */
	private final int primaryIncrements;

	/** The secondary increments step. */
	private final int secondaryIncrements;

	/** The maximum value. */
	private final int maxValue;

	/** The minimum value. */
	private final int minValue;

	/**
	 * Instantiates a new bar chart axis.
	 *
	 * @param maxValue
	 *            the maximum value
	 * @param minValue
	 *            the minimum value
	 * @param primaryIncrements
	 *            the primary increments step
	 * @param secondaryIncrements
	 *            the secondary increments step
	 */
	public BarChartAxis(int maxValue, int minValue, int primaryIncrements, int secondaryIncrements) {
		this.maxValue = maxValue;
		this.minValue = minValue;
		this.primaryIncrements = primaryIncrements;
		this.secondaryIncrements = secondaryIncrements;
	}

	/**
	 * Gets the primary increments step.
	 *
	 * @return the primary increments
	 */
	public int getPrimaryIncrements() {
		return primaryIncrements;
	}

	/**
	 * Gets the secondary increments step.
	 *
	 * @return the secondary increments
	 */
	public int getSecondaryIncrements() {
		return secondaryIncrements;
	}

	/**
	 * Gets the maximum value.
	 *
	 * @return the maximum value
	 */
	public int getMaxValue() {
		return maxValue;
	}

	/**
	 * Gets the minimum value.
	 *
	 * @return the minimum value
	 */
	public int getMinValue() {
		return minValue;
	}
}