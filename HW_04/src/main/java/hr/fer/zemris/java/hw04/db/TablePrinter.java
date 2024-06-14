package hr.fer.zemris.java.hw04.db;

import java.util.ArrayList;

/**
 * The table printer classes takes a matrix of data and prints it.
 * 
 * @author Karlo Bencic
 * 
 */
public class TablePrinter {

	/**
	 * The class Row represents one row of data.
	 * 
	 * @author Karlo Bencic
	 * 
	 */
	private static class Row {
		/** The data. */
		String[] data;

		/**
		 * Instantiates a new row.
		 *
		 * @param data
		 *            the data
		 */
		Row(String[] data) {
			this.data = data;
		}
	}

	/**
	 * The class Col represents one column of data. It contains column header
	 * and max width information
	 * 
	 * @author Karlo Bencic
	 * 
	 */
	private static class Col {
		/** The name. */
		String name;

		/** The max width. */
		int maxWidth;
	}

	/** The columns. */
	private Col[] cols;

	/** The rows. */
	private ArrayList<Row> rows;

	/**
	 * Instantiates a new table printer.
	 *
	 * @param names
	 *            the column names
	 */
	public TablePrinter(String... names) {
		cols = new Col[names.length];
		for (int i = 0; i < cols.length; i++) {
			cols[i] = new Col();
			cols[i].name = names[i];
			cols[i].maxWidth = names[i].length();
		}

		rows = new ArrayList<Row>();
	}

	/**
	 * Adds the row to the print queue.
	 *
	 * @param values
	 *            the values
	 */
	public void addRow(String... values) {
		if (values.length != cols.length) {
			return;
		}

		Row row = new Row(values);
		rows.add(row);
		for (int i = 0; i < values.length; i++) {
			if (values[i].length() > cols[i].maxWidth) {
				cols[i].maxWidth = values[i].length();
			}
		}
	}

	/**
	 * Helper method to make sure column headers and row information are printed
	 * the same.
	 *
	 * @param value
	 *            the value
	 * @param width
	 *            the width
	 */
	private void print(String value, int width) {
		System.out.printf(" %s%s |", value, spaces(width - value.length()));
	}

	/**
	 * Prints the table.
	 */
	public void print() {
		int numOfDashes = cols.length * 3 + 1;
		for (Col col : cols) {
			numOfDashes += col.maxWidth;
		}

		System.out.println(dashes(numOfDashes));
		System.out.print("|");
		for (Col col : cols) {
			print(col.name, col.maxWidth);
		}
		System.out.println("");

		System.out.println(dashes(numOfDashes));
		for (Row row : rows) {
			System.out.print("|");
			int i = 0;
			for (String v : row.data) {
				print(v, cols[i++].maxWidth);
			}
			System.out.println("");
		}
		System.out.println(dashes(numOfDashes));
	}

	/**
	 * Gets the spaces.
	 *
	 * @param i
	 *            the amount of spaces
	 * @return the spaces string
	 */
	private static String spaces(int i) {
		StringBuilder sb = new StringBuilder();
		while (i-- > 0) {
			sb.append(" ");
		}
		return sb.toString();
	}

	/**
	 * Gets the dashes.
	 *
	 * @param i
	 *            the amount of dashes
	 * @return the dashes string
	 */
	private static String dashes(int i) {
		StringBuilder sb = new StringBuilder();

		for (int j = 0; j < i; j++) {
			if (j == 0 || j == i - 1) {
				sb.append("+");
				continue;
			}
			sb.append("=");
		}

		return sb.toString();
	}
}