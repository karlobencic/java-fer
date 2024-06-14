package hr.fer.zemris.java.custom.scripting.elems;

/**
 * The class ElementString represents a single node element. It inherits
 * from {@code Element} class.
 * 
 * @author Karlo Bencic
 * 
 */
public class ElementString extends Element {
	
	/** The value. */
	private final String value;
	
	/**
	 * Gets the value.
	 *
	 * @return the value
	 */
	public String getValue() {
		return value;
	}

	/**
	 * Instantiates a new element string.
	 *
	 * @param value
	 *            the value
	 */
	public ElementString(String value) {
		super();
		this.value = value;
	}
	
	/* (non-Javadoc)
	 * @see hr.fer.zemris.java.custom.scripting.elems.Element#asText()
	 */
	@Override
	public String asText() {
		return value;
	}
}
