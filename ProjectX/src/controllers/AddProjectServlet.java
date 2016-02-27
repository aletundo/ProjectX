package controllers;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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

		ProjectBean project = new ProjectBean();
		ClientBean client = new ClientBean();
		Map<String, String> messages = new HashMap<String, String>();
        request.setAttribute("messages", messages);
		Integer idProjectManager = (Integer) request.getSession().getAttribute("idUser");

		if(!checkParameters(request, messages))
		{
			RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/views/create-project.jsp");
			dispatcher.forward(request, response);
		}
			
			
		String name = request.getParameter("name");
		String goals = request.getParameter("goals");
		String requirements = request.getParameter("requirements");
		String clientName = request.getParameter("client-name");
		String clientMail = request.getParameter("client-mail");
		String subjectAreas = request.getParameter("subject-areas");
		String deadline = request.getParameter("deadline"); // yyyy-MM-dd
		String start = request.getParameter("start"); // yyyy-MM-dd
		double budget = Double.parseDouble(request.getParameter("budget"));
		double estimatedCosts = Double.parseDouble(request.getParameter("estimated-costs"));

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
		String clientName = request.getParameter("client-name");
		String clientMail = request.getParameter("client-mail");
		String subjectAreas = request.getParameter("subject-areas");
		String deadline = request.getParameter("deadline"); // yyyy-MM-dd
		String start = request.getParameter("start"); // yyyy-MM-dd
		Double budget = Double.parseDouble(request.getParameter("budget"));
		Double estimatedCosts = Double.parseDouble(request.getParameter("estimated-costs"));

		if (name == null || name.trim().isEmpty())
		{
			messages.put("name", "Please, insert a valid one.");
			return false;
		}

		if (goals == null || goals.trim().isEmpty())
		{
			messages.put("goals", "Please, insert a valid one.");
			return false;
		}

		if (requirements == null || requirements.trim().isEmpty())
		{
			messages.put("requirements", "Please, insert a valid one.");
			return false;
		}

		if (clientName == null || clientName.trim().isEmpty())
		{
			messages.put("client-name", "Please, insert a valid one.");
			return false;
		}

		if (clientMail == null || clientMail.trim().isEmpty() || !isValidMail(clientMail))
		{
			messages.put("client-mail", "Please, insert a valid one.");
			return false;
		}

		if (subjectAreas == null || subjectAreas.trim().isEmpty())
		{
			messages.put("subjectAreas", "Please, insert a valid one.");
			return false;
		}

		if (deadline == null || deadline.trim().isEmpty())
		{
			messages.put("deadlin", "Please, insert a valid one.");
			return false;
		}

		if (start == null || start.trim().isEmpty())
		{
			messages.put("start", "Please, insert a valid one.");
			return false;
		}
		
		if (budget.isNaN())
		{
			messages.put("budget", "Please, insert a valid one.");
			return false;
		}
		
		if (estimatedCosts.isNaN())
		{
			messages.put("estimated-costs", "Please, insert a valid one.");
			return false;
		}

		return true;
	}

	private static boolean isValidMail(String email) {
		final Pattern VALID_EMAIL_ADDRESS_REGEX = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$",
				Pattern.CASE_INSENSITIVE);

		Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(email);
		return matcher.find();
	}

}
