package hr.fer.zemris.java.hw01;

import static org.junit.Assert.*;

import org.junit.Test;

import hr.fer.zemris.java.hw01.UniqueNumbers.TreeNode;

public class UniqueNumbersTest {

	@Test
	public void treeSizeEmpty() {
		TreeNode head = null;

		int size = UniqueNumbers.treeSize(head);
		assertEquals(0, size);
	}

	@Test
	public void addDuplicates() {
		TreeNode head = null;
		head = UniqueNumbers.addNode(head, 69);
		head = UniqueNumbers.addNode(head, 360);
		head = UniqueNumbers.addNode(head, 420);
		head = UniqueNumbers.addNode(head, 666);
		head = UniqueNumbers.addNode(head, 1337);
		head = UniqueNumbers.addNode(head, 420);

		int size = UniqueNumbers.treeSize(head);
		assertEquals(5, size);
	}

	@Test
	public void containsNull() {
		TreeNode head = null;

		boolean contains = UniqueNumbers.containsValue(head, 360);
		assertEquals(false, contains);
	}

	@Test
	public void containsSorted() {
		TreeNode head = null;
		head = UniqueNumbers.addNode(head, 360);
		head = UniqueNumbers.addNode(head, 420);
		head = UniqueNumbers.addNode(head, 1337);

		boolean contains = UniqueNumbers.containsValue(head, 360);
		assertEquals(true, contains);
	}

	@Test
	public void containsNonSorted() {
		TreeNode head = null;
		head = UniqueNumbers.addNode(head, 1337);
		head = UniqueNumbers.addNode(head, 360);
		head = UniqueNumbers.addNode(head, 420);

		boolean contains = UniqueNumbers.containsValue(head, 360);
		assertEquals(true, contains);
	}
}
