package hr.fer.zemris.java.hw06.crypto;

import static org.junit.Assert.*;

import org.junit.Test;

public class UtilTests {

	@Test
    public void testHexToByte() {
		assertArrayEquals(new byte[] {1, -82, 34}, Util.hexToByte("01aE22"));
    }
	
	@Test
    public void testByteToHex() {
		assertEquals("01ae22", Util.byteToHex(new byte[] {1, -82, 34}));
    }
	
	@Test
    public void emptyByteToHex() {
		assertEquals("", Util.byteToHex(new byte[] {}));
    }
	
	@Test(expected = IllegalArgumentException.class)
    public void hexToByteOddLenght() {
		Util.hexToByte("123");
    }
	
	@Test(expected = IllegalArgumentException.class)
    public void hexToByteInvalidChar() {
		Util.hexToByte("1337no_scope");
    }
	
	@Test(expected = IllegalArgumentException.class)
    public void hexToByteX() {
		Util.hexToByte("69x420");
    }
	
	@Test
    public void hexToByteZeroLenght() {
		assertArrayEquals(new byte[] {}, Util.hexToByte(""));
    }

}
