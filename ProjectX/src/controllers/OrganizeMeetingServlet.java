package controllers;

import java.io.IOException;
import java.util.List;

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

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			if (!isAuthorized(request, response))
				return;
		} catch (Exception e) {
			/* TODO LOGGER */
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
			/* TODO LOGGER */
		}
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String host = "localhost";
		String port = ""; /* TODO check port */
		final String password = ""; /* TODO check password */
		String userName = "";

		try {
			if (!isAuthorized(request, response))
				return;
		} catch (Exception e) {
			/* TODO LOGGER */
		}

		try {
			if (request.getParameter("idProject") != null) {
				String pmMail = request.getParameter("pm-mail");
				String clientMail = request.getParameter("client-mail");
				String object = request.getParameter("object");
				String message = request.getParameter("message");
				if (request.getParameter("invite-supervisors").equals("Yes")) {
					List<String> supervisorsMail = UserDAO.getInstance()
							.getAllSupervisorsMail(Integer.parseInt(request.getParameter("idProject")));
					for (String mails : supervisorsMail) {
						controllers.utils.SendEmail.sendEmail(host, port, userName, password, mails, object, message);
					}
					controllers.utils.SendEmail.sendEmail(host, port, userName, password, pmMail, object, message);
					controllers.utils.SendEmail.sendEmail(host, port, userName, password, clientMail, object, message);
				}

				/* TODO get the name of the supervisors and maybe extract method*/

			} else if (request.getParameter("idStage") != null) {
				String supervisorMail = request.getParameter("supervisor-mail");
				String object = request.getParameter("object");
				String message = request.getParameter("message");
				List<String> developersMail = UserDAO.getInstance()
						.getAllDevelopersMail(Integer.parseInt(request.getParameter("idStage")));
				if (request.getParameter("invite-project-manager").equals("Yes")) {
					UserDAO.getInstance()
							.getProjectManagerMailByIdStage(Integer.parseInt(request.getParameter("idStage")));
					for (String mails : developersMail) {
						controllers.utils.SendEmail.sendEmail(host, port, userName, password, mails, object, message);
					}
					controllers.utils.SendEmail.sendEmail(host, port, userName, password, supervisorMail, object,
							message);
				}
				/* TODO get the name of the developers and maybe extract method */
			}
		} catch (Exception e) {
			/* TODO LOGGER */
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
