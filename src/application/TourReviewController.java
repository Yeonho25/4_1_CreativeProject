package application;

import javafx.fxml.FXML;

import javafx.scene.control.Button;

import javafx.scene.control.TextField;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.DAO.ReviewDao;
import com.persistence.Review;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;

import javafx.scene.control.TextArea;

import javafx.scene.control.ChoiceBox;

public class TourReviewController {
	
	private String userId = "test";
	
	private static Connection con;
	private PreparedStatement pstmt;
	private ObservableList<Review> data;
	private ReviewDao reviewDao;
	private boolean canSave = false;
	private boolean canEdit = false;
	
	@FXML
	private TextField tourName;
	@FXML
	private TextArea contents;
	@FXML
	private Button tourReviewRegister;

	@FXML
	private TextField grade;

	// Event Listener on Button[#tourReviewRegister].onAction
	@FXML
	public void handelAdd(ActionEvent event) {
		Review rev = new Review(userId,contents.getText(),Double.parseDouble(grade.getText()));
		rev.setTourName(tourName.getText());
		reviewDao = new ReviewDao();
		try {
			reviewDao.registerTourReview(rev);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void initData(String name)
	{
		tourName.setText(name);
		tourName.setDisable(true);
	}
}
