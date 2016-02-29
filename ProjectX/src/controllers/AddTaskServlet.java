package controllers;

import java.io.IOException;
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

import controllers.utils.UtilityFunctions;
import controllers.utils.security.SecureStageStrategy;
import models.TaskBean;
import models.TaskDAO;

@WebServlet(name = "AddTaskServlet", urlPatterns = { "/addtask" })
public class AddTaskServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger LOGGER = Logger.getLogger(AddTaskServlet.class.getName());

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
		} catch (Exception e){
			LOGGER.log(Level.SEVERE, "Something went wrong during getting create task page", e);
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
				RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/views/create-task.jsp");
				dispatcher.forward(request, response);
				return;
			}
			
			TaskBean task = new TaskBean();
			// Get parameters
			int idStage = Integer.parseInt(request.getParameter("idStage"));
			String name = request.getParameter("name");
			String startDay = request.getParameter("startday");
			String finishDay = request.getParameter("finishday");

			// Set the bean
			task.setIdStage(idStage);
			task.setName(name);
			task.setStartDay(startDay);
			task.setFinishDay(finishDay);

			int idTask = TaskDAO.getInstance().createTask(task);

			if (idTask != Integer.MIN_VALUE)
				response.sendRedirect(request.getContextPath() + "/adddeveloper?idStage=" + idStage + "&idTask="
						+ idTask + "&startDay=" + startDay + "&finishDay=" + finishDay);

		} catch (Exception e) {
			LOGGER.log(Level.SEVERE, "Something went wrong during adding tasks", e);
		}
	}
	
	private static boolean checkParameters(HttpServletRequest request, Map<String, String> messages) {

		String name = request.getParameter("name");
		String finishDay = request.getParameter("finishday");
		String startDay = request.getParameter("startday");

		if (name == null || name.trim().isEmpty()) {
			messages.put("name", "<i class='fa fa-exclamation'></i>&nbsp;Please, insert a valid name.");
			return false;
		}

		if (startDay == null || startDay.trim().isEmpty() || !UtilityFunctions.isValidDateFormat(startDay)) {

			messages.put("startday", "<i class='fa fa-exclamation'></i>&nbsp;Please, insert a valid one.");
			return false;
		}

		if (finishDay == null || finishDay.trim().isEmpty() || !UtilityFunctions.isValidDateFormat(finishDay)) {
			messages.put("finishday", "<i class='fa fa-exclamation'></i>&nbsp;Please, insert a valid one.");
			return false;
		}

		return true;
	}
}