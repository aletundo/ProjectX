package controllers;

import java.io.IOException;
import java.util.ArrayList;
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


@WebServlet(name = "AddPrecendencesServlet", urlPatterns = {"/addprecedences"})
public class AddPrecedencesServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		int idProject = Integer.parseInt(request.getParameter("idProject"));
		List<StageBean> stages = StageDAO.getInstance().getStages(idProject);
		List<StageBean> stagesQueue = new ArrayList<StageBean>();
		for(StageBean s : stages){
			stagesQueue.add(s);
		}
		HttpSession session = request.getSession();
		session.setAttribute("stages", stages);
		session.setAttribute("stagesQueue", stagesQueue);
		request.setAttribute("idProject", idProject);
		
		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/views/add-precedences.jsp");
		dispatcher.forward(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		List<StageBean> precedences = new ArrayList<StageBean>();
		StageBean stage = new StageBean();

		//Get parameters
		int idProject = Integer.parseInt(request.getParameter("id-project"));
		@SuppressWarnings("unchecked")
		List<StageBean> stagesQueue = (List<StageBean>)request.getSession().getAttribute("stagesQueue");
		
		stage.setIdStage(stagesQueue.remove(0).getIdStage());
		
		if(request.getParameterValues("id-precedences") != null)
		{
			String[] idPrecedences = request.getParameterValues("id-precedences");
			//Build the list with the precedences to store
			for(String p : idPrecedences){
				StageBean sb = new StageBean();
				sb.setIdStage(Integer.parseInt(p));
				precedences.add(sb);
			}
			StageDAO.getInstance().addPrecedences(stage, precedences);
		}
		
		if(!stagesQueue.isEmpty()){
			request.setAttribute("idProject", idProject);
			RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/views/add-precedences.jsp");
			dispatcher.forward(request, response);
		}else{
			request.getSession().removeAttribute("stages");
			request.getSession().removeAttribute("stagesQueue");
			request.getSession().removeAttribute("idProject");
		}
	}
}
