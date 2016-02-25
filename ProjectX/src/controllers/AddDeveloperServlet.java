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
import controllers.utils.CalculateWeights;
import models.UserDAO;
import models.StageDAO;
import models.TaskBean;
import models.TaskDAO;
import models.UserBean;

@WebServlet(name = "AddDeveloperServlet", urlPatterns = { "/add-developer" })
public class AddDeveloperServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		if (!isAuthorized(request, response)) {
			return;
		}
		long taskHoursRequired;
		TaskBean task = new TaskBean();
		task.setIdTask(Integer.parseInt(request.getParameter("idTask")));
		task.setStartDay(request.getParameter("startDay"));
		task.setFinishDay(request.getParameter("finishDay"));
		Map<Integer, List<Object>> workMap = UserDAO.getInstance().getCandidateDevelopers();
		List<UserBean> candidates = CalculateAvailableUsers.calculate(workMap, task);
		List<UserBean> candidatesWithInfos = UserDAO.getInstance().getUsersInfo(candidates);

		if (request.getAttribute("taskHoursRequired") == null) {
			taskHoursRequired = CalculateAvailableUsers.getHoursRequestedTask(request.getParameter("startDay"),
					request.getParameter("finishDay"));
		} else {
			taskHoursRequired = (Long) (request.getAttribute("taskHoursRequired"));
		}

		request.setAttribute("taskHoursRequired", taskHoursRequired);
		request.getSession().setAttribute("candidates", candidatesWithInfos);

		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/views/add-developer.jsp");
		dispatcher.forward(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		if (!isAuthorized(request, response)) {
			return;
		}
		TaskBean task = new TaskBean();
		int idTask = Integer.parseInt(request.getParameter("idTask"));
		int indexDeveloper = Integer.parseInt(request.getParameter("index")) - 1;
		long taskHoursRequired = Long.parseLong(request.getParameter("task-hours-required"));
		@SuppressWarnings("unchecked")
		List<UserBean> candidatesFromPage = (List<UserBean>) request.getSession().getAttribute("candidates");

		long developerFreeHours = candidatesFromPage.get(indexDeveloper).getTemporaryHoursAvailable();
		long updatedTaskHoursRequired = taskHoursRequired - developerFreeHours;

		task.setIdDeveloper(candidatesFromPage.get(indexDeveloper).getIdUser());
		task.setIdTask(idTask);

		if (updatedTaskHoursRequired > 0) {
			TaskDAO.getInstance().addDeveloper(task, developerFreeHours);
			request.setAttribute("taskHoursRequired", updatedTaskHoursRequired);
			request.getSession().removeAttribute("candidates");
			doGet(request, response);
		} else {
			TaskDAO.getInstance().addDeveloper(task, taskHoursRequired);
			request.getSession().removeAttribute("candidates");
			if (request.getParameter("completed") != null) {
				List<TaskBean> tasks = TaskDAO.getInstance().getTasksByStageId(Integer.parseInt(request.getParameter("idStage")));
				CalculateWeights.computeTasksWeight(tasks);
				response.sendRedirect(request.getContextPath() + "/stage?idStage="
						+ Integer.parseInt(request.getParameter("idStage")));
				return;
			}
			response.sendRedirect(
					request.getContextPath() + "/addtask?idStage=" + Integer.parseInt(request.getParameter("idStage")));

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

		int[] idAuthorizedUsers = StageDAO.getInstance()
				.getAuthorizedUsers(Integer.parseInt(request.getParameter("idStage")));
		int idLoggedUser = (Integer) (session.getAttribute("idUser"));
		if (idAuthorizedUsers[0] != idLoggedUser && idAuthorizedUsers[1] != idLoggedUser) {
			RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/views/access-denied.jsp");
			dispatcher.forward(request, response);
			return false;
		}
		return true;
	}
}