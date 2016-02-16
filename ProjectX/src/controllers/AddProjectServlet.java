package controllers;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.ClientBean;
import models.ClientDAO;
import models.ProjectBean;
import models.ProjectDAO;

/**
 * Servlet implementation class AddProjectServlet
 */
@WebServlet(name = "AddProjectServlet", urlPatterns = { "/addproject" })
public class AddProjectServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		ProjectBean project = new ProjectBean();
		ClientBean client = new ClientBean();
		Integer idProjectManager = (Integer) request.getSession().getAttribute("idUser");
		String userType = request.getSession().getAttribute("userType").toString();
		
		if(!userType.equals("ProjectManager")){
			//MUST BE DECIDED WHERE REDIRECT THE USER!!!!!!!!!!!!
			response.sendRedirect(request.getContextPath());
		}	
		
		String name = request.getParameter("name");
		double budget = Double.parseDouble(request.getParameter("budget"));
		String goals = request.getParameter("goals");
		String requirements = request.getParameter("requirements");
		String clientName = request.getParameter("client-name");
		String clientMail = request.getParameter("client-mail");
		String subjectAreas = request.getParameter("subject-areas");
		int estimatedDuration = Integer.parseInt(request.getParameter("estimated-duration"));
		double estimatedCosts = Double.parseDouble(request.getParameter("estimated-costs"));
		String deadline = request.getParameter("deadline"); //yyyy-MM-dd
		
		project.setName(name);
		project.setBudget(budget);
		project.setGoals(goals);
		project.setRequirements(requirements);
		project.setSubjectAreas(subjectAreas);
		project.setEstimatedCosts(estimatedCosts);
		project.setEstimatedDuration(estimatedDuration);
		project.setDeadline(deadline);
		project.setIdProjectManager(idProjectManager);
		client.setName(clientName);
		client.setMail(clientMail);
		
		int idClient = ClientDAO.getInstance().addClient(client);
		project.setIdClient(idClient);
		boolean added = ProjectDAO.getInstance().addProject(project);	
		System.out.println(added);
	}
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/views/create-project.jsp");
		dispatcher.forward(request, response);
	}

}
