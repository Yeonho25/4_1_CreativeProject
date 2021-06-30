package controller;

import java.net.URL;
import java.sql.*;
import java.util.*;

import DTO.TourDTO;
import application.TourReviewController;
import application.TourReviewSearchController;
import javafx.event.*;
import javafx.fxml.*;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class Tour_CRUD implements Initializable 
{
	@FXML private AnchorPane anchorPane;
	@FXML private Button searchBtn;
	@FXML private Button createBtn;
	@FXML private Button updateBtn;
	@FXML private Button deleteBtn;
	@FXML private TextField name;
	@FXML private TextField classify;
	@FXML private TextField address;
	@FXML private TextField convenience;
	@FXML private TextField recreational;
	@FXML private TextField cultural;
	@FXML private TextField capacity;
	@FXML private TextField parking;
	@FXML private TextField introduction;
	@FXML private TextField management;
	@FXML private TextField management_phone;
	@FXML private Button reviewSearchBtn;
	@FXML private Button reviewCreateBtn;
	
	private List<TextField> textFields = null;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) 
	{
		name.setDisable(true);
		
		textFields = Arrays.asList(name, classify, address, convenience, recreational, cultural, capacity, parking, introduction, management, management_phone);	
	}
	
	
	
	public void handleReviewCreate(ActionEvent event)
	{
		try
		{
			FXMLLoader loader = new FXMLLoader();
			  loader.setLocation(getClass().getResource("/view/TourReview.fxml"));
			  Parent root = (Parent) loader.load();
			  Scene scene = new Scene(root);
			  
			  TourReviewController pop = loader.getController();
			  pop.initData(name.getText());
			  
			  Stage stage = new Stage();
			  stage.setScene(scene);
			  stage.show();
			stage.show();
			
		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public void handleReview(ActionEvent event)
	{
		try
		{
			FXMLLoader loader = new FXMLLoader();
			  loader.setLocation(getClass().getResource("/view/TourReviewSearch.fxml"));
			  Parent root = (Parent) loader.load();
			  Scene scene = new Scene(root);
			  
			  TourReviewSearchController pop = loader.getController();
			  pop.initData(name.getText());
			  
			  Stage stage = new Stage();
			  stage.setScene(scene);
			  stage.show();
			stage.show();
		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public void initData(String tourName)
	{
		
		name.setText(tourName);
		TourDTO tour = new TourDTO();
		tour.setName(tourName);
		
		setTourInfo();
	}
	
	public void setTourInfo()
	{
		DBConnect connect = new DBConnect();
		connect.connect();
		
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		
		try
		{
			String sql = "select * from tour where name='" + name.getText() + "'";
			pstmt = connect.getConnection().prepareStatement(sql);
			rs = pstmt.executeQuery();
			
			if(rs.next())
			{
				int i = 1;
				for (TextField field : textFields)
				{
					field.setText(rs.getString(i++));
				}
				
				name.setDisable(true);
				searchBtn.setText("조회");
			} else
			{
				showAlert(false, "없습니다."); 
			}
			
		} catch (SQLException e)
		{
			e.printStackTrace();
		} finally
		{
			connect.close();
		}
		
	}
	
	public void handleSearch(ActionEvent event)
	{
		
		if (searchBtn.getText().equals("조회"))
		{
			searchBtn.setText("검색");
			name.setDisable(false);
			makeNull();			
		} else
		{
			setTourInfo();
		}
	}
	
	public void handleCreate(ActionEvent event)
	{
		if (createBtn.getText().equals("추가"))
		{
			makeNull();
						
			name.setDisable(false);
			createBtn.setText("등록");
			updateBtn.setDisable(true);
			deleteBtn.setDisable(true);
			searchBtn.setText("조회");
			searchBtn.setDisable(true);
		} else
		{
			if (isNull() != false)
			{
				
				DBConnect connect = new DBConnect();
				connect.connect();
				
				ResultSet rs;
				PreparedStatement pstmt = null;
				
				try
				{
					String sql = "select * from tour where name='" + name.getText() + "'";
					pstmt = connect.getConnection().prepareStatement(sql);
					rs = pstmt.executeQuery();
					
					if(rs.next())
					{	
						showAlert(false, "이미 존재합니다.");
					} else
					{
						sql = "insert into tour values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
						
						pstmt = connect.getConnection().prepareStatement(sql);
						
						pstmt.setString(1, name.getText());
						pstmt.setString(2, classify.getText());
						pstmt.setString(3, address.getText());
						pstmt.setString(4, convenience.getText());
						pstmt.setString(5, recreational.getText());
						pstmt.setString(6, cultural.getText());
						pstmt.setInt(7, Integer.valueOf(capacity.getText()));
						pstmt.setInt(8, Integer.valueOf(parking.getText()));
						pstmt.setString(9, introduction.getText());
						pstmt.setString(10, management.getText());
						pstmt.setString(11, management_phone.getText());
						
						pstmt.executeUpdate();
						
						showAlert(true, "등록되었습니다.");
						
						name.setDisable(true);
						createBtn.setText("추가");
						updateBtn.setDisable(false);
						deleteBtn.setDisable(false);
						searchBtn.setDisable(false);
						
					}
				} catch (SQLException e)
				{
					e.printStackTrace();
				} finally
				{
					connect.close();
				}
			}
			
		}
		
	}
	
	public void handleUpdate(ActionEvent event)
	{
		
		
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Question!");
		alert.setContentText("[" + name.getText() + "] 관광지를 수정하시겠습니까?");
		Optional<ButtonType> result = alert.showAndWait();
		
		if (result.get() == ButtonType.OK && isNull() == true)
		{
			DBConnect connect = new DBConnect();
			connect.connect();
			
			ResultSet rs = null;
			PreparedStatement pstmt = null;
			String sql  = null;
			
			try
			{
				sql = "select * from tour where name='" + name.getText() + "'";
				pstmt = connect.getConnection().prepareStatement(sql);
				rs = pstmt.executeQuery();
				
				if(rs.next())
				{	
					sql = "update tour set classification=?, address=?, convenience=?, recreational=?, cultural=?, capacity=?, parking_available=?, introduction=?, management_institution=?, management_institution_phone=? where name='" + name.getText() + "'";
					
					pstmt = connect.getConnection().prepareStatement(sql);
					pstmt.setString(1, classify.getText());
					pstmt.setString(2, address.getText());
					pstmt.setString(3, convenience.getText());
					pstmt.setString(4, recreational.getText());
					pstmt.setString(5, cultural.getText());
					pstmt.setInt(6, Integer.valueOf(capacity.getText()));
					pstmt.setInt(7, Integer.valueOf(parking.getText()));
					pstmt.setString(8, introduction.getText());
					pstmt.setString(9, management.getText());
					pstmt.setString(10, management_phone.getText());
					
					pstmt.executeUpdate();
					
					showAlert(true, "수정되었습니다.");
					
					name.setDisable(true);
					searchBtn.setText("조회");
				} else
				{
					showAlert(false, "없습니다.");
				}
				
				
			} 
			 catch (SQLException e)
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
		
		
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Question!");
		alert.setContentText("[" + name.getText() + "] 관광지를 삭제하시겠습니까?");
		Optional<ButtonType> result = alert.showAndWait();
		
		if (result.get() == ButtonType.OK && ! name.getText().equals(""))
		{
			
			DBConnect connect = new DBConnect();
			connect.connect();
			
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			String sql  = null;
			
			try
			{
				sql = "select * from tour where name='" + name.getText() + "'";
				pstmt = connect.getConnection().prepareStatement(sql);
				rs = pstmt.executeQuery();
				
				if(rs.next())
				{
				
					sql = "delete from tour where name='" + name.getText() + "'";
					
					pstmt = connect.getConnection().prepareStatement(sql);
					pstmt.executeUpdate();
					
					
					showAlert(true, "삭제되었습니다.");
					
					makeNull();
					name.setDisable(true);
					searchBtn.setText("조회");
				} else
				{
					showAlert(false, "없습니다.");
				}
				
				
				
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
		
		alert.setContentText("[" + name.getText() + "] 관광지가 " + message);
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
