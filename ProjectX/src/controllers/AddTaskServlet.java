package controllers;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import controllers.utils.security.SecureStageStrategy;
import models.TaskBean;
import models.TaskDAO;

@WebServlet(name = "AddTaskServlet", urlPatterns = { "/addtask" })
public class AddTaskServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			if (!SecureStageStrategy.getInstance().isAuthorized(request, response, getServletContext()))
				return;

			int idStage = Integer.parseInt(request.getParameter("idStage"));
			request.setAttribute("idStage", idStage);
			RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/views/create-task.jsp");
			dispatcher.forward(request, response);
		} catch (Exception e) {
			/* TODO LOGGER */
		}
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			if (!SecureStageStrategy.getInstance().isAuthorized(request, response, getServletContext()))
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
				response.sendRedirect(request.getContextPath() + "/adddeveloper?idStage=" + idStage + "&idTask="
						+ idTask + "&startDay=" + startDay + "&finishDay=" + finishDay);

		} catch (Exception e) {
			/* TODO LOGGER */
		}
	}
}