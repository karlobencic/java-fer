package hr.fer.zemris.cmdapps.jvdraw.color;

import java.awt.Color;

/**
 * The {@code IColorProvider} interface is implemebted by Classes that must have
 * methods for providing it's color.
 * 
 * @author Karlo Bencic
 * 
 */
public interface IColorProvider {

	/**
	 * Gets the current color.
	 * 
	 * @return current color
	 */
	public Color getCurrentColor();

	/**
	 * Adds the given listener to the listeners list.
	 * 
	 * @param l
	 *            listener to add
	 */
	public void addColorChangeListener(ColorChangeListener l);

	/**
	 * Removes the given listener from listeners list.
	 * 
	 * @param l
	 *            listener to remove
	 */
	public void removeColorChangeListener(ColorChangeListener l);

	/**
	 * Checks if is foreground color.
	 *
	 * @return true, if is foreground color
	 */
	public boolean isForegroundColor();
}