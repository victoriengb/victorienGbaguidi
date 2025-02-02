package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import model.User;

public class UserDao {

	private Connection conn;
	private String query;
	private PreparedStatement preparedStatement;
	private ResultSet rs;
	
	public UserDao(Connection conn) {
		this.conn = conn;
	}
	
	public User userLogin(String email, String password) {
		User user = new User();
		try {
			query = "select * from users where email=? and password=?";
			preparedStatement = this.conn.prepareStatement(query);
			preparedStatement.setString(1, email);
			preparedStatement.setString(2, password);
            rs = preparedStatement.executeQuery();
            
            if(rs.next()){
            	user.setId(rs.getInt("id"));
            	user.setName(rs.getString("name"));
            	user.setEmail(rs.getString("email"));
            	user.setPassword(rs.getString("password"));
            }
		}catch (SQLException e) {
            System.out.print(e.getMessage());
        }
		return user;
	}
}
