package controllers.utils.security;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import models.ProjectDAO;
import javax.servlet.ServletContext;

public class SecureProjectStrategy implements SecureResourcesStrategy {
	

	private static final SecureProjectStrategy INSTANCE = new SecureProjectStrategy();

	private SecureProjectStrategy() {

	}

	public static SecureProjectStrategy getInstance() {

		return INSTANCE;

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
		HttpSession session = request.getSession();
		// If the session is not valid redirect to login
		if (session == null || session.getAttribute("idUser") == null) {
			response.sendError(403, "Your session is not valid! Try again.");
			//response.sendRedirect(request.getContextPath());
			return false;
		}

		if (!"ProjectManager".equals(session.getAttribute("userType"))) {
			RequestDispatcher dispatcher = context.getRequestDispatcher("/views/access-denied.jsp");
			dispatcher.forward(request, response);
			return false;
		}
		return true;
	}

}
