package controllers;

import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import models.ProjectBean;
import models.ProjectDAO;
import models.UserBean;
import models.UserDAO;

@WebServlet(name = "LoginServlet", urlPatterns = { "/login" })
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger LOGGER = Logger.getLogger(LoginServlet.class.getName());

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			// Get query parameters
			String username = request.getParameter("user");
			String pw = request.getParameter("pw");

			// Set a user bean
			UserBean user = new UserBean();
			user.setPw(pw);
			user.setUsername(username);

			// Validate user
			boolean userValid = UserDAO.getInstance().validateUser(user);

			// If the user inserts a valid username and pw, create a session, a
			// cookie
			// and give to him his projects
			if (userValid) {
				List<ProjectBean> projects = ProjectDAO.getInstance().getUserProjects(user);
				if (!projects.isEmpty()) {
					request.setAttribute("projects", projects);
				}
				HttpSession session = request.getSession();
				session.setAttribute("username", username);
				session.setAttribute("idUser", user.getIdUser());
				session.setAttribute("userType", user.getType());
				// Setting session to expires in 30 minutes
				session.setMaxInactiveInterval(30 * 60);

				response.sendRedirect(request.getContextPath() + "/myprojects");
			} else {
				request.setAttribute("errorLogin", true);
				RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/index.jsp");
				dispatcher.forward(request, response);
			}
		} catch (Exception e) {
			LOGGER.log(Level.SEVERE, "Something went wrong during login", e);
		}
	}
}
