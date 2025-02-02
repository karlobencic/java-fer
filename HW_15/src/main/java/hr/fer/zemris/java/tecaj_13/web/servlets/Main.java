package hr.fer.zemris.java.tecaj_13.web.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.tecaj_13.dao.DAOProvider;

/**
 * Main servlet that displays the landing page of this app.
 * 
 * @author Karlo Bencic
 */
@WebServlet(urlPatterns = { "/servleti/main" })
public class Main extends HttpServlet {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setAttribute("registeredUsers", DAOProvider.getDAO().getBlogUsers());
		req.getRequestDispatcher("/WEB-INF/index.jsp").forward(req, resp);
	}
}