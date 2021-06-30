package controller;

import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import DTO.RestaurantDTO;
import application.RestaurantReviewSearchController;
import application.TourReviewController;
import application.TourReviewSearchController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class Restaurant_CRUD implements Initializable {
	
	@FXML private TextField id;
	@FXML private TextField name;
	@FXML private TextField phone;
	@FXML private TextField category;
	@FXML private TextField address;
	@FXML private Button createBtn;
	@FXML private Button updateBtn;
	@FXML private Button deleteBtn;
	@FXML private Button reviewCreateBtn;
	@FXML private Button reviewSearchBtn;
	
	private List<TextField> textFields = null;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) 
	{
	
		id.setDisable(true);
		textFields = Arrays.asList(name, category, phone, address);
		
		
	}
	
	public void handleReviewCreate(ActionEvent event)
	{
		
	}
	
	public void handleReview(ActionEvent event)
	{
		try
		{
			FXMLLoader loader = new FXMLLoader();
			  loader.setLocation(getClass().getResource("/view/RestaurantReivewSearch.fxml"));
			  Parent root = (Parent) loader.load();
			  Scene scene = new Scene(root);
			  RestaurantReviewSearchController pop = loader.getController();
			  pop.initData(id.getText());
			  
			  Stage stage = new Stage();
			  stage.setScene(scene);
			  stage.show();
			stage.show();
		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public void initData(int restaurantId)
	{
		// 음식점 검색 controller에서 값 받아와얗마
		System.out.println(restaurantId);
		id.setText(String.valueOf(restaurantId));
		
		DBConnect connect = new DBConnect();
		connect.connect();
		
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		String sql  = null;
		
		try
		{
			sql = "select * from restaurant where id=" + id.getText();
			
			pstmt = connect.getConnection().prepareStatement(sql);
			rs = pstmt.executeQuery();
			
			rs.next();
			
			int index = 1;
			for (TextField field : textFields) 
			{
				field.setText(rs.getString(++index));
			}
		} catch (SQLException e)
		{
			e.printStackTrace();
		} finally
		{
			connect.close();
		}
	}
	
	
	public void handleCreate(ActionEvent event)
	{
		if (createBtn.getText().equals("추가"))
		{
			makeNull();
			id.setText("");
			createBtn.setText("등록");
			updateBtn.setDisable(true);
			deleteBtn.setDisable(true);

		} else
		{
			if (isNull() != false)
			{
				DBConnect connect = new DBConnect();
				connect.connect();
				
				PreparedStatement pstmt = null;
				String sql  = null;
				
				try
				{
					sql = "insert into restaurant(name, category, store_tel, address) values (?, ?, ?, ?)";
					
					pstmt = connect.getConnection().prepareStatement(sql);
					int index = 1;
					for (TextField field : textFields) 
					{
						pstmt.setString(index++, field.getText());
					}
					pstmt.executeUpdate();
					
					
					
					sql = "select * from restaurant where name=? and category=? and store_tel=? and address=?";
					pstmt = connect.getConnection().prepareStatement(sql);
					index = 1;
					for (TextField field : textFields) 
					{
						pstmt.setString(index++, field.getText());
					}
					
					ResultSet rs = pstmt.executeQuery();
					rs.next();
					id.setText(String.valueOf(rs.getInt(1)));
					
					showAlert(true, "등록되었습니다.");
				} catch (SQLException e)
				{
					e.printStackTrace();
				} finally
				{
					connect.close();
					
					createBtn.setText("추가");

					updateBtn.setDisable(false);
					deleteBtn.setDisable(false);
					
				}
			}
			
		}
		
	}
	
	public void handleUpdate(ActionEvent event)
	{
		DBConnect connect = new DBConnect();
		connect.connect();
		
		PreparedStatement pstmt = null;
		String sql  = null;
		
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Question!");
		alert.setContentText("[" + name.getText() + "] 음식점을 수정하시겠습니까?");
		Optional<ButtonType> result = alert.showAndWait();
		
		if (result.get() == ButtonType.OK )
		{
			try
			{	
				sql = "update restaurant set name=?, category=?, store_tel=?, address=? where id=?";
				
				pstmt = connect.getConnection().prepareStatement(sql);
				
				int index = 1;
				for (TextField field : textFields) 
				{
					pstmt.setString(index++, field.getText());
				}
				pstmt.setString(5, id.getText());
				
				pstmt.executeUpdate();
				
				showAlert(true, "수정되었습니다.");
			} catch (SQLException e)
			{
				e.printStackTrace();
			} finally
			{
				connect.close();
			}
		}
				
	}
	
	public void handleDelete(ActionEvent event)
	{
		DBConnect connect = new DBConnect();
		connect.connect();
		
		PreparedStatement pstmt = null;
		String sql  = null;
		
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Question!");
		alert.setContentText("[" + name.getText() + "] 음식점을 삭제하시겠습니까?");
		Optional<ButtonType> result = alert.showAndWait();
		
		if (result.get() == ButtonType.OK )
		{
			try
			{
				sql = "delete from restaurant where id=" + id.getText();
				
				pstmt = connect.getConnection().prepareStatement(sql);
				pstmt.executeUpdate();
				
				showAlert(true, "삭제되었습니다.\n창이 자동으로 종료됩니다.");
				
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
	}
	
	private void showAlert(boolean isSuccess, String message)
	{
		Alert alert = null;
		if (isSuccess == true)
		{
			alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Success!");
		}
		else
		{
			alert = new Alert(AlertType.WARNING);
			alert.setTitle("Fail!");
		}	
		
		alert.setContentText("[" + id.getText() + " - " + name.getText() + "] 음식점이 " + message);
		alert.showAndWait();
	}
	
	private void makeNull()
	{
		for (TextField field : textFields) 
		{
	        field.setText("");
		}
	}
	
	private boolean isNull()
	{		
		
		for (TextField field : textFields) 
		{
	        if (field.getText().isEmpty()) 
	        {
	        	
	        	Alert alert = new Alert(AlertType.WARNING);
				alert.setTitle("Fail!");
				alert.setContentText("빈 칸이 존재합니다.");
				alert.showAndWait();
	            return false;
	        }
		}
		return true;
	}

	
}
