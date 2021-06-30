package com.DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.persistence.Tour;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class TourDao {

	String className = "com.mysql.cj.jdbc.Driver";
    String url = "jdbc:mysql://localhost:3306/mydb";
    String user = "root";
    String passwd = "yeonho5376!";

    ObservableList<Tour> list;
    
	public TourDao() {
		try {
			Class.forName(className);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public ObservableList<Tour> getTourList() {
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
		String sql = "select * from tour";
		try {
			st = conn.createStatement();
			rs = st.executeQuery(sql);
			while (rs.next()) {
				String name = rs.getString("name");
				String classification = rs.getString("classification");
				String address = rs.getString("address");
				String convenience = rs.getString("convenience");
				String recreational = rs.getString("recreational");
				String cultural = rs.getString("cultural");
				int capacity = rs.getInt("capacity");
				int parkingAvailable = rs.getInt("parking_available");
				String introduction = rs.getString("introduction");
				String managementInstitutionPhone = rs.getString("management_institution_phone");
				String managementInstitution = rs.getString("management_institution");
			
				Tour tour = new Tour(name,classification,address,convenience,recreational,cultural,
						capacity,parkingAvailable,introduction,managementInstitutionPhone,managementInstitution);

				list.add(tour);
			}
			rs.close();
			st.close();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
	public ObservableList<Tour> searchTour(String tourName, String siDo, String area) {
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
		String sql = "";
		if(tourName.equals(""))
	      sql = "SELECT * FROM tour where address like (\'" + siDo + " " + area + "%\')";
		else
			sql = "SELECT * FROM tour where name like '%" + tourName + "%' and  address like (\'" + siDo + " " + area + "%\')";

	      try {
				st = conn.createStatement();
				rs = st.executeQuery(sql);
				while (rs.next()) {
					String name = rs.getString("name");
					String classification = rs.getString("classification");
					String address = rs.getString("address");
					String convenience = rs.getString("convenience");
					String recreational = rs.getString("recreational");
					String cultural = rs.getString("cultural");
					int capacity = rs.getInt("capacity");
					int parkingAvailable = rs.getInt("parking_available");
					String introduction = rs.getString("introduction");
					String managementInstitutionPhone = rs.getString("management_institution_phone");
					String managementInstitution = rs.getString("management_institution");
				
					Tour tour = new Tour(name,classification,address,convenience,recreational,cultural,
							capacity,parkingAvailable,introduction,managementInstitutionPhone,managementInstitution);

					list.add(tour);
				}
				rs.close();
				st.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return list;
//	      try {
//	         pstmt = conn.prepareStatement(sql);
//	         rs = pstmt.executeQuery(sql);
//
//	         while (rs.next()) {
//
//	            for (int i = 1; i <= 11; i++) {
//	               str+=rs.getString(i)+"/";
//	            }
//	            strCopy=str.split("/");
//	            for(int i=0;i<strCopy.length;i++)
//	               System.out.print(strCopy[i]+" ");
//	            System.out.println();
//	         }
//
//	         return strCopy;
//	      } catch (SQLException e) {
//	         // TODO Auto-generated catch block
//	         e.printStackTrace();
//	         return null;
//	      }
	}
	
	
	
	public Tour getTourInfo(String tourName) {
		Tour tour = null;
		Connection conn = null;
		Statement st = null;
		ResultSet rs = null;
		try {
    		conn = DriverManager.getConnection(url, user, passwd);
    		System.out.println("접속완료!!");
    	} catch(Exception e) {
    		e.printStackTrace();
    	}
		String sql = "select * from tour where name=\""+tourName+"\"";
		try {
			st = conn.createStatement();
			rs = st.executeQuery(sql);
			while (rs.next()) {
				String name = rs.getString("name");
				String classfication = rs.getString("classfication");
				String address = rs.getString("address");
				String convienience = rs.getString("convienience");
				String recreational = rs.getString("recreational");
				String cultural = rs.getString("cultural");
				int capacity = rs.getInt("capacity");
				int parkingAvailable = rs.getInt("parking_available");
				String introduction = rs.getString("introduction");
				String managementInstitutionPhone = rs.getString("management_institution_phone");
				String managementInstitution = rs.getString("String management_institution");
			
				tour = new Tour(name,classfication,address,convienience,recreational,cultural,
						capacity,parkingAvailable,introduction,managementInstitutionPhone,managementInstitution);

			}
			rs.close();
			st.close();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return tour;
	}
	
}
