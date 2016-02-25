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
import models.TaskBean;
import models.TaskDAO;

@WebServlet(name = "AddTaskServlet", urlPatterns = { "/addtask" })
public class AddTaskServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		if (!isAuthorized(request, response))
			return;

		int idStage = Integer.parseInt(request.getParameter("idStage"));
		request.setAttribute("idStage", idStage);
		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/views/create-task.jsp");
		dispatcher.forward(request, response);

	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		if (!isAuthorized(request, response))
			return;
		TaskBean task = new TaskBean();
		// Get parameters
		int idStage = Integer.parseInt(request.getParameter("idStage"));
		String name = request.getParameter("name");
		String startDay = request.getParameter("start-day");
		String finishDay = request.getParameter("finish-day");

		// Set the bean
		task.setIdStage(idStage);
		task.setName(name);
		task.setStartDay(startDay);
		task.setFinishDay(finishDay);

		int idTask = TaskDAO.getInstance().createTask(task);

		if (idTask != 0)
			response.sendRedirect(request.getContextPath() + "/add-developer?idStage=" + idStage + "&idTask=" + idTask
					+ "&startDay=" + startDay + "&finishDay=" + finishDay);

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