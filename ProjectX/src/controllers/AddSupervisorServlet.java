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
import models.ProjectDAO;
import models.StageBean;
import models.StageDAO;
import models.UserBean;

@WebServlet(name = "AddSupervisorServlet", urlPatterns = { "/addsupervisor" })
public class AddSupervisorServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		if (!isAuthorized(request, response))
			return;
		
			int idStage = Integer.parseInt(request.getParameter("idStage"));
			String startDay = request.getParameter("startDay");
			String finishDay = request.getParameter("finishDay");
			
			StageBean stage = new StageBean();
			stage.setIdStage(idStage);
			stage.setStartDay(startDay);
			stage.setFinishDay(finishDay);
			
			Map<Integer, List<Object>> workMap = UserDAO.getInstance().newGetCandidateSupervisors();
			List<UserBean> candidates = CalculateAvailableUsers.calculate(workMap, stage);
			candidates = UserDAO.getInstance().getUsersInfo(candidates);
			if(candidates.isEmpty())
			{
				request.setAttribute("outsourcing", "True");
			}else{
				request.setAttribute("candidates", candidates);
			}
			RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/views/add-supervisor.jsp");
			dispatcher.forward(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		if (!isAuthorized(request, response))
			return;
		
			StageBean stage = new StageBean();
			int idStage = Integer.parseInt(request.getParameter("id-stage"));
			int idProject = Integer.parseInt(request.getParameter("id-project"));
			int idSupervisor;
			if(request.getParameter("company-name") != null){
				String companyName = request.getParameter("company-name");
				String companyMail = request.getParameter("company-mail");
				stage.setOutsourcing("True");
				stage.setCompanyName(companyName);
				stage.setCompanyMail(companyMail);
			}else{
				idSupervisor = Integer.parseInt(request.getParameter("id-supervisor"));
				stage.setOutsourcing("False");
				stage.setIdSupervisor(idSupervisor);
			}
			stage.setIdStage(idStage);
			stage.setIdProject(idProject);
			boolean updated = StageDAO.getInstance().addSupervisor(stage);
			if (!updated) {
				// TODO RESPONSE with an alter or something like that OR FUCK!!! no errors
			} else if (request.getParameter("completed") == null) {
				response.sendRedirect(request.getContextPath() + "/addstages?idProject=" + idProject);
			} else {
				response.sendRedirect(request.getContextPath() + "/addprecedences?idProject=" + idProject);
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

		int idProjectManager = ProjectDAO.getInstance()
				.getProjectManagerId(Integer.parseInt(request.getParameter("idProject")));
		if (idProjectManager != (Integer) (session.getAttribute("idUser"))) {
			RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/views/access-denied.jsp");
			dispatcher.forward(request, response);
			return false;
		}
		return true;
	}

}
