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
import controllers.utils.security.SecureProjectStrategy;
import models.ClientBean;
import models.ClientDAO;
import models.ProjectBean;
import models.ProjectDAO;

@WebServlet(name = "AddProjectServlet", urlPatterns = { "/addproject" })
public class AddProjectServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger LOGGER = Logger.getLogger(AddProjectServlet.class.getName());

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {

			if (!SecureProjectStrategy.getInstance().isAuthorizedCreate(request, response, getServletContext()))
				return;

			Map<String, String> messages = new HashMap<String, String>();

			request.setAttribute("messages", messages);

			if (!checkParameters(request, messages)) {
				RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/views/create-project.jsp");
				dispatcher.forward(request, response);
				return;
			}

			String name = request.getParameter("name");

			if (ProjectDAO.getInstance().checkNameAlreadyExist(name)) {
				messages.put("name",
						"<i class='fa fa-frown-o'></i>&nbsp;Oops! Sorry, name already exist. Try another one.");
				RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/views/create-project.jsp");
				dispatcher.forward(request, response);
				return;
			}

			ProjectBean project = new ProjectBean();
			ClientBean client = new ClientBean();
			Integer idProjectManager = (Integer) request.getSession().getAttribute("idUser");

			String goals = request.getParameter("goals");
			String requirements = request.getParameter("requirements");
			String clientName = request.getParameter("clientname");
			String clientMail = request.getParameter("clientmail");
			String subjectAreas = request.getParameter("subjectareas");
			String deadline = request.getParameter("deadline");
			String start = request.getParameter("start");
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
		} catch (Exception e) {
			LOGGER.log(Level.SEVERE, "Something went wrong during adding a project", e);
		}
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try{
		if (!SecureProjectStrategy.getInstance().isAuthorizedCreate(request, response, getServletContext()))
			return;

		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/views/create-project.jsp");
		dispatcher.forward(request, response);
		}catch(Exception e){
			LOGGER.log(Level.SEVERE, "Something went wrong during getting create project page", e);
		}
	}

	private static boolean checkParameters(HttpServletRequest request, Map<String, String> messages) {

		String name = request.getParameter("name");
		String goals = request.getParameter("goals");
		String requirements = request.getParameter("requirements");
		String clientName = request.getParameter("clientname");
		String clientMail = request.getParameter("clientmail");
		String subjectAreas = request.getParameter("subjectareas");
		String deadline = request.getParameter("deadline");
		String start = request.getParameter("start");
		Double budget = Double.parseDouble(request.getParameter("budget"));
		Double estimatedCosts = Double.parseDouble(request.getParameter("estimatedcosts"));

		if (name == null || name.trim().isEmpty()) {
			messages.put("name", "<i class='fa fa-exclamation'></i>&nbsp;Please, insert a valid name.");
			return false;
		}

		if (goals == null || goals.trim().isEmpty()) {
			messages.put("goals", "<i class='fa fa-exclamation'></i>&nbsp;Please, insert goals.");
			return false;
		}

		if (requirements == null || requirements.trim().isEmpty()) {
			messages.put("requirements", "<i class='fa fa-exclamation'></i>&nbsp;Please, insert requirements.");
			return false;
		}

		if (budget.isNaN()) {
			messages.put("budget", "<i class='fa fa-exclamation'></i>&nbsp;Please, insert a valid budget.");
			return false;
		}

		if (estimatedCosts.isNaN()) {
			messages.put("estimatedcosts", "<i class='fa fa-exclamation'></i>&nbsp;Please, insert valid costs.");
			return false;
		}

		if (clientName == null || clientName.trim().isEmpty()) {
			messages.put("clientname", "<i class='fa fa-exclamation'></i>&nbsp;Please, insert the client.");
			return false;
		}

		if (clientMail == null || clientMail.trim().isEmpty() || !UtilityFunctions.isValidMail(clientMail)) {
			messages.put("clientmail", "<i class='fa fa-exclamation'></i>&nbsp;Please, insert a valid mail.");
			return false;
		}

		if (start == null || start.trim().isEmpty() || !UtilityFunctions.isValidDateFormat(start)) {

			messages.put("start", "<i class='fa fa-exclamation'></i>&nbsp;Please, insert a valid one.");
			return false;
		}

		if (deadline == null || deadline.trim().isEmpty() || !UtilityFunctions.isValidDateFormat(deadline)) {
			messages.put("deadline", "<i class='fa fa-exclamation'></i>&nbsp;Please, insert a valid one.");
			return false;
		}

		if (subjectAreas == null || subjectAreas.trim().isEmpty()) {
			messages.put("subjectareas", "<i class='fa fa-exclamation'></i>&nbsp;Please, insert a valid one.");
			return false;
		}

		return true;
	}

}
