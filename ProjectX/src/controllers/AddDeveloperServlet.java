package controllers;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import controllers.utils.CalculateAvailableUsers;
import models.UserDAO;
import models.TaskBean;
import models.TaskDAO;
import models.UserBean;

@WebServlet(name = "AddDeveloperServlet", urlPatterns = { "/add-developer" })
public class AddDeveloperServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		HttpSession session = request.getSession(false);
		if (session == null || session.getAttribute("idUser") == null) {
			response.sendRedirect(request.getContextPath());
		} else {
			TaskBean task = new TaskBean();
			task.setIdTask(Integer.parseInt(request.getParameter("idTask")));
			task.setStartDay(request.getParameter("startDay"));
			task.setFinishDay(request.getParameter("finishDay"));
			
			Map<Integer, List<Object>> workMap = UserDAO.getInstance().getCandidateDevelopers();
			List<UserBean> candidates = CalculateAvailableUsers.calculate(workMap, task);
			candidates = UserDAO.getInstance().getUsersInfo(candidates);
			
			request.setAttribute("candidates", candidates);
			RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/views/add-developer.jsp");
			dispatcher.forward(request, response);
		}
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession(false);
		if (session == null || session.getAttribute("idUser") == null) {
			response.sendRedirect(request.getContextPath());
		} else {
			TaskBean task = new TaskBean();
			int idTask = Integer.parseInt(request.getParameter("id-task"));
			int idDeveloper;
			idDeveloper = Integer.parseInt(request.getParameter("id-developer"));
			task.setIdDeveloper(idDeveloper);
			task.setIdTask(idTask);
			boolean updated = TaskDAO.getInstance().addDeveloper(task);
			if (!updated) {
				// TODO RESPONSE with an alter or something like that OR FUCK!!!
				// no errors
			} else if (request.getParameter("completed") == null) {
				response.sendRedirect(request.getContextPath() + "/addtask");
			}

			else {
				response.sendRedirect(request.getContextPath() + "/add-developer?idTask=" + idTask);
			}
		}
	}
}