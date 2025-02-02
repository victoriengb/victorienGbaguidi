package connection;

import java.sql.*;

public class DatabaseConnector {
	private static String url = "jdbc:mysql://localhost:3306/projet_java";
    private static String user = "root";
    private static String password = "";
    
    private static Connection conn = null;
    
    public static Connection getConnection() throws ClassNotFoundException{
    	if(conn == null) {
    		Class.forName("com.mysql.cj.jdbc.Driver");
    		try {
    			conn = DriverManager.getConnection(url, user, password);
    		}catch(SQLException e) {
    			e.printStackTrace();
    		}    		
    	}
    	return conn;
    }
}
