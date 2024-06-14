package hr.fer.zemris.java.hw04.db;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.List;

import org.junit.Test;

public class QueryFilterTest {

	@Test
	public void testParseSingle() {
		StudentDatabase db = initDatabase();

		QueryParser parser = new QueryParser("lastName LIKE \"B*\"");
		for (StudentRecord r : db.filter(new QueryFilter(parser.getQuery()))) {
			assertEquals(true, r.getLastName().startsWith("B"));
		}
	}
	
	@Test
	public void testParseDirect() {
		StudentDatabase db = initDatabase();

		QueryParser parser = new QueryParser("jmbag = \"0000000003\"");
		for (StudentRecord r : db.filter(new QueryFilter(parser.getQuery()))) {
			assertEquals("0000000003", r.getJmbag());
		}
	}
	
	@Test
	public void testParseMultiple() {
		StudentDatabase db = initDatabase();

		QueryParser parser = new QueryParser("jmbag = \"0000000003\" AND lastName LIKE \"B*\"");
		for (StudentRecord r : db.filter(new QueryFilter(parser.getQuery()))) {
			assertEquals("0000000003", r.getJmbag());
		}
	}
	
	@Test
	public void testFilter() {
		StudentDatabase db = initDatabase();

		QueryParser parser = new QueryParser("lastName LIKE \"B*\"");
		assertEquals(4, db.filter(new QueryFilter(parser.getQuery())).size());
	}

	private StudentDatabase initDatabase() {
		List<String> data = new ArrayList<>();
		
		try {
			File databaseFile = new File(this.getClass().getResource("/database.txt").getFile());
			data = Files.readAllLines(Paths.get(databaseFile.toString()));
		} catch (IOException e) {
			fail("Can't open database file!");
		}
		
		return new StudentDatabase(data);
	}
}
