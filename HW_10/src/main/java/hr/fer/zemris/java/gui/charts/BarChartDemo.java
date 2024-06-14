package hr.fer.zemris.java.gui.charts;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

/**
 * The {@code BarChartDemo} class draws a {@link BarChartComponent} inside a
 * frame. The chart is scalable which means it supports all screen resolutions,
 * including hi-dpi screens. The program expects a single argument: a path to
 * the file which contains the chart data in the following line format: x-axis
 * description, y-axis description, XY coordinates, minimum Y, maximum Y and Y
 * offset.
 * 
 * @author Karlo Bencic
 * 
 */
public class BarChartDemo extends JFrame {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/**
	 * The main method.
	 *
	 * @param args
	 *            the arguments
	 */
	public static void main(String[] args) {
		if (args.length != 1) {
			System.err.println("Expected 1 argument.");
			return;
		}

		String fileName = args[0];
		String filePath;
		List<String> lines = new ArrayList<>();
		try {
			File databaseFile = new File(new BarChartDemo().getClass().getResource("/" + fileName).getFile());
			filePath = databaseFile.getPath();
			lines = Files.readAllLines(Paths.get(databaseFile.toString()));
		} catch (IOException e) {
			System.err.println("Can't open file!");
			return;
		}

		if (lines.size() != 6) {
			System.err.println("Invalid file, was not 6 lines");
			return;
		}

		String xDescription = lines.get(0);
		String yDescription = lines.get(1);
		String[] values = lines.get(2).split(" ");
		List<XYValue> xyValues = new ArrayList<>();

		for (String value : values) {
			String[] coordinates = value.split(",");
			if (coordinates.length != 2) {
				System.err.println("Invalid coordinates, expected x,y");
				return;
			}

			try {
				int x = Integer.parseInt(coordinates[0]);
				int y = Integer.parseInt(coordinates[1]);
				xyValues.add(new XYValue(x, y));
			} catch (NumberFormatException ex) {
				System.err.println("Invalid coordinates format");
				return;
			}
		}

		BarChart model;
		try {
			int yMin = Integer.parseInt(lines.get(3));
			int yMax = Integer.parseInt(lines.get(4));
			int yOffset = Integer.parseInt(lines.get(5));

			model = new BarChart(xyValues, xDescription, yDescription, yMin, yMax, yOffset);
		} catch (NumberFormatException ex) {
			System.err.println("Invalid yMin, yMax, yOffset format");
			return;
		}

		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				new BarChartDemo(model, filePath);
			}
		});
	}

	/**
	 * Instantiates a new bar chart.
	 */
	private BarChartDemo() {
	}

	/**
	 * Instantiates a new bar chart and starts initializing the GUI.
	 *
	 * @param chart
	 *            the bar chart
	 * @param filePath
	 *            the data file path
	 */
	public BarChartDemo(BarChart chart, String filePath) {
		initGUI(chart, filePath);
	}

	/**
	 * Initializes the GUI.
	 *
	 * @param chart
	 *            the bar chart
	 * @param filePath
	 *            the data file path
	 */
	private void initGUI(BarChart chart, String filePath) {
		JFrame barChartFrame = new JFrame();

		barChartFrame.setTitle("Bar Chart");
		barChartFrame.setSize(chart.getValues().size() * 100, 400);
		barChartFrame.setDefaultCloseOperation(DISPOSE_ON_CLOSE);

		BarChartComponent component = new BarChartComponent(chart, filePath);
		barChartFrame.add(component);

		barChartFrame.setVisible(true);
	}
}
