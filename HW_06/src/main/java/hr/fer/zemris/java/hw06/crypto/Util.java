package hr.fer.zemris.java.hw06.crypto;

/**
 * The Util class offers methods to convert a {@code String} representation of a
 * hexadecimal number to byte array and vice-versa. The bytes are written in
 * big-endian notation.
 * 
 * @author Karlo Bencic
 * 
 */
public class Util {

	/**
	 * Converts a string of hexadecimal digits to a byte array. The string
	 * should be a valid hexadecimal number, i.e. it can only contain the
	 * following symbols: {@code 0123456789ABCDEF}. The text should also be
	 * even-sized, meaning it can't be written as {@code ABC} but {@code 0ABC}.
	 *
	 * @param keyText
	 *            the hex text, not null
	 * @return the byte representation of a given string.
	 * @throws IllegalArgumentException
	 *             if keyText is null or keyText has odd length or keyText
	 *             contains illegal characters
	 */
	public static byte[] hexToByte(String keyText) {
		if (keyText == null) {
			throw new IllegalArgumentException("Argument can't be null.");
		}

		int length = keyText.length();
		if (length % 2 != 0) {
			throw new IllegalArgumentException("Argument has to be even-sized");
		}

		byte[] data = new byte[length / 2];

		for (int i = 0; i < length; i += 2) {
			int high = hexToBinary(keyText.charAt(i));
			int low = hexToBinary(keyText.charAt(i + 1));

			if (high == -1 || low == -1) {
				throw new IllegalArgumentException("Argument contains illegal character");
			}

			data[i / 2] = (byte) (high * 16 + low);
		}

		return data;
	}

	/**
	 * Byte to hex.
	 *
	 * @param data
	 *            the data
	 * @return the string
	 */
	public static String byteToHex(byte[] data) {
		StringBuilder hex = new StringBuilder(data.length * 2);
		for (byte b : data) {
			hex.append(String.format("%02x", b));
		}
		return hex.toString();
	}

	/**
	 * Converts a character to it's binary representation.
	 *
	 * @param c
	 *            the character
	 * @return the binary representation of a character if it's a valid
	 *         hexadecimal character, -1 otherwise
	 */
	private static int hexToBinary(char c) {
		if ('0' <= c && c <= '9') {
			return c - '0';
		}
		if ('A' <= c && c <= 'F') {
			return c - 'A' + 10;
		}
		if ('a' <= c && c <= 'f') {
			return c - 'a' + 10;
		}
		return -1;
	}
}
