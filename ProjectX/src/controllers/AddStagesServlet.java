package controllers;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import models.ProjectDAO;
import models.StageBean;
import models.StageDAO;

@WebServlet(name = "AddStagesServlet", urlPatterns = { "/addstages" })
public class AddStagesServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		if (!isAuthorized(request, response))
			return;

		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/views/create-stage.jsp");
		dispatcher.forward(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		if (!isAuthorized(request, response))
			return;

		StageBean stage = new StageBean();
		// Get parameters
		int idProject = Integer.parseInt(request.getParameter("idProject")); //From URL
		String name = request.getParameter("name");
		String goals = request.getParameter("goals");
		String requirements = request.getParameter("requirements");
		String startDay = request.getParameter("start-day");
		String finishDay = request.getParameter("finish-day");
		int estimatedDuration = Integer.parseInt(request.getParameter("estimated-duration"));

		// Set the bean
		stage.setName(name);
		stage.setGoals(goals);
		stage.setRequirements(requirements);
		stage.setStartDay(startDay);
		stage.setFinishDay(finishDay);
		stage.setEstimatedDuration(estimatedDuration);
		stage.setIdProject(idProject);

		int idStage = StageDAO.getInstance().createStage(stage);

		if (idStage != 0)
			response.sendRedirect(request.getContextPath() + "/addsupervisor?idProject=" + idProject + "&idStage="
					+ idStage + "&startDay=" + startDay + "&finishDay=" + finishDay);
	}

	private boolean isAuthorized(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		HttpSession session = request.getSession();
		// If the session is not valid redirect to login
		if (session == null || session.getAttribute("idUser") == null) {
			response.sendRedirect(request.getContextPath());
			return false;
		}

		int idProjectManager = ProjectDAO.getInstance()
				.getProjectManagerId(Integer.parseInt(request.getParameter("idProject")));
		if (idProjectManager != (Integer) (session.getAttribute("idUser"))) {
			RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/views/access-denied.jsp");
			dispatcher.forward(request, response);
			return false;
		}
		return true;
	}
}
