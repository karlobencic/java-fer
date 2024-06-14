package hr.fer.zemris.java.custom.scripting.nodes;

/**
 * The class DocumentNode represents a single node constructed by the parser. It
 * inherits {@code Node} class. This class is used as a root of all the other
 * nodes.
 * 
 * @author Karlo Bencic
 * 
 */
public class DocumentNode extends Node {

	@Override
	public String asText() {
		StringBuilder output = new StringBuilder();		
		int n = numberOfChildren();
		if (n > 0) {
			for (int i = 0; i < n; i++) {
				output.append(getChild(i).asText());
			}
		}
		return output.toString();
	}

	@Override
	public void accept(INodeVisitor visitor) {
		visitor.visitDocumentNode(this);
	}
}
