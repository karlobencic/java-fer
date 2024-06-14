package hr.fer.zemris.java.custom.scripting.nodes;

import hr.fer.zemris.java.custom.scripting.elems.Element;

/**
 * The class EchoNode represents a single node constructed by the parser. It
 * inherits {@code Node} class.
 * 
 * @author Karlo Bencic
 * 
 */
public class EchoNode extends Node {

	/** The elements. */
	private final Element[] elements;

	/**
	 * Gets the elements.
	 *
	 * @return the elements
	 */
	public Element[] getElements() {
		return elements;
	}

	/**
	 * Instantiates a new echo node.
	 *
	 * @param elements
	 *            the elements
	 */
	public EchoNode(Element[] elements) {
		this.elements = elements;
	}

	@Override
	public String asText() {
		StringBuilder output = new StringBuilder();
		output.append("{$=");
		for (Element element : elements) {
			output.append(element.asText()).append(" ");
		}
		output.append("$}");
		return output.toString();
	}

	@Override
	public void accept(INodeVisitor visitor) {
		visitor.visitEchoNode(this);
	}
}
