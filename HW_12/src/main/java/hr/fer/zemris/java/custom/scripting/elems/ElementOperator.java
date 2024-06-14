package hr.fer.zemris.java.custom.scripting.elems;

/**
 * The class ElementOperator represents a single node element. It inherits
 * from {@code Element} class.
 * 
 * @author Karlo Bencic
 * 
 */
public class ElementOperator extends Element {
	
	/** The symbol. */
	private final String symbol;

	/**
	 * Gets the symbol.
	 *
	 * @return the symbol
	 */
	public String getSymbol() {
		return symbol;
	}

	/**
	 * Instantiates a new element operator.
	 *
	 * @param symbol
	 *            the symbol
	 */
	public ElementOperator(String symbol) {
		this.symbol = symbol;
	}

	@Override
	public String asText() {
		return symbol;
	}
}
