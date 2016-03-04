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

import controllers.utils.UtilityFunctions;
import controllers.utils.security.SecureProjectStrategy;
import controllers.utils.security.SecureStageStrategy;
import models.StageBean;
import models.StageDAO;

@WebServlet("/editstage")
public class EditStageServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final Logger LOGGER = Logger.getLogger(EditStageServlet.class.getName());

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            if (!SecureStageStrategy.getInstance().isAuthorized(request, response, getServletContext()))
                return;

            int idStage = Integer.parseInt(request.getParameter("idStage"));
            StageBean stage = StageDAO.getInstance().getStageInfo(idStage);
            request.setAttribute("stage", stage);
            
            RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/views/edit-stage.jsp");
            dispatcher.forward(request, response);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Something went wrong during getting edit stage page", e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // TODO Auto-generated method stub
        try {

            if (!SecureProjectStrategy.getInstance().isAuthorizedCreate(request, response, getServletContext()))
                return;

            Map<String, String> messages = new HashMap<>();

            request.setAttribute("messages", messages);

            if (!checkParameters(request, messages)) {
                RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/views/edit-stage.jsp");
                dispatcher.forward(request, response);
                return;
            }
             

            String name = request.getParameter("name");

            StageBean stage = new StageBean();

            int idStage = Integer.parseInt(request.getParameter("idStage"));
            stage.setIdStage(idStage);

            Map<String, Object> attributes = new HashMap<>();

            attributes.put("name", name);
            String goals = request.getParameter("goals");
            attributes.put("goals", goals);
            String requirements = request.getParameter("requirements");
            attributes.put("requirements", requirements);
            String start = request.getParameter("startday");
            attributes.put("startDay", start);
            String deadline = request.getParameter("finishday");
            attributes.put("finishDay", deadline);

  
           StageDAO.getInstance().updateStage(stage, attributes);
            
            response.sendRedirect(request.getContextPath() + "/stage?idStage=" + idStage);
            
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Something went wrong during editing project page", e);
        }
    }
    
    private static boolean checkParameters(HttpServletRequest request, Map<String, String> messages) {
        String finishDay = request.getParameter("finishday");
        String startDay = request.getParameter("startday");

        if (startDay != "" && !UtilityFunctions.isValidDateFormat(startDay)) {
            messages.put("startday", "<i class='fa fa-exclamation'></i>&nbsp;Please, insert a valid one.");
            return false;
        }

        if (finishDay != "" && !UtilityFunctions.isValidDateFormat(finishDay)) {
            messages.put("finishday", "<i class='fa fa-exclamation'></i>&nbsp;Please, insert a valid one.");
            return false;
        }
        
        return true;
    }
}
