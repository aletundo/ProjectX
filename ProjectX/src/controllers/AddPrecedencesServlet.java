package controllers;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.StageBean;
import models.StageDAO;


@WebServlet(name = "AddPrecendencesServlet", urlPatterns = {"/addprecedences"})
public class AddPrecedencesServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		int idProject = Integer.parseInt(request.getParameter("idProject"));
		List<StageBean> stages = StageDAO.getInstance().getStages(idProject);
		List<StageBean> stagesQueue = new LinkedList<StageBean>();
		for(StageBean s : stages){
			stagesQueue.add(s);
		}
		request.setAttribute("stages", stages);
		request.setAttribute("stagesQueue", stagesQueue);
		
		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/views/add-precedences.jsp");
		dispatcher.forward(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int idProject = Integer.parseInt(request.getParameter("id-project"));
		int idStage = Integer.parseInt(request.getParameter("id-stage"));
		String[] idPrecedences = request.getParameterValues("id-precedences");
		System.out.println(idStage);
		for(String s : idPrecedences){
			System.out.println(s);
		}
		
	}

}
