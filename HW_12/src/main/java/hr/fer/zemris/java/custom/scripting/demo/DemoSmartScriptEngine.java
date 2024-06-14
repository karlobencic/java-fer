package hr.fer.zemris.java.custom.scripting.demo;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import hr.fer.zemris.java.custom.scripting.exec.SmartScriptEngine;
import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParser;
import hr.fer.zemris.java.webserver.RequestContext;
import hr.fer.zemris.java.webserver.RequestContext.RCCookie;

/**
 * The {@code DemoSmartScriptEngine} class demonstrates the proper functionality
 * of smart script engine. Tests are copied directly from assignment.
 * 
 * @author Karlo Bencic
 * 
 */
public class DemoSmartScriptEngine {

	/**
	 * The main method.
	 *
	 * @param args
	 *            the arguments
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	public static void main(String[] args) throws IOException {
		test1();
		System.out.printf("%n-------------------------------------------------%n");
		test2();
		System.out.printf("%n-------------------------------------------------%n");
		test3();
		System.out.printf("%n-------------------------------------------------%n");
		test4();
	}

	/**
	 * Test 1.
	 *
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	private static void test1() throws IOException {
		String filepath = "webroot/scripts/osnovni.smscr";
		String documentBody = new String(Files.readAllBytes(Paths.get(filepath)), StandardCharsets.UTF_8);

		Map<String, String> parameters = new HashMap<>();
		Map<String, String> persistentParameters = new HashMap<>();
		List<RCCookie> cookies = new ArrayList<>();
		parameters.put("broj", "4");

		new SmartScriptEngine(new SmartScriptParser(documentBody).getDocumentNode(),
				new RequestContext(System.out, parameters, persistentParameters, cookies)).execute();
	}

	/**
	 * Test 2.
	 *
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	private static void test2() throws IOException {
		String filepath = "webroot/scripts/zbrajanje.smscr";
		String documentBody = new String(Files.readAllBytes(Paths.get(filepath)), StandardCharsets.UTF_8);

		Map<String, String> parameters = new HashMap<>();
		Map<String, String> persistentParameters = new HashMap<>();
		List<RCCookie> cookies = new ArrayList<>();
		parameters.put("a", "4");
		parameters.put("b", "2");

		new SmartScriptEngine(new SmartScriptParser(documentBody).getDocumentNode(),
				new RequestContext(System.out, parameters, persistentParameters, cookies)).execute();
	}

	/**
	 * Test 3.
	 *
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	private static void test3() throws IOException {
		String filepath = "webroot/scripts/brojPoziva.smscr";
		String documentBody = new String(Files.readAllBytes(Paths.get(filepath)), StandardCharsets.UTF_8);

		Map<String, String> parameters = new HashMap<>();
		Map<String, String> persistentParameters = new HashMap<>();
		List<RCCookie> cookies = new ArrayList<>();
		persistentParameters.put("brojPoziva", "3");

		RequestContext rc = new RequestContext(System.out, parameters, persistentParameters, cookies);
		new SmartScriptEngine(new SmartScriptParser(documentBody).getDocumentNode(), rc).execute();

		System.out.println();
		System.out.println("Vrijednost u mapi: " + rc.getPersistentParameter("brojPoziva"));
	}

	/**
	 * Test 4.
	 *
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	private static void test4() throws IOException {
		String filepath = "webroot/scripts/fibonacci.smscr";
		String documentBody = new String(Files.readAllBytes(Paths.get(filepath)), StandardCharsets.UTF_8);

		Map<String, String> parameters = new HashMap<>();
		Map<String, String> persistentParameters = new HashMap<>();
		List<RCCookie> cookies = new ArrayList<>();

		new SmartScriptEngine(new SmartScriptParser(documentBody).getDocumentNode(),
				new RequestContext(System.out, parameters, persistentParameters, cookies)).execute();
	}
}