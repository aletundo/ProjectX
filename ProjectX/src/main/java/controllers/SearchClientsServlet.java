package controllers;

import java.io.IOException;
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
import models.ClientBean;
import models.ClientDAO;

@WebServlet(name = "SearchClientsServlet", urlPatterns = { "/searchclients" })
public class SearchClientsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger LOGGER = Logger.getLogger(SearchClientsServlet.class.getName());

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			if (!SecureProjectStrategy.getInstance().isAuthorizedCreate(request, response, getServletContext()))
				return;

			RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/views/search-clients.jsp");
			dispatcher.forward(request, response);
		} catch (Exception e) {
			LOGGER.log(Level.SEVERE, "Something went wrong during getting search clients page", e);
		}
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			if (!SecureProjectStrategy.getInstance().isAuthorizedCreate(request, response, getServletContext()))
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
		} catch (Exception e) {
			LOGGER.log(Level.SEVERE, "Something went wrong during searching clients", e);
		}
	}

}
