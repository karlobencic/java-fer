package hr.fer.zemris.java.gui.charts;

import java.awt.*;
import java.awt.geom.AffineTransform;

import javax.swing.JComponent;

/**
 * The {@code BarChartComponent} class creates a data view which is defined by
 * the {@link BarChart} object. The chart is scalable which means it supports
 * all screen resolutions, including hi-dpi screens.
 * 
 * @author Karlo Bencic
 * 
 */
public class BarChartComponent extends JComponent {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The bar chart. */
	private final BarChart chart;

	/** The y axis. */
	private final BarChartAxis yAxis;

	/** The bar title. */
	private final String title;

	/** The left offset. */
	private final int leftOffset;

	/** The top offset. */
	private final int topOffset = 100;

	/** The bottom offset. */
	private final int bottomOffset = 100;

	/** The right offset. */
	private final int rightOffset = 15;

	/** The x-axis label offset. */
	private final int xLabelOffset = 40;

	/** The y-axis label offset. */
	private final int yLabelOffset;

	/** The major tick width. */
	private final int majorTickWidth = 10;

	/** The secondary tick width. */
	private final int secTickWidth = 5;

	/** The shadow depth. */
	private final int shadowDepth = 3;

	/** The label font. */
	private final Font labelFont = new Font("Arial", Font.PLAIN, 12);

	/** The bar font. */
	private final Font barFont = new Font("Arial", Font.BOLD, 12);

	/** The title font. */
	private final Font titleFont = new Font("Arial", Font.BOLD, 14);

	/** The shadow color. */
	private final Color shadowColor = new Color(195, 195, 195, 128);

	/** The bar color. */
	private final Color barColor = new Color(244, 119, 72);

	/** The label color. */
	private final Color labelColor = Color.BLACK;

	/**
	 * Instantiates a new bar chart component.
	 *
	 * @param chart
	 *            the bar chart
	 */
	public BarChartComponent(BarChart chart) {
		this(chart, "");
	}

	/**
	 * Instantiates a new bar chart component.
	 *
	 * @param chart
	 *            the bar chart
	 * @param title
	 *            the bar title
	 */
	public BarChartComponent(BarChart chart, String title) {
		this.chart = chart;
		this.title = title;
		yAxis = new BarChartAxis(chart.getMaxY(), chart.getMinY(), chart.getyOffset(), chart.getyOffset() / 2);
		yLabelOffset = (String.valueOf(chart.getMaxY()).length() + 1) * 10;
		leftOffset = (String.valueOf(chart.getMaxY()).length() + 1) * 15;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.JComponent#paintComponent(java.awt.Graphics)
	 */
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;

		int width = getSize().width;
		int height = getSize().height;

		g.drawRect(0, 0, width, height);
		g2.setColor(Color.WHITE);
		g.fillRect(0, 0, width, height);
		g2.setColor(Color.BLACK);

		int heightChart = height - (topOffset + bottomOffset);
		int widthChart = width - (leftOffset + rightOffset);

		drawArrowLine(g, Color.GRAY, leftOffset, heightChart + topOffset, leftOffset, topOffset - 10, 5, 5);
		drawArrowLine(g, Color.GRAY, leftOffset, heightChart + topOffset, leftOffset + widthChart,
				heightChart + topOffset, 5, 5);

		drawTick(heightChart, yAxis.getPrimaryIncrements(), g, Color.BLACK, majorTickWidth);
		drawTick(heightChart, yAxis.getSecondaryIncrements(), g, Color.BLACK, secTickWidth);

		drawYLabels(heightChart, yAxis.getPrimaryIncrements(), g, labelColor);

		drawBars(heightChart, widthChart, g);

		drawLabels(heightChart, widthChart, g);
	}

	/**
	 * Draws a tick line which indicates the position in the coordinate system.
	 *
	 * @param heightChart
	 *            the height chart
	 * @param increment
	 *            the increment value
	 * @param g
	 *            the graphics object
	 * @param c
	 *            the color object
	 * @param tickWidth
	 *            the tick width
	 */
	private void drawTick(int heightChart, int increment, Graphics g, Color c, int tickWidth) {
		int incrementNo = yAxis.getMaxValue() / increment;
		double factor = ((double) heightChart / (double) yAxis.getMaxValue());
		double incrementInPixel = (double) (increment * factor);

		g.setColor(c);

		for (int i = 0; i <= incrementNo; i++) {
			int fromTop = heightChart + topOffset - (int) (i * incrementInPixel);
			g.drawLine(leftOffset, fromTop, leftOffset + tickWidth, fromTop);
		}
	}

	/**
	 * Draws a line with an arrow on the end. The line is drawn from (x1, y1) to
	 * (x2, y2) point.
	 *
	 * @param g
	 *            the graphics object
	 * @param c
	 *            the color object
	 * @param x1
	 *            the x1
	 * @param y1
	 *            the y1
	 * @param x2
	 *            the x2
	 * @param y2
	 *            the y2
	 * @param d
	 *            the distance
	 * @param h
	 *            the height
	 */
	private void drawArrowLine(Graphics g, Color c, int x1, int y1, int x2, int y2, int d, int h) {
		int dx = x2 - x1;
		int dy = y2 - y1;

		double D = Math.sqrt(dx * dx + dy * dy);

		double xm = D - d;
		double xn = xm;
		double ym = h;
		double yn = -h, x;

		double sin = dy / D, cos = dx / D;

		x = xm * cos - ym * sin + x1;
		ym = xm * sin + ym * cos + y1;
		xm = x;

		x = xn * cos - yn * sin + x1;
		yn = xn * sin + yn * cos + y1;
		xn = x;

		int[] xPoints = { x2, (int) xm, (int) xn };
		int[] yPoints = { y2, (int) ym, (int) yn };

		g.setColor(c);
		g.drawLine(x1, y1, x2, y2);
		g.fillPolygon(xPoints, yPoints, 3);
	}

	/**
	 * Draws Y-axis labels.
	 *
	 * @param heightChart
	 *            the height chart
	 * @param increment
	 *            the increment
	 * @param g
	 *            the graphics object
	 * @param c
	 *            the color object
	 */
	private void drawYLabels(int heightChart, int increment, Graphics g, Color c) {
		FontMetrics fm = getFontMetrics(barFont);
		g.setColor(c);

		int incrementNo = yAxis.getMaxValue() / increment;
		double factor = ((double) heightChart / (double) yAxis.getMaxValue());
		int incrementInPixel = (int) (increment * factor);

		for (int i = 0; i <= incrementNo; i++) {
			int fromTop = heightChart + topOffset - (i * incrementInPixel);

			String yLabel = String.valueOf(i * increment);
			int widthStr = fm.stringWidth(yLabel);
			int heightStr = fm.getHeight();

			g.setFont(barFont);
			g.drawString(yLabel, (leftOffset - yLabelOffset) + (yLabelOffset / 2 - widthStr / 2),
					fromTop + (heightStr / 2));
		}
	}

	/**
	 * Draws bars.
	 *
	 * @param heightChart
	 *            the height chart
	 * @param widthChart
	 *            the width chart
	 * @param g
	 *            the graphics object
	 */
	private void drawBars(int heightChart, int widthChart, Graphics g) {
		FontMetrics fm = getFontMetrics(barFont);

		int barNumber = chart.getValues().size();
		int pointDistance = (int) (widthChart / (barNumber + 1));

		int i = 1;
		for (XYValue bar : chart.getValues()) {
			double factor = ((double) heightChart / (double) yAxis.getMaxValue());
			int scaledBarHeight = (int) (bar.getY() * factor);

			int barWidth = getSize().width / (barNumber * 2);

			g.setColor(shadowColor);
			g.fillRect(leftOffset + (i * pointDistance) - (barWidth / 2) + shadowDepth,
					topOffset + heightChart - scaledBarHeight + shadowDepth, barWidth, scaledBarHeight - shadowDepth);

			g.setColor(barColor);
			g.fillRect(leftOffset + (i * pointDistance) - (barWidth / 2), topOffset + heightChart - scaledBarHeight,
					barWidth, scaledBarHeight);

			g.setColor(Color.BLACK);
			g.drawLine(leftOffset + (i * pointDistance), topOffset + heightChart, leftOffset + (i * pointDistance),
					topOffset + heightChart + 5);

			int widthStr = fm.stringWidth(String.valueOf(i));
			int heightStr = fm.getHeight();

			g.setFont(barFont);
			g.setColor(labelColor);

			int xPosition = leftOffset + (i * pointDistance) - (widthStr / 2);
			int yPosition = topOffset + heightChart + xLabelOffset - heightStr / 2;

			g.drawString(String.valueOf(i), xPosition, yPosition);
			i++;
		}
	}

	/**
	 * Draws labels.
	 *
	 * @param heightChart
	 *            the height chart
	 * @param widthChart
	 *            the width chart
	 * @param g
	 *            the graphics object
	 */
	private void drawLabels(int heightChart, int widthChart, Graphics g) {
		Graphics2D g2d = (Graphics2D) g;

		AffineTransform oldTransform = g2d.getTransform();

		FontMetrics fm = getFontMetrics(labelFont);
		int yAxisStringWidth = fm.stringWidth(chart.getyDescription());
		int yAxisStringHeight = fm.getHeight();
		int xAxisStringWidth = fm.stringWidth(chart.getxDescription());

		g2d.setColor(labelColor);
		g2d.rotate(Math.toRadians(270));

		int translateDown = -leftOffset - (topOffset + heightChart / 2 + yAxisStringWidth / 2);
		int translateLeft = -topOffset + (leftOffset - yLabelOffset) / 2 + yAxisStringHeight / 2;

		g2d.translate(translateDown, translateLeft);
		g2d.setFont(labelFont);
		g2d.drawString(chart.getyDescription(), leftOffset, topOffset);
		g2d.setTransform(oldTransform);

		int xAxesLabelHeight = bottomOffset - xLabelOffset;

		g2d.setFont(labelFont);
		g2d.drawString(chart.getxDescription(), widthChart / 2 + leftOffset - xAxisStringWidth / 2,
				topOffset + heightChart + xLabelOffset + xAxesLabelHeight / 2);

		FontMetrics fmTitle = getFontMetrics(titleFont);
		int titleStringWidth = fmTitle.stringWidth(title);
		int titleStringHeight = fmTitle.getHeight();

		g2d.setFont(titleFont);
		int titleX = (leftOffset + rightOffset + widthChart) / 2 - titleStringWidth / 2;
		int titleY = topOffset / 2 + titleStringHeight / 2;

		g2d.drawString(title, titleX, titleY);
	}
}
