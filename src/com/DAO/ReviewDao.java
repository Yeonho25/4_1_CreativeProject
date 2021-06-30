package com.DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.persistence.Review;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class ReviewDao {
	
	
	String className = "com.mysql.cj.jdbc.Driver";
    String url = "jdbc:mysql://localhost:3306/mydb";
    String user = "root";
    String passwd = "yeonho5376!";
    
    ObservableList<Review> list;
    
	public ReviewDao() {
		try {
			Class.forName(className);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public List<Review> getList() {
		List<Review> list = new ArrayList<Review>(); 
		Connection conn = null;
		Statement st = null;
		ResultSet rs = null;
		try {
    		conn = DriverManager.getConnection(url, user, passwd);
    		System.out.println("접속완료!!");
    	} catch(Exception e) {
    		e.printStackTrace();
    	}
		String sql = "select * from review";
		try {
			st = conn.createStatement();
			rs = st.executeQuery(sql);
			while (rs.next()) {
				int id = rs.getInt("id");
				String memberID = rs.getString("member_id");
				String content = rs.getString("content");
				LocalDateTime regdate = rs.getTimestamp("register_date").toLocalDateTime();
				Double grade = rs.getDouble("rating");
				
				Review rev = new Review(memberID,content,grade);
				rev.setRegisterDate(regdate);
				rev.setId(id);
				list.add(rev);
			}
			rs.close();
			st.close();
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}
	
	public ObservableList<Review> getTourReviewList(String tourName) {
		list =  FXCollections.observableArrayList(); 
		Connection conn = null;
		ResultSet rs = null;
		Statement st = null;
		
		try {
    		conn = DriverManager.getConnection(url, user, passwd);
    		System.out.println("접속완료!!");
    	} catch(Exception e) {
    		e.printStackTrace();
    	}
		String sql = "select * from review Where tour_name = \""+tourName+"\"";
		try {
			
			st = conn.createStatement();
			rs = st.executeQuery(sql);
			while (rs.next()) {
				int id = rs.getInt("id");
				String memberID = rs.getString("member_id");
				String content = rs.getString("content");
				//LocalDateTime regdate = rs.getTimestamp("register_date").toLocalDateTime();
				Double grade = rs.getDouble("rating");
				
				Review rev = new Review(memberID,content,grade);
				
				//rev.setRegisterDate(regdate);
				rev.setId(id);
				list.add(rev);
			}
			rs.close();
			st.close();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
	
	public ObservableList<Review> getRestaurantReviewList(int restaurantId) {
	      list = FXCollections.observableArrayList(); 
	      Connection conn = null;
	      ResultSet rs = null;
	      Statement st = null;
	      
	      try {
	          conn = DriverManager.getConnection(url, user, passwd);
	          System.out.println("접속완료!!");
	       } catch(Exception e) {
	          e.printStackTrace();
	       }
	      String sql = "select * from review Where restaurant_id = "+restaurantId;
	      try {
	         
	         st = conn.createStatement();
	         rs = st.executeQuery(sql);
	         while (rs.next()) {
	            int id = rs.getInt("id");
	            String memberID = rs.getString("member_id");
	            String content = rs.getString("content");
	            LocalDateTime regdate = rs.getTimestamp("register_date").toLocalDateTime();
	            Double grade = rs.getDouble("rating");
	            
	            Review rev = new Review(memberID,content,grade);
	            rev.setId(id);
	            rev.setRegisterDate(regdate);
	            list.add(rev);
	         }
	         rs.close();
	         st.close();
	         conn.close();
	      } catch (SQLException e) {
	         // TODO Auto-generated catch block
	         e.printStackTrace();
	      }
	      return list;
	   }
	
	public int registerTourReview(Review rev) throws SQLException {

		Connection conn = DriverManager.getConnection(url, user, passwd);
		PreparedStatement pstmt;
		String tourName = rev.getTourName();
		String content = rev.getContent();
		String memberId = "root";
		Double grade = rev.getGrade();
		
PreparedStatement pstmt2;
		
		String sql2 = "INSERT INTO point(point,member_id) " + 
				"VALUES(?,?)";
		
		pstmt2 =  conn.prepareStatement(sql2);
		pstmt2.setInt(1, 500);
		pstmt2.setString(2, "root");
		//pstmt2.setInt(3, x);
		int result2 = pstmt2.executeUpdate();
		if(result2 == 1) {
			System.out.println(tourName+" 관광지에 대한 리뷰 등록 완료 !");
//			conn.commit;
		}else {
			// 에러난경우
		}
		
		String sql = "INSERT INTO review(content,tour_name,member_id,rating, point_id) " + 
				"VALUES(?,?,?,?, 1)";
		
		pstmt = conn.prepareStatement(sql);
		pstmt.setString(1, content);
		pstmt.setString(2, tourName);
		pstmt.setString(3, memberId);
		pstmt.setDouble(4, grade);

		int result = pstmt.executeUpdate();
		if(result == 1) {
			System.out.println(tourName+" 관광지에 대한 리뷰 등록 완료 !");
		}else {
			// 에러난경우
		}
		
		pstmt.close();
		conn.close();
		
		return result;
	}
	
//	public int 관광지리뷰등록및포인트적립(Review rev) throws SQLException {
//
//		Connection conn = DriverManager.getConnection(url, user, passwd);
//		conn.setAutoCommit(false);
//		
//		PreparedStatement pstmt;
//		PreparedStatement pstmt2;
//		
//		String tourName = rev.getTourName();
//		String content = rev.getContent();
//		String memberId = rev.getMemberId();
//		Double grade = rev.getGrade();
//		
//		String sql = "INSERT INTO review(content,tour_name,member_id,grade) " + 
//				"VALUES(?,?,?,?)";
//		
//		pstmt = conn.prepareStatement(sql);
//		pstmt.setString(1, content);
//		pstmt.setString(2, tourName);
//		pstmt.setString(3, memberId);
//		pstmt.setDouble(4, grade);
//
//		int result = pstmt.executeUpdate();
//		if(result == 1) {
//			System.out.println(tourName+" 관광지에 대한 리뷰 등록 완료 !");
//		}else {
//			// 에러난경우
//		}
//		
//		String sql2 = "INSERT INTO point(point,member_id,review_id) " + 
//				"VALUES(?,?,LAST_INSERT_ID())";
//		
//		pstmt2 =  conn.prepareStatement(sql2);
//		pstmt2.setInt(1, 500);
//		pstmt2.setString(2, memberId);
//		//pstmt2.setInt(3, x);
//		int result2 = pstmt.executeUpdate();
//		if(result2 == 1) {
//			conn.commit;
//		}else {
//			// 에러난경우
//		}
//		
//		pstmt.close();
//		conn.close();
//		
//		return result;
//	}
	
	public int registerRestaurantReview(Review rev) throws SQLException {
		Connection conn = DriverManager.getConnection(url, user, passwd);

		String restaurantId = rev.getRestaurantId();
		String content = rev.getContent();
		String memberId = rev.getMemberId();
		Double grade = rev.getGrade();
		
		String sql = "INSERT INTO review(content,restaurant_id,member_id,rating) " + 
				"VALUES(?,?,?,?)";
	
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setString(1, content);
		pstmt.setString(2, restaurantId);
		pstmt.setString(3, memberId);
		pstmt.setDouble(4, grade);

		int result = pstmt.executeUpdate();
		if(result == 1) {
			// 등록완료
		}else {
			// 에러난경우
		}
		pstmt.close();
		conn.close();
		return result;
	}
    
	public int UpdateReviewContent(Review rev) throws SQLException {
		Connection conn = DriverManager.getConnection(url, user, passwd);
		
		int id = rev.getId();
		
		String sql = "UPDATE review SET content=\"?\" WHERE id=?";
	
		PreparedStatement pstmt = conn.prepareStatement(sql);
		
		String content = rev.getContent();
		pstmt.setString(1, content);
		pstmt.setInt(2, id);
		
		int result = pstmt.executeUpdate();
		
		if(result == 1) {
			// 등록완료
		}else {
			// 등록실패
		}
		pstmt.close();
		conn.close();
		
		return 0;
	}
	
	public int UpdateReviewGrade(Review rev) throws SQLException {
		Connection conn = DriverManager.getConnection(url, user, passwd);
		
		int id = rev.getId();
		
		String sql = "UPDATE review SET grade=\"?\" WHERE id=?";
	
		PreparedStatement pstmt = conn.prepareStatement(sql);
		
		Double grade = rev.getGrade();
		pstmt.setDouble(1, grade);
		pstmt.setInt(2, id);
		
		int result = pstmt.executeUpdate();
		
		if(result == 1) {
			// 등록완료
		}else {
			// 등록실패
		}
		pstmt.close();
		conn.close();
		
		return result;
	}
	
	public int DeleteReview(Review rev) throws SQLException {
		Connection conn = DriverManager.getConnection(url, user, passwd);
		
		int id = rev.getId();
		
		String sql = "DELETE FROM review WHERE id=?";
		
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setInt(1, id);
		
		int result = pstmt.executeUpdate();
		
		if(result == 1) {
			// 등록완료
		}else {
			// 등록실패
		}
		pstmt.close();
		conn.close();
		
		return result;
	}
	
}
