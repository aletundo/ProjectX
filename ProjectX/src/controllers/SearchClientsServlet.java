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

/**
 * Servlet implementation class SearchClients
 */
@WebServlet(name = "SearchClientsServlet", urlPatterns = {"/searchclients"})
public class SearchClientsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if(request.getSession().isNew())
		{
			response.sendRedirect(request.getContextPath());
		}else{
			RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/views/search-clients.jsp");
			dispatcher.forward(request, response);
		}
	}
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		if(request.getSession().isNew())
		{
			response.sendRedirect(request.getContextPath());
		}else{
			String subjectArea = request.getParameter("subject-area");
			List<ClientBean> clients = ClientDAO.getInstance().getRelatedClients(subjectArea);
			request.setAttribute("clients", clients);
			request.setAttribute("subjectArea", subjectArea);
			RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/views/related-clients.jsp");
			dispatcher.forward(request, response);
		}
	}

}
