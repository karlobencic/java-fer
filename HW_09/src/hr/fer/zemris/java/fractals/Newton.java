package hr.fer.zemris.java.fractals;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadFactory;

import hr.fer.zemris.java.fractals.viewer.FractalViewer;
import hr.fer.zemris.java.fractals.viewer.IFractalProducer;
import hr.fer.zemris.java.fractals.viewer.IFractalResultObserver;
import hr.fer.zemris.math.Complex;
import hr.fer.zemris.math.ComplexPolynomial;
import hr.fer.zemris.math.ComplexRootedPolynomial;

/**
 * The {@code Newton} class is a program for calculation and representation of
 * Newton-Raphson iteration-based fractal.
 * 
 * @author Karlo Bencic
 * 
 */
public class Newton {

	/** Maximum iterations for Newton-Rapshon computation */
	private static final int MAX_ITERATIONS = Integer.MAX_VALUE;

	/** Convergence threshold for Newton-Rapshon computation */
	private static final double CONVERGENCE_THRESHOLD = 0.002;

	/** The rooted polynom. */
	private static ComplexRootedPolynomial rootedPolynom;

	/** The derived polynom. */
	private static ComplexPolynomial derivedPolynom;

	/**
	 * The main method.
	 *
	 * @param args
	 *            the arguments
	 */
	public static void main(String[] args) {
		System.out.println("Welcome to Newton-Raphson iteration-based fractal viewer.");
		System.out.println("Please enter at least two roots, one root per line. Enter 'done' when done.");

		List<Complex> roots = new ArrayList<Complex>();
		Scanner sc = new Scanner(System.in);

		while (true) {
			System.out.printf("Root %d>", roots.size() + 1);
			String currentLine = sc.nextLine();
			if (currentLine.equals("done")) {
				break;
			}

			Complex c = Complex.parse(currentLine);
			if (c == null) {
				System.out.println("Error parsing complex number.");
				continue;
			}

			roots.add(c);
			System.out.println(c.toString());
		}

		sc.close();

		System.out.println("Image of fractal will appear shortly. Thank you.");

		rootedPolynom = new ComplexRootedPolynomial(roots.toArray(new Complex[roots.size()]));
		derivedPolynom = rootedPolynom.toComplexPolynom().derive();

		FractalViewer.show(new NewtonRapshonProducer());
	}

	/**
	 * Class which computes variables necessary for drawing fractal.
	 * 
	 * @author Karlo Bencic
	 * 
	 */
	private static class NewtonRapshonComputation implements Callable<Void> {

		/** The minimum real. */
		private double reMin;

		/** The maximum real. */
		private double reMax;

		/** The minimum imaginary. */
		private double imMin;

		/** The maximum imaginary. */
		private double imMax;

		/** The width. */
		private int width;

		/** The height. */
		private int height;

		/** The y minimum. */
		private int yMin;

		/** The y maximum. */
		private int yMax;

		/** The data. */
		private short[] data;

		/**
		 * Creates new computation class with provided arguments.
		 * 
		 * @param reMin
		 *            Minimum real value
		 * @param reMax
		 *            Maximum real value
		 * @param imMin
		 *            Minimum imaginary value
		 * @param imMax
		 *            Maximum imaginary value
		 * @param width
		 *            Width of the drawing area
		 * @param height
		 *            Height of the drawing area
		 * @param yMin
		 *            Minimum value of Y-axis
		 * @param yMax
		 *            Maximum value of Y-axis
		 * @param data
		 *            Data array for calculation results
		 */
		public NewtonRapshonComputation(double reMin, double reMax, double imMin, double imMax, int width, int height,
				int yMin, int yMax, short[] data) {
			this.reMin = reMin;
			this.reMax = reMax;
			this.imMin = imMin;
			this.imMax = imMax;
			this.width = width;
			this.height = height;
			this.yMin = yMin;
			this.yMax = yMax;
			this.data = data;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.util.concurrent.Callable#call()
		 */
		@Override
		public Void call() throws Exception {
			for (int y = yMin, offset = yMin * width; y <= yMax; y++) {
				for (int x = 0; x < width; x++) {
					double re = x / (width - 1.0) * (reMax - reMin) + reMin;
					double im = (height - 1 - y) / (height - 1.0) * (imMax - imMin) + imMin;
					Complex zn = new Complex(re, im);
					Complex zn1 = new Complex();

					for (int i = 0; i < MAX_ITERATIONS; i++) {
						Complex fraction = rootedPolynom.apply(zn).divide(derivedPolynom.apply(zn));
						zn1 = zn.sub(fraction);

						double module = zn1.sub(zn).module();
						if (module <= CONVERGENCE_THRESHOLD) {
							break;
						}
						zn = zn1;
					}

					short index = (short) rootedPolynom.indexOfClosestRootFor(zn1, CONVERGENCE_THRESHOLD);
					data[offset++] = (short) (index == -1 ? 0 : index + 1);
				}
			}

			return null;
		}
	}

	/**
	 * Producer used to display and calculate fractal using parallelization.
	 * 
	 * @author Karlo Bencic
	 * 
	 */
	private static class NewtonRapshonProducer implements IFractalProducer {

		/** The thread pool. */
		private ExecutorService pool;

		/** The number of available processors. */
		private int numberOfAvailableProcessors = Runtime.getRuntime().availableProcessors();

		/**
		 * Instantiates a new newton rapshon producer.
		 */
		public NewtonRapshonProducer() {
			DaemonicThreadFactory threadFactory = new DaemonicThreadFactory();
			pool = Executors.newFixedThreadPool(numberOfAvailableProcessors, threadFactory);
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * hr.fer.zemris.java.fractals.viewer.IFractalProducer#produce(double,
		 * double, double, double, int, int, long,
		 * hr.fer.zemris.java.fractals.viewer.IFractalResultObserver)
		 */
		@Override
		public void produce(double reMin, double reMax, double imMin, double imMax, int width, int height,
				long requestNo, IFractalResultObserver observer) {

			List<Future<Void>> results = new ArrayList<Future<Void>>();

			System.out.println("Započinjem izračun...");

			short[] data = new short[width * height];
			int stripsCount = height / 8 * numberOfAvailableProcessors;
			int stripHeight = height / stripsCount;

			for (int i = 0; i < stripsCount; i++) {
				int yMin = i * stripHeight;
				int yMax = (i + 1) * stripHeight - 1;

				if (i == stripsCount - 1) {
					yMax = height - 1;
				}

				NewtonRapshonComputation job = new NewtonRapshonComputation(reMin, reMax, imMin, imMax, width, height,
						yMin, yMax, data);
				results.add(pool.submit(job));
			}

			for (Future<Void> job : results) {
				try {
					job.get();
				} catch (InterruptedException | ExecutionException ignorable) {
				}
			}

			System.out.println("Računanje gotovo. Idem obavijestiti promatrača tj. GUI!");
			observer.acceptResult(data, (short) (rootedPolynom.order() + 1), requestNo);
		}
	}

	/**
	 * A factory for creating DaemonicThread objects.
	 * 
	 * @author Karlo Bencic
	 * 
	 */
	private static class DaemonicThreadFactory implements ThreadFactory {

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.util.concurrent.ThreadFactory#newThread(java.lang.Runnable)
		 */
		@Override
		public Thread newThread(Runnable r) {
			Thread t = Executors.defaultThreadFactory().newThread(r);
			t.setDaemon(true);
			return t;
		}
	}
}
