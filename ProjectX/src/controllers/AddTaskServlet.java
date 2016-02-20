package controllers;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import models.TaskBean;
import models.TaskDAO;

@WebServlet(name = "AddTaskServlet", urlPatterns = { "/addtask" })
public class AddTaskServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession(false);
		if (session == null || session.getAttribute("idUser") == null) {
			response.sendRedirect(request.getContextPath());
		} else {
			RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/views/create-task.jsp");
			dispatcher.forward(request, response);
		}
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession(false);
		if (session == null || session.getAttribute("idUser") == null) {
			response.sendRedirect(request.getContextPath());
		} else {
			TaskBean task = new TaskBean();
			// Get parameters
			int idStage = 3;
			String startDay = request.getParameter("start-day");
			String finishDay = request.getParameter("finish-day");

			// Set the bean
			task.setStartDay(startDay);
			task.setFinishDay(finishDay);
			task.setIdStage(idStage);

			int idTask = TaskDAO.getInstance().createTask(task);

			if (idTask != 0)
				response.sendRedirect(
						request.getContextPath() + "/add-developer?idTask=" + idTask);
		}

	}
}