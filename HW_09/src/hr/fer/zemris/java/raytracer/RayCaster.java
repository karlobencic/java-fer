package hr.fer.zemris.java.raytracer;

import java.util.List;

import hr.fer.zemris.java.raytracer.model.GraphicalObject;
import hr.fer.zemris.java.raytracer.model.IRayTracerProducer;
import hr.fer.zemris.java.raytracer.model.IRayTracerResultObserver;
import hr.fer.zemris.java.raytracer.model.LightSource;
import hr.fer.zemris.java.raytracer.model.Point3D;
import hr.fer.zemris.java.raytracer.model.Ray;
import hr.fer.zemris.java.raytracer.model.RayIntersection;
import hr.fer.zemris.java.raytracer.model.Scene;
import hr.fer.zemris.java.raytracer.viewer.RayTracerViewer;

/**
 * The {@code RayCaster} class is a program which uses ray-casting to create
 * image of two 3D sphere models under illumination..
 * 
 * @author Karlo Bencic
 * 
 */
public class RayCaster {

	/** The Constant MAX_RGB. */
	protected static final int MAX_RGB = 255;

	/** The Constant AMBIENT_LIGHT. */
	private static final int AMBIENT_LIGHT = 16;

	/**
	 * The main method.
	 *
	 * @param args
	 *            the arguments
	 */
	public static void main(String[] args) {
		RayTracerViewer.show(getIRayTracerProducer(), new Point3D(10, 0, 0), new Point3D(0, 0, 0),
				new Point3D(0, 0, 10), 20, 20);
	}

	/**
	 * Producer used to calculate necessary image data
	 *
	 * @return the ray tracer producer with calculation results ready for
	 *         drawing
	 */
	private static IRayTracerProducer getIRayTracerProducer() {
		return new IRayTracerProducer() {
			@Override
			public void produce(Point3D eye, Point3D view, Point3D viewUp, double horizontal, double vertical,
					int width, int height, long requestNo, IRayTracerResultObserver observer) {

				System.out.println("Započinjem izračune...");
				short[] red = new short[width * height];
				short[] green = new short[width * height];
				short[] blue = new short[width * height];

				Point3D zAxis = view.sub(eye).modifyNormalize();
				Point3D yAxis = viewUp.normalize().sub(zAxis.scalarMultiply(viewUp.normalize().scalarProduct(zAxis)))
						.normalize();
				Point3D xAxis = zAxis.vectorProduct(yAxis).normalize();

				Point3D screenCorner = view.sub(xAxis.scalarMultiply(horizontal / 2.0))
						.add(yAxis.scalarMultiply(vertical / 2.0));

				Scene scene = RayTracerViewer.createPredefinedScene();

				short[] rgb = new short[3];
				for (int y = 0, offset = 0; y < height; y++) {
					for (int x = 0; x < width; x++, offset++) {
						Point3D screenPoint = screenCorner.add(xAxis.scalarMultiply(x / (width - 1.0) * horizontal))
								.sub(yAxis.scalarMultiply(y / (height - 1.0) * vertical));
						Ray ray = Ray.fromPoints(eye, screenPoint);

						tracer(scene, ray, rgb);

						red[offset] = clampRgb(rgb[0]);
						green[offset] = clampRgb(rgb[1]);
						blue[offset] = clampRgb(rgb[2]);
					}
				}

				System.out.println("Izračuni gotovi...");
				observer.acceptResult(red, green, blue, requestNo);
				System.out.println("Dojava gotova...");
			}
		};
	}

	/**
	 * Clamps rgb value in range [0, 255].
	 *
	 * @param value
	 *            the value
	 * @return the clamped value
	 */
	protected static short clampRgb(short value) {
		return value > MAX_RGB ? MAX_RGB : value;
	}

	/**
	 * Traces image using ray-casting model and adds results into rgb array.
	 *
	 * @param scene
	 *            the scene
	 * @param ray
	 *            the ray from the eye
	 * @param rgb
	 *            the rgb color data
	 */
	protected static void tracer(Scene scene, Ray ray, short[] rgb) {
		float[] light = new float[3];

		for (int i = 0; i < light.length; i++) {
			light[i] = AMBIENT_LIGHT;
		}

		RayIntersection closestIntersection = findClosestIntersection(scene, ray);
		if (closestIntersection != null) {
			determineColor(scene, ray, light, closestIntersection);
		}

		for (int i = 0; i < rgb.length; i++) {
			rgb[i] = (short) light[i];
		}
	}

	/**
	 * Find closest intersection between ray and objects in the scene.
	 *
	 * @param scene
	 *            the scene
	 * @param ray
	 *            the ray from the eye
	 * @return the closest ray intersection with any object in the scene
	 */
	protected static RayIntersection findClosestIntersection(Scene scene, Ray ray) {
		RayIntersection closestIntersection = null;

		List<GraphicalObject> objects = scene.getObjects();
		for (GraphicalObject object : objects) {
			RayIntersection currentIntersection = object.findClosestRayIntersection(ray);
			if (currentIntersection == null) {
				continue;
			}
			if (closestIntersection == null || currentIntersection.getDistance() < closestIntersection.getDistance()) {
				closestIntersection = currentIntersection;
			}
		}

		return closestIntersection;
	}

	/**
	 * Determines color for specific intersection. Adds diffuse and reflective
	 * component if necessary.
	 *
	 * @param scene
	 *            the scene
	 * @param ray
	 *            the ray used to find the intersection
	 * @param rgb
	 *            the rgb color data
	 * @param intersection
	 *            the intersection between ray and object
	 */
	private static void determineColor(Scene scene, Ray ray, float[] rgb, RayIntersection intersection) {
		List<LightSource> lights = scene.getLights();
		for (LightSource light : lights) {
			Ray lightToIntersection = Ray.fromPoints(light.getPoint(), intersection.getPoint());

			RayIntersection closestIntersection = findClosestIntersection(scene, lightToIntersection);
			if (closestIntersection == null) {
				continue;
			}

			double lightSourceDistance = light.getPoint().sub(closestIntersection.getPoint()).norm();
			double eyeDistance = light.getPoint().sub(intersection.getPoint()).norm();

			if (Double.compare(lightSourceDistance + 0.01, eyeDistance) >= 0) {
				addDiffusseComponent(light, rgb, closestIntersection);
				addReflectiveComponent(light, ray, rgb, closestIntersection);
			}
		}
	}

	/**
	 * Adds the diffusse component to RGB color data.
	 *
	 * @param light
	 *            the light source
	 * @param rgb
	 *            the rgb color data
	 * @param intersection
	 *            the focused intersection
	 */
	private static void addDiffusseComponent(LightSource light, float[] rgb, RayIntersection intersection) {
		Point3D normal = intersection.getNormal();
		Point3D l = light.getPoint().sub(intersection.getPoint()).normalize();

		double ln = l.scalarProduct(normal);

		rgb[0] += light.getR() * intersection.getKdr() * Math.max(ln, 0);
		rgb[1] += light.getG() * intersection.getKdg() * Math.max(ln, 0);
		rgb[2] += light.getB() * intersection.getKdb() * Math.max(ln, 0);
	}

	/**
	 * Adds the reflective component to RGB color data.
	 *
	 * @param light
	 *            the light source
	 * @param ray
	 *            the ray used to find intersection
	 * @param rgb
	 *            the rgb color data
	 * @param intersection
	 *            the focused intersection
	 */
	private static void addReflectiveComponent(LightSource light, Ray ray, float[] rgb, RayIntersection intersection) {
		Point3D normal = intersection.getNormal();
		Point3D l = light.getPoint().sub(intersection.getPoint());
		Point3D lProjectionOnN = normal.scalarMultiply(l.scalarProduct(normal));
		Point3D r = lProjectionOnN.add(lProjectionOnN.negate().add(l).scalarMultiply(-1));
		Point3D v = ray.start.sub(intersection.getPoint());
		double cos = r.normalize().scalarProduct(v.normalize());

		if (Double.compare(cos, 0) >= 0) {
			cos = Math.pow(cos, intersection.getKrn());

			rgb[0] += light.getR() * intersection.getKrr() * cos;
			rgb[1] += light.getG() * intersection.getKrg() * cos;
			rgb[2] += light.getB() * intersection.getKrb() * cos;
		}
	}
}
