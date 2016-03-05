package controllers;

import java.io.IOException;
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
import controllers.utils.UtilityFunctions;
import controllers.utils.security.SecureProjectStrategy;
import models.UserDAO;
import models.StageBean;
import models.StageDAO;
import models.UserBean;

@WebServlet(name = "AddSupervisorServlet", urlPatterns = { "/addsupervisor" })
public class AddSupervisorServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final Logger LOGGER = Logger.getLogger(AddSupervisorServlet.class.getName());

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            if (!SecureProjectStrategy.getInstance().isAuthorized(request, response, getServletContext()))
                return;

            int idStage = Integer.parseInt(request.getParameter("idStage"));
            String startDay = request.getParameter("startDay");
            String finishDay = request.getParameter("finishDay");

            StageBean stage = new StageBean();
            stage.setIdStage(idStage);
            stage.setStartDay(startDay);
            stage.setFinishDay(finishDay);

            Map<Integer, List<Object>> workMap = UserDAO.getInstance().getCandidateSupervisors();
            List<UserBean> candidates = CalculateAvailableUsers.calculate(workMap, stage);
            candidates = UserDAO.getInstance().getUsersInfo(candidates);
            if (candidates.isEmpty()) {
                request.setAttribute("outsourcing", "True");
            } else {
                request.setAttribute("candidates", candidates);
            }
            RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/views/add-supervisor.jsp");
            dispatcher.forward(request, response);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Something went wrong during adding supervisors", e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        Map<String, String> messages = new HashMap<>();
        request.setAttribute("messages", messages);

        try {
            if (!SecureProjectStrategy.getInstance().isAuthorized(request, response, getServletContext()))
                return;

            StageBean stage = new StageBean();
            int idStage = Integer.parseInt(request.getParameter("idStage"));
            int idProject = Integer.parseInt(request.getParameter("idProject"));
            int idSupervisor;
            if (request.getParameter("companyname") != null) {

                String companyName = request.getParameter("companyname");
                String companyMail = request.getParameter("companymail");
                if (companyMail == null || companyMail.trim().isEmpty() || !UtilityFunctions.isValidMail(companyMail)) {
                    messages.put("clientmail", "<i class='fa fa-exclamation'></i>&nbsp;Please, insert a valid mail.");
                    RequestDispatcher dispatcher = getServletContext()
                            .getRequestDispatcher("/views/add-supervisor.jsp");
                    dispatcher.forward(request, response);
                    return;
                }
                idSupervisor = (Integer) request.getSession().getAttribute("idUser");
                stage.setIdSupervisor(idSupervisor);
                stage.setOutsourcing("True");
                
                String host = "localhost";
                String from = "fooCompany@bar.baz";
                String subject = "[OUTSOURCING]";
                String message = "Dear " + companyName + ", We are writing to ask you to carry out a stage of our project...";
                controllers.utils.SendEmail.sendEmail(companyMail, from, subject, message, host);

            } else {
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
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Something went wrong during adding supervisors", e);
        }
    }
}