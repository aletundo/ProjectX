package controllers.utils.security;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import models.StageDAO;
import models.UserDAO;

public class SecureStageStrategy implements SecureResourcesStrategy {
	
	private static final SecureStageStrategy INSTANCE = new SecureStageStrategy();

	private SecureStageStrategy() {

	}

	public static SecureStageStrategy getInstance() {

		return INSTANCE;

	}
	
	@Override
	public boolean isAuthorizedVisualize(HttpServletRequest request, HttpServletResponse response, ServletContext context)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		// If the session is not valid redirect to login
		if (session == null || session.getAttribute("idUser") == null) {
			response.sendError(403, "Your session is not valid! Try again.");
			return false;
		}
		
		List<Integer> involvedUsers = UserDAO.getInstance().getAllUsersInvolvedByStage(Integer.parseInt(request.getParameter("idStage")));
		
		if(!involvedUsers.contains(session.getAttribute("idUser"))){
			RequestDispatcher dispatcher = context.getRequestDispatcher("/views/access-denied.jsp");
			dispatcher.forward(request, response);
			return false;
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
		HttpSession session = request.getSession();
		// If the session is not valid redirect to login
		if (session == null || session.getAttribute("idUser") == null) {
			response.sendError(403, "Your session is not valid! Try again.");
			return false;
		}

		int[] idAuthorizedUsers = StageDAO.getInstance()
				.checkIdProjectManagerOrSupervisor(Integer.parseInt(request.getParameter("idStage")));
		int idLoggedUser = (Integer) (session.getAttribute("idUser"));
		if (idAuthorizedUsers[0] != idLoggedUser && idAuthorizedUsers[1] != idLoggedUser) {
			RequestDispatcher dispatcher = context.getRequestDispatcher("/views/access-denied.jsp");
			dispatcher.forward(request, response);
			return false;
		}
		return true;
	}

}
