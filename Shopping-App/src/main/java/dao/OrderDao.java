package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.Order;
import model.Product;

public class OrderDao {

	private Connection conn;
	private String query;
	private PreparedStatement preparedStatement;
	private ResultSet rs;
	
	public OrderDao(Connection conn) {
		this.conn = conn;
	}
	
	public boolean insertOrder(Order order) {
        boolean result = false;
        try {
            query = "insert into orders (p_id, u_id, o_quantity, o_date) values(?,?,?,?)";
            preparedStatement = this.conn.prepareStatement(query);
            preparedStatement.setInt(1, order.getId());
            preparedStatement.setInt(2, order.getUid());
            preparedStatement.setInt(3, order.getQuantity());
            preparedStatement.setString(4, order.getDate());
            preparedStatement.executeUpdate();
            result = true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return result;
    }
	
    public List<Order> userOrders(int id) {
        List<Order> orders = new ArrayList<>();
        try {
            query = "select * from orders where u_id=? order by orders.o_id desc";
            preparedStatement = this.conn.prepareStatement(query);
            preparedStatement.setInt(1, id);
            rs = preparedStatement.executeQuery();
            while (rs.next()) {
                Order order = new Order();
                ProductDao productDao = new ProductDao(this.conn);
                int productId = rs.getInt("p_id");
                Product product = productDao.getSingleProduct(productId);
                order.setOrderId(rs.getInt("o_id"));
                order.setId(productId);
                order.setName(product.getName());
                order.setCategory(product.getCategory());
                order.setPrice(product.getPrice()*rs.getInt("o_quantity"));
                order.setQuantity(rs.getInt("o_quantity"));
                order.setDate(rs.getString("o_date"));
                orders.add(order);
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
        return orders;
    }
    
    public void cancelOrder(int id) {
        try {
            query = "delete from orders where o_id=?";
            preparedStatement = this.conn.prepareStatement(query);
            preparedStatement.setInt(1, id);
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.print(e.getMessage());
        }
    }
}
