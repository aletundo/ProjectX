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

import controllers.utils.CalculateAvailableUsers;
import controllers.utils.security.SecureProjectStrategy;
import models.UserDAO;
import models.StageBean;
import models.StageDAO;
import models.UserBean;

@WebServlet(name = "AddSupervisorServlet", urlPatterns = { "/addsupervisor" })
public class AddSupervisorServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		if (!SecureProjectStrategy.getInstance().isAuthorized(request, response, getServletContext()))
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
		if (!SecureProjectStrategy.getInstance().isAuthorized(request, response, getServletContext()))
			return;
		
			StageBean stage = new StageBean();
			int idStage = Integer.parseInt(request.getParameter("idStage"));
			int idProject = Integer.parseInt(request.getParameter("idProject"));
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
			StageDAO.getInstance().addSupervisor(stage);
			
			if (request.getParameter("completed") == null) {
				response.sendRedirect(request.getContextPath() + "/addstages?idProject=" + idProject);
			} else {
				response.sendRedirect(request.getContextPath() + "/addprecedences?idProject=" + idProject);
			}
		}
}
