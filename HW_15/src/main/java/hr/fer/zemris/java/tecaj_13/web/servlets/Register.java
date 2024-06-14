package hr.fer.zemris.java.tecaj_13.web.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.tecaj_13.dao.DAOProvider;
import hr.fer.zemris.java.tecaj_13.model.BlogUser;
import hr.fer.zemris.java.tecaj_13.web.servlets.utils.PasswordUtil;

/**
 * Servlet that handles displaying registration form and processing registration
 * forms for new users.
 * 
 * @author Karlo Bencic
 *
 */
@WebServlet(urlPatterns = "/servleti/register")
public class Register extends HttpServlet {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.getRequestDispatcher("/WEB-INF/register.jsp").forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String firstname = req.getParameter("firstname");
		String lastname = req.getParameter("lastname");
		String nick = req.getParameter("nick");
		String email = req.getParameter("email");
		String pass = req.getParameter("pass");
		boolean success = true;
		if (firstname == null || pass == null || lastname == null || nick == null || email == null) {
			resp.sendError(400);
			success = false;
		}
		if (firstname.trim().isEmpty()) {
			req.getSession().setAttribute("registrationError", "First name can't be empty");
			success = false;
		}
		if (lastname.trim().isEmpty()) {
			req.getSession().setAttribute("registrationError", "Last name can't be empty");
			success = false;
		}
		if (nick.trim().isEmpty() || DAOProvider.getDAO().getUser(nick) != null) {
			req.getSession().setAttribute("registrationError", "Invalid nick");
			success = false;
		}
		if (pass.trim().isEmpty()) {
			req.getSession().setAttribute("registrationError", "Password can't be empty");
			success = false;
		}
		if (success) {
			BlogUser user = new BlogUser();
			user.setFirstName(firstname);
			user.setLastName(lastname);
			user.setNick(nick);
			user.setEmail(email);
			String hashed = PasswordUtil.hashPassword(pass);
			if (hashed == null) {
				resp.sendError(500);
			}
			user.setPasswordHash(hashed);
			DAOProvider.getDAO().createNewUser(user);
			resp.sendRedirect("../main");
		} else {
			doGet(req, resp);
		}
	}
}