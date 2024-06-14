package hr.fer.zemris.java.hw04.collections;

import static org.junit.Assert.*;

import java.util.ConcurrentModificationException;
import java.util.Iterator;

import org.junit.Test;

public class SimpleHashtableTest {

	@Test
	public void iteratorRemove() {
		SimpleHashtable<String, Integer> examMarks = getExamMarks();

		Iterator<SimpleHashtable.TableEntry<String, Integer>> iter = examMarks.iterator();
		while (iter.hasNext()) {
			SimpleHashtable.TableEntry<String, Integer> pair = iter.next();
			if (pair.getKey().equals("Ivana")) {
				iter.remove();
			}
		}

		assertEquals(3, examMarks.size());
	}

	@Test(expected = IllegalStateException.class)
	public void iteratorMultipleRemove() {
		SimpleHashtable<String, Integer> examMarks = getExamMarks();

		Iterator<SimpleHashtable.TableEntry<String, Integer>> iter = examMarks.iterator();
		while (iter.hasNext()) {
			SimpleHashtable.TableEntry<String, Integer> pair = iter.next();
			if (pair.getKey().equals("Ivana")) {
				iter.remove();
				iter.remove();
			}
		}
	}

	@Test(expected = ConcurrentModificationException.class)
	public void removeDuringIteration() {
		SimpleHashtable<String, Integer> examMarks = getExamMarks();

		Iterator<SimpleHashtable.TableEntry<String, Integer>> iter = examMarks.iterator();
		while (iter.hasNext()) {
			SimpleHashtable.TableEntry<String, Integer> pair = iter.next();
			if (pair.getKey().equals("Ivana")) {
				examMarks.remove("Ivana");
			}
		}
	}
	
	@Test
	public void tablePut() {
		SimpleHashtable<String, Integer> examMarks = getExamMarks();
		
		examMarks.put("Ana", 1);
		int anaGrade = examMarks.get("Ana");
		assertEquals(1, anaGrade);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void tablePutNull() {
		SimpleHashtable<String, Integer> examMarks = getExamMarks();
		
		examMarks.put(null, 1);
	}
	
	@Test
	public void tableContainsKeyNull() {
		SimpleHashtable<String, Integer> examMarks = getExamMarks();

		assertEquals(false, examMarks.containsKey(null));
	}
	
	@Test
	public void tableContainsValueNull() {
		SimpleHashtable<String, Integer> examMarks = getExamMarks();

		assertEquals(false, examMarks.containsValue(null));
		examMarks.put("NullValue", null);
		assertEquals(true, examMarks.containsValue(null));
		assertEquals(null, examMarks.get("NullValue"));
	}

	@Test
	public void tableGet() {
		SimpleHashtable<String, Integer> examMarks = getExamMarks();
		
		int kristinaGrade = examMarks.get("Kristina");
		assertEquals(5, kristinaGrade);
	}
	
	@Test
	public void tableGetNull() {
		SimpleHashtable<String, Integer> examMarks = getExamMarks();
		
		assertEquals(null, examMarks.get(null));
	}
	
	@Test
	public void tableSize() {
		SimpleHashtable<String, Integer> examMarks = getExamMarks();
		
		assertEquals(4, examMarks.size());
	}
	
	@Test
	public void tableClear() {
		SimpleHashtable<String, Integer> examMarks = getExamMarks();
		
		examMarks.put("Test", 1);
		examMarks.clear();
		assertEquals(0, examMarks.size());
		assertEquals(true, examMarks.isEmpty());
		assertEquals(null, examMarks.get("Test"));
	}
	
	@Test
	public void tableRemove() {
		SimpleHashtable<String, Integer> examMarks = getExamMarks();
		
		examMarks.remove("Ante");
		assertEquals(null, examMarks.get("Ante"));
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void tableInitIllegal() {
		new SimpleHashtable<>(-2);
	}

	private SimpleHashtable<String, Integer> getExamMarks() {
		SimpleHashtable<String, Integer> examMarks = new SimpleHashtable<>(2);
		examMarks.put("Ivana", 2);
		examMarks.put("Ante", 2);
		examMarks.put("Jasna", 2);
		examMarks.put("Kristina", 5);
		examMarks.put("Ivana", 5);
		return examMarks;
	}

}
