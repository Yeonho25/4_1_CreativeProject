package com.DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.persistence.Restaurant;
import com.persistence.Tour;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;


public class RestaurantDao {

	String className = "com.mysql.cj.jdbc.Driver";
    String url = "jdbc:mysql://localhost:3306/mydb";
    String user = "root";
    String passwd = "yeonho5376!";
    
    ObservableList<Restaurant> list;
    
	public RestaurantDao() {
		try {
			Class.forName(className);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public ObservableList<Restaurant> getRestaurantList() {
		ObservableList<Restaurant> list = FXCollections.observableArrayList(); 
		Connection conn = null;
		Statement st = null;
		ResultSet rs = null;
		try {
    		conn = DriverManager.getConnection(url, user, passwd);
    		System.out.println("접속완료!!");
    	} catch(Exception e) {
    		e.printStackTrace();
    	}
		String sql = "select * from restaurant";
		try {
			st = conn.createStatement();
			rs = st.executeQuery(sql);
			while (rs.next()) {
				int id = rs.getInt("id");
				String storeTel = rs.getString("store_tel");
				String category = rs.getString("category");
				String address = rs.getString("address");
				String name = rs.getString("name");

			
				Restaurant res = new Restaurant(storeTel,category,address,name);
				res.setId(id);
				list.add(res);
			}
			rs.close();
			st.close();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
	public ObservableList<Restaurant> searchRestaurant(String name){
		list = FXCollections.observableArrayList(); 
		Connection conn = null;
		Statement st = null;
		ResultSet rs = null;
		try {
    		conn = DriverManager.getConnection(url, user, passwd);
    		System.out.println("접속완료!!");
    	} catch(Exception e) {
    		e.printStackTrace();
    	}
		
	      String sql = "SELECT * FROM restaurant where name=\'"+name+"/'";

	      try {
				st = conn.createStatement();
				rs = st.executeQuery(sql);
				while (rs.next()) {
					int id = rs.getInt("id");
					String storeTel = rs.getString("store_tel");
					String category = rs.getString("category");
					String address = rs.getString("address");
				
					Restaurant res = new Restaurant(storeTel,category,address,name);
					res.setId(id);
					list.add(res);
				}
				rs.close();
				st.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return list;
	}
	public ObservableList<Restaurant> searchRestaurant(String siDo, String area){
		list = FXCollections.observableArrayList(); 
		Connection conn = null;
		Statement st = null;
		ResultSet rs = null;
		try {
    		conn = DriverManager.getConnection(url, user, passwd);
    		System.out.println("접속완료!!");
    	} catch(Exception e) {
    		e.printStackTrace();
    	}
		
	      String sql = "SELECT * FROM restaurant where address like (\'" + siDo + " " + area + "%\')";

	      try {
				st = conn.createStatement();
				rs = st.executeQuery(sql);
				while (rs.next()) {
					int id = rs.getInt("id");
					String storeTel = rs.getString("store_tel");
					String category = rs.getString("category");
					String address = rs.getString("address");
					String name = rs.getString("name");

				
					Restaurant res = new Restaurant(storeTel,category,address,name);
					res.setId(id);
					list.add(res);
				}
				rs.close();
				st.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return list;
	}
	public ObservableList<Restaurant> searchRestaurant(String siDo, String area,String name){
		list = FXCollections.observableArrayList(); 
		Connection conn = null;
		Statement st = null;
		ResultSet rs = null;
		try {
    		conn = DriverManager.getConnection(url, user, passwd);
    		System.out.println("접속완료!!");
    	} catch(Exception e) {
    		e.printStackTrace();
    	}
		
	      String sql = "SELECT * FROM restaurant where name like \'%" + name + "%\' and address like (\'" + siDo + " " + area + "%\')";

	      try {
				st = conn.createStatement();
				rs = st.executeQuery(sql);
				while (rs.next()) {
					int id = rs.getInt("id");
					String storeTel = rs.getString("store_tel");
					String category = rs.getString("category");
					String address = rs.getString("address");

					Restaurant res = new Restaurant(storeTel,category,address,rs.getString("name"));
					res.setId(id);
					list.add(res);
				}
				rs.close();
				st.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return list;
	}
	public Restaurant getRestaurantInfo(int rsId) {
		Restaurant restaurant = null;
		Connection conn = null;
		Statement st = null;
		ResultSet rs = null;
		try {
    		conn = DriverManager.getConnection(url, user, passwd);
    		System.out.println("접속완료!!");
    	} catch(Exception e) {
    		e.printStackTrace();
    	}
		String sql = "select * from restaurant where id="+rsId;
		try {
			st = conn.createStatement();
			rs = st.executeQuery(sql);
			if (rs.next()) {
				int id = rs.getInt("id");
				String storeTel = rs.getString("storeTel");
				String category = rs.getString("category");
				String address = rs.getString("address");
				String name = rs.getString("name");
			
				restaurant = new Restaurant(storeTel,category,address,name);
				restaurant.setId(id);
			}
			rs.close();
			st.close();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return restaurant;
	}
	
}
