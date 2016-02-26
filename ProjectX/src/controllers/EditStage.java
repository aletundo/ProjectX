package controllers;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import controllers.utils.security.SecureStageStrategy;

@WebServlet("/editstage")
public class EditStage extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			if (!SecureStageStrategy.getInstance().isAuthorized(request, response, getServletContext()))
				return;

			RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/views/edit-stage.jsp");
			dispatcher.forward(request, response);
		} catch (Exception e) {
			/* TODO LOGGER */
		}
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		try {
			doGet(request, response);
		} catch (Exception e) {
			/* TODO LOGGER */
		}
	}
}
