package hr.fer.zemris.java.hw01;

import java.util.Scanner;

/**
 * This program calculates the area and the perimiter of a rectangle using the
 * provided width and height, respectively. It can run with 2 arguments, but can
 * also run without any arguments in which case it scans for user input. Width
 * and height can be non-negative decimal numbers.
 * 
 * @author Karlo Benčić
 * 
 */
public class Rectangle {

	/**
	 * The main method.
	 *
	 * @param args
	 *            the arguments
	 */
	public static void main(String[] args) {
		double width = 0, height = 0;

		if (args.length != 0) {
			if (args.length != 2) {
				System.err.println("Predan je neispravan broj argumenata.");
				return;
			}

			try {
				width = Double.parseDouble(args[0]);
				height = Double.parseDouble(args[1]);
			} catch (NumberFormatException ex) {
				System.err.println("Predan je neispravan argument.");
				return;
			}
		} else {
			Scanner sc = new Scanner(System.in);

			while (width <= 0) {
				System.out.print("Unesite širinu > ");
				width = parseInput(sc.next());
			}
			while (height <= 0) {
				System.out.print("Unesite visinu > ");
				height = parseInput(sc.next());
			}

			sc.close();
		}

		double area = calculateArea(width, height);
		double perimeter = calculatePerimeter(width, height);

		System.out.format("Pravokutnik širine %.1f i visine %.1f ima površinu %.1f te opseg %.1f.", width, height, area,
				perimeter);
	}

	/**
	 * Parses and validates the string provided from user input. The input can
	 * be a non-negative decimal number.
	 *
	 * @param input
	 *            the input string
	 * @return if validation fails, it returns 0, otherwise it returns parsed
	 *         input
	 */
	private static double parseInput(String input) {
		double value = 0;
		try {
			value = Double.parseDouble(input);
			if (value < 0) {
				System.out.println("Unijeli ste negativnu vrijenost.");
			}
		} catch (NumberFormatException ex) {
			System.out.printf("'%s' se ne može protumačiti kao broj.%n", input);
		}
		return value;
	}

	/**
	 * Calculates the area of a rectangle using the provided width and height.
	 *
	 * @param width
	 *            the width
	 * @param height
	 *            the height
	 * @return the area
	 */
	public static double calculateArea(double width, double height) {
		return width * height;
	}

	/**
	 * Calculates the perimeter of a rectangle using the provided width and
	 * height.
	 *
	 * @param width
	 *            the width
	 * @param height
	 *            the height
	 * @return the perimiter
	 */
	public static double calculatePerimeter(double width, double height) {
		return 2 * (width + height);
	}
}
