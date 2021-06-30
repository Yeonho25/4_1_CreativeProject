package controller;

import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.sql.Date;
import java.util.ResourceBundle;

import DTO.ReviewDTO;
import DTO.TourDTO;
import application.Main;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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

public class Tour_Review implements Initializable {
	@FXML private TextField name;
	@FXML private ComboBox<String> rating;
	@FXML private TextArea content;
	@FXML private Button createBtn;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) 
	{
		name.setDisable(true);
		
		ObservableList<String> list = FXCollections.observableArrayList();
		
		for (float i=0; i<=5; i+=0.5)
		{
			list.add(String.valueOf(i));
		}
		
		rating.setItems(list);
		
	}
	
	public void initData(String name)
	{
		this.name.setText(name);
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
				ReviewDTO sendReview = new ReviewDTO();
				
				Main.client.send("review", sendReview);
				
				ReviewDTO resultReview = (ReviewDTO) Main.client.receive();
				
				if (resultReview != null)
				{
					alert = new Alert(AlertType.INFORMATION);
					alert.setTitle("Success!");
					alert.setContentText("[" + name.getText() + "] 리뷰가 등록되었습니다.");
					
					alert.showAndWait();
					
					Stage stage = (Stage) createBtn.getScene().getWindow();
				    stage.close();
				}
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	
}
