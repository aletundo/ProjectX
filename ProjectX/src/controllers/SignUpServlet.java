package controllers;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.UserBean;
import models.UserDAO;

/**
 * Servlet implementation class SignUp
 */
@WebServlet(name = "SignUpServlet", urlPatterns = {"/SignUp"})
public class SignUpServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SignUpServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
    @Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String username = request.getParameter("user");
		String pw = request.getParameter("pw");
		String type = request.getParameter("type");
		String name = request.getParameter("name");
		
		
		UserBean user = new UserBean();
		user.setUsername(username);
		user.setPw(pw);
		user.setName(name);
		user.setType(type);
		
		boolean saved = UserDAO.getInstance().signUpUser(user);
		System.out.println("stored? " + saved);//NOPMD
	}

}
