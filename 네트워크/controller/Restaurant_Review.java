package controller;

import java.net.URL;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ResourceBundle;

import com.sun.javadoc.ThrowsTag;

import DTO.RestaurantDTO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class Restaurant_Review implements Initializable {
	@FXML private TextField id;
	@FXML private TextField name;
	@FXML private ComboBox<String> rating;
	@FXML private TextArea content;
	@FXML private Button createBtn;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		ObservableList<String> list = FXCollections.observableArrayList();
		
		for (float i=0; i<=5; i+=0.5)
		{
			list.add(String.valueOf(i));
		}
		
		rating.setItems(list);
	}
	
	public void initData(RestaurantDTO restaurant)
	{
		this.id.setText(String.valueOf(restaurant.getId()));
		this.name.setText(restaurant.getName());
		
		this.id.setDisable(true);
		this.name.setDisable(true);
	}
	
	public void handleCreate(ActionEvent event)
	{
		Alert alert = null;
		try
		{
			if (rating.getSelectionModel().getSelectedItem().toString().equals("") || content.getText().equals(""))
			{
				alert = new Alert(AlertType.WARNING);
				alert.setTitle("Error!");
				alert.setContentText("빈 칸이 존재합니다.");
				
				alert.showAndWait();
			} else
			{
				DBConnect connect = new DBConnect();
				connect.connect();
				
				PreparedStatement pstmt = null;
				ResultSet rs = null;
				String sql  = null;
				
				try
				{
					// point table
					sql = "insert into point(member_id, point, register_date) values (?, ?, ?)";
					pstmt = connect.getConnection().prepareStatement(sql);
//					pstmt.setString(1, Member);	// member id
					pstmt.setString(1, "root");
					pstmt.setInt(2, 500);
					
					SimpleDateFormat input_format    = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					String now = input_format.format( new Date(System.currentTimeMillis()));
					pstmt.setString(3, now);
					
					pstmt.executeUpdate();
					sql = "select * from point where register_date = '" + now + "' and member_id = 'root'"; // + member_id 
					pstmt = connect.getConnection().prepareStatement(sql);
					
					rs = pstmt.executeQuery();
					
					rs.next();
					
					// review table
					sql = "insert into review(member_id, restaurant_id, content, rating, register_date, point_id) values (?, ?, ?, ?, ?, ?)";
					
					pstmt = connect.getConnection().prepareStatement(sql);
//					pstmt.setString(1, rs.getString(member_id));
					pstmt.setString(1, "root");
					pstmt.setString(2, id.getText());
					pstmt.setString(3, content.getText());
					pstmt.setFloat(4, Float.parseFloat(rating.getSelectionModel().getSelectedItem().toString()));
					pstmt.setString(5, now);
					pstmt.setInt(6, rs.getInt(1));
					
					pstmt.executeUpdate();
					
					
					// member table -> point
//					sql = "select point from member where id='" + id + "'";
					sql = "select point from member where id='root'";
					pstmt = connect.getConnection().prepareStatement(sql);
					
					rs = pstmt.executeQuery();
					rs.next();
					
//					sql = "update member set point = " + (rs.getInt(1)+500) + " where id='"  + 멤버id + "'";
					sql = "update member set point = " + (rs.getInt(1)+500) + " where id='root'";
					pstmt = connect.getConnection().prepareStatement(sql);
					pstmt.executeUpdate();
					
					
					alert = new Alert(AlertType.INFORMATION);
					alert.setTitle("Success!");
					alert.setContentText("[" + name.getText() + "] 리뷰가 등록되었습니다.");
					
					alert.showAndWait();
					
					Stage stage = (Stage) createBtn.getScene().getWindow();
				    stage.close();

				} catch (SQLException e)
				{
					e.printStackTrace();
				} finally
				{
					connect.close();
				}
			}
		} catch (Exception e)
		{
			alert = new Alert(AlertType.WARNING);
			alert.setTitle("Error!");
			alert.setContentText("빈 칸이 존재합니다.");
			
			alert.showAndWait();
		}
		
		
		
	}


}
