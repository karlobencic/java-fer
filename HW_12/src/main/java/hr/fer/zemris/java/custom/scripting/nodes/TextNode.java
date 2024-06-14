package hr.fer.zemris.java.custom.scripting.nodes;

/**
 * The class TextNode represents a single node constructed by the parser. It
 * inherits {@code Node} class.
 * 
 * @author Karlo Bencic
 * 
 */
public class TextNode extends Node {

	/** The text. */
	private final String text;

	/**
	 * Gets the text.
	 *
	 * @return the text
	 */
	public String getText() {
		return text;
	}

	/**
	 * Instantiates a new text node.
	 *
	 * @param text
	 *            the text
	 */
	public TextNode(String text) {
		this.text = text;
	}
	
	@Override
	public String asText() {
		return text;
	}
	
	@Override
	public void accept(INodeVisitor visitor) {
		visitor.visitTextNode(this);
	}
}
