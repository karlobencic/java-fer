package hr.fer.zemris.java.hw02;

import static org.junit.Assert.*;

import org.junit.Test;

import hr.fer.zemris.java.hw02.ComplexNumber;

public class ComplexNumberTest {
	
	private ComplexNumber c1 = new ComplexNumber(2, 4);
	private ComplexNumber c2 = new ComplexNumber(2, 2);
	private ComplexNumber c3 = new ComplexNumber(-4, -10);
	private ComplexNumber c4 = new ComplexNumber(4, 6);
	private ComplexNumber c5 = new ComplexNumber(-5, 12);

	@Test
	public void parse() {	
		assertEquals(ComplexNumber.fromReal(3.51), ComplexNumber.parse("3.51"));
		assertEquals(ComplexNumber.fromReal(-3.17), ComplexNumber.parse("-3.17"));
		assertEquals(ComplexNumber.fromImaginary(2.71), ComplexNumber.parse("2.71i"));
		assertEquals(ComplexNumber.fromImaginary(-2.72), ComplexNumber.parse("-2.72i"));
		assertEquals(ComplexNumber.fromImaginary(1), ComplexNumber.parse("i"));
		assertEquals(ComplexNumber.fromReal(1), ComplexNumber.parse("1"));
		assertEquals(new ComplexNumber(-2.71, -3.15), ComplexNumber.parse("-2.71-3.15i"));
	}

	@Test
	public void addTest() {
		assertEquals(new ComplexNumber(4, 6), c1.add(c2));
		assertEquals(new ComplexNumber(-2, -6), c1.add(c3));
	}
	
	@Test
	public void subTest() {
		assertEquals(new ComplexNumber(0, 2), c1.sub(c2));
		assertEquals(new ComplexNumber(-2, -2), c1.sub(c4));
	}
	
	@Test
	public void mulTest() {
		assertEquals(new ComplexNumber(-4, 12), c1.mul(c2));
		assertEquals(new ComplexNumber(32, -36), c1.mul(c3));
	}
	
	@Test
	public void divTest() {
		assertEquals(new ComplexNumber(1.5, 0.5), c1.div(c2));
		assertEquals(new ComplexNumber(2.5, 0.5), c4.div(c2));
	}
	
	@Test
	public void powerTest() {
		assertEquals(new ComplexNumber(-16, 16), c2.power(3));
		assertEquals(new ComplexNumber(-12, 16), c1.power(2));
	}
	
	@Test
	public void rootTest() {
		assertEquals(new ComplexNumber(2, 3), c5.root(2)[0]);
		assertEquals(new ComplexNumber(-2, -3), c5.root(2)[1]);
		assertEquals(new ComplexNumber(2, 3), c5.root(5)[4]);
	}
	
	@Test
	public void reciprocalTest() {
		assertEquals(new ComplexNumber(0.25, -0.25), c2.reciprocal());
	}
	
	@Test
	public void magnitudeTest() {
		assertEquals(13, c5.getMagnitude(), 1E-8);
	}
	
	@Test
	public void angleTest() {
		assertEquals(Math.PI / 4, c2.getAngle(), 1E-8);
	}
}
