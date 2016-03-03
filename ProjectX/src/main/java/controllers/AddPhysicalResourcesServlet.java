package controllers;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;
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
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
            if (!SecureProjectStrategy.getInstance().isAuthorized(request, response, getServletContext()))
                return;

            int idProject = Integer.parseInt(request.getParameter("idProject"));
            List<PhysicalResourceBean> resources = PhysicalResourceDAO.getInstance().getAvailableResources();
           
            Serializable resourcesSer = (Serializable) resources;
            
            request.setAttribute("resources", resourcesSer);
            request.setAttribute("idProject", idProject);

            RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/views/add-physical-resources.jsp");
            dispatcher.forward(request, response);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Something went wrong during adding precedences to project", e);
        }
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}

}
