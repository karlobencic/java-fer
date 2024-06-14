package hr.fer.zemris.java.hw04.db;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class FieldValueGettersTest {

	@Test
	public void testFirstName() {
		StudentRecord record = getSomehowOneRecord();

		assertEquals("Marin", FieldValueGetters.FIRST_NAME.get(record));
	}
	
	@Test
	public void testLastName() {
		StudentRecord record = getSomehowOneRecord();

		assertEquals("Akšamović", FieldValueGetters.LAST_NAME.get(record));
	}
	
	@Test
	public void testJmbag() {
		StudentRecord record = getSomehowOneRecord();

		assertEquals("0000000001", FieldValueGetters.JMBAG.get(record));
	}

	private StudentRecord getSomehowOneRecord() {
		List<String> data = new ArrayList<>();

		try {
			File databaseFile = new File(this.getClass().getResource("/database.txt").getFile());
			data = Files.readAllLines(Paths.get(databaseFile.toString()));
		} catch (IOException e) {
			fail("Can't open database file!");
		}

		StudentDatabase database = new StudentDatabase(data);
		return database.forJMBAG("0000000001");
	}

}
