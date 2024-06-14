package hr.fer.zemris.java.hw01;

import java.util.Scanner;

/**
 * Program that uses the binary tree implenentation of a unique integer list.
 * 
 * @author Karlo Benčić
 * 
 */
public class UniqueNumbers {
	
	/**
	 * A struct that represents a binary tree node.
	 * 
	 * @author Karlo Benčić
	 *
	 */
	static class TreeNode {
		TreeNode left;
		TreeNode right;
		int value;

		TreeNode(int value) {
			this.value = value;
			left = null;
			right = null;
		}
	}

	/**
	 * The main method.
	 *
	 * @param args
	 *            the arguments
	 */
	public static void main(String[] args) {
		TreeNode head = null;
		Scanner sc = new Scanner(System.in);

		while (true) {
			System.out.print("Unesite broj > ");
			if (sc.hasNextInt()) {
				int number = sc.nextInt();
				if (containsValue(head, number)) {
					System.out.println("Broj već postoji. Preskačem.");
					continue;
				}
				head = addNode(head, number);
				System.out.println("Dodano.");
			} else {
				String input = sc.next();
				if (input.equals("kraj")) {
					break;
				}
				System.out.format("'%s' nije cijeli broj%n", input);
			}
		}

		sc.close();

		System.out.printf("Veličina stabla: %d%n", treeSize(head));
		System.out.print("Ispis od najmanjeg: ");
		printLeftToRight(head);
		System.out.printf("%nIspis od najvećeg: ");
		printRightToLeft(head);
	}

	/**
	 * Adds the data to the binary tree that was passed as an argument, sorting
	 * the tree inorder, i.e value on the left is smaller than the value on the
	 * right.
	 *
	 * @param head
	 *            tree head
	 * @param data
	 *            data that has to be inserted
	 * @return newly inserted tree node
	 */
	public static TreeNode addNode(TreeNode head, int data) {
		if (head == null) {
			return new TreeNode(data);
		} else {
			if (!containsValue(head, data)) {
				if (data <= head.value) {
					head.left = addNode(head.left, data);
				} else {
					head.right = addNode(head.right, data);
				}
			}

			return head;
		}
	}

	/**
	 * Calculates the number of nodes in a binary tree.
	 *
	 * @param head
	 *            tree head
	 * @return number of nodes
	 */
	public static int treeSize(TreeNode head) {
		return head == null ? 0 : treeSize(head.left) + treeSize(head.right) + 1;
	}

	/**
	 * Checks if a given integer value is found in a binary tree. The tree does
	 * not have to be ordered.
	 *
	 * @param head
	 *            the head
	 * @param value
	 *            data that is checked
	 * @return true if data is found in the tree, false otherwise
	 */
	public static boolean containsValue(TreeNode head, int value) {
		if (head == null) {
			return false;
		}
		if (value == head.value) {
			return true;
		}
		return containsValue(head.left, value) || containsValue(head.right, value);
	}

	/**
	 * Prints the tree inorder using the standard output.
	 *
	 * @param head
	 *            the tree head
	 */
	private static void printRightToLeft(TreeNode head) {
		if (head == null) {
			return;
		}
		printRightToLeft(head.right);
		System.out.printf("%s ", head.value);
		printRightToLeft(head.left);
	}

	/**
	 * Prints the tree in reversed order using the standard output.
	 *
	 * @param head
	 *            the tree head
	 */
	private static void printLeftToRight(TreeNode head) {
		if (head == null) {
			return;
		}
		printLeftToRight(head.left);
		System.out.printf("%s ", head.value);
		printLeftToRight(head.right);
	}
}
