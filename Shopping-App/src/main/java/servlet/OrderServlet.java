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
import model.User;

/**
 * Servlet implementation class OrderServlet
 */
@WebServlet("/order")
public class OrderServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try (PrintWriter out = response.getWriter()) {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            Date date = new Date();

            String productId = request.getParameter("id");
            int productQuantity = Integer.parseInt(request.getParameter("quantity"));
            if (productQuantity <= 0) {
            	productQuantity = 1;
            }
            Order order = new Order();
            order.setId(Integer.parseInt(productId));
            order.setUid(new User().getId());
            order.setQuantity(productQuantity);
            order.setDate(formatter.format(date));

            OrderDao orderDao = new OrderDao(DatabaseConnector.getConnection());
            boolean result = orderDao.insertOrder(order);
            if (result) {
                List<Cart> cartListSession = (ArrayList<Cart>) request.getSession().getAttribute("cart-list");
                if (cartListSession != null) {
                    for (Cart c : cartListSession) {
                        if (c.getId() == Integer.parseInt(productId)) {
                        	cartListSession.remove(cartListSession.indexOf(c));
                            break;
                        }
                    }
                }
                response.sendRedirect("orders.jsp");
            } else {
                out.println("order failed");
            }
        }catch (ClassNotFoundException e) {
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
