package hr.fer.zemris.cmdapps.jvdraw.color;

import java.awt.Color;

/**
 * The {@code ColorChangeListener} interface is used by classes that have to
 * listen to {@link IColorProvider}.
 * 
 * @author Karlo Bencic
 * 
 */
public interface ColorChangeListener {

	/**
	 * This method will be called when any {@link IColorProvider} changes it's
	 * color.
	 * 
	 * @param source
	 *            source that changed it's color
	 * @param oldColor
	 *            old color
	 * @param newColor
	 *            new color
	 */
	public void newColorSelected(IColorProvider source, Color oldColor, Color newColor);
}