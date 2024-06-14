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
 * Servlet that handles login process by processing the login form and accepting
 * the user if the entered data exists in the database and is valid.
 * 
 * @author Karlo Bencic
 *
 */
@WebServlet(urlPatterns = "/servleti/login")
public class Login extends HttpServlet {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String nick = req.getParameter("nick");
		String pass = req.getParameter("pass");
		if (nick == null || nick.trim().isEmpty() || pass == null) {
			req.getSession().setAttribute("error", "Nicknake or password fields are empty");
		} else {
			nick = nick.trim();
			BlogUser enteredUser = DAOProvider.getDAO().getUser(nick);
			if (enteredUser == null) {
				req.getSession().setAttribute("error", "User with that nickname does not exists");
			} else if (PasswordUtil.checkPassword(pass, enteredUser.getPasswordHash())) {
				req.getSession().setAttribute("current.user.id", enteredUser.getId());
				req.getSession().setAttribute("current.user.fn", enteredUser.getFirstName());
				req.getSession().setAttribute("current.user.ln", enteredUser.getLastName());
				req.getSession().setAttribute("current.user.nick", enteredUser.getNick());
			} else {
				req.getSession().setAttribute("error", "Wrong password");
			}
		}
		resp.sendRedirect("../main");
	}

}