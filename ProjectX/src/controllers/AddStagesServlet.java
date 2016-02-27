package controllers;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import controllers.utils.UtilityFunctions;
import controllers.utils.security.SecureProjectStrategy;
import models.StageBean;
import models.StageDAO;

@WebServlet(name = "AddStagesServlet", urlPatterns = { "/addstages" })
public class AddStagesServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			if (!SecureProjectStrategy.getInstance().isAuthorized(request, response, getServletContext()))
				return;

			RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/views/create-stage.jsp");
			dispatcher.forward(request, response);
		} catch (Exception e) {
			/* TODO LOGGER */
		}
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			if (!SecureProjectStrategy.getInstance().isAuthorized(request, response, getServletContext()))
				return;
			
			Map<String, String> messages = new HashMap<String, String>();

			request.setAttribute("messages", messages);

			if (!checkParameters(request, messages)) {
				RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/views/create-stage.jsp");
				dispatcher.forward(request, response);
				return;
			}

			StageBean stage = new StageBean();
			// Get parameters
			int idProject = Integer.parseInt(request.getParameter("idProject")); 
			String name = request.getParameter("name");
			String goals = request.getParameter("goals");
			String requirements = request.getParameter("requirements");
			String startDay = request.getParameter("startday");
			String finishDay = request.getParameter("finishday");

			// Set the bean
			stage.setName(name);
			stage.setGoals(goals);
			stage.setRequirements(requirements);
			stage.setStartDay(startDay);
			stage.setFinishDay(finishDay);
			stage.setIdProject(idProject);

			int idStage = StageDAO.getInstance().createStage(stage);

			if (idStage != Integer.MIN_VALUE)
				response.sendRedirect(request.getContextPath() + "/addsupervisor?idProject=" + idProject + "&idStage="
						+ idStage + "&startDay=" + startDay + "&finishDay=" + finishDay);
		} catch (Exception e) {
			/* TODO LOGGER */
		}
	}
	
	private boolean checkParameters(HttpServletRequest request, Map<String, String> messages) {

		String name = request.getParameter("name");
		String goals = request.getParameter("goals");
		String requirements = request.getParameter("requirements");
		String finishDay = request.getParameter("finishday");
		String startDay = request.getParameter("startday");

		if (name == null || name.trim().isEmpty()) {
			messages.put("name", "Please, insert a valid name.");
			return false;
		}

		if (goals == null || goals.trim().isEmpty()) {
			messages.put("goals", "Please, insert goals.");
			return false;
		}

		if (requirements == null || requirements.trim().isEmpty()) {
			messages.put("requirements", "Please, insert requirements.");
			return false;
		}

		
		if (startDay == null || startDay.trim().isEmpty() || !UtilityFunctions.isValidDateFormat(startDay)) {

			messages.put("startday", "Please, insert a valid one.");
			return false;
		}

		if (finishDay == null || finishDay.trim().isEmpty() || !UtilityFunctions.isValidDateFormat(finishDay)) {
			messages.put("finishday", "Please, insert a valid one.");
			return false;
		}

		return true;
	}
}
