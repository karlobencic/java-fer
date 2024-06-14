package hr.fer.zemris.java.custom.scripting.elems;

/**
 * The class ElementFunction represents a single node element. It inherits
 * from {@code Element} class.
 * 
 * @author Karlo Bencic
 * 
 */
public class ElementFunction extends Element {
	
	/** The name. */
	private final String name;
	
	/**
	 * Gets the name.
	 *
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Instantiates a new element function.
	 *
	 * @param name
	 *            the name
	 */
	public ElementFunction(String name) {
		this.name = name;
	}
	
	@Override
	public String asText() {
		return name;
	}
}
