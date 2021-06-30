package application;

import javafx.fxml.FXML;

import javafx.scene.control.Button;

import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import com.DAO.ReviewDao;
import com.persistence.Review;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;

import javafx.scene.control.TableView;

import javafx.scene.control.TableColumn;

public class TourReviewSearchController {
	
	private ObservableList<Review> data;
	private ReviewDao reviewDao;
	
	
	
	@FXML
	private Button serchBtn;
	@FXML
	private TextField tourName;
	@FXML
	private TableView addrTable;
	@FXML
	private TableColumn<Review,String> colWriter;
	@FXML
	private TableColumn<Review,String> colGrade;
	@FXML
	private TableColumn<Review,String> colContents;
	@FXML
	private TableColumn<Review,String> colDate;
	
	@FXML
	private void initialize() {
		reviewDao = new ReviewDao();
		tourName.setDisable(true);
	}
	public void initData(String name)
	{
		tourName.setText(name);
		showTourReviewList();
	}
	
	// Event Listener on Button[#serchBtn].onAction
	@FXML
	public void getList(ActionEvent event) {
//		showTourReviewList();
	}
	
	
	private void showTourReviewList() {
		try {
			System.out.println(tourName.getText());
			data = reviewDao.getTourReviewList(tourName.getText());
			
			colWriter.setCellValueFactory(new PropertyValueFactory<Review,String>("memberId"));
			colGrade.setCellValueFactory(new PropertyValueFactory<Review,String>("grade"));
			colContents.setCellValueFactory(new PropertyValueFactory<Review,String>("content"));
			colDate.setCellValueFactory(new PropertyValueFactory<Review,String>("registerDate"));
			
			addrTable.setItems(null);
			addrTable.setItems(data);
		} catch(Exception e) {
			e.printStackTrace();
			System.out.println("Data를 테이블에 가져올 수 없습니다.");
		}
	}
	
	
}
