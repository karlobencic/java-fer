package hr.fer.zemris.bf.qmc;

import java.util.Arrays;
import java.util.Collections;
import java.util.Optional;
import java.util.Set;
import java.util.TreeSet;

import hr.fer.zemris.bf.utils.Util;

/**
 * The {@code Mask} class.
 * 
 * @author Karlo Bencic
 * 
 */
public class Mask {

	/** The values. */
	private final byte[] values;

	/** The indexes. */
	private final Set<Integer> indexes;

	/** The dont care. */
	private final boolean dontCare;

	/** The hash code. */
	private int hashCode;

	/** The combined. */
	private boolean combined;

	/** The count of ones. */
	private int countOfOnes;

	/**
	 * Instantiates a new mask.
	 *
	 * @param index
	 *            the index of minterm
	 * @param numberOfVariables
	 *            the number of variables
	 * @param dontCare
	 *            the dont care
	 */
	public Mask(int index, int numberOfVariables, boolean dontCare) {
		if (numberOfVariables < 0) {
			throw new IllegalArgumentException("Number of variables can't be negative.");
		}
		int maxIndex = (int) Math.pow(2, numberOfVariables) - 1;
		if (index < 0 || index > maxIndex) {
			throw new IndexOutOfBoundsException("Index is out of bounds.");
		}

		this.dontCare = dontCare;
		values = Util.indexToByteArray(index, numberOfVariables);
		indexes = new TreeSet<>();
		indexes.add(index);
		calculateHashCode();
		countOnes();
	}

	/**
	 * Instantiates a new mask.
	 *
	 * @param values
	 *            the values table
	 * @param indexes
	 *            the indexes where minterms are present in the values table
	 * @param dontCare
	 *            the dont care
	 */
	public Mask(byte[] values, Set<Integer> indexes, boolean dontCare) {
		if (values == null || indexes == null) {
			throw new IllegalArgumentException("Arguments can't be null.");
		}
		if (indexes.isEmpty()) {
			throw new IllegalArgumentException("Indexes can't be empty.");
		}

		this.values = values.clone();
		this.indexes = new TreeSet<>(indexes);
		this.dontCare = dontCare;
		calculateHashCode();
		countOnes();
	}

	/**
	 * Calculates hash code of the mask by calculating the hash of the values
	 * array.
	 */
	private void calculateHashCode() {
		hashCode = Arrays.hashCode(values);
	}

	/**
	 * Checks if is combined.
	 *
	 * @return true, if is combined
	 */
	public boolean isCombined() {
		return combined;
	}

	/**
	 * Sets the combined flag.
	 *
	 * @param combined
	 *            the new combined
	 */
	public void setCombined(boolean combined) {
		this.combined = combined;
	}

	/**
	 * Checks if this mask is dont care value.
	 *
	 * @return true, if is dont care
	 */
	public boolean isDontCare() {
		return dontCare;
	}

	/**
	 * Gets the indexes of minterms.
	 *
	 * @return the indexes, unmodifiable
	 */
	public Set<Integer> getIndexes() {
		return Collections.unmodifiableSet(indexes);
	}

	/**
	 * Gets the count of ones.
	 *
	 * @return the count of ones
	 */
	public int countOfOnes() {
		return countOfOnes;
	}

	/**
	 * Counts binary ones in the mask.
	 */
	private void countOnes() {
		for (int i = 0; i < values.length; i++) {
			if (values[i] == (byte) 1) {
				countOfOnes++;
			}
		}
	}

	/**
	 * Gets the amount of bits in the mask.
	 *
	 * @return the mask size
	 */
	public int size() {
		return values.length;
	}

	/**
	 * Gets the bit value at {@code position} in the mask.
	 *
	 * @param position
	 *            the position
	 * @return the bit value
	 */
	public byte getValueAt(int position) {
		if (position < 0 || position > values.length - 1) {
			throw new IndexOutOfBoundsException();
		}
		return values[position];
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		return hashCode;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if (obj.hashCode() != hashCode) {
			return false;
		}
		if (!(obj instanceof Mask)) {
			return false;
		}
		Mask other = (Mask) obj;
		return Arrays.equals(values, other.values);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();

		for (byte value : values) {
			if (value == (byte) 2) {
				sb.append('-');
				continue;
			}
			sb.append(value);
		}

		sb.append(dontCare ? " D" : " .");
		sb.append(combined ? " * " : "   ");
		sb.append(indexes.toString());

		return sb.toString();
	}

	/**
	 * Combines this mask with another. In order for masks to be compatible they
	 * should have same values size. There should also be only one change in the
	 * bits. If those conditions are not satisfied, an optional empty object is
	 * returned.
	 *
	 * @param other
	 *            the other mask, not null
	 * @return the combined mask, can be empty
	 */
	public Optional<Mask> combineWith(Mask other) {
		if (other == null) {
			throw new IllegalArgumentException("Mask can't be null.");
		}
		if (other.values.length != values.length) {
			throw new IllegalArgumentException("Mask is not same lenght.");
		}

		for (int i = 0, numOfChanges = 0; i < values.length; i++) {
			if (values[i] == (byte) 2 && other.values[i] != (byte) 2) {
				return Optional.empty();
			}
			if (values[i] != other.values[i]) {
				numOfChanges++;
			}
			if (numOfChanges > 1) {
				return Optional.empty();
			}
		}

		byte[] maskedValues = new byte[values.length];
		Set<Integer> maskedIndexes = new TreeSet<>();
		maskedIndexes.addAll(indexes);
		maskedIndexes.addAll(other.indexes);

		for (int i = 0; i < maskedValues.length; i++) {
			if (values[i] != other.values[i]) {
				maskedValues[i] = (byte) 2;
				continue;
			}
			maskedValues[i] = values[i];
		}

		Mask mask = new Mask(maskedValues, maskedIndexes, dontCare && other.dontCare);
		return Optional.of(mask);
	}
}
