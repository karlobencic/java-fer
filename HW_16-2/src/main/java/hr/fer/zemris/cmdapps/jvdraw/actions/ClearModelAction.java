package hr.fer.zemris.cmdapps.jvdraw.actions;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import hr.fer.zemris.cmdapps.jvdraw.models.DrawingModel;

/**
 * The {@code ClearModelAction} class represents an action that clears the
 * model.
 * 
 * @author Karlo Bencic
 *
 */
public class ClearModelAction extends AbstractAction {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The model. */
	private DrawingModel model;

	/**
	 * Instantiates a new clear model action.
	 *
	 * @param model
	 *            the model
	 */
	public ClearModelAction(DrawingModel model) {
		this.model = model;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		model.clear();
	}
}