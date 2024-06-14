package hr.fer.zemris.bf.utils;

import static org.junit.Assert.*;

import org.junit.Test;

import hr.fer.zemris.bf.utils.Util;

public class UtilTests {

	@Test
	public void indexToByteArray() {
		byte[] array1 = Util.indexToByteArray(3, 2);
		byte[] array2 = Util.indexToByteArray(3, 4);
		byte[] array3 = Util.indexToByteArray(3, 6);

		assertArrayEquals(new byte[] { 1, 1 }, array1);
		assertArrayEquals(new byte[] { 0, 0, 1, 1 }, array2);
		assertArrayEquals(new byte[] { 0, 0, 0, 0, 1, 1 }, array3);
	}

	@Test
	public void indexToByteArrayLoss() {
		byte[] array = Util.indexToByteArray(19, 4);

		assertArrayEquals(new byte[] { 0, 0, 1, 1 }, array);
	}

	@Test
	public void indexToByteArrayNegative() {
		byte[] array1 = Util.indexToByteArray(-2, 16);
		byte[] array2 = Util.indexToByteArray(-2, 32);

		assertArrayEquals(new byte[] { 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0 }, array1);
		assertArrayEquals(new byte[] { 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1,
				1, 1, 1, 1, 0 }, array2);
	}

	@Test(expected = IllegalArgumentException.class)
	public void indexToByteArrayInvalid() {
		 Util.indexToByteArray(1, -1);
	}

}
