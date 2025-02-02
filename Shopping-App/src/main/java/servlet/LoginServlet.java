package servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import connection.DatabaseConnector;
import dao.UserDao;
import model.User;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/user-login")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8");
		
		try (PrintWriter out = response.getWriter()) {
			String email = request.getParameter("login-email");
			String password = request.getParameter("login-password");
			
			UserDao userDao = null;
			try {
				userDao = new UserDao(DatabaseConnector.getConnection());				
			}catch(ClassNotFoundException e) {
				e.printStackTrace();
				System.out.println(e.getMessage());
			}
			User user = null;
			
			if(userDao != null) {				
				user = userDao.userLogin(email, password);
			}
			
			if(user != null) {
				request.getSession().setAttribute("auth", user);
				response.sendRedirect("index.jsp");
			}
			else {
				out.println("there is no user");
			}
		}
	}

}
