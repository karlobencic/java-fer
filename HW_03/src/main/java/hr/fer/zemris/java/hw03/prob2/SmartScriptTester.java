package hr.fer.zemris.java.hw03.prob2;

import java.io.*;
import java.nio.file.Files;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;

import hr.fer.zemris.java.custom.scripting.nodes.DocumentNode;
import hr.fer.zemris.java.custom.scripting.nodes.EchoNode;
import hr.fer.zemris.java.custom.scripting.nodes.ForLoopNode;
import hr.fer.zemris.java.custom.scripting.nodes.Node;
import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParser;
import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParserException;

/**
 * This program represents a simple test for the {@code SmartScriptParser}.
 * Sample texts used for testing can be found in examples folder.
 * 
 * @author Karlo Bencic
 * 
 */
public class SmartScriptTester {

	/**
	 * The main method.
	 *
	 * @param args
	 *            the arguments
	 */
	public static void main(String[] args) {
		String docBody = null;
		try {
			docBody = new String(Files.readAllBytes(Paths.get("examples", "doc1.txt")), StandardCharsets.UTF_8);
		} catch (IOException e1) {
			System.out.println("Unable to open file!");
		}

		SmartScriptParser parser = null;
		try {
			parser = new SmartScriptParser(docBody);
		} catch (SmartScriptParserException e) {
			System.out.println("Unable to parse document!");
			System.exit(-1);
		} catch (Exception e) {
			System.out.println("If this line ever executes, you have failed this class!");
			System.exit(-1);
		}

		DocumentNode document = parser.getDocumentNode();
		String originalDocumentBody = createOriginalDocumentBody(document);
		System.out.printf("ORIGINAL:%n%s%n%n", docBody);
		System.out.printf("RECONSTRUCTED:%n%s", originalDocumentBody);
	}

	/**
	 * Creates the original document body.
	 *
	 * @param document
	 *            the document root
	 * @return the string
	 * @throws IllegalArgumentException
	 *             if document is null
	 */
	public static String createOriginalDocumentBody(DocumentNode document) {
		if (document == null) {
			throw new IllegalArgumentException("Document can't be null.");
		}

		StringBuilder docBody = new StringBuilder();
		getDocumentString(document, docBody);
		return docBody.toString();
	}

	/**
	 * Gets the document string recursively.
	 *
	 * @param parent
	 *            the parent node
	 * @param docBody
	 *            the doc body string
	 * @return the full document string
	 */
	private static void getDocumentString(Node parent, StringBuilder docBody) {
		for (int i = 0, count = parent.numberOfChildren(); i < count; i++) {
			if (parent.getChild(i) instanceof EchoNode) {
				docBody.append("{$=");
			}	
			docBody.append(parent.getChild(i));
			getDocumentString(parent.getChild(i), docBody);

			if (parent.getChild(i) instanceof ForLoopNode) {
				docBody.append("{$ END $}");
			}
			if (parent.getChild(i) instanceof EchoNode) {
				docBody.append("$}");
			}
		}
	}

	/*private String loader(String filename) {
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		try (InputStream is = this.getClass().getClassLoader().getResourceAsStream(filename)) {
			byte[] buffer = new byte[1024];
			while (true) {
				int read = is.read(buffer);
				if (read < 1)
					break;
				bos.write(buffer, 0, read);
			}
			return new String(bos.toByteArray(), StandardCharsets.UTF_8);
		} catch (IOException ex) {
			return null;
		}
	}*/
}
