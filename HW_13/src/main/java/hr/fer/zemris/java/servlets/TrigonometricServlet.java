package hr.fer.zemris.java.servlets;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * This servlet generates trigonometric functions sine and cosine from angles a
 * to b in degrees.
 * 
 * @author Karlo Bencic
 *
 */
@WebServlet(urlPatterns = "/trigonometric")
public class TrigonometricServlet extends HttpServlet {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String argA = req.getParameter("a") == null ? "0" : (String) req.getParameter("a");
		String argB = req.getParameter("b") == null ? "360" : (String) req.getParameter("b");

		int a, b;
		try {
			a = Integer.parseInt(argA);
			b = Integer.parseInt(argB);
		} catch (NumberFormatException e) {
			req.getRequestDispatcher("index.jsp").forward(req, resp);
			return;
		}

		if (a > b) {
			a ^= b;
			b ^= a;
			a ^= b;
		}		
		b = b > a + 720 ? a + 720 : b;

		Map<Integer, Double[]> trigonometric = new HashMap<>();
		for (int i = a; i <= b; i++) {
			trigonometric.put(i, new Double[] { Math.sin(Math.toRadians(i)), Math.cos(Math.toRadians(i)) });
		}
		req.getSession(true).setAttribute("trig", trigonometric);
		req.getRequestDispatcher("/WEB-INF/pages/trigonometric.jsp").forward(req, resp);
	}
}