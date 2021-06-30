package application;

import java.net.URL;
import java.util.ResourceBundle;

import Login.LoginDAO;
import Login.LoginDTO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class LoginController implements Initializable {
	@FXML
	private TextField id;
	
	@FXML
	private Button pointBtn;
	
	@FXML
	private TextField pw;

	@FXML
	private ToggleButton sceneChangeBtn;

	LoginDTO member;
	LoginDAO check = new LoginDAO();

	public void checkMember() throws ClassNotFoundException {

		boolean checkLogin = false;
		do {
			member = new LoginDTO(id.getText(), pw.getText());
			System.out.println("�Էµ� ID : " + id.getText());
			System.out.println("�Էµ� PW : " + pw.getText());
			checkLogin = check.checkMember(member);

			if (checkLogin) {
				try {
					Parent foodScene = FXMLLoader.load(getClass().getResource("/view/TourSearch.fxml"));

					Scene scene = new Scene(foodScene);

					Stage primaryStage = (Stage) sceneChangeBtn.getScene().getWindow(); // ���� ������ ��������

					primaryStage.setScene(scene);

				} catch (Exception e) {
					e.printStackTrace();
				}

				checkLogin = false;
			}

			else {
				Alert alert = new Alert(AlertType.CONFIRMATION);
				alert.setTitle("Login");
				alert.setHeaderText("����");
				alert.setContentText("��ġ�ϴ� ���̵�� ��й�ȣ�� �������� �ʽ��ϴ�.");
				alert.showAndWait();
			}

		} while (checkLogin);
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
	}
	
	public void handlePoint(ActionEvent evet)
	{
		try
		{
			FXMLLoader loader = new FXMLLoader();
			  loader.setLocation(getClass().getResource("/view/po.fxml"));
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

	public void changeScene(ActionEvent event) {
		try {
			Parent signUpScene = FXMLLoader.load(getClass().getResource("/view/SignUp.fxml"));

			Scene scene = new Scene(signUpScene);

			Stage primaryStage = (Stage) sceneChangeBtn.getScene().getWindow(); // ���� ������ ��������

			primaryStage.setScene(scene);
			primaryStage.show();
			
			try
			{
				FXMLLoader another = new FXMLLoader( Main.class.getResource( "/view/Point.fxml" ) );
				System.out.println(1);
				Parent root = (Parent)another.load();	
				   Scene anotherScene = new Scene( root );
				  
				  PointController pop = another.getController();
				  pop.initData(id.getText(), pw.getText());
				  
				  Stage stage = new Stage();
				  stage.setScene(anotherScene);
				  stage.show();
				stage.show();
			} catch (Exception e) {
				e.printStackTrace();
			}
				
			

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
