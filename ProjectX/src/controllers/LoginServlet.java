package controllers;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import models.ProjectBean;
import models.ProjectDAO;
import models.UserBean;
import models.UserDAO;

@WebServlet(name = "LoginServlet", urlPatterns = { "/myprojects" })
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// Get query parameters
		String username = request.getParameter("user");
		String pw = request.getParameter("pw");
		RequestDispatcher dispatcher;

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
			session.setAttribute("user", username);
			// Setting session to expiry in 30 minuntes
			session.setMaxInactiveInterval(30 * 60);
			Cookie userName = new Cookie("user", username);
			userName.setMaxAge(30 * 60);
			response.addCookie(userName);

			dispatcher = getServletContext().getRequestDispatcher("/views/myprojects.jsp");
			dispatcher.forward(request, response);
		} else {
			// Redirect to home(login page)
			response.sendRedirect(request.getContextPath());
		}
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// No possibility to get the resources before login
		response.sendRedirect(request.getContextPath());

	}
}
