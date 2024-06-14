package hr.fer.zemris.cmdapps.jvdraw.util;

import java.awt.Color;

/**
 * The {@code Colors} class is a utility class that offers a method to parse the
 * color from RGB string.
 * 
 * @author Karlo Bencic
 *
 */
public class Colors {

	/**
	 * Gets the color that is parsed from a string. String should look like
	 * (r,g,b)
	 * 
	 * @param rgb
	 *            string to parse
	 * @return color represented by this string
	 */
	public static Color getColor(String rgb) {
		String[] args;
		int r = 0, g = 0, b = 0;
		try {
			args = rgb.substring(1, rgb.length() - 1).split(",");
			if (args.length != 3) {
				return Color.BLACK;
			}
			r = Integer.parseInt(args[0]);
			g = Integer.parseInt(args[1]);
			b = Integer.parseInt(args[2]);
		} catch(StringIndexOutOfBoundsException | NumberFormatException ignored) { }
		
		return new Color(r, g, b);
	}

	/**
	 * Converts color to RGB string.
	 *
	 * @param c
	 *            the color
	 * @return the RGB string
	 */
	public static String toRgbString(Color c) {
		return String.format("(%d,%d,%d)", c.getRed(), c.getGreen(), c.getBlue());
	}
}