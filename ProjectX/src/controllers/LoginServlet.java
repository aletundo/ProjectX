package controllers;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import models.UserBean;
import models.UserDAO;

@WebServlet(name = "LoginServlet", urlPatterns = { "/myprojects"})
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String username = request.getParameter("user");
		String pw = request.getParameter("pw");
		
		UserBean user = new UserBean();
		user.setPw(pw);
		user.setUsername(username);
		System.out.println("Username: " + user.getUsername());
		
		boolean userValid = UserDAO.getInstance().validateUser(user);
		
		System.out.println(userValid);
		
		if(userValid){

	        HttpSession session = request.getSession();  
	        session.setAttribute("user", username);
	        //Setting session to expiry in 30 mins
            session.setMaxInactiveInterval(30*60);
            Cookie userName = new Cookie("user", username);
            userName.setMaxAge(30*60);
            response.addCookie(userName);
	        RequestDispatcher dispatcher =
					getServletContext().getRequestDispatcher("/views/myprojects.jsp");
			dispatcher.forward(request, response);
	        
		}
	}
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//No possibility to get the resources before login
		response.sendRedirect(request.getContextPath());

	}
}
