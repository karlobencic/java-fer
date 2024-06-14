package hr.fer.zemris.cmdapps.jvdraw.models;

import java.awt.Rectangle;

import hr.fer.zemris.cmdapps.jvdraw.drawing.objects.GeometricalObject;

/**
 * The {@code DrawingModel} interface is implemented by models that provide
 * graphical objects.
 * 
 * @author Karlo Bencic
 * 
 */
public interface DrawingModel {

	/**
	 * Number of objects.
	 * 
	 * @return number of objects
	 */
	public int getSize();

	/**
	 * Gets the object at given index.
	 * 
	 * @param index
	 *            object's index
	 * @return object at given index
	 */
	public GeometricalObject getObject(int index);

	/**
	 * Adds object to a list of objects.
	 * 
	 * @param obj
	 *            object to add
	 */
	public void add(GeometricalObject obj);

	/**
	 * Removes the object from the object list
	 * 
	 * @param obj
	 *            object to remove
	 */
	public void remove(GeometricalObject obj);

	/**
	 * Clears the model.
	 */
	public void clear();

	/**
	 * Adds the listener to the listeners list.
	 * 
	 * @param l
	 *            listener to add
	 */
	public void addDrawingModelListener(DrawingModelListener l);

	/**
	 * Removes the listener from the listeners list.
	 * 
	 * @param l
	 *            listener to remove
	 */
	public void removeDrawingModelListener(DrawingModelListener l);

	/**
	 * Gets the bounding box of this model.
	 * 
	 * @return bounding box of this model
	 */
	public Rectangle getBoundingBox();

}