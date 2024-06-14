package hr.fer.zemris.java.servlets;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * This servlet serves as a voting simulator for Bands. Voting results are
 * stored in a file named {@code glasanjerezultati.txt}.
 * 
 * @author Karlo Bencic
 *
 */
@WebServlet(urlPatterns = { "/glasanje-glasaj" })
public class VotingVoteServlet extends HttpServlet {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	@SuppressWarnings("unchecked")
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String fileName = req.getServletContext().getRealPath("/WEB-INF/glasanjerezultati.txt");
		Path file = Paths.get(fileName);
		if (!Files.exists(file)) {
			Files.createFile(file);
			fillFile(file, (Map<String, Band>) req.getSession().getAttribute("bands"));
		}
		String id = req.getParameter("id");
		List<String> lines = Files.readAllLines(file);
		Map<String, Band> bands = (Map<String, Band>) req.getSession().getAttribute("bands");
		for (String line : lines) {
			String[] pair = line.split("\\t");
			Band bend = bands.get(pair[0]);
			if (pair[0].equals(id)) {
				pair[1] = String.valueOf(Integer.parseInt(pair[1]) + 1);
			}
			bend.setVotes(Integer.parseInt(pair[1]));
			bands.put(bend.getId(), bend);
		}
		fillFile(file, bands);
		req.getSession().setAttribute("bands", bands);
		resp.sendRedirect(req.getContextPath() + "/glasanje-rezultati");
	}

	/**
	 * Fills the file containing voting data.
	 * 
	 * @param file
	 *            File to be filled
	 * @param bands
	 *            Map of bands
	 */
	private void fillFile(Path file, Map<String, Band> bands) {
		try {
			BufferedWriter writer = Files.newBufferedWriter(file, StandardCharsets.UTF_8);
			for (Band b : bands.values()) {
				writer.write(b.getId() + "\t" + b.getVotes() + "\r\n");
			}
			writer.flush();
			writer.close();
		} catch (IOException ignored) {
		}
	}
}