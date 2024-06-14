package hr.fer.zemris.cmdapps.jvdraw.actions;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

import javax.imageio.ImageIO;
import javax.swing.AbstractAction;
import javax.swing.ButtonGroup;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

import hr.fer.zemris.cmdapps.jvdraw.models.DrawingModel;

/**
 * The {@code ExportFileAction} class represents an action that is used for
 * exporting files into JPEG, PNG, GIF format.
 * 
 * @author Karlo Bencic
 *
 */
public class ExportFileAction extends AbstractAction {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The model. */
	private DrawingModel model;

	/**
	 * Instantiates a new export file action.
	 *
	 * @param model
	 *            the model
	 */
	public ExportFileAction(DrawingModel model) {
		this.model = model;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		export(model);
	}

	/**
	 * Exports given model as an image.
	 * 
	 * @param model
	 *            model to export
	 */
	private void export(DrawingModel model) {
		JPanel p = new JPanel();
		p.setLayout(new GridLayout(3, 1));

		JRadioButton[] buttons = new JRadioButton[3];
		buttons[0] = new JRadioButton("PNG");
		buttons[1] = new JRadioButton("JPG");
		buttons[2] = new JRadioButton("GIF");

		ButtonGroup group = new ButtonGroup();
		for (int i = 0; i < 3; i++) {
			group.add(buttons[i]);
			p.add(buttons[i]);
		}

		JOptionPane.showMessageDialog(null, p, "Select image type to export", JOptionPane.INFORMATION_MESSAGE);

		String ext = "";
		for (int i = 0; i < 3; i++) {
			if (buttons[i].isSelected()) {
				ext = buttons[i].getText();
				break;
			}
		}
		
		if (ext.isEmpty())
			return;
		
		createImage(ext, model);
	}

	/**
	 * Creates an image with the given extension and the given model.
	 *
	 * @param ext
	 *            extension of the image
	 * @param model
	 *            model that provides objects to draw
	 */
	private void createImage(String ext, DrawingModel model) {
		Rectangle boundingBox = model.getBoundingBox();
		BufferedImage image = new BufferedImage(boundingBox.width, boundingBox.height, BufferedImage.TYPE_3BYTE_BGR);

		Graphics2D g = image.createGraphics();

		g.setColor(Color.WHITE);
		g.fillRect(0, 0, boundingBox.width, boundingBox.height);

		for (int i = 0, size = model.getSize(); i < size; i++) {
			model.getObject(i).drawShifted(g, boundingBox.x, boundingBox.y);
		}
		g.dispose();

		JFileChooser fc = new JFileChooser();
		if (fc.showSaveDialog(null) != JFileChooser.APPROVE_OPTION) {
			return;
		}

		File file = Paths.get(fc.getSelectedFile().toPath() + "." + ext.toLowerCase()).toFile();

		try {
			ImageIO.write(image, ext, file);
			JOptionPane.showMessageDialog(null, "Image successfully created!", "Success",
					JOptionPane.INFORMATION_MESSAGE);
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "Error while creating image.", "Error", JOptionPane.ERROR_MESSAGE);
		}
	}

}