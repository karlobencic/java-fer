package hr.fer.zemris.bf.qmc;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.Optional;
import java.util.Set;
import java.util.TreeSet;

import org.junit.Test;

public class MaskTests {

	private byte[] values = new byte[] { 1, 1, 0, 1 };
	private Set<Integer> indexes = new TreeSet<Integer>(Arrays.asList(13));
	private boolean dontCare;

	@Test
	public void testImmutable() {
		Mask mask = new Mask(values, indexes, dontCare);

		indexes.add(14);
		values = new byte[] { 1, 1, 1, 1 };

		assertEquals(1, mask.getIndexes().size());
		assertEquals(3, mask.countOfOnes());
	}

	@Test(expected = IllegalArgumentException.class)
	public void testInvalidCtor1() {
		new Mask(null, null, false);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testInvalidCtor2() {
		new Mask(new byte[1], new TreeSet<>(), false);
	}

	@Test(expected = IndexOutOfBoundsException.class)
	public void testInvalidCtor3() {
		new Mask(1, 1, false);
		new Mask(-1, 1, false);
	}

	@Test
	public void testToString() {
		byte[] values2 = new byte[] { 0, 1, 2, 0 };
		Set<Integer> indexes2 = new TreeSet<>();
		indexes2.add(4);
		indexes2.add(6);

		Mask mask = new Mask(values, indexes, dontCare);
		mask.setCombined(true);

		Mask mask2 = new Mask(values2, indexes2, !dontCare);

		assertEquals("1101 . * [13]", mask.toString());
		assertEquals("01-0 D   [4, 6]", mask2.toString());
	}

	@Test
	public void testEquals() {
		Mask mask1 = new Mask(values, indexes, dontCare);
		Mask mask2 = new Mask(values, indexes, dontCare);

		assertEquals(true, mask1.equals(mask2));
	}

	@Test
	public void testCountOfOnes() {
		Mask mask = new Mask(values, indexes, dontCare);

		assertEquals(3, mask.countOfOnes());
	}

	@Test
	public void testCombine() {
		byte[] values1 = new byte[] { 0, 2, 0, 1 };
		byte[] values2 = new byte[] { 0, 2, 0, 0 };
		Set<Integer> indexes1 = new TreeSet<Integer>();
		indexes1.add(1);
		indexes1.add(5);

		Set<Integer> indexes2 = new TreeSet<Integer>();
		indexes2.add(0);
		indexes2.add(4);

		Mask mask1 = new Mask(values1, indexes1, dontCare);
		Mask mask2 = new Mask(values2, indexes2, dontCare);

		Optional<Mask> mask3 = mask1.combineWith(mask2);
		assertEquals(0, mask3.get().countOfOnes());
		assertEquals(indexes1.size() + indexes2.size(), mask3.get().getIndexes().size());
	}

	@Test(expected = IllegalArgumentException.class)
	public void testCombineInvalid() {
		Mask mask1 = new Mask(values, indexes, dontCare);
		mask1.combineWith(null);
	}

	@Test()
	public void testCombineNotCompatible() {
		Mask mask1 = new Mask(values, indexes, dontCare);
		
		byte[] values2 = new byte[] { 0, 0, 1, 1 };
		Set<Integer> indexes2 = new TreeSet<Integer>();
		indexes2.add(3);
		
		Mask mask2 = new Mask(values2, indexes2, !dontCare);
		Optional<Mask> mask3 = mask1.combineWith(mask2);
		
		assertEquals(Optional.empty(), mask3);
	}
}
