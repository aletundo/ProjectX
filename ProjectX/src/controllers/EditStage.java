package controllers;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import models.StageDAO;

@WebServlet("/editstage")
public class EditStage extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		if (!isAuthorized(request, response)) {
			return;
		}

		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/views/edit-stage.jsp");
		dispatcher.forward(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

	@Override
	protected void doDelete(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @param request
	 * @param response
	 * @throws IOException
	 * @throws ServletException
	 * @return boolean
	 */
	private boolean isAuthorized(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		HttpSession session = request.getSession();
		// If the session is not valid redirect to login
		if (session == null || session.getAttribute("idUser") == null) {
			response.sendRedirect(request.getContextPath());
			return false;
		}

		int[] idAuthorizedUsers = StageDAO.getInstance()
				.getAuthorizedUsers(Integer.parseInt(request.getParameter("idStage")));
		int idLoggedUser = (Integer) (session.getAttribute("idUser"));
		if (idAuthorizedUsers[0] != idLoggedUser && idAuthorizedUsers[1] != idLoggedUser) {
			RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/views/access-denied.jsp");
			dispatcher.forward(request, response);
			return false;
		}
		return true;
	}

}
