package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.Cart;
import model.Product;

public class ProductDao {

	private Connection conn;
	private String query;
	private PreparedStatement preparedStatement;
	private ResultSet rs;
	
	public ProductDao(Connection conn) {
		this.conn = conn;
	}
	
	public List<Product> getAllProducts(){
		List<Product> products = new ArrayList<>();
		
		try {
			query = "select * from products";
			preparedStatement = this.conn.prepareStatement(query);
            rs = preparedStatement.executeQuery();
            
            while (rs.next()) {
            	Product row = new Product();
                row.setId(rs.getInt("id"));
                row.setName(rs.getString("name"));
                row.setCategory(rs.getString("category"));
                row.setPrice(rs.getDouble("price"));
                row.setImage(rs.getString("image"));
                
                products.add(row);
            }
		}catch(SQLException e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
		return products;
	}
	
	public List<Cart> getCartProducts(List<Cart> cartList) {
        List<Cart> cartProducts = new ArrayList<>();
        try {
            if (cartList.size() > 0) {
                for (Cart item : cartList) {
                    query = "select * from products where id=?";
                    preparedStatement = this.conn.prepareStatement(query);
                    preparedStatement.setInt(1, item.getId());
                    rs = preparedStatement.executeQuery();
                    while (rs.next()) {
                        Cart row = new Cart();
                        row.setId(rs.getInt("id"));
                        row.setName(rs.getString("name"));
                        row.setCategory(rs.getString("category"));
                        row.setPrice(rs.getDouble("price")*item.getQuantity());
                        row.setQuantity(item.getQuantity());
                        cartProducts.add(row);
                    }

                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
        return cartProducts;
    }
	
	public double getTotalCartPrice(List<Cart> cartList) {
        double sum = 0;
        try {
            if (cartList.size() > 0) {
                for (Cart item : cartList) {
                    query = "select price from products where id=?";
                    preparedStatement = this.conn.prepareStatement(query);
                    preparedStatement.setInt(1, item.getId());
                    rs = preparedStatement.executeQuery();
                    while (rs.next()) {
                        sum+=rs.getDouble("price")*item.getQuantity();
                    }

                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
        return sum;
    }
	
	 public Product getSingleProduct(int id) {
		 Product row = null;
	        try {
	            query = "select * from products where id=? ";

	            preparedStatement = this.conn.prepareStatement(query);
	            preparedStatement.setInt(1, id);
	            ResultSet rs = preparedStatement.executeQuery();

	            while (rs.next()) {
	            	row = new Product();
	                row.setId(rs.getInt("id"));
	                row.setName(rs.getString("name"));
	                row.setCategory(rs.getString("category"));
	                row.setPrice(rs.getDouble("price"));
	                row.setImage(rs.getString("image"));
	            }
	        } catch (Exception e) {
	            e.printStackTrace();
	            System.out.println(e.getMessage());
	        }

	        return row;
	    }
}
