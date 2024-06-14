package hr.fer.zemris.math;

/**
 * The {@code Vector3} class is an implementation of a 3-dimensional vector.
 * This structure is used to pass 3D positions and directions around. It also
 * contains functions for doing common vector operations.
 * 
 * @author Karlo Bencic
 */
public class Vector3 {

	/** The x value. */
	private double x;

	/** The y value. */
	private double y;

	/** The z value. */
	private double z;

	/**
	 * Instantiates a new vector 3.
	 *
	 * @param x
	 *            the x value
	 * @param y
	 *            the y value
	 * @param z
	 *            the z value
	 */
	public Vector3(double x, double y, double z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}

	/**
	 * Gets the normalized value of this vector.
	 *
	 * @return the normalized value
	 */
	public double norm() {
		return Math.sqrt(x * x + y * y + z * z);
	}

	/**
	 * Gets the normalized vector.
	 *
	 * @return the normalized vector
	 */
	public Vector3 normalized() {
		double d = norm();
		return new Vector3(x / d, y / d, z / d);
	}

	/**
	 * Adds another vector to this vector. Returns a new vector and does not
	 * modify current vector.
	 *
	 * @param other
	 *            the other vector
	 * @return the new added vector
	 */
	public Vector3 add(Vector3 other) {
		return new Vector3(x + other.x, y + other.y, z + other.z);
	}

	/**
	 * Substracts another vector from this vector. Returns a new vector and does
	 * not modify current vector.
	 *
	 * @param other
	 *            the other vector
	 * @return the new substracted vector
	 */
	public Vector3 sub(Vector3 other) {
		return new Vector3(x - other.x, y - other.y, z - other.z);
	}

	/**
	 * Returns the dot product of this vector and other vector.
	 *
	 * @param other
	 *            the other vector
	 * @return the dot product
	 */
	public double dot(Vector3 other) {
		return (x * other.x) + (y * other.y) + (z * other.z);
	}

	/**
	 * Returns the cross product of this vector and other vector.
	 *
	 * @param other
	 *            the other vector
	 * @return the cross product
	 */
	public Vector3 cross(Vector3 other) {
		double newX = (y * other.z) - (z * other.y);
		double newY = (z * other.x) - (x * other.z);
		double newZ = (x * other.y) - (y * other.x);

		return new Vector3(newX, newY, newZ);
	}

	/**
	 * Returns a new vector scaled by {@code s}.
	 *
	 * @param s
	 *            the scale
	 * @return the new scaled vector
	 */
	public Vector3 scale(double s) {
		return new Vector3(x * s, y * s, z * s);
	}

	/**
	 * Gets the cosinus angle between this vector and other vector.
	 *
	 * @param other
	 *            the other vector
	 * @return the cosinus angle
	 */
	public double cosAngle(Vector3 other) {
		return dot(other) / (norm() * other.norm());
	}

	/**
	 * Gets the x value.
	 *
	 * @return the x
	 */
	public double getX() {
		return x;
	}

	/**
	 * Gets the y value.
	 *
	 * @return the y
	 */
	public double getY() {
		return y;
	}

	/**
	 * Gets the z value.
	 *
	 * @return the z
	 */
	public double getZ() {
		return z;
	}

	/**
	 * Converts this vector to double array of three elements: x, y, z
	 *
	 * @return the double[] array
	 */
	public double[] toArray() {
		return new double[] { x, y, z };
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return String.format("(%f, %f, %f)", x, y, z);
	}
}
