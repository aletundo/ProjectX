package controllers;

import java.io.IOException;
import java.util.List;

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
 * Servlet implementation class SearchProjects
 */
@WebServlet("/SearchProjects")
public class SearchProjects extends HttpServlet {
	private static final long serialVersionUID = 1L;
    

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/views/create-project.jsp");
		dispatcher.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		
		Integer idProjectManager = (Integer) request.getSession().getAttribute("idUser");
		String userType = request.getSession().getAttribute("userType").toString();
		
		if(!userType.equals("ProjectManager")){
			//MUST BE DECIDED WHERE REDIRECT THE USER!!!!!!!!!!!!
			response.sendRedirect(request.getContextPath());
		}	
		
		
		String subjectArea = request.getParameter("subject-areas");
		
		
		List<ProjectBean> projects = ProjectDAO.getInstance().searchProjects(subjectArea);	
		
	}

}
