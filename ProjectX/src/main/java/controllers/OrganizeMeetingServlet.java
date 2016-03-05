package controllers;

import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.mail.MessagingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import models.ClientDAO;
import models.ProjectDAO;
import models.StageDAO;
import models.UserDAO;

@WebServlet(name = "OrganizeMeetingPMServlet", urlPatterns = { "/organizemeeting" })
public class OrganizeMeetingServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger LOGGER = Logger.getLogger(OrganizeMeetingServlet.class.getName());

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			if (!isAuthorized(request, response))
				return;
		} catch (Exception e) {
			LOGGER.log(Level.SEVERE, "Something went wrong during authorize to organize a meeting", e);
		}

		RequestDispatcher dispatcher;
		String senderMail = UserDAO.getInstance()
				.getGenericUserMailById((Integer) request.getSession().getAttribute("idUser"));

		try {
			if (request.getParameter("idProject") != null) {
				String clientMail = ClientDAO.getInstance()
						.getClientMail(Integer.parseInt(request.getParameter("idProject")));
				request.setAttribute("clientMail", clientMail);
				request.setAttribute("pmMail", senderMail);
				dispatcher = getServletContext().getRequestDispatcher("/views/organize-client-meeting.jsp");
				dispatcher.forward(request, response);
			} else if (request.getParameter("idStage") != null) {
				request.setAttribute("supervisorMail", senderMail);
				dispatcher = getServletContext().getRequestDispatcher("/views/organize-stage-meeting.jsp");
				dispatcher.forward(request, response);
			}
		} catch (Exception e) {
			LOGGER.log(Level.SEVERE, "Something went wrong during getting organize meeting page", e);
		}
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		try {
			if (!isAuthorized(request, response))
				return;
		} catch (Exception e) {
			LOGGER.log(Level.SEVERE, "Something went wrong during authorize to organize a meeting", e);
		}

		try {
			if (request.getParameter("idProject") != null) {
				String pmMail = UserDAO.getInstance()
						.getGenericUserMailById((Integer) request.getSession().getAttribute("idUser"));
				String clientMail = ClientDAO.getInstance()
						.getClientMail(Integer.parseInt(request.getParameter("idProject")));
				String object = request.getParameter("object");
				String message = request.getParameter("message");
				sendEmailToSupervisors(request, pmMail, clientMail, object, message);

			} else if (request.getParameter("idStage") != null) {
				String supervisorMail = UserDAO.getInstance()
						.getGenericUserMailById((Integer) request.getSession().getAttribute("idUser"));
				String object = request.getParameter("object");
				String message = request.getParameter("message");
				List<String> developersMail = UserDAO.getInstance()
						.getAllDevelopersMail(Integer.parseInt(request.getParameter("idStage")));
				sendEmailToDevelopers(request, supervisorMail, object, message, developersMail);
			}
		} catch (Exception e) {
			LOGGER.log(Level.SEVERE, "Something went wrong during organize a meeting", e);
		}
	}

	private static void sendEmailToDevelopers(HttpServletRequest request, String supervisorMail, String object,
			String message, List<String> developersMail) throws MessagingException {
		String host = "localhost";
		for (String to : developersMail) {
			controllers.utils.SendEmail.sendEmail(to, supervisorMail, object, message, host);
		}
		if ("Yes".equals(request.getParameter("invite-project-manager"))) {
			String pmMail = UserDAO.getInstance()
					.getProjectManagerMailByIdStage(Integer.parseInt(request.getParameter("idStage")));
			controllers.utils.SendEmail.sendEmail(pmMail, supervisorMail, object, message, host);
		}
	}

	private static void sendEmailToSupervisors(HttpServletRequest request, String pmMail, String clientMail,
			String object, String message) throws MessagingException {
		String host = "localhost";
		
		controllers.utils.SendEmail.sendEmail(clientMail, pmMail, object, message, host);
		
		if ("Yes".equals(request.getParameter("invite-supervisors"))) {
			List<String> supervisorsMail = UserDAO.getInstance()
					.getAllSupervisorsMail(Integer.parseInt(request.getParameter("idProject")));
			for (String mail : supervisorsMail) {
				controllers.utils.SendEmail.sendEmail(mail, pmMail, object, message, host);
			}
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
		if (session == null || session.getAttribute("idUser") == null) {
			response.sendRedirect(request.getContextPath());
			return false;
		}

		if (request.getParameter("idStage") != null) {

			int[] idAuthorizedUsers = StageDAO.getInstance()
					.checkIdProjectManagerOrSupervisor(Integer.parseInt(request.getParameter("idStage")));
			int idSupervisor = idAuthorizedUsers[0];
			int idLoggedUser = (Integer) (session.getAttribute("idUser"));
			if (idSupervisor != idLoggedUser) {
				RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/views/access-denied.jsp");
				dispatcher.forward(request, response);
				return false;
			}

			return true;
		}

		int idProjectManager = ProjectDAO.getInstance()
				.getProjectManagerId(Integer.parseInt(request.getParameter("idProject")));
		if (idProjectManager != (Integer) (session.getAttribute("idUser"))) {
			RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/views/access-denied.jsp");
			dispatcher.forward(request, response);
			return false;
		}

		return true;
	}

}
