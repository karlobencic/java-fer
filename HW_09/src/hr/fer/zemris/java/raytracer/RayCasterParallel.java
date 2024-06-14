package hr.fer.zemris.java.raytracer;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;

import hr.fer.zemris.java.raytracer.model.IRayTracerProducer;
import hr.fer.zemris.java.raytracer.model.IRayTracerResultObserver;
import hr.fer.zemris.java.raytracer.model.Point3D;
import hr.fer.zemris.java.raytracer.model.Ray;
import hr.fer.zemris.java.raytracer.model.Scene;
import hr.fer.zemris.java.raytracer.viewer.RayTracerViewer;

/**
 * The {@code RayCasterParallel} class is a program that uses ray-casting to
 * create image of two 3D sphere models under illumination. This is an
 * implementation of {@link RayCaster} class which uses parallelization to speed
 * up calculations.
 * 
 * @author Karlo Bencic
 * 
 */
public class RayCasterParallel {

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
	 * Producer used to calculate necessary image data using parallelization
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

				ForkJoinPool pool = new ForkJoinPool();
				pool.invoke(new ImageCalculation(scene, 0, height, width, height, horizontal, vertical, screenCorner,
						xAxis, yAxis, eye, red, blue, green));
				pool.shutdown();

				System.out.println("Izračuni gotovi...");
				observer.acceptResult(red, green, blue, requestNo);
				System.out.println("Dojava gotova...");
			}
		};
	}

	/**
	 * The {@code ImageCalculation} class calculates image data in parallel
	 * using recursion.
	 */
	private static class ImageCalculation extends RecursiveAction {

		/** The Constant serialVersionUID. */
		private static final long serialVersionUID = 1L;

		/** The scene. */
		private Scene scene;

		/** The minimum y-axis value. */
		private int yMin;

		/** The maximum y-axis value. */
		private int yMax;

		/** The width of space. */
		private int width;

		/** The height of space. */
		private int height;

		/** The horizontal width of space. */
		private double horizontal;

		/** The vertical width of space. */
		private double vertical;

		/** The upper left screen corner. */
		private Point3D screenCorner;

		/** The x axis. */
		private Point3D xAxis;

		/** The y axis. */
		private Point3D yAxis;

		/** The eye position of the viewer. */
		private Point3D eye;

		/** The red color. */
		private short[] red;

		/** The blue color. */
		private short[] blue;

		/** The green color. */
		private short[] green;

		/** The Constant THRESHOLD. */
		private static final int THRESHOLD = 16;

		/**
		 * Instantiates a new image calculation.
		 *
		 * @param scene
		 *            the scene
		 * @param yMin
		 *            the minimum y-axis value
		 * @param yMax
		 *            the maximum y-axis value
		 * @param width
		 *            the width of space
		 * @param height
		 *            the height of space
		 * @param horizontal
		 *            the horizontal width of space
		 * @param vertical
		 *            the vertical width of space
		 * @param screenCorner
		 *            the upper left screen corner
		 * @param xAxis
		 *            the x axis
		 * @param yAxis
		 *            the y axis
		 * @param eye
		 *            the eye position of the viewer
		 * @param red
		 *            the red color
		 * @param blue
		 *            the blue color
		 * @param green
		 *            the green color
		 */
		public ImageCalculation(Scene scene, int yMin, int yMax, int width, int height, double horizontal,
				double vertical, Point3D screenCorner, Point3D xAxis, Point3D yAxis, Point3D eye, short[] red,
				short[] blue, short[] green) {
			this.scene = scene;
			this.yMin = yMin;
			this.yMax = yMax;
			this.width = width;
			this.height = height;
			this.horizontal = horizontal;
			this.vertical = vertical;
			this.screenCorner = screenCorner;
			this.xAxis = xAxis;
			this.yAxis = yAxis;
			this.eye = eye;
			this.red = red;
			this.blue = blue;
			this.green = green;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.util.concurrent.RecursiveAction#compute()
		 */
		@Override
		protected void compute() {
			if (yMax - yMin + 1 <= THRESHOLD) {
				computeDirect();
				return;
			}

			invokeAll(
					new ImageCalculation(scene, yMin, yMin + (yMax - yMin) / 2, width, height, horizontal, vertical,
							screenCorner, xAxis, yAxis, eye, red, blue, green),
					new ImageCalculation(scene, yMin + (yMax - yMin) / 2, yMax, width, height, horizontal, vertical,
							screenCorner, xAxis, yAxis, eye, red, blue, green));
		}

		/**
		 * Computes directly if the calculation space does not exceed
		 * {@code THRESHOLD}.
		 */
		protected void computeDirect() {
			short[] rgb = new short[3];
			int offset = yMin * width;
			for (int y = yMin; y < yMax; y++) {
				for (int x = 0; x < width; x++, offset++) {
					Point3D screenPoint = screenCorner.add(xAxis.scalarMultiply(x / (width - 1.0) * horizontal))
							.sub(yAxis.scalarMultiply(y / (height - 1.0) * vertical));

					Ray ray = Ray.fromPoints(eye, screenPoint);

					RayCaster.tracer(scene, ray, rgb);

					red[offset] = RayCaster.clampRgb(rgb[0]);
					green[offset] = RayCaster.clampRgb(rgb[1]);
					blue[offset] = RayCaster.clampRgb(rgb[2]);
				}
			}
		}
	}
}
