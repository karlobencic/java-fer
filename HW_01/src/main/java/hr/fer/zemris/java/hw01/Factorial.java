package hr.fer.zemris.java.hw01;

import java.util.Scanner;

/**
 * This program scans for user input, checks if a given input is an integer and
 * in range [1, 20] calculates the factorial of a provided integer. The program
 * quits by typing "kraj" in the console.
 * 
 * @author Karlo Benčić
 * 
 */
public class Factorial {

	/**
	 * The main method.
	 *
	 * @param args
	 *            the arguments
	 */
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);

		while (true) {
			System.out.print("Unesite broj > ");
			if (sc.hasNextInt()) {
				int number = sc.nextInt();
				if (number < 1 || number > 20) {
					System.out.printf("'%d' nije broj u dozvoljenom rasponu.%n", number);
					continue;
				}
				System.out.printf("%d! = %d%n", number, factorial(number));
			} else {
				String input = sc.next();
				if (input.equals("kraj")) {
					System.out.println("Doviđenja.");
					break;
				}
				System.out.format("'%s' nije cijeli broj%n", input);
			}
		}

		sc.close();
	}

	/**
	 * This method is used to calculate the factorial of an integer.
	 *
	 * @param n
	 *            integer
	 * @return the factorial of n
	 */
	public static long factorial(int n) {
		long fact = 1;
		for (int i = 1; i <= n; i++) {
			fact *= i;
		}
		return fact;
	}
}
