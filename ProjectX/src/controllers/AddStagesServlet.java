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

import models.UserBean;
import models.UserDAO;

@WebServlet(name = "AddStagesServlet", urlPatterns = { "/addstages" })
public class AddStagesServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession(false);
		if (session == null || session.getAttribute("idUser") == null) {
			response.sendRedirect(request.getContextPath());
		} else {
			List<UserBean> cadidates = UserDAO.getInstance().getCadidateSupervisor();
			RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/views/create-stages.jsp");
			dispatcher.forward(request, response);
		}
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		int idProject = Integer.parseInt(request.getParameter("id-project"));
		
	}

}
