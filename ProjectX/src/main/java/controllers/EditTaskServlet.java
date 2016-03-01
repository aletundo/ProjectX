package controllers;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
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
import models.ProjectBean;
import models.ProjectDAO;
import models.TaskBean;
import models.TaskDAO;

@WebServlet("/edittask")
public class EditTaskServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private static final Logger LOGGER = Logger.getLogger(EditProjectServlet.class.getName());

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			/*if (!SecureProjectStrategy.getInstance().isAuthorized(request, response, getServletContext()))
				return;*/
			
			int idTask = Integer.parseInt(request.getParameter("idTask"));
			TaskBean task = TaskDAO.getInstance().getTaskInfo(idTask);
			request.setAttribute("task", task);
			
			RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/views/edit-task.jsp");
			dispatcher.forward(request, response);
		} catch (Exception e) {
			LOGGER.log(Level.SEVERE, "Something went wrong during getting edit project page", e);
		}
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		try {

			try {

				if (!SecureProjectStrategy.getInstance().isAuthorizedCreate(request, response, getServletContext()))
					return;

				Map<String, String> messages = new HashMap<>();

				request.setAttribute("messages", messages);
				
				//TODO re-use of method checkParameters in class addProjectServlet
				/*if (!checkParameters(request, messages)) {
					RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/views/edit-project.jsp");
					dispatcher.forward(request, response);
					return;
				}*/
		
				String name = request.getParameter("name");
				

				TaskBean task = new TaskBean();
				
				int idTask = Integer.parseInt(request.getParameter("idTask"));
				task.setIdTask(idTask);
				
				Map<String, Object> attributes = new HashMap<>();
				
				attributes.put("name",name);
				String finishDay = request.getParameter("finishDay");
				attributes.put("finishDay",finishDay);
				String startDay = request.getParameter("startDay");
				attributes.put("startDay",startDay);
				
				TaskBean oldTask = (TaskBean)request.getAttribute("task");
				String oldFinishDay = oldTask.getFinishDay();

				TaskDAO.getInstance().updateTask(task, attributes);
				if (idTask != Integer.MIN_VALUE)
					response.sendRedirect(request.getContextPath() + "/adddeveloper?idStage=" + idTask + "&idTask="
							+ idTask + "&startDay=" + oldFinishDay + "&finishDay=" + finishDay);
			} catch (Exception e) {
				LOGGER.log(Level.SEVERE, "Something went wrong during adding a project", e);
			}
		
		} catch (Exception e) {
			LOGGER.log(Level.SEVERE, "Something went wrong during editing a project", e);
		}
	}
	

}
