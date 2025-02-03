package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.Cart;

/**
 * Servlet implementation class AddToCartServlet
 */
@WebServlet("/add-to-cart")
public class AddToCartServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		response.setContentType("text/html;charset=UTF-8");
		
		try (PrintWriter out = response.getWriter()) {
			List<Cart> cartList = new ArrayList<>();
			int id = Integer.parseInt(request.getParameter("id"));
			
			Cart cart = new Cart();
            cart.setId(id);
            cart.setQuantity(1);
            
            HttpSession session = request.getSession();
            
            List<Cart> cartListSession = (ArrayList<Cart>) session.getAttribute("cart-list");
            
            if (cartListSession == null) {
                cartList.add(cart);
                session.setAttribute("cart-list", cartList);
                response.sendRedirect("index.jsp");
            }
            else {
            	cartList = cartListSession;
            	boolean exist = false;
            	for(Cart c : cartList) {
            		if(c.getId() == id) {
            			exist = true;
            			out.println("<h3 style='color:crimson; text-align: center'>Item Already in Cart. <a href='cart.jsp'>GO to Cart Page</a></h3>");       		
            		}
            	}
            	if (!exist) {
                    cartList.add(cart);
                    response.sendRedirect("index.jsp");
                }
            }
		}
	}

}
