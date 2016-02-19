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

		HttpSession session = request.getSession(false);
		if (session == null || session.getAttribute("idUser") == null) {
			response.sendRedirect(request.getContextPath());
		} else {
			List<UserBean> candidates = UserDAO.getInstance().getCandidateSupervisors();
			if(candidates.isEmpty())
			{
				request.setAttribute("outsourcing", true);
			}else{
				request.setAttribute("candidates", candidates);
			}
			RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/views/add-supervisor.jsp");
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
			StageBean stage = new StageBean();
			int idStage = Integer.parseInt(request.getParameter("id-stage"));
			int idProject = Integer.parseInt(request.getParameter("id-project"));
			int idSupervisor;
			if(request.getParameter("company-name") != null){
				String companyName = request.getParameter("company-name");
				String companyMail = request.getParameter("company-mail");
				stage.setOutsourcing(true);
				stage.setCompanyName(companyName);
				stage.setCompanyMail(companyMail);
			}else{
				idSupervisor = Integer.parseInt(request.getParameter("id-supervisor"));
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
				response.sendRedirect(request.getContextPath() + "/myprojects");
			}
		}
	}

}
