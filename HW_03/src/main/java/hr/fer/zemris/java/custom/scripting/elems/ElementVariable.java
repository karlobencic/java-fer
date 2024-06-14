package hr.fer.zemris.java.custom.scripting.elems;

/**
 * The class ElementVariable represents a single node element. It inherits
 * from {@code Element} class.
 * 
 * @author Karlo Bencic
 * 
 */
public class ElementVariable extends Element {
	
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
	 * Instantiates a new element variable.
	 *
	 * @param name
	 *            the name
	 */
	public ElementVariable(String name) {
		super();
		this.name = name;
	}
	
	/* (non-Javadoc)
	 * @see hr.fer.zemris.java.custom.scripting.elems.Element#asText()
	 */
	@Override
	public String asText() {
		return name;
	}
}
