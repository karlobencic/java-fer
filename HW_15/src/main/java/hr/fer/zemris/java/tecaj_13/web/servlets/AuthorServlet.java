package hr.fer.zemris.java.tecaj_13.web.servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.tecaj_13.dao.DAOProvider;
import hr.fer.zemris.java.tecaj_13.model.BlogEntry;

/**
 * Servlet that handles operations with author, like redirecting to page to edit
 * existing or creating a new {@link BlogEntry}, displaying it's blog entries and
 * redirecting to blog entry page.
 * 
 * @author Karlo Bencic
 *
 */
@WebServlet(urlPatterns = "/servleti/author/*")
public class AuthorServlet extends HttpServlet {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String path = req.getPathInfo().substring(1);
		String[] args = path.split("/");
		if (args.length == 1) {
			String nick = args[0];
			req.setAttribute("nick", nick);
			showUser(req, resp);
		} else if (args.length == 2) {
			String nick = args[0];
			req.setAttribute("nick", nick);
			if (isNumber(args[1])) {
				req.setAttribute("id", Long.valueOf(args[1]));
				displayBlogEntry(req, resp);
			} else {
				if (args[1].equals("new") || args[1].equals("edit")) {
					req.setAttribute("operation", args[1]);
					displayEditor(req, resp);
				} else {
					resp.sendError(400, "Unknown operation");
				}
			}
		} else {
			resp.sendError(404, "Invalid path");
		}
	}

	/**
	 * Dispatches the user to the entry editor.
	 * 
	 * @param req
	 *            the request
	 * @param resp
	 *            the response
	 * @throws ServletException
	 * @throws IOException
	 */
	private void displayEditor(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String operation = (String) req.getAttribute("operation");
		if (operation.equals("edit")) {
			String sID = req.getParameter("eid");
			Long id = null;
			try {
				id = Long.valueOf(sID);
			} catch (Exception ignorable) {
			}
			if (id != null) {
				BlogEntry entry = DAOProvider.getDAO().getBlogEntry(id);
				req.setAttribute("entry", entry);
			}
		}
		req.getRequestDispatcher("/WEB-INF/pages/BlogEntryEditor.jsp").forward(req, resp);
	}

	/**
	 * Displays the blog entry page.
	 * 
	 * @param req
	 *            the request
	 * @param resp
	 *            the response
	 * @throws ServletException
	 * @throws IOException
	 */
	private void displayBlogEntry(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		Long id = (Long) req.getAttribute("id");
		if (id != null) {
			BlogEntry blogEntry = DAOProvider.getDAO().getBlogEntry(id);
			if (blogEntry != null) {
				req.setAttribute("blogEntry", blogEntry);
			}
		}
		req.getRequestDispatcher("/WEB-INF/pages/BlogEntryPage.jsp").forward(req, resp);
	}

	/**
	 * Displays the blog entries associated with the clicked user.
	 * 
	 * @param req
	 *            the request
	 * @param resp
	 *            the response
	 * @throws ServletException
	 * @throws IOException
	 */
	private void showUser(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String nick = (String) req.getAttribute("nick");
		List<BlogEntry> entries = DAOProvider.getDAO().getBlogEntriesForUser(DAOProvider.getDAO().getUser(nick));
		req.setAttribute("blogEntries", entries);
		req.getRequestDispatcher("/WEB-INF/pages/authorPage.jsp").forward(req, resp);
	}

	/**
	 * Checks if string is a long number.
	 *
	 * @param string
	 *            the string to be checked
	 * @return true, if is number
	 */
	private boolean isNumber(String string) {
		try {
			Long.parseLong(string);
		} catch (NumberFormatException e) {
			return false;
		}
		return true;
	}

}