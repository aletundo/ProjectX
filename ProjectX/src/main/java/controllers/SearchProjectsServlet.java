package controllers;

import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import models.ProjectBean;
import models.ProjectDAO;

@WebServlet(name = "SearchProjectsServlet", urlPatterns = { "/searchprojects" })
public class SearchProjectsServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final Logger LOGGER = Logger.getLogger(SearchProjectsServlet.class.getName());

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            HttpSession session = request.getSession(false);
            if (session == null || session.getAttribute("idUser") == null) {
                response.sendError(403, "Your session is not valid! Try again.");
            }
            RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/views/search-projects.jsp");
            dispatcher.forward(request, response);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Something went wrong during getting search project page", e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            HttpSession session = request.getSession(false);
            if (session == null || session.getAttribute("idUser") == null) {
                response.sendError(403, "Your session is not valid! Try again.");
            }

            String subjectArea = request.getParameter("subject-area");
            List<ProjectBean> projects = ProjectDAO.getInstance().searchProjects(subjectArea);
            RequestDispatcher dispatcher;
            if (projects.isEmpty()) {
                request.setAttribute("noMatchFound", "No related projects found! Sorry, try again :(");
            } else {
                request.setAttribute("projects", projects);
            }
            request.setAttribute("subjectArea", subjectArea);
            dispatcher = getServletContext().getRequestDispatcher("/views/related-projects.jsp");
            dispatcher.forward(request, response);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Something went wrong during searching projects", e);
        }
    }

}
