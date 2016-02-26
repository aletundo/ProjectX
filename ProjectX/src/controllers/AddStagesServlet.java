package controllers;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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

			StageBean stage = new StageBean();
			// Get parameters
			int idProject = Integer.parseInt(request.getParameter("idProject")); // From
																					// URL
			String name = request.getParameter("name");
			String goals = request.getParameter("goals");
			String requirements = request.getParameter("requirements");
			String startDay = request.getParameter("start-day");
			String finishDay = request.getParameter("finish-day");

			// Set the bean
			stage.setName(name);
			stage.setGoals(goals);
			stage.setRequirements(requirements);
			stage.setStartDay(startDay);
			stage.setFinishDay(finishDay);
			stage.setIdProject(idProject);

			int idStage = StageDAO.getInstance().createStage(stage);

			if (idStage != 0)
				response.sendRedirect(request.getContextPath() + "/addsupervisor?idProject=" + idProject + "&idStage="
						+ idStage + "&startDay=" + startDay + "&finishDay=" + finishDay);
		} catch (Exception e) {
			/* TODO LOGGER */
		}
	}
}
