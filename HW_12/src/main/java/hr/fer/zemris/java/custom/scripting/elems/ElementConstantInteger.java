package hr.fer.zemris.java.custom.scripting.elems;

/**
 * The class ElementConstantInteger represents a single node element. It inherits
 * from {@code Element} class.
 * 
 * @author Karlo Bencic
 * 
 */
public class ElementConstantInteger extends Element {
	
	/** The value. */
	private final int value;
	
	/**
	 * Gets the value.
	 *
	 * @return the value
	 */
	public int getValue() {
		return value;
	}

	/**
	 * Instantiates a new element constant integer.
	 *
	 * @param value
	 *            the value
	 */
	public ElementConstantInteger(int value) {
		this.value = value;
	}
	
	@Override
	public String asText() {
		return String.valueOf(value);
	}
}
