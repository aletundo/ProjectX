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

import models.StageBean;
import models.StageDAO;
import models.TaskBean;
import models.TaskDAO;

@WebServlet(name= "StageDetailsServlet", urlPatterns = {"/stage"})
public class StageDetailsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(false);
		if (session == null || session.getAttribute("idUser") == null) {
			response.sendRedirect(request.getContextPath());
		} else {
			int idStage = Integer.parseInt(request.getParameter("idStage"));
			RequestDispatcher dispatcher;

			List<TaskBean> tasks = TaskDAO.getInstance().getTasksDetails(idStage);
			StageBean stageInfo = StageDAO.getInstance().getStageInfo(idStage);

			request.setAttribute("stage", stageInfo);
			request.setAttribute("tasks", tasks);

			dispatcher = getServletContext().getRequestDispatcher("/views/visualize-stage.jsp");
			dispatcher.forward(request, response);
		}
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
