package hr.fer.zemris.java.servlets;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * This servlet prepares and serves a jsp page on which a band voting definition
 * is rendered.
 * 
 * @author Karlo Bencic
 *
 */
@WebServlet(urlPatterns = { "/glasanje" })
public class VotingServlet extends HttpServlet {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String fileName = req.getServletContext().getRealPath("/WEB-INF/glasanje-definicija.txt");
		List<String> lines = Files.readAllLines(Paths.get(fileName));
		Map<String, Band> bands = new HashMap<>();
		for (String line : lines) {
			String[] pair = line.split("\\t");
			Band b = new Band();
			b.setId(pair[0]);
			b.setName(pair[1]);
			b.setLink(pair[2]);
			bands.put(b.getId(), b);
		}
		req.getSession().setAttribute("bands", bands);
		req.getRequestDispatcher("/WEB-INF/pages/glasanjeIndex.jsp").forward(req, resp);
	}
}