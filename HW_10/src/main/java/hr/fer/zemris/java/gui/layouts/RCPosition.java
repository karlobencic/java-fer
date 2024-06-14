package hr.fer.zemris.java.gui.layouts;

/**
 * The {@code RCPosition} class defines a slot in the {@link CalcLayout}.
 * 
 * @author Karlo Bencic
 * 
 */
public class RCPosition {

	/** The row. */
	private final int row;

	/** The column. */
	private final int column;

	/**
	 * Instantiates a new RC position.
	 *
	 * @param row
	 *            the row
	 * @param column
	 *            the column
	 */
	public RCPosition(int row, int column) {
		this.row = row;
		this.column = column;
	}

	/**
	 * Gets the row.
	 *
	 * @return the row
	 */
	public int getRow() {
		return row;
	}

	/**
	 * Gets the column.
	 *
	 * @return the column
	 */
	public int getColumn() {
		return column;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + column;
		result = prime * result + row;
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof RCPosition)) {
			return false;
		}
		RCPosition other = (RCPosition) obj;
		if (column != other.column) {
			return false;
		}
		if (row != other.row) {
			return false;
		}
		return true;
	}
}