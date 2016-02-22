package controllers;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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
		if(!isAuthorized(request, response))
			return;

		ProjectBean project = new ProjectBean();
		ClientBean client = new ClientBean();
		Integer idProjectManager = (Integer) request.getSession().getAttribute("idUser");

		// Get parameters
		String name = request.getParameter("name");
		double budget = Double.parseDouble(request.getParameter("budget"));
		String goals = request.getParameter("goals");
		String requirements = request.getParameter("requirements");
		String clientName = request.getParameter("client-name");
		String clientMail = request.getParameter("client-mail");
		String subjectAreas = request.getParameter("subject-areas");
		int estimatedDuration = Integer.parseInt(request.getParameter("estimated-duration"));
		double estimatedCosts = Double.parseDouble(request.getParameter("estimated-costs"));
		String deadline = request.getParameter("deadline"); // yyyy-MM-dd
		String start = request.getParameter("start"); // yyyy-MM-dd

		// Set the bean
		project.setName(name);
		project.setBudget(budget);
		project.setGoals(goals);
		project.setRequirements(requirements);
		project.setSubjectAreas(subjectAreas);
		project.setEstimatedCosts(estimatedCosts);
		project.setEstimatedDuration(estimatedDuration);
		project.setStart(start);
		project.setDeadline(deadline);
		project.setIdProjectManager(idProjectManager);
		client.setName(clientName);
		client.setMail(clientMail);

		// Add the client and retrieve his id
		int idClient = ClientDAO.getInstance().addClient(client);
		project.setIdClient(idClient);
		int idProject = ProjectDAO.getInstance().addProject(project);
		if (idProject != 0) {
			response.sendRedirect(request.getContextPath() + "/addstages?idProject=" + idProject);
		}
	}

	/**
	 * @param request
	 * @param response
	 * @throws IOException
	 * @throws ServletException
	 * @return boolean
	 */
	private boolean isAuthorized(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		HttpSession session = request.getSession();
		// If the session is not valid redirect to login
		if (session == null || session.getAttribute("idUser") == null) {
			response.sendRedirect(request.getContextPath());
			return false;
		}

		if (!"ProjectManager".equals(session.getAttribute("userType"))) {
			RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/views/access-denied.jsp");
			dispatcher.forward(request, response);
			return false;
		}
		return true;
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		if(!isAuthorized(request, response))
			return;

		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/views/create-project.jsp");
		dispatcher.forward(request, response);
	}

}
