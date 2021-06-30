package controller;

import java.sql.*;

public class DBConnect 
{
	private Connection conn;
	
	DBConnect()
	{
		conn = null;
	}
	
	public Connection getConnection()
	{
		return conn;
	}
	
	public void connect()
	{
		String url = "jdbc:mysql://localhost:3306/mydb";
		String id = "root";
		String pw = "yeonho5376!";
		
		try
		{
			Class.forName("com.mysql.cj.jdbc.Driver");
			
			conn = DriverManager.getConnection(url, id, pw);
		} catch (ClassNotFoundException e)
		{
			e.printStackTrace();
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
	}
	
	public void close()
	{
		try {
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}	
	}
}
