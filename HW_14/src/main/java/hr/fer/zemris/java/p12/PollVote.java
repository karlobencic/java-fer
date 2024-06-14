package hr.fer.zemris.java.p12;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.p12.dao.DAOProvider;
import hr.fer.zemris.java.p12.model.PollOption;

/**
 * The {@code PollVote} servlet handels voting process. It updates the data in
 * the database after a vote has happened.
 * 
 * @author Karlo Bencic
 * 
 */
@WebServlet(urlPatterns = { "/servleti/glasaj" })
public class PollVote extends HttpServlet {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		long id = Long.parseLong(req.getParameter("id"));
		DAOProvider.getDao().updatePoll(id);
		long pollID = (Long) req.getSession().getAttribute("pollID");
		List<PollOption> options = DAOProvider.getDao().getPollOptions(pollID);
		List<PollOption> best = new ArrayList<>();
		PollOption max = options.get(0);

		for (PollOption option : options) {
			if (option.getVotesCount() > max.getVotesCount()) {
				max = option;
			}
		}
		for (PollOption option : options) {
			if (option.getVotesCount() == max.getVotesCount()) {
				best.add(option);
			}
		}

		options.sort((v1, v2) -> Long.compare(v2.getVotesCount(), v1.getVotesCount()));
		req.setAttribute("best", best);
		req.setAttribute("opcije", options);
		req.getRequestDispatcher("/WEB-INF/pages/glasanjeRez.jsp").forward(req, resp);
	}
}