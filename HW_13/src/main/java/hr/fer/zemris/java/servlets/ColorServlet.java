package hr.fer.zemris.java.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * This servlet handles color settings for the page background.
 * 
 * @author Karlo Bencic
 *
 */
@WebServlet(urlPatterns = { "/setcolor", "/colors" })
public class ColorServlet extends HttpServlet {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		if (!req.getRequestURL().toString().endsWith("colors")) {
			String color = req.getParameter("color") == null ? "white" : (String) req.getParameter("color");
			if (!color.equals("white") && !color.equals("red") && !color.equals("green") && !color.equals("cyan")) {
				color = "white";
			}
			req.getSession(true).setAttribute("selectedBgColor", color);
			req.getRequestDispatcher("index.jsp").forward(req, resp);
		} else {
			req.getRequestDispatcher("/WEB-INF/pages/colors.jsp").forward(req, resp);
		}

	}
}