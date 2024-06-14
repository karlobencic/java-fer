package hr.fer.zemris.cmdapps.jvdraw.drawing;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.BorderFactory;
import javax.swing.JComponent;

import hr.fer.zemris.cmdapps.jvdraw.JVDraw;
import hr.fer.zemris.cmdapps.jvdraw.models.DrawingModel;
import hr.fer.zemris.cmdapps.jvdraw.models.DrawingModelListener;

/**
 * The {@code JDrawingCanvas} class represents the main component in the
 * {@link JVDraw} program.
 * 
 * @author Karlo Bencic
 * 
 */
public class JDrawingCanvas extends JComponent implements DrawingModelListener {

	/** Generated serial version UID. */
	private static final long serialVersionUID = 1L;

	/** The model. */
	private DrawingModel model;

	/**
	 * Creates a new {@link JDrawingCanvas} that listens to model.
	 *
	 * @param model
	 *            model to listen to
	 */
	public JDrawingCanvas(DrawingModel model) {
		this.model = model;
		model.addDrawingModelListener(this);
		setBorder(BorderFactory.createLineBorder(Color.RED, 3));
	}

	/**
	 * Returns 200, 200 dimension for preffered size.
	 */
	@Override
	public Dimension getPreferredSize() {
		return new Dimension(200, 200);
	}

	@Override
	protected void paintComponent(Graphics g) {
		for (int i = 0, limit = model.getSize(); i < limit; i++) {
			model.getObject(i).paintComponent(g);
		}
	}

	@Override
	public void objectsAdded(DrawingModel source, int index0, int index1) {
		Graphics g = getGraphics();
		for (int i = index0; i <= index1; i++) {
			model.getObject(i).paintComponent(g);
		}
		repaint();
	}

	@Override
	public void objectsRemoved(DrawingModel source, int index0, int index1) {
		repaint();
	}

	@Override
	public void objectsChanged(DrawingModel source, int index0, int index1) {
		Graphics g = getGraphics();
		for (int i = index0; i <= index1; i++) {
			model.getObject(i).paintComponent(g);
		}
	}
}