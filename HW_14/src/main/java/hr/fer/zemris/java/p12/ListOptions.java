package hr.fer.zemris.java.p12;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.p12.dao.DAOProvider;
import hr.fer.zemris.java.p12.model.Poll;
import hr.fer.zemris.java.p12.model.PollOption;

/**
 * The {@code ListOptions} servlet gets the options for each poll from the
 * database which the user has requested and sends them into html document.
 */
@WebServlet("/servleti/glasanje")
public class ListOptions extends HttpServlet {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		long pollID = Long.parseLong(req.getParameter("pollID"));
		List<PollOption> options = DAOProvider.getDao().getPollOptions(pollID);
		Poll poll = DAOProvider.getDao().getPoll(pollID);
		req.getSession().setAttribute("currPoll", poll);
		req.getSession().setAttribute("pollID", pollID);
		req.setAttribute("opcije", options);
		req.getRequestDispatcher("/WEB-INF/pages/poll.jsp").forward(req, resp);
	}

}