package controllers.utils.security;

import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import models.ProjectDAO;
import models.UserDAO;

import javax.servlet.ServletContext;

public class SecureProjectStrategy implements SecureResourcesStrategy {

	private static final SecureProjectStrategy INSTANCE = new SecureProjectStrategy();
	private static final Logger LOGGER = Logger.getLogger(SecureProjectStrategy.class.getName());

	private SecureProjectStrategy() {

	}

	public static SecureProjectStrategy getInstance() {

		return INSTANCE;

	}

	@Override
	public boolean isAuthorizedVisualize(HttpServletRequest request, HttpServletResponse response,
			ServletContext context) throws ServletException, IOException {
		try {
			HttpSession session = request.getSession();
			// If the session is not valid redirect to login
			if (session == null || session.getAttribute("idUser") == null) {
				response.sendError(403, "Your session is not valid! Try again.");
				return false;
			}

			List<Integer> involvedUsers = UserDAO.getInstance()
					.getAllUsersInvolvedByProject(Integer.parseInt(request.getParameter("idProject")));

			if (!involvedUsers.contains(session.getAttribute("idUser"))) {
				RequestDispatcher dispatcher = context.getRequestDispatcher("/views/access-denied.jsp");
				dispatcher.forward(request, response);
				return false;
			}
		} catch (Exception e) {
			LOGGER.log(Level.SEVERE, "Something went wrong during authorize to visualize a project", e);
		}
		return true;
	}

	@Override
	/**
	 * @param request
	 * @param response
	 * @throws IOException
	 * @throws ServletException
	 * @return boolean
	 */
	public boolean isAuthorized(HttpServletRequest request, HttpServletResponse response, ServletContext context)
			throws IOException, ServletException {
		try {
			HttpSession session = request.getSession();
			// If the session is not valid redirect to login
			if (session == null || session.getAttribute("idUser") == null) {
				response.sendError(403, "Your session is not valid! Try again.");
				return false;
			}

			int idProjectManager = ProjectDAO.getInstance()
					.getProjectManagerId(Integer.parseInt(request.getParameter("idProject")));
			if (idProjectManager != (Integer) (session.getAttribute("idUser"))) {
				RequestDispatcher dispatcher = context.getRequestDispatcher("/views/access-denied.jsp");
				dispatcher.forward(request, response);
				return false;
			}
		} catch (Exception e) {
			LOGGER.log(Level.SEVERE, "Something went wrong during authorize to access  project", e);
		}
		return true;
	}

	/**
	 * @param request
	 * @param response
	 * @throws IOException
	 * @throws ServletException
	 * @return boolean
	 */
	public boolean isAuthorizedCreate(HttpServletRequest request, HttpServletResponse response, ServletContext context)
			throws IOException, ServletException {
		try {
			HttpSession session = request.getSession();
			// If the session is not valid redirect to login
			if (session == null || session.getAttribute("idUser") == null) {
				response.sendError(403, "Your session is not valid! Try again.");
				return false;
			}

			if (!"ProjectManager".equals(session.getAttribute("userType"))) {
				RequestDispatcher dispatcher = context.getRequestDispatcher("/views/access-denied.jsp");
				dispatcher.forward(request, response);
				return false;
			}
		} catch (Exception e) {
			LOGGER.log(Level.SEVERE, "Something went wrong during authorize to create a project", e);
		}
		return true;
	}

}
