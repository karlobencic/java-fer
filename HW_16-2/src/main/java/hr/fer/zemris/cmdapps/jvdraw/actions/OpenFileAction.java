package hr.fer.zemris.cmdapps.jvdraw.actions;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

import javax.swing.AbstractAction;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import hr.fer.zemris.cmdapps.jvdraw.JVDraw;
import hr.fer.zemris.cmdapps.jvdraw.drawing.objects.Circle;
import hr.fer.zemris.cmdapps.jvdraw.drawing.objects.FCircle;
import hr.fer.zemris.cmdapps.jvdraw.drawing.objects.GeometricalObject;
import hr.fer.zemris.cmdapps.jvdraw.drawing.objects.Line;
import hr.fer.zemris.cmdapps.jvdraw.models.DrawingModel;

/**
 * The {@code OpenFileAction} class represents an action that opens files from
 * the storage.
 * 
 * @author Karlo Bencic
 *
 */
public class OpenFileAction extends AbstractAction {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The model. */
	private DrawingModel model;

	/** The path. */
	private Path path;

	/**
	 * Instantiates a new open file action.
	 *
	 * @param model
	 *            the model
	 * @param path
	 *            the path
	 */
	public OpenFileAction(DrawingModel model, Path path) {
		this.model = model;
		this.path = path;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		JFileChooser fc = new JFileChooser();
		if (fc.showOpenDialog(null) != JFileChooser.APPROVE_OPTION) {
			return;
		}

		path = fc.getSelectedFile().toPath();
		if (!path.getFileName().toString().endsWith(JVDraw.EXTENSION)) {
			JOptionPane.showMessageDialog(null, "File must have *" + JVDraw.EXTENSION + " extension", "Error",
					JOptionPane.ERROR_MESSAGE);
			return;
		}

		model.clear();
		String[] rows = readFromFile(path).split("\n");
		for (String row : rows) {
			model.add(getObject(row));
		}
	}

	/**
	 * Reads entire file content as a string.
	 * 
	 * @param file
	 *            path to the file
	 * @return string content of the file
	 */
	private static String readFromFile(Path file) {
		if (file == null)
			return "";
		if (!Files.isReadable(file)) {
			JOptionPane.showMessageDialog(null, "File is not readable", "Error while reading",
					JOptionPane.ERROR_MESSAGE);
			return "";
		}

		byte[] content;
		try {
			content = Files.readAllBytes(file);
			return new String(content, StandardCharsets.UTF_8);
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "File is not readable", "Error while reading",
					JOptionPane.ERROR_MESSAGE);
			return "";
		}
	}

	/**
	 * Returns a {@link GeometricalObject} that is a representation of the given
	 * row.
	 *
	 * @param row
	 *            row that is parsed
	 * @return a geometrical object that is parsed from given row
	 */
	private GeometricalObject getObject(String row) {
		if (row.startsWith("LINE")) {
			return parseLine(row);
		} else if (row.startsWith("CIRCLE")) {
			return parseCircle(row);
		} else {
			return parseFCircle(row);
		}
	}

	/**
	 * Parses a given string as a {@link Line}.
	 *
	 * @param row
	 *            row to parse
	 * @return line parsed from string
	 */
	private GeometricalObject parseLine(String row) {
		String[] args = row.split(" ");

		int sx = Integer.parseInt(args[1]);
		int sy = Integer.parseInt(args[2]);
		int ex = Integer.parseInt(args[3]);
		int ey = Integer.parseInt(args[4]);
		int r = Integer.parseInt(args[5]);
		int g = Integer.parseInt(args[6]);
		int b = Integer.parseInt(args[7]);

		return new Line(sx, sy, ex, ey, new Color(r, g, b));
	}

	/**
	 * Parses a given string as a {@link Circle}.
	 *
	 * @param row
	 *            row to parse
	 * @return circle parsed from string
	 */
	private GeometricalObject parseCircle(String row) {
		String[] args = row.split(" ");

		int x = Integer.parseInt(args[1]);
		int y = Integer.parseInt(args[2]);
		int radius = Integer.parseInt(args[3]);
		int r = Integer.parseInt(args[4]);
		int g = Integer.parseInt(args[5]);
		int b = Integer.parseInt(args[6]);

		return new Circle(x, y, radius, new Color(r, g, b));
	}

	/**
	 * Parses a given string as a {@link FCircle}.
	 *
	 * @param row
	 *            row to parse
	 * @return fcircle parsed from string
	 */
	private GeometricalObject parseFCircle(String row) {
		String[] args = row.split(" ");

		int x = Integer.parseInt(args[1]);
		int y = Integer.parseInt(args[2]);
		int radius = Integer.parseInt(args[3]);
		int r1 = Integer.parseInt(args[4]);
		int g1 = Integer.parseInt(args[5]);
		int b1 = Integer.parseInt(args[6]);
		int r2 = Integer.parseInt(args[7]);
		int g2 = Integer.parseInt(args[8]);
		int b2 = Integer.parseInt(args[9]);

		return new FCircle(x, y, radius, new Color(r1, g1, b1), new Color(r2, g2, b2));
	}
}