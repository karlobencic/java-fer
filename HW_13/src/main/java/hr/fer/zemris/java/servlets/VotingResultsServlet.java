package hr.fer.zemris.java.servlets;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * This servlet serves as a band voting result provider. Voting results are
 * pulled from file {@code glasanjerezultati.txt}.
 * 
 * @author Karlo Bencic
 *
 */
@WebServlet(urlPatterns = { "/glasanje-rezultati" })
public class VotingResultsServlet extends HttpServlet {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	@SuppressWarnings("unchecked")
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String fileName = req.getServletContext().getRealPath("/WEB-INF/glasanjerezultati.txt");
		Path file = Paths.get(fileName);
		if (!Files.exists(file)) {
			Files.createFile(file);
		}
		List<Band> results = new ArrayList<>();
		Map<String, Band> bands = (Map<String, Band>) req.getSession().getAttribute("bands");
		List<Band> best = new ArrayList<>();
		for (String line : Files.readAllLines(file)) {
			String[] pair = line.split("\\t");
			results.add(bands.get(pair[0]));
		}
		Band max = bands.get("1");
		for (Band band : bands.values()) {
			if (band.getVotes() > max.getVotes()) {
				max = band;
			}
		}
		for (Band band : bands.values()) {
			if (band.getVotes() == max.getVotes()) {
				best.add(band);
			}
		}
		results.sort((b1, b2) -> Integer.compare(b2.getVotes(), b1.getVotes()));
		req.getSession().setAttribute("best", best);
		req.getSession().setAttribute("results", results);
		req.getRequestDispatcher("/WEB-INF/pages/glasanjeRez.jsp").forward(req, resp);
	}
}