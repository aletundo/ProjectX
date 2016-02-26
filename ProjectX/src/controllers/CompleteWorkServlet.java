package controllers;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import controllers.utils.UpdateRateCompleted;
import controllers.utils.security.SecureTaskStrategy;
import models.TaskBean;
import models.TaskDAO;

@WebServlet("/setworkcompleted")
public class CompleteWorkServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			if (!SecureTaskStrategy.getInstance().isAuthorized(request, response, getServletContext()))
				return;

			TaskBean task = new TaskBean();
			task.setIdTask(Integer.parseInt(request.getParameter("idTask")));
			task.setIdDeveloper((Integer) request.getSession().getAttribute("idUser"));

			TaskDAO.getInstance().setPieceWorkCompleted(task);
			UpdateRateCompleted.checkTaskCompleted(task.getIdTask());

			response.sendRedirect(request.getContextPath() + "/task?idTask=" + task.getIdTask());

		} catch (Exception e) {
			/* TODO LOGGER */
		}
	}
}