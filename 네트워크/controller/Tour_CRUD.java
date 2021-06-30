package controller;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.URL;
import java.sql.*;
import java.util.*;

import DTO.TourDTO;
import application.Client;
import application.Main;
import javafx.application.Platform;
import javafx.event.*;
import javafx.fxml.*;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class Tour_CRUD implements Initializable 
{
	@FXML private AnchorPane anchorPane;
	@FXML private Button searchBtn;
	@FXML private Button createBtn;
	@FXML private Button updateBtn;
	@FXML private Button deleteBtn;
	@FXML private Button reviewBtn;
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
	
	private List<TextField> textFields = null;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) 
	{
		name.setDisable(true);
		
		textFields = Arrays.asList(name, classify, address, convenience, recreational, cultural, capacity, parking, introduction, management, management_phone);	
	}
	
	public void setTourInfo(TourDTO tour)
	{	
		classify.setText(tour.getClassification());
		address.setText(tour.getAddress());
		convenience.setText(tour.getConvenience());
		recreational.setText(tour.getRecreational());
		cultural.setText(tour.getCultural());
		capacity.setText(String.valueOf(tour.getCapacity()));
		parking.setText(String.valueOf(tour.getParking_avaliable()));
		introduction.setText(tour.getIntroduction());
		management.setText(tour.getManagement_institution());
		management_phone.setText(tour.getManagement_institution_phone());
		
	}
	
	public void initData(String tourName)
	{
		// 관광지 검색에서 값 받아와얗마
		name.setText(tourName);
		TourDTO tour = new TourDTO();
		tour.setName(tourName);
		
		
		Main.client.send("select", tour);
		System.out.println("클라이언트 CRUD: " + tour.toString());
		TourDTO newTour = (TourDTO) Main.client.receive();
		
		setTourInfo(newTour);
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
			
			TourDTO tour = new TourDTO();
			tour.setName(name.getText());
	
			Main.client.send("select", tour);
			
			TourDTO resultTour =  (TourDTO) Main.client.receive();
			
			if (resultTour == null)
			{
				showAlert(false, "없습니다.");
			} else
			{
				setTourInfo(resultTour);
			}
			searchBtn.setText("조회");
			name.setDisable(true);
			
		}
	}
	
	private TourDTO setTourDTO()
	{
		TourDTO result = new TourDTO();
		
		result.setName(name.getText());
		result.setClassification(classify.getText());
		result.setAddress(address.getText());
		result.setConvenience(convenience.getText());
		result.setRecreational(recreational.getText());
		result.setCultural(cultural.getText());
		result.setCapacity(Integer.valueOf(capacity.getText()));
		result.setParking_avaliable(Integer.valueOf(parking.getText()));
		result.setIntroduction(introduction.getText());
		result.setManagement_institution(management.getText());
		result.setManagement_institution_phone(management_phone.getText());
		
		return result;
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
			reviewBtn.setDisable(true);
			searchBtn.setText("조회");
			searchBtn.setDisable(true);
		} else
		{
			if (isNull() != false)
			{
				TourDTO sendTour = setTourDTO();
				Main.client.send("create", sendTour);
				
				TourDTO resultTour = (TourDTO) Main.client.receive();
				
				if (resultTour != null)
				{
					showAlert(true, "등록되었습니다.");
					
					name.setDisable(true);
					createBtn.setText("추가");
					updateBtn.setDisable(false);
					deleteBtn.setDisable(false);
					reviewBtn.setDisable(false);
					searchBtn.setDisable(false);
					
				} else
				{
					showAlert(false, "이미 존재합니다.");
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
			TourDTO sendTour = setTourDTO();
			Main.client.send("update", sendTour);
			
			TourDTO resultTour = (TourDTO) Main.client.receive();
			
			if (resultTour != null)
			{
				showAlert(true, "수정되었습니다.");
				
				name.setDisable(true);
				searchBtn.setText("조회");
			} else
			{
				showAlert(false, "없습니다.");
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
			
			TourDTO sendTour = setTourDTO();
			Main.client.send("delete", sendTour);
			
			TourDTO resultTour = (TourDTO) Main.client.receive();
			
			if (resultTour != null)
			{
				showAlert(true, "삭제되었습니다.");
				
				makeNull();
				name.setDisable(true);
				searchBtn.setText("조회");
			} else
			{
				showAlert(false, "없습니다.");
			}
				
		}
	}
	
	public void handleReview(ActionEvent event)
	{
		DBConnect connect = new DBConnect();
		connect.connect();
		
		try {
			
			TourDTO sendTour = setTourDTO();
			Main.client.send("select", sendTour);
			
			TourDTO resultTour = (TourDTO) Main.client.receive();
			
			if (resultTour != null)
			{
				FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/TourReview.fxml"));
				Parent root = (Parent)loader.load();
				Scene scene = new Scene(root, 600,580);
				
				Tour_Review review = loader.getController();
				review.initData(resultTour.getName());
				
				Stage stage = new Stage();
				stage.initModality(Modality.APPLICATION_MODAL);
				stage.setTitle("Tour Review");
				stage.setScene(scene);
				stage.show();
				
			} else
			{
				showAlert(false, "없습니다.");
			}
		} catch (IOException e)
		{
			e.printStackTrace();
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
