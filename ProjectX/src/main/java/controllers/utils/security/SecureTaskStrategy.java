package controllers.utils.security;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import models.TaskDAO;
import models.UserBean;
import models.UserDAO;

public class SecureTaskStrategy implements SecureResourcesStrategy {

	private static final SecureTaskStrategy INSTANCE = new SecureTaskStrategy();
	private static final Logger LOGGER = Logger.getLogger(SecureTaskStrategy.class.getName());

	private SecureTaskStrategy() {

	}

	public static SecureTaskStrategy getInstance() {

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
					.getAllUsersInvolvedByTask(Integer.parseInt(request.getParameter("idTask")));

			if (!involvedUsers.contains(session.getAttribute("idUser"))) {
				RequestDispatcher dispatcher = context.getRequestDispatcher("/views/access-denied.jsp");
				dispatcher.forward(request, response);
				return false;
			}
		} catch (Exception e) {
			LOGGER.log(Level.SEVERE, "Something went wrong during authorize to access a task", e);
		}
		return true;
	}

	@Override
	public boolean isAuthorized(HttpServletRequest request, HttpServletResponse response, ServletContext context)
			throws IOException, ServletException {
		try {
			HttpSession session = request.getSession();
			// If the session is not valid redirect to login
			if (session == null || session.getAttribute("idUser") == null) {
				response.sendError(403, "Your session is not valid! Try again.");
				return false;
			}

			List<UserBean> developersBean = TaskDAO.getInstance()
					.getAllDevelopersByIdTask(Integer.parseInt(request.getParameter("idTask")));

			List<Integer> developersId = new ArrayList<>();
			for (UserBean u : developersBean) {
				developersId.add(u.getIdUser());
			}

			if (!developersId.contains(session.getAttribute("idUser"))) {
				RequestDispatcher dispatcher = context.getRequestDispatcher("/views/access-denied.jsp");
				dispatcher.forward(request, response);
				return false;
			}
		} catch (Exception e) {
			LOGGER.log(Level.SEVERE, "Something went wrong during authorize to visualize a task", e);
		}
		return true;
	}

}
