package hr.fer.zemris.java.custom.scripting.elems;

/**
 * The class ElementConstantDouble represents a single node element. It inherits
 * from {@code Element} class.
 * 
 * @author Karlo Bencic
 * 
 */
public class ElementConstantDouble extends Element {

	/** The value. */
	private final double value;

	/**
	 * Gets the value.
	 *
	 * @return the value
	 */
	public double getValue() {
		return value;
	}

	/**
	 * Instantiates a new element constant double.
	 *
	 * @param value
	 *            the value
	 */
	public ElementConstantDouble(double value) {
		super();
		this.value = value;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see hr.fer.zemris.java.custom.scripting.elems.Element#asText()
	 */
	@Override
	public String asText() {
		return String.valueOf(value);
	}
}
