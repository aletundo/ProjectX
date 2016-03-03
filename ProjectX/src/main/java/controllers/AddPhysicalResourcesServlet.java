package controllers;

import java.io.IOException;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
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
import models.PhysicalResourceBean;
import models.PhysicalResourceDAO;

@WebServlet("/addphysicalresources")
public class AddPhysicalResourcesServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger LOGGER = Logger.getLogger(AddPhysicalResourcesServlet.class.getName());

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			if (!SecureProjectStrategy.getInstance().isAuthorized(request, response, getServletContext()))
				return;

			int idProject = Integer.parseInt(request.getParameter("idProject"));
			List<PhysicalResourceBean> resources = PhysicalResourceDAO.getInstance().getAvailableResources();

			Serializable resourcesSer = (Serializable) resources;

			request.getSession().setAttribute("resources", resourcesSer);
			request.setAttribute("idProject", idProject);

			RequestDispatcher dispatcher = getServletContext()
					.getRequestDispatcher("/views/add-physical-resources.jsp");
			dispatcher.forward(request, response);
		} catch (Exception e) {
			LOGGER.log(Level.SEVERE, "Something went wrong during adding precedences to project", e);
		}
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			if (!SecureProjectStrategy.getInstance().isAuthorized(request, response, getServletContext()))
				return;

			Map<String, String> messages = new HashMap<>();

			request.setAttribute("messages", messages);

			int idProject = Integer.parseInt(request.getParameter("idProject"));
			@SuppressWarnings("unchecked")
			List<PhysicalResourceBean> resources = (List<PhysicalResourceBean>) (Serializable) request.getSession()
					.getAttribute("resources");
			Map<Integer, Integer> selectedResources = new HashMap<>();
			
			for(Integer index = 1; index <= resources.size(); index++){
				
				String insertedResource = request.getParameter(index.toString());
				
				if(insertedResource.equals("")){
					//doNothing
				}else if(Integer.parseInt(insertedResource) > resources.get(index - 1).getQuantity()){
					messages.put("error",
							"<i class='fa fa-exclamation'></i>&nbsp;Please, a quantity less than or equal to available.");
					RequestDispatcher dispatcher = getServletContext()
							.getRequestDispatcher("/views/add-physical-resources.jsp");
					dispatcher.forward(request, response);
					return;
				}else{
					int idResource = resources.get(index - 1).getIdPhysicalResource();
					selectedResources.put(idResource, Integer.parseInt(insertedResource));
				}
			}

			PhysicalResourceDAO.getInstance().assignResourceToProject(idProject, selectedResources);

			request.getSession().removeAttribute("resources");
			response.sendRedirect(request.getContextPath() + "/project?idProject="
					+ Integer.parseInt(request.getParameter("idProject")));
		} catch (Exception e) {
			LOGGER.log(Level.SEVERE, "Something went wrong during adding precedences to project", e);
		}
	}

}
