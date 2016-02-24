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

@WebServlet(name = "OrganizeMeetingPMServlet", urlPatterns = { "/organizemeetingpm" })
public class OrganizeMeetingPMServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		if (!isAuthorized(request, response))
			return;

		ProjectBean project = new ProjectBean();
		ClientBean client = new ClientBean();
		// Integer idProjectManager = (Integer) request.getSession().getAttribute("idUser");

		// Get parameters
		String pmName = request.getParameter("pmName");
		String clientMail = request.getParameter("client-mail");

		// Set the bean
		project.setName(pmName);
		client.setMail(clientMail);

		// Add the client and retrieve his id
		int idClient = ClientDAO.getInstance().addClient(client);
		project.setIdClient(idClient);
		int idProject = ProjectDAO.getInstance().addProject(project);
		if (idProject != 0) {
			response.sendRedirect(request.getContextPath()
					+ "/addstages?idProject=" + idProject);
		}

	}

	/**
	 * @param request
	 * @param response
	 * @throws IOException
	 * @throws ServletException
	 * @return boolean
	 */
	private boolean isAuthorized(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {
		HttpSession session = request.getSession();
		// If the session is not valid redirect to login
		if (session == null || session.getAttribute("idUser") == null) {
			response.sendRedirect(request.getContextPath());
			return false;
		}

		if (!"ProjectManager".equals(session.getAttribute("userType"))) {
			RequestDispatcher dispatcher = getServletContext()
					.getRequestDispatcher("/views/access-denied.jsp");
			dispatcher.forward(request, response);
			return false;
		}
		return true;
	}

	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		if (!isAuthorized(request, response))
			return;

		RequestDispatcher dispatcher = getServletContext()
				.getRequestDispatcher("/views/organize-meeting-PM.jsp");
		dispatcher.forward(request, response);
	}

}
