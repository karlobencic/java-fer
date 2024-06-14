package hr.fer.zemris.java.hw04.db;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class StudentDatabaseTest {

	@Test
	public void forJmbagTrue() {
		StudentDatabase database = initDatabase();

		assertEquals(database.size(), database.filter(s -> s.getFinalGrade() > 0).size());
	}

	@Test
	public void forJmbagFalse() {
		StudentDatabase database = initDatabase();

		assertEquals(0, database.filter(s -> s.getFinalGrade() < 0).size());
	}

	@Test(expected = IllegalArgumentException.class)
	public void initNull() {
		new StudentDatabase(null);
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
