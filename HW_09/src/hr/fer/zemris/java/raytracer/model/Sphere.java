package hr.fer.zemris.java.raytracer.model;

/**
 * The {@code Sphere} class represents a graphical object for raycasting and
 * raytracing. It has it's center, radius, diffuse color components and
 * reflective color components.
 * 
 * @author Karlo Bencic
 * 
 */
public class Sphere extends GraphicalObject {

	/** The sphere center. */
	private Point3D center;

	/** The sphere radius. */
	private double radius;

	/** The red diffuse compoment. */
	private double kdr;

	/** The green diffuse compoment. */
	private double kdg;

	/** The blue diffuse compoment. */
	private double kdb;

	/** The red reflective component. */
	private double krr;

	/** The green reflective component. */
	private double krg;

	/** The blue reflective component. */
	private double krb;

	/** The exponent to power cos(n) in Phong illumination model. */
	private double krn;

	/**
	 * Instantiates a new sphere.
	 *
	 * @param center
	 *            the sphere center
	 * @param radius
	 *            the sphere radius
	 * @param kdr
	 *            the red diffuse compoment
	 * @param kdg
	 *            the reen diffuse compoment
	 * @param kdb
	 *            the blue diffuse compoment
	 * @param krr
	 *            the red reflective component
	 * @param krg
	 *            the green reflective component
	 * @param krb
	 *            the blue reflective component
	 * @param krn
	 *            the exponent to power cos(n) in Phong illumination model
	 */
	public Sphere(Point3D center, double radius, double kdr, double kdg, double kdb, double krr, double krg, double krb,
			double krn) {
		this.center = center;
		this.radius = radius;
		this.kdr = kdr;
		this.kdg = kdg;
		this.kdb = kdb;
		this.krr = krr;
		this.krg = krg;
		this.krb = krb;
		this.krn = krn;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see hr.fer.zemris.java.raytracer.model.GraphicalObject#
	 * findClosestRayIntersection(hr.fer.zemris.java.raytracer.model.Ray)
	 */
	public RayIntersection findClosestRayIntersection(Ray ray) {
		Point3D start = ray.start;
		Point3D vertical = start.sub(center);
		Point3D direction = ray.direction;

		double[] solutions = getSolutions(vertical, direction);
		if (solutions == null) {
			return null;
		}

		if (solutions[0] <= 0 && solutions[1] <= 0) {
			return null;
		}

		Point3D intersection1 = start.add(direction.scalarMultiply(solutions[0]));
		Point3D intersection2 = start.add(direction.scalarMultiply(solutions[1]));

		double intersection1Distance = intersection1.sub(start).norm();
		double intersection2Distance = intersection2.sub(start).norm();

		Point3D closerIntersection = intersection1;
		double closerIntersectionDistance = intersection1Distance;
		if (intersection1Distance > intersection2Distance) {
			closerIntersection = intersection2;
			closerIntersectionDistance = intersection2Distance;
		}

		boolean isOuterIntersection = closerIntersection.sub(center).norm() > radius;

		RayIntersection closestIntersection = new RayIntersection(closerIntersection, closerIntersectionDistance,
				isOuterIntersection) {

			@Override
			public Point3D getNormal() {
				return getPoint().sub(center).normalize();
			}

			@Override
			public double getKdr() {
				return kdr;
			}

			@Override
			public double getKdg() {
				return kdg;
			}

			@Override
			public double getKdb() {
				return kdb;
			}

			@Override
			public double getKrr() {
				return krr;
			}

			@Override
			public double getKrg() {
				return krg;
			}

			@Override
			public double getKrb() {
				return krb;
			}

			@Override
			public double getKrn() {
				return krn;
			}
		};

		return closestIntersection;
	}

	/**
	 * Gets the solutions of the quadratic equation.
	 *
	 * @param vertical
	 *            the ray vertical
	 * @param direction
	 *            the ray direction
	 * @return the solutions
	 */
	private double[] getSolutions(Point3D vertical, Point3D direction) {
		double A = direction.scalarProduct(direction);
		double B = vertical.scalarMultiply(2).scalarProduct(direction);
		double C = vertical.scalarProduct(vertical) - Math.pow(radius, 2);

		double discriminant = Math.pow(B, 2) - (4 * A * C);

		if (discriminant < 0) {
			return null;
		}

		double x1 = (-B + Math.sqrt(discriminant)) / (2 * A);
		double x2 = (-B - Math.sqrt(discriminant)) / (2 * A);

		return new double[] { x1, x2 };
	}
}
