package hr.fer.zemris.cmdapps.jvdraw.drawing;

import java.awt.Color;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.MouseMotionListener;

import javax.swing.JComponent;

import hr.fer.zemris.cmdapps.jvdraw.color.ColorChangeListener;
import hr.fer.zemris.cmdapps.jvdraw.color.IColorProvider;
import hr.fer.zemris.cmdapps.jvdraw.color.JColorArea;
import hr.fer.zemris.cmdapps.jvdraw.drawing.objects.Circle;
import hr.fer.zemris.cmdapps.jvdraw.drawing.objects.FCircle;
import hr.fer.zemris.cmdapps.jvdraw.drawing.objects.GeometricalObject;
import hr.fer.zemris.cmdapps.jvdraw.drawing.objects.Line;
import hr.fer.zemris.cmdapps.jvdraw.models.DrawingModel;

/**
 * The {@code Painter} class is used for drawing objects on
 * {@link JDrawingCanvas}.
 * 
 * @author Karlo Bencic
 * 
 */
public class Painter implements ColorChangeListener {

	/** The Constant LINE. */
	public static final int LINE = 0;

	/** The Constant CIRCLE. */
	public static final int CIRCLE = 1;

	/** The Constant FCIRCLE. */
	public static final int FCIRCLE = 2;

	/** The status. */
	private int status = LINE;

	/** The first click flag. */
	private boolean firstClick = true;

	/** The parent. */
	private JComponent parent;

	/** The model. */
	private DrawingModel model;

	/** The obj. */
	private GeometricalObject obj;

	/** The colors. */
	private Color[] colors = new Color[2];

	/** The changed. */
	private boolean changed;

	/** coordinates of the first point. */
	private Point start;

	/**
	 * Instantiates a new painter.
	 * 
	 * @param parent
	 *            component on which drawer will add mouse event listeners
	 * @param model
	 *            model to which drawer will add drawn objects
	 * @param foreground
	 *            {@link JColorArea} that is responsible for foreground color
	 * @param background
	 *            {@link JColorArea} that is responsible for background color
	 */
	public Painter(JComponent parent, DrawingModel model, JColorArea foreground, JColorArea background) {
		this.parent = parent;
		this.parent.addMouseListener(mouseClick);
		this.model = model;

		colors[0] = foreground.getCurrentColor();
		colors[1] = background.getCurrentColor();
		foreground.addColorChangeListener(this);
		background.addColorChangeListener(this);
	}

	/**
	 * Checks if the drawing has changed since last save.
	 * 
	 * @return true if it has changed
	 */
	public boolean hasChanged() {
		return changed;
	}

	/**
	 * Sets change to false.
	 */
	public void fileSaved() {
		changed = false;
	}

	/**
	 * Sets the status of the drawer.
	 * 
	 * @param status
	 *            new status
	 */
	public void setStatus(int status) {
		this.status = status;
	}

	/**
	 * Mouse listener that is added to the drawing canvas and is used to know
	 * when user clicks on the canvas.
	 */
	private MouseListener mouseClick = new MouseAdapter() {
		public void mouseClicked(MouseEvent e) {
			if (firstClick) {
				firstClick = false;
				start = e.getPoint();
				setObjectToDraw();
				Painter.this.parent.addMouseMotionListener(mouseMove);
			} else {
				firstClick = true;
				Painter.this.parent.removeMouseMotionListener(mouseMove);
				changed = true;
				model.add(obj);
			}
		};
	};

	/**
	 * Added to the canvas after a first click has been made, removed when
	 * second click is made.
	 */
	private MouseMotionListener mouseMove = new MouseMotionAdapter() {
		public void mouseMoved(MouseEvent e) {
			obj.changeEndPoint(e.getPoint());
			parent.repaint();
			obj.paintComponent(parent.getGraphics());
		}
	};

	/**
	 * Sets new graphics object depending on the current status
	 */
	private void setObjectToDraw() {
		switch (status) {
		case LINE:
			obj = new Line(start.x, start.y, start.x, start.y, colors[0]);
			break;
		case CIRCLE:
			obj = new Circle(start.x, start.y, 0, colors[0]);
			break;
		default:
			obj = new FCircle(start.x, start.y, 0, colors[0], colors[1]);
		}
	};

	@Override
	public void newColorSelected(IColorProvider source, Color oldColor, Color newColor) {
		colors[source.isForegroundColor() ? 0 : 1] = newColor;
	}
}