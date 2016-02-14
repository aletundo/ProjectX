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

/**
 * Servlet implementation class HomeServlet
 */
@WebServlet(name = "LoginServlet", urlPatterns = { "/myprojects"})
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
    public LoginServlet() {
        super();
        // TODO Auto-generated constructor stub
    }
    
    
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String user = request.getParameter("user");
		String pw = request.getParameter("pw");
		boolean userValid = true;
		
		if(userValid){

	        HttpSession session = request.getSession();  
	        session.setAttribute("user", user);
	        //setting session to expiry in 30 mins
            session.setMaxInactiveInterval(30*60);
            Cookie userName = new Cookie("user", user);
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
