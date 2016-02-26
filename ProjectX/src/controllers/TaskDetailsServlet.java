package controllers;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import controllers.utils.security.SecureTaskStrategy;
import models.TaskBean;
import models.TaskDAO;
import models.UserBean;

@WebServlet("/task")
public class TaskDetailsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			if (!SecureTaskStrategy.getInstance().isAuthorizedVisualize(request, response, getServletContext()))
				return;

			RequestDispatcher dispatcher;
			int idTask = Integer.parseInt(request.getParameter("idTask"));

			TaskBean taskInfo = TaskDAO.getInstance().getTaskInfo(idTask);
			List<UserBean> developers = TaskDAO.getInstance().getAllDevelopersByIdTask(idTask);

			request.setAttribute("task", taskInfo);
			request.setAttribute("developers", developers);
			dispatcher = getServletContext().getRequestDispatcher("/views/visualize-task.jsp");
			dispatcher.forward(request, response);
		} catch (Exception e) {
			/* TODO LOGGER */
		}
	}
}
