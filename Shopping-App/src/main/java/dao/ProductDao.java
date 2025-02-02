package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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
}
