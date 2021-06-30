package controller;

import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import DTO.RestaurantDTO;
import DTO.TourDTO;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class Review implements Initializable {
	@FXML private ToggleGroup toggle1;
	@FXML private ToggleGroup toggle2;
	@FXML private ToggleGroup toggle3;
	@FXML private RadioButton tour;
	@FXML private TableView<TourDTO> tableView1;
	@FXML private TableColumn<TourDTO, String> t_nameColumn;
	@FXML private TableColumn<TourDTO, String> t_classificationColumn;
	@FXML private TableColumn<TourDTO, String> t_addressColumn;
	@FXML private TableColumn<TourDTO, String> t_managementColumn;
	@FXML private TableColumn<TourDTO, String> t_managementPhoneColumn;
	@FXML private TableView<RestaurantDTO> tableView2;
	@FXML private TableColumn<RestaurantDTO, String> r_idColumn;
	@FXML private TableColumn<RestaurantDTO, String> r_nameColumn;
	@FXML private TableColumn<RestaurantDTO, String> r_categoryColumn;
	@FXML private TableColumn<RestaurantDTO, String> r_telColumn;
	@FXML private TableColumn<RestaurantDTO, String> r_addressColumn;
	@FXML private Button closeBtn;
	
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		
		toggle1.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {

			@Override
			public void changed(ObservableValue<? extends Toggle> arg0, Toggle oldValue, Toggle newValue) 
			{
				changeList1();
			}
			
		});
		
		toggle2.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {

			@Override
			public void changed(ObservableValue<? extends Toggle> arg0, Toggle oldValue, Toggle newValue) 
			{
				changeList2();
			}
			
		});
		
		

	}
	
	private void changeList1()
	{		
		String sql = getSql(toggle1.getSelectedToggle().getUserData().toString(), true);;

		DBConnect connect = new DBConnect();
		connect.connect();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try
		{
			pstmt = connect.getConnection().prepareStatement(sql);
			rs = pstmt.executeQuery();
			
			ObservableList<TourDTO> data = FXCollections.observableArrayList();
			
			for (int i=0; i<10 && rs.next(); i++)
			{
				TourDTO tour = new TourDTO();
				tour.setName(rs.getString(1));
				tour.setClassification(rs.getString(2));
				tour.setAddress(rs.getString(3));
				tour.setManagement_institution(rs.getString(4));
				tour.setManagement_institution_phone(rs.getString(5));
				data.add(tour);
			}
			
	        t_nameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(new String(cellData.getValue().getName())));
	        t_classificationColumn.setCellValueFactory(cellData -> new SimpleStringProperty(new String(cellData.getValue().getClassification())));
	        t_addressColumn.setCellValueFactory(cellData -> new SimpleStringProperty(new String(cellData.getValue().getAddress())));
	        t_managementColumn.setCellValueFactory(cellData -> new SimpleStringProperty(new String(cellData.getValue().getManagement_institution())));
	        t_managementPhoneColumn.setCellValueFactory(cellData -> new SimpleStringProperty(new String(cellData.getValue().getManagement_institution_phone())));

			tableView1.setItems(data);
		} catch (SQLException e)
		{
			e.printStackTrace();
		} finally
		{
			connect.close();
		}
	}
	
	public void changeList2()
	{
		String sql = getSql(toggle2.getSelectedToggle().getUserData().toString(), false);

		DBConnect connect = new DBConnect();
		connect.connect();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try
		{
			pstmt = connect.getConnection().prepareStatement(sql);
			rs = pstmt.executeQuery();
			
			ObservableList<RestaurantDTO> data = FXCollections.observableArrayList();
			
			for (int i=0; i<10 && rs.next(); i++)
			{
				RestaurantDTO restaurant = new RestaurantDTO();
				restaurant.setId(rs.getInt(1));
				restaurant.setName(rs.getString(2));
				restaurant.setCategory(rs.getString(3));
				restaurant.setStore_tel(rs.getString(4));
				restaurant.setAddress(rs.getString(5));
				data.add(restaurant);
			}
			
			r_idColumn.setCellValueFactory(cellData -> new SimpleStringProperty(new String( String.valueOf(cellData.getValue().getId()))));
	        r_nameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(new String(cellData.getValue().getName())));
	        r_categoryColumn.setCellValueFactory(cellData -> new SimpleStringProperty(new String(cellData.getValue().getCategory())));
	        r_telColumn.setCellValueFactory(cellData -> new SimpleStringProperty(new String(cellData.getValue().getStore_tel())));
	        r_addressColumn.setCellValueFactory(cellData -> new SimpleStringProperty(new String(cellData.getValue().getAddress())));
	        
			tableView2.setItems(data);
		} catch (SQLException e)
		{
			e.printStackTrace();
		} finally
		{
			connect.close();
		}

	}
	
	private String getSql(String operation, boolean isTour)
	{
		String sql = "select ";
		
		if (operation.equals("리뷰"))
		{
			operation = "count";
		} else
		{
			operation = "avg";
		}
		
		if (isTour == true)
		{
			sql += "tour.name, tour.classification, tour.address, tour.management_institution, tour.management_institution_phone, " 
				+ operation + "(review.rating) from tour, review where tour.name=review.tour_name group by tour.name order by " + operation + "(review.rating) desc";
		} else
		{
			sql += "restaurant.id, restaurant.name, restaurant.category, restaurant.store_tel, restaurant.address, " 
					+ operation + "(review.rating) from restaurant, review where restaurant.id=review.restaurant_id group by restaurant.id order by " + operation + "(review.rating) desc";
		}

		return sql;
	}
	
	public void handleClose(ActionEvent event)
	{
		Stage stage = (Stage) closeBtn.getScene().getWindow();
	    stage.close();
	}

}
