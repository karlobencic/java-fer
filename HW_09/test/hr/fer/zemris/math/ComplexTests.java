package hr.fer.zemris.math;

import static org.junit.Assert.*;

import org.junit.Test;

public class ComplexTests {

	private Complex c1 = new Complex(2, 4);
	private Complex c2 = new Complex(2, 2);
	private Complex c3 = new Complex(-4, -10);
	private Complex c4 = new Complex(4, 6);
	private Complex c5 = new Complex(-5, 12);

	@Test
	public void parse() {	
		assertEquals(new Complex(3.51, 0), Complex.parse("3.51"));
		assertEquals(new Complex(-3.17, 0), Complex.parse("-3.17"));
		assertEquals(new Complex(0, 2.71), Complex.parse("2.71i"));
		assertEquals(new Complex(0, -2.72), Complex.parse("-2.72i"));
		assertEquals(new Complex(0, 1), Complex.parse("i"));
		assertEquals(new Complex(1, 0), Complex.parse("1"));
		assertEquals(new Complex(-2.71, -3.15), Complex.parse("-2.71-3.15i"));
	}

	@Test
	public void addTest() {
		assertEquals(new Complex(4, 6), c1.add(c2));
		assertEquals(new Complex(-2, -6), c1.add(c3));
	}
	
	@Test
	public void subTest() {
		assertEquals(new Complex(0, 2), c1.sub(c2));
		assertEquals(new Complex(-2, -2), c1.sub(c4));
	}
	
	@Test
	public void mulTest() {
		assertEquals(new Complex(-4, 12), c1.multiply(c2));
		assertEquals(new Complex(32, -36), c1.multiply(c3));
	}
	
	@Test
	public void divTest() {
		assertEquals(new Complex(1.5, 0.5), c1.divide(c2));
		assertEquals(new Complex(2.5, 0.5), c4.divide(c2));
	}
	
	@Test
	public void powerTest() {
		assertEquals(new Complex(-16, 16), c2.power(3));
		assertEquals(new Complex(-12, 16), c1.power(2));
	}
	
	@Test
	public void rootTest() {
		assertEquals(new Complex(2, 3), c5.root(2).get(0));
		assertEquals(new Complex(-2, -3), c5.root(2).get(1));
		assertEquals(new Complex(2, 3), c5.root(5).get(4));
	}
	
	@Test
	public void reciprocalTest() {
		assertEquals(new Complex(0.25, -0.25), c2.reciprocal());
	}
	
	@Test
	public void magnitudeTest() {
		assertEquals(13, c5.module(), 1E-8);
	}
	
	@Test
	public void angleTest() {
		assertEquals(Math.PI / 4, c2.getAngle(), 1E-8);
	}

}
