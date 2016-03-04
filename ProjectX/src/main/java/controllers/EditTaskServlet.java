package controllers;

import java.io.IOException;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import controllers.utils.UtilityFunctions;
import controllers.utils.security.SecureStageStrategy;
import models.TaskBean;
import models.TaskDAO;

@WebServlet("/edittask")
public class EditTaskServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private static final Logger LOGGER = Logger.getLogger(EditProjectServlet.class.getName());

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {

			if (!SecureStageStrategy.getInstance().isAuthorized(request, response, getServletContext()))
				return;

			int idTask = Integer.parseInt(request.getParameter("idTask"));
			TaskBean task = TaskDAO.getInstance().getTaskInfo(idTask);

			HttpSession session = request.getSession();
			request.setAttribute("task", task);
			session.setAttribute("task", task);

			RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/views/edit-task.jsp");
			dispatcher.forward(request, response);
		} catch (Exception e) {
			LOGGER.log(Level.SEVERE, "Something went wrong during getting edit project page", e);
		}
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {

			if (!SecureStageStrategy.getInstance().isAuthorized(request, response, getServletContext()))
				return;

			Map<String, String> messages = new HashMap<>();

			request.setAttribute("messages", messages);

			if (!checkParameters(request, messages)) {
				RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/views/edit-task.jsp");
				dispatcher.forward(request, response);
				return;
			}

			String name = request.getParameter("name");

			int idStage = Integer.parseInt(request.getParameter("idStage"));

			TaskBean task = new TaskBean();

			int idTask = Integer.parseInt(request.getParameter("idTask"));
			task.setIdTask(idTask);

			Map<String, Object> attributes = new HashMap<>();

			attributes.put("name", name);
			String finishDay = request.getParameter("finishday");
			attributes.put("finishDay", finishDay);
			String startDay = request.getParameter("startday");
			attributes.put("startDay", startDay);

			TaskBean oldTask = (TaskBean) request.getSession().getAttribute("task");
			String oldFinishDay = oldTask.getFinishDay();

			request.getSession().removeAttribute("task");

			TaskDAO.getInstance().updateTask(task, attributes);
			if (idTask != Integer.MIN_VALUE)
				response.sendRedirect(request.getContextPath() + "/adddeveloper?idStage=" + idStage + "&idTask="
						+ idTask + "&startDay=" + oldFinishDay + "&finishDay=" + finishDay);
		} catch (Exception e) {
			LOGGER.log(Level.SEVERE, "Something went wrong during editing task", e);
		}

	}

	private static boolean checkParameters(HttpServletRequest request, Map<String, String> messages) {
		String finishDay = request.getParameter("finishday");
		String startDay = request.getParameter("startday");

		if (!startDay.equals("") && !UtilityFunctions.isValidDateFormat(startDay)) {

			messages.put("startday", "<i class='fa fa-exclamation'></i>&nbsp;Please, insert a valid one.");
			return false;
		}

		if (!finishDay.equals("") && !UtilityFunctions.isValidDateFormat(finishDay)) {
			messages.put("finishday", "<i class='fa fa-exclamation'></i>&nbsp;Please, insert a valid one.");
			return false;
		}

		return true;
	}
}
