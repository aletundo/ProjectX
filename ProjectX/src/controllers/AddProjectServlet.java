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
import models.ClientBean;
import models.ClientDAO;
import models.ProjectBean;
import models.ProjectDAO;

@WebServlet(name = "AddProjectServlet", urlPatterns = { "/addproject" })
public class AddProjectServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		if (!SecureProjectStrategy.getInstance().isAuthorizedCreate(request, response, getServletContext()))
			return;

		Map<String, String> messages = new HashMap<String, String>();

		request.setAttribute("messages", messages);

		if (!checkParameters(request, messages)) {
			RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/views/create-project.jsp");
			dispatcher.forward(request, response);
			return;
		}

		ProjectBean project = new ProjectBean();
		ClientBean client = new ClientBean();
		Integer idProjectManager = (Integer) request.getSession().getAttribute("idUser");

		String name = request.getParameter("name");
		String goals = request.getParameter("goals");
		String requirements = request.getParameter("requirements");
		String clientName = request.getParameter("clientname");
		String clientMail = request.getParameter("clientmail");
		String subjectAreas = request.getParameter("subjectareas");
		String deadline = request.getParameter("deadline"); // yyyy-MM-dd
		String start = request.getParameter("start"); // yyyy-MM-dd
		double budget = Double.parseDouble(request.getParameter("budget"));
		double estimatedCosts = Double.parseDouble(request.getParameter("estimatedcosts"));

		project.setName(name);
		project.setBudget(budget);
		project.setGoals(goals);
		project.setRequirements(requirements);
		project.setSubjectAreas(subjectAreas);
		project.setEstimatedCosts(estimatedCosts);
		project.setStart(start);
		project.setDeadline(deadline);
		project.setIdProjectManager(idProjectManager);

		int idClient = ClientDAO.getInstance().getClientByName(clientName);
		if (idClient != Integer.MIN_VALUE)
			project.setIdClient(idClient);
		else {
			client.setName(clientName);
			client.setMail(clientMail);
			idClient = ClientDAO.getInstance().addClient(client);
			project.setIdClient(idClient);
		}
		int idProject = ProjectDAO.getInstance().addProject(project);
		if (idProject != Integer.MIN_VALUE) {
			response.sendRedirect(request.getContextPath() + "/addstages?idProject=" + idProject);
		}
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		if (!SecureProjectStrategy.getInstance().isAuthorizedCreate(request, response, getServletContext()))
			return;

		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/views/create-project.jsp");
		dispatcher.forward(request, response);
	}

	private boolean checkParameters(HttpServletRequest request, Map<String, String> messages) {

		String name = request.getParameter("name");
		String goals = request.getParameter("goals");
		String requirements = request.getParameter("requirements");
		String clientName = request.getParameter("clientname");
		String clientMail = request.getParameter("clientmail");
		String subjectAreas = request.getParameter("subjectareas");
		String deadline = request.getParameter("deadline"); // yyyy-MM-dd
		String start = request.getParameter("start"); // yyyy-MM-dd
		Double budget = Double.parseDouble(request.getParameter("budget"));
		Double estimatedCosts = Double.parseDouble(request.getParameter("estimatedcosts"));

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
		
		if (budget.isNaN()) {
			messages.put("budget", "Please, insert a valid budget.");
			return false;
		}

		if (estimatedCosts.isNaN()) {
			messages.put("estimatedcosts", "Please, insert valid costs.");
			return false;
		}

		if (clientName == null || clientName.trim().isEmpty()) {
			messages.put("clientname", "Please, insert the client.");
			return false;
		}

		if (clientMail == null || clientMail.trim().isEmpty() || !UtilityFunctions.isValidMail(clientMail)) {
			messages.put("clientmail", "Please, insert a valid mail.");
			return false;
		}

		
		if (start == null || start.trim().isEmpty() || !UtilityFunctions.isValidDateFormat(start)) {

			messages.put("start", "Please, insert a valid one.");
			return false;
		}

		if (deadline == null || deadline.trim().isEmpty() || !UtilityFunctions.isValidDateFormat(deadline)) {
			messages.put("deadline", "Please, insert a valid one.");
			return false;
		}
		
		if (subjectAreas == null || subjectAreas.trim().isEmpty()) {
			messages.put("subjectareas", "Please, insert a valid one.");
			return false;
		}

		return true;
	}

}
