package hr.fer.zemris.math;

import static org.junit.Assert.*;

import org.junit.Test;

public class Vector3Tests {
	
	private static final double DELTA = 0.00001;

	private static final double X = 2;
	private static final double Y = 3;
	private static final double Z = 4;

	@Test
	public void testNorm() {
		Vector3 a = new Vector3(X, Y, Z);

		double normA = a.norm();
		double expected = Math.sqrt(X * X + Y * Y + Z * Z);
		assertEquals(expected, normA, DELTA);
	}

	@Test
	public void testNormalized() {
		Vector3 a = new Vector3(X, Y, Z);
		
		Vector3 normalized = a.normalized();
		double norm = a.norm();
		
		assertEquals(X / norm, normalized.getX(), DELTA);
		assertEquals(Y / norm, normalized.getY(), DELTA);
		assertEquals(Z / norm, normalized.getZ(), DELTA);
	}

	@Test
	public void testAdd() {
		Vector3 a = new Vector3(X, Y, Z);
		Vector3 b = new Vector3(-X, -Y, -Z);

		Vector3 c = a.add(b);
		assertEquals(0.0, c.getX(), 0.0);
		assertEquals(0.0, c.getY(), 0.0);
		assertEquals(0.0, c.getZ(), 0.0);
	}

	@Test
	public void testSub() {
		Vector3 a = new Vector3(X, Y, Z);
		Vector3 b = new Vector3(-X, -Y, -Z);

		Vector3 c = a.sub(b);
		assertEquals(2 * X, c.getX(), DELTA);
		assertEquals(2 * Y, c.getY(), DELTA);
		assertEquals(2 * Z, c.getZ(), DELTA);
	}

	@Test
	public void testDot() {
		Vector3 a = new Vector3(X, Y, Z);
		Vector3 b = new Vector3(-X, -Y, -Z);
		Vector3 c = new Vector3(0, 0, 0);

		double result = a.dot(b);
		assertEquals((-X * X - Y * Y - Z * Z), result, DELTA);

		result = a.dot(c);
		assertEquals(0.0, result, DELTA);
	}

	@Test
	public void testCross() {
		Vector3 a = new Vector3(X, Y, Z);
		Vector3 b = new Vector3(-X, -Y, -Z);
		
		Vector3 c = a.cross(b);
		
		assertEquals(0, c.getX(), DELTA);
		assertEquals(0, c.getY(), DELTA);
		assertEquals(0, c.getZ(), DELTA);
	}

	@Test
	public void testScale() {
		Vector3 a = new Vector3(X, Y, Z);
		Vector3 b = a.scale(2);
		
		assertEquals(X * 2, b.getX(), DELTA);
		assertEquals(Y * 2, b.getY(), DELTA);
		assertEquals(Z * 2, b.getZ(), DELTA);
	}

	@Test
	public void testCosAngle() {
		Vector3 a = new Vector3(X, Y, Z);
		Vector3 b = new Vector3(-X, -Y, -Z);
		
		double angle = a.cosAngle(b);
		
		assertEquals(-1, angle, DELTA);
	}

}
