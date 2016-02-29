package controllers;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import controllers.utils.CalculateWeights;
import controllers.utils.CriticalPath;
import controllers.utils.SchedulerEventsThread;
import controllers.utils.security.SecureProjectStrategy;
import models.StageBean;
import models.StageDAO;

@WebServlet(name = "AddPrecendencesServlet", urlPatterns = { "/addprecedences" })
public class AddPrecedencesServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger LOGGER = Logger.getLogger(AddPrecedencesServlet.class.getName());

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			if (!SecureProjectStrategy.getInstance().isAuthorized(request, response, getServletContext()))
				return;

			int idProject = Integer.parseInt(request.getParameter("idProject"));
			List<StageBean> stages = StageDAO.getInstance().getStagesByIdProject(idProject);
			List<StageBean> stagesQueue = new ArrayList<>();
			for (StageBean s : stages) {
				stagesQueue.add(s);
			}
			Serializable stagesSer = (Serializable) stages;
			Serializable stagesQueueSer = (Serializable) stagesQueue;

			HttpSession session = request.getSession();
			session.setAttribute("stages", stagesSer);
			session.setAttribute("stagesQueue", stagesQueueSer);
			request.setAttribute("idProject", idProject);

			RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/views/add-precedences.jsp");
			dispatcher.forward(request, response);
		} catch (Exception e) {
			LOGGER.log(Level.SEVERE, "Something went wrong during adding precedences to project", e);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			if (!SecureProjectStrategy.getInstance().isAuthorized(request, response, getServletContext()))
				return;

			List<StageBean> precedences = new ArrayList<>();
			StageBean stage = new StageBean();

			List<StageBean> stagesQueue = (List<StageBean>) request.getSession().getAttribute("stagesQueue");

			stage.setIdStage(stagesQueue.remove(0).getIdStage());

			if (request.getParameterValues("id-precedences") != null) {
				String[] idPrecedences = request.getParameterValues("id-precedences");
				// Build the list with the precedences to store
				for (String p : idPrecedences) {
					StageBean sb = new StageBean();
					sb.setIdStage(Integer.parseInt(p));
					precedences.add(sb);
				}
				StageDAO.getInstance().addPrecedences(stage, precedences);
			}

			if (!stagesQueue.isEmpty()) {
				RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/views/add-precedences.jsp");
				dispatcher.forward(request, response);
			} else {
				CalculateWeights.computeStagesWeight((List<StageBean>) request.getSession().getAttribute("stages"));
				System.out.println(request.getParameter("idProject"));
				
				CriticalPath.computeCriticalStages(request.getParameter("idProject"));
				SchedulerEventsThread scheduler = new SchedulerEventsThread(Integer.parseInt(request.getParameter("idProject")));
				final ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor();
				service.scheduleAtFixedRate(scheduler, 0, 5, TimeUnit.SECONDS);
				request.getSession().removeAttribute("stages");
				request.getSession().removeAttribute("stagesQueue");
				response.sendRedirect(request.getContextPath() + "/project?idProject="
						+ Integer.parseInt(request.getParameter("idProject")));
			}
		} catch (Exception e) {
			LOGGER.log(Level.SEVERE, "Something went wrong during adding precedences to project", e);
		}
	}
}
