package controllers;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import controllers.utils.UtilityFunctions;
import models.UserBean;
import models.UserDAO;

@WebServlet(name = "SignUpServlet", urlPatterns = { "/signup" })
public class SignUpServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/views/signup.jsp");
		dispatcher.forward(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		Map<String, String> messages = new HashMap<>();

		request.setAttribute("messages", messages);

		if (!checkParameters(request, messages)) {
			RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/views/signup.jsp");
			dispatcher.forward(request, response);
			return;
		}

		String username = request.getParameter("username");
		String pw = request.getParameter("pw");
		String type = request.getParameter("type");
		String fullname = request.getParameter("fullname");
		String mail = request.getParameter("mail");
		String skills = request.getParameter("skills");

		UserBean user = new UserBean();
		user.setUsername(username);
		user.setPw(pw);
		user.setFullname(fullname);
		user.setType(type);
		user.setSkills(skills);
		user.setMail(mail);
		boolean stored = UserDAO.getInstance().signUpUser(user);

		if (!stored) {
			messages.put("error", "<br><i class='fa fa-frown-o'></i>&nbsp;Sorry, something went wrong during your registration, try again.");
			RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/views/signup.jsp");
			dispatcher.forward(request, response);
			return;
		}

		response.sendRedirect(request.getContextPath());

	}

	private static boolean checkParameters(HttpServletRequest request, Map<String, String> messages) {

		String username = request.getParameter("username");
		String pw = request.getParameter("pw");
		String type = request.getParameter("type");
		String fullname = request.getParameter("fullname");
		String mail = request.getParameter("mail");
		String skills = request.getParameter("skills");

		if (fullname == null || fullname.trim().isEmpty()) {
			messages.put("fullname", "<i class='fa fa-exclamation'></i>&nbsp;Please, insert a valid fullname.");
			return false;
		}

		if (username == null || username.trim().isEmpty()) {
			messages.put("username", "<i class='fa fa-exclamation'></i>&nbsp;Please, insert username.");
			return false;
		}

		if (pw == null || pw.trim().isEmpty()) {
			messages.put("pw", "<i class='fa fa-exclamation'></i>&nbsp;Please, insert pw.");
			return false;
		}

		if (mail == null || mail.trim().isEmpty() || !UtilityFunctions.isValidMail(mail)) {
			messages.put("mail", "<i class='fa fa-exclamation'></i>&nbsp;Please, insert a valid mail.");
			return false;
		}

		if (skills == null || skills.trim().isEmpty()) {

			messages.put("skills", "<i class='fa fa-exclamation'></i>&nbsp;Please, insert skills.");
			return false;
		}

		if (type == null || type.trim().isEmpty()) {
			messages.put("type", "<i class='fa fa-exclamation'></i>&nbsp;Please, insert type.");
			return false;
		}

		return true;
	}

}
