package controllers;

import java.io.IOException;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import controllers.utils.CalculateAvailableUsers;
import controllers.utils.CalculateWeights;
import controllers.utils.UtilityFunctions;
import controllers.utils.security.SecureStageStrategy;
import models.UserDAO;
import models.StageBean;
import models.StageDAO;
import models.TaskBean;
import models.TaskDAO;
import models.UserBean;

@WebServlet(name = "AddDeveloperServlet", urlPatterns = { "/adddeveloper" })
public class AddDeveloperServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger LOGGER = Logger.getLogger(AddDeveloperServlet.class.getName());
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			if (!SecureStageStrategy.getInstance().isAuthorized(request, response, getServletContext()))
				return;

			RequestDispatcher dispatcher;
			long taskHoursRequired;
			TaskBean task = new TaskBean();
			task.setIdTask(Integer.parseInt(request.getParameter("idTask")));
			task.setStartDay(request.getParameter("startDay"));
			task.setFinishDay(request.getParameter("finishDay"));
			Map<Integer, List<Object>> workMap = UserDAO.getInstance().getCandidateDevelopers();
			List<UserBean> candidates = CalculateAvailableUsers.calculate(workMap, task);

			if (candidates.isEmpty()) {
				request.setAttribute("outsourcing", "True");
				dispatcher = getServletContext().getRequestDispatcher("/views/add-developer.jsp");
				dispatcher.forward(request, response);
				return;
			}

			List<UserBean> candidatesWithInfos = UserDAO.getInstance().getUsersInfo(candidates);

			if (request.getAttribute("taskHoursRequired") == null) {
				taskHoursRequired = UtilityFunctions.getHoursRequestedTask(request.getParameter("startDay"),
						request.getParameter("finishDay"));
			} else {
				taskHoursRequired = (Long) (request.getAttribute("taskHoursRequired"));
			}
			
			Serializable candidatesWithInfosSer = (Serializable) candidatesWithInfos;
			
			request.setAttribute("taskHoursRequired", taskHoursRequired);
			request.getSession().setAttribute("candidates", candidatesWithInfosSer);

			dispatcher = getServletContext().getRequestDispatcher("/views/add-developer.jsp");
			dispatcher.forward(request, response);
		} catch (Exception e) {
			LOGGER.log(Level.SEVERE, "Something went wrong during setting developers", e);
		}
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String host = "localhost";
		String port = ""; /* TODO check port */
		final String pw = ""; /* TODO check password */
		String userName = "";
		Map<String, String> messages = new HashMap<>();
		request.setAttribute("messages", messages);
		
		try {
			if (!SecureStageStrategy.getInstance().isAuthorized(request, response, getServletContext()))
				return;
			
			if (request.getParameter("companyname") != null) {

				String companyName = request.getParameter("companyname");
				String companyMail = request.getParameter("companymail");
				if (companyMail == null || companyMail.trim().isEmpty() || !UtilityFunctions.isValidMail(companyMail)) {
					messages.put("clientmail", "<i class='fa fa-exclamation'></i>&nbsp;Please, insert a valid mail.");
					RequestDispatcher dispatcher = getServletContext()
							.getRequestDispatcher("/views/add-developer.jsp");
					dispatcher.forward(request, response);
					return;
				}
				int idSupervisor = (Integer) request.getSession().getAttribute("idUser");
				int idStage = Integer.parseInt(request.getParameter("idStage"));
				StageBean stage = new StageBean();
				stage.setIdSupervisor(idSupervisor);
				stage.setIdStage(idStage);
				stage.setOutsourcing("True");
				// TODO Modify message
				String object = "[OUTSOURCING]";
				String message = "Can i ask you," + companyName + " , if you can outsorce some resources to us?";
				
				TaskDAO.getInstance().removeTasksWhenOutsourcing(idStage);
				StageDAO.getInstance().addSupervisor(stage);
				
				controllers.utils.SendEmail.sendEmail(host, port, userName, pw, companyMail, object, message);
				
				request.getSession().removeAttribute("candidates");
				response.sendRedirect(request.getContextPath() + "/stage?idStage="
						+ Integer.parseInt(request.getParameter("idStage")));
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
					List<TaskBean> tasks = TaskDAO.getInstance()
							.getTasksByStageId(Integer.parseInt(request.getParameter("idStage")));
					CalculateWeights.computeTasksWeight(tasks);
					response.sendRedirect(request.getContextPath() + "/stage?idStage="
							+ Integer.parseInt(request.getParameter("idStage")));
					return;
				}
				response.sendRedirect(request.getContextPath() + "/addtask?idStage="
						+ Integer.parseInt(request.getParameter("idStage")));

			}
		} catch (Exception e) {
			LOGGER.log(Level.SEVERE, "Something went wrong during setting developers", e);
		}
	}
}