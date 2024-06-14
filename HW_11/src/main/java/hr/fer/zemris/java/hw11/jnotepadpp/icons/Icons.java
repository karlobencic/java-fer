package hr.fer.zemris.java.hw11.jnotepadpp.icons;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.swing.Icon;
import javax.swing.ImageIcon;

/**
 * The {@code Icons} class offers icons as constants.
 * 
 * @author Karlo Bencic
 * 
 */
public class Icons {

	/** The Icon GREEN. */
	public static final Icon GREEN = getIcon("greenDisk.png");

	/** The Icon RED. */
	public static final Icon RED = getIcon("redDisk.png");

	/** The Icon SAVE. */
	public static final Icon SAVE = getIcon("saveFile.png");

	/** The Icon COPY. */
	public static final Icon COPY = getIcon("copy.png");

	/** The Icon NEWFILE. */
	public static final Icon NEWFILE = getIcon("newFile.png");

	/** The Icon SAVEAS. */
	public static final Icon SAVEAS = getIcon("saveAs.png");

	/** The Icon CLOSE. */
	public static final Icon CLOSE = getIcon("closeFile.png");

	/** The Icon CUT. */
	public static final Icon CUT = getIcon("cut.png");

	/** The Icon EXIT. */
	public static final Icon EXIT = getIcon("exit.png");

	/** The Icon INVERT_CASE. */
	public static final Icon INVERT = getIcon("invertCase.png");

	/** The Icon LOWER_CASE. */
	public static final Icon LOWER = getIcon("lowerCase.png");

	/** The Icon UPPER_CASE. */
	public static final Icon UPPER = getIcon("upperCase.png");

	/** The Icon SORT_ASC. */
	public static final Icon SORT_ASC = getIcon("sortAsc.png");

	/** The Icon SORT_DESC. */
	public static final Icon SORT_DESC = getIcon("sortDesc.png");

	/** The Icon PASTE. */
	public static final Icon PASTE = getIcon("paste.png");

	/** The OPEN_EXISTING icon. */
	public static final Icon OPEN_EXISTING = getIcon("openFile.png");

	/** The UNIQUE icon. */
	public static final Icon UNIQUE = getIcon("unique.png");

	/** The STATISTICS icon. */
	public static final Icon STATISTICS = getIcon("stats.png");

	/**
	 * Gets the icon.
	 *
	 * @param name
	 *            the icon name
	 * @return the icon
	 */
	private static Icon getIcon(String name) {
		Icons icons = new Icons();
		try (InputStream is = icons.getClass().getResourceAsStream(name)) {
			byte[] bytes = readAllBytes(is);
			return new ImageIcon(bytes);
		} catch (IOException e) {
			throw new IllegalArgumentException("Invalid icon name.");
		}
	}

	/**
	 * Reads all bytes from the input stream.
	 *
	 * @param is
	 *            the input stream
	 * @return the bytes
	 */
	private static byte[] readAllBytes(InputStream is) {	
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		byte[] buffer = new byte[1024];
		try {
			while (true) {
				int read = is.read(buffer);
				if (read < 1)
					break;
				bos.write(buffer, 0, read);
			}
		} catch (IOException ignorable) { }
		return bos.toByteArray();
	}
}
