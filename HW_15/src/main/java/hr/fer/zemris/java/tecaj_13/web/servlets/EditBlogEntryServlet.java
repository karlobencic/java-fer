package hr.fer.zemris.java.tecaj_13.web.servlets;

import java.io.IOException;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.tecaj_13.dao.DAOProvider;
import hr.fer.zemris.java.tecaj_13.model.BlogEntry;

/**
 * This servlet performs the action of editing an existing {@link BlogEntry} and
 * storing it into database.
 * 
 * @author Karlo Bencic
 *
 */
@WebServlet(urlPatterns = "/servleti/editExisting")
public class EditBlogEntryServlet extends HttpServlet {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String eid = req.getParameter("id");
		String title = req.getParameter("title");
		String text = req.getParameter("text");
		Long id = null;
		try {
			id = Long.valueOf(eid);
		} catch (Exception ignorable) {
		}
		if (id != null) {
			BlogEntry entry = DAOProvider.getDAO().getBlogEntry(id);
			entry.setTitle(title);
			entry.setText(text);
			entry.setLastModifiedAt(new Date());
			DAOProvider.getDAO().updateBlogEntry(entry);
		}
		resp.sendRedirect("author/" + (String) req.getSession().getAttribute("current.user.nick") + "/" + eid);
	}
}