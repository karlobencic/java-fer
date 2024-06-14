package hr.fer.zemris.java.hw03.prob2;

import static org.junit.Assert.*;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.junit.Test;

import hr.fer.zemris.java.custom.scripting.nodes.DocumentNode;
import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParser;

public class ParserTest {

	@Test
	public void testDocumentSize() {
		String docBody = null;
		try {
			docBody = new String(Files.readAllBytes(Paths.get("examples", "doc1.txt")), StandardCharsets.UTF_8);
		} catch (IOException e1) {
			System.out.println("Unable to open file!");
		}

		SmartScriptParser parser = new SmartScriptParser(docBody);
		DocumentNode document = parser.getDocumentNode();
		String originalDocumentBody = SmartScriptTester.createOriginalDocumentBody(document);
		SmartScriptParser parser2 = new SmartScriptParser(originalDocumentBody);
		DocumentNode document2 = parser2.getDocumentNode();
		
		assertEquals(document.numberOfChildren(), document2.numberOfChildren());
	}
	
	@Test
	public void testDocumentChildren() {
		String docBody = null;
		try {
			docBody = new String(Files.readAllBytes(Paths.get("examples", "doc1.txt")), StandardCharsets.UTF_8);
		} catch (IOException e1) {
			System.out.println("Unable to open file!");
		}

		SmartScriptParser parser = new SmartScriptParser(docBody);
		DocumentNode document = parser.getDocumentNode();
		String originalDocumentBody = SmartScriptTester.createOriginalDocumentBody(document);
		SmartScriptParser parser2 = new SmartScriptParser(originalDocumentBody);
		DocumentNode document2 = parser2.getDocumentNode();
		
		for (int i = 0, count = document.numberOfChildren(); i < count; i++) {
			assertEquals(document.getChild(i).numberOfChildren(), document2.getChild(i).numberOfChildren());
		}
	}
}
