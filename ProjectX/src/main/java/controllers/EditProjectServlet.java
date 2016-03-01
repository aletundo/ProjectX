package controllers;

import java.util.List;
import java.io.IOException;
import java.util.ArrayList;
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
import controllers.utils.security.SecureProjectStrategy;
import models.ClientBean;
import models.ClientDAO;
import models.ProjectBean;
import models.ProjectDAO;

@WebServlet("/editproject")
public class EditProjectServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger LOGGER = Logger.getLogger(EditProjectServlet.class.getName());

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			if (!SecureProjectStrategy.getInstance().isAuthorized(request, response, getServletContext()))
				return;
			
			int idProject = Integer.parseInt(request.getParameter("idProject"));
			ProjectBean project = ProjectDAO.getInstance().getProjectInfo(idProject);
			project.setIdProject(idProject);
			request.setAttribute("project", project);
			
			RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/views/edit-project.jsp");
			dispatcher.forward(request, response);
		} catch (Exception e) {
			LOGGER.log(Level.SEVERE, "Something went wrong during getting edit project page", e);
		}
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		try {

			try {

				if (!SecureProjectStrategy.getInstance().isAuthorizedCreate(request, response, getServletContext()))
					return;

				Map<String, String> messages = new HashMap<>();

				request.setAttribute("messages", messages);
				
				//TODO re-use of method checkParameters in class addProjectServlet
				/*if (!checkParameters(request, messages)) {
					RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/views/edit-project.jsp");
					dispatcher.forward(request, response);
					return;
				0}*/
		
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
				
				int idProject = Integer.parseInt(request.getParameter("idProject"));
				project.setIdProject(idProject);
				
				Map<String, Object> attributes = new HashMap<>();
				
				attributes.put("name",name);
				String goals = request.getParameter("goals");
				attributes.put("goals",goals);
				String requirements = request.getParameter("requirements");
				attributes.put("requirements",requirements);
				String clientName = request.getParameter("clientname");
				String clientMail = request.getParameter("clientmail");
				String subjectAreas = request.getParameter("subjectareas");
				attributes.put("subjectAreas",subjectAreas);
				String deadline = request.getParameter("deadline");
				attributes.put("deadline",deadline);
				String start = request.getParameter("start");
				attributes.put("start",start);
				if(request.getParameter("budget") == ""){
					attributes.put("budget", "");
				}else{
					Double budget = Double.parseDouble(request.getParameter("budget"));
					attributes.put("budget", budget);
				}
				if(request.getParameter("estimatedcosts") == ""){
					attributes.put("estimatedCosts","");
				}else{
					double estimatedCosts = Double.parseDouble(request.getParameter("estimatedcosts"));
				attributes.put("estimatedCosts",(Double)estimatedCosts);
				}

				int idClient = ClientDAO.getInstance().getClientByName(clientName);
				if (idClient != Integer.MIN_VALUE)
					project.setIdClient(idClient);
				else {
					client.setName(clientName);
					client.setMail(clientMail);
					idClient = ClientDAO.getInstance().addClient(client);
					project.setIdClient(idClient);
				}
				ProjectDAO.getInstance().updateProject(project, attributes);
				if (idProject != Integer.MIN_VALUE) {
					response.sendRedirect(request.getContextPath() + "/addstages?idProject=" + idProject);
				}
			} catch (Exception e) {
				LOGGER.log(Level.SEVERE, "Something went wrong during adding a project", e);
			}
		
		} catch (Exception e) {
			LOGGER.log(Level.SEVERE, "Something went wrong during editing a project", e);
		}
	}
	
	/*public String checkValue(HttpServletRequest request, String value, String attribute){
		if(request.getParameter(value) == null){
			ProjectBean project = (ProjectBean)request.getAttribute("project");
			
		}
	}*/
}
