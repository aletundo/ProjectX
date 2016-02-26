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

import models.ProjectBean;
import models.ProjectDAO;
import models.UserBean;

@WebServlet("/myprojects")
public class MyProjectServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession(false);
		if(session == null || session.getAttribute("idUser") == null)
		{
			response.sendRedirect(request.getContextPath());
		}
		else{
			Integer idUser = (Integer) session.getAttribute("idUser");
			String userType = (String) session.getAttribute("userType");
			UserBean user = new UserBean();
			user.setIdUser(idUser);
			user.setType(userType);
			List<ProjectBean> projects = ProjectDAO.getInstance().getUserProjects(user);
			if (!projects.isEmpty()) {
				request.setAttribute("projects", projects);
			}
			RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/views/myprojects.jsp");
			dispatcher.forward(request, response);
		}

	}

}
