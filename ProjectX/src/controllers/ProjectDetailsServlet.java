package controllers;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import controllers.utils.security.SecureProjectStrategy;
import models.ProjectBean;
import models.ProjectDAO;
import models.StageBean;
import models.StageDAO;

@WebServlet(name = "ProjectDetailsServlet", urlPatterns = { "/project/*" })
public class ProjectDetailsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		if(!SecureProjectStrategy.getInstance().isAuthorizedVisualize(request, response, getServletContext()))
			return;
			
		int idProject = Integer.parseInt(request.getParameter("idProject"));
		RequestDispatcher dispatcher;

		List<StageBean> stagesInfo = StageDAO.getInstance().getStagesByIdProject(idProject);
		ProjectBean projectInfo = ProjectDAO.getInstance().getProjectInfo(idProject);

		request.setAttribute("project", projectInfo);
		request.setAttribute("stages", stagesInfo);

		dispatcher = getServletContext().getRequestDispatcher("/views/visualize-project.jsp");
		dispatcher.forward(request, response);
	}
}
