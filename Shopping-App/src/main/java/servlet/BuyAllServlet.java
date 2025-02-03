package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import connection.DatabaseConnector;
import dao.OrderDao;
import model.Cart;
import model.Order;

/**
 * Servlet implementation class BuyAllServlet
 */
@WebServlet("/buy-all")
public class BuyAllServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		try(PrintWriter out = response.getWriter()){
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            Date date = new Date();
			List<Cart> cartListSession = (ArrayList<Cart>) request.getSession().getAttribute("cart-list");
			
			if(cartListSession != null) {
				for(Cart c:cartListSession) {
					Order order = new Order();
					order.setId(c.getId());
					order.setUid(1);
					order.setQuantity(c.getQuantity());
					order.setDate(formatter.format(date));
					
					OrderDao oDao = new OrderDao(DatabaseConnector.getConnection());
					boolean result = oDao.insertOrder(order);
					if(!result) break;
				}
				cartListSession.clear();
				response.sendRedirect("orders.jsp");
			}else {
				response.sendRedirect("cart.jsp");
			}
		} catch (ClassNotFoundException e) {
			
			e.printStackTrace();
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
