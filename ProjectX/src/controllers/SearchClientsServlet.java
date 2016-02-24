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

import models.ClientBean;
import models.ClientDAO;

@WebServlet(name = "SearchClientsServlet", urlPatterns = { "/searchclients" })
public class SearchClientsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		if (!isAuthorized(request, response))
			return;

		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/views/search-clients.jsp");
		dispatcher.forward(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		if (!isAuthorized(request, response))
			return;

		String subjectArea = request.getParameter("subject-area");
		List<ClientBean> clients = ClientDAO.getInstance().getRelatedClients(subjectArea);
		RequestDispatcher dispatcher;
		if (clients.isEmpty()) {
			request.setAttribute("noMatchFound", "No related clients found! Sorry, try again :(");
		} else {
			request.setAttribute("clients", clients);
		}
		request.setAttribute("subjectArea", subjectArea);
		dispatcher = getServletContext().getRequestDispatcher("/views/related-clients.jsp");
		dispatcher.forward(request, response);
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

}
