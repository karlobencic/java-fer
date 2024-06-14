package hr.fer.zemris.java.hw02;

import static org.junit.Assert.*;

import org.junit.Test;

import hr.fer.zemris.java.custom.collections.LinkedListIndexedCollection;

public class LinkedListIndexedCollectionTest {
	
	private LinkedListIndexedCollection[] cols = new LinkedListIndexedCollection[] {
			new LinkedListIndexedCollection(),
			new LinkedListIndexedCollection(new LinkedListIndexedCollection()),
	};
	
	@Test
	public void collectionInit() {
		for(LinkedListIndexedCollection col : cols) {
			assertEquals(0, col.size());
			assertEquals(true, col.isEmpty());
		}
	}

	@Test
	public void collectionCreateFromOther() {
		for(LinkedListIndexedCollection col : cols) {
			final LinkedListIndexedCollection col1 = col;
			col1.add(1);
			LinkedListIndexedCollection col2 = new LinkedListIndexedCollection(col1);

			assertEquals(1, col2.size());
			assertEquals(true, col2.contains(1));
		}				
	}

	@Test
	public void collectionAddWithSize() {
		for(LinkedListIndexedCollection col : cols) {
			col.add(69);
			col.add("aw");
			col.add(Math.PI);

			assertEquals(3, col.size());
		}	
	}

	@Test
	public void collectionContains() {
		for(LinkedListIndexedCollection col : cols) {
			col.add(69);
			col.add("aw");
			col.add(Math.PI);

			assertEquals(true, col.contains(69));
			assertEquals(true, col.contains("aw"));
			assertEquals(true, col.contains(Math.PI));
			assertEquals(false, col.contains(Math.E));
		}	
	}

	@Test
	public void collectionInsert() {
		for(LinkedListIndexedCollection col : cols) {
			col.add(69);
			col.add(360);
			col.add(420);
			col.insert(666, 1);
			
			assertEquals(true, col.contains(666));
			assertEquals(true, col.contains(360));
			assertEquals(4, col.size());
			assertEquals(1, col.indexOf(666));
			assertEquals(2, col.indexOf(360));
		}	
	}

	@Test
	public void collectionIndexOf() {
		for(LinkedListIndexedCollection col : cols) {
			col.add(69);
			col.add(360);
			col.add(420);
			col.add(666);
			col.add(1337);

			assertEquals(1, col.indexOf(360));
			assertEquals(4, col.indexOf(1337));
		}	
	}

	@Test
	public void collectionRemove() {
		for(LinkedListIndexedCollection col : cols) {
			col.add("69");
			col.add("360");
			col.add("420");
			col.add("666");
			col.add("1337");
			col.remove(0);
			col.remove("666");

			assertEquals(false, col.contains("69"));
			assertEquals(false, col.contains("666"));
			assertEquals(3, col.size());
		}	
	}

	@Test
	public void collectionAddAll() {
		LinkedListIndexedCollection col1 = new LinkedListIndexedCollection();
		LinkedListIndexedCollection col2 = new LinkedListIndexedCollection();

		col1.add(69);
		col1.add("aw");
		col1.add(Math.PI);
		col2.addAll(col1);

		assertEquals(3, col2.size());
		assertEquals(true, col2.contains("aw"));
	}

	@Test
	public void collectionClear() {
		for(LinkedListIndexedCollection col : cols) {
			col.add(69);
			col.add(360);
			col.clear();

			assertEquals(true, col.isEmpty());
			assertEquals(false, col.contains(69));
		}	
	}

	@Test
	public void collectionGet() {
		for(LinkedListIndexedCollection col : cols) {
			final int value = 420;
			col.add(value);
			col.add(1337);
			int newValue = (int) col.get(0);

			assertEquals(true, value == newValue);
		}	
	}
	

	@Test
	public void collectionNull() {
		for(LinkedListIndexedCollection col : cols) {
			try {
				col.add(null);
				fail();
			} catch (IllegalArgumentException e) {	
			}
			
			try {
				col.insert(null, 0);
				fail();
			} catch (IllegalArgumentException e) {	
			}

			col.addAll(null);		
			
			assertEquals(-1, col.indexOf(null));
			assertEquals(false, col.contains(null));
			assertEquals(false, col.remove(null));
			assertEquals(true, col.isEmpty());
		}	
	}
	
	@Test
	public void collectionIllegalArguments() {
		LinkedListIndexedCollection col = new LinkedListIndexedCollection();
		
		try {
			col.remove(-5);
			col.remove(1337);
			fail();
		} catch (IndexOutOfBoundsException e) {	
		}
		
		try {
			col.insert("aw", -2);
			col.insert("lol", -420);
			fail();
		} catch (IndexOutOfBoundsException e) {	
		}
		
		try {
			col.get(-22);
			col.get(22);
			fail();
		} catch (IndexOutOfBoundsException e) {	
		}
		
		assertEquals(true, col.isEmpty());
	}
}
