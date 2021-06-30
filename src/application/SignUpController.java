package application;

import java.net.URL;
import java.util.ResourceBundle;

import SignUp.SignUpDAO;
import SignUp.SignUpDTO;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class SignUpController implements Initializable {
	@FXML
	private TextField id;

	@FXML
	private TextField passWord;

	@FXML
	private TextField Name;

	@FXML
	private TextField Phone;

	@FXML
	private TextField Address;

	@FXML
	private Button sceneChangeBtn;

	@FXML
	public void sendData() {
		String tempId = id.getText();
		String tempPw = passWord.getText();
		String tempName = Name.getText();
		String tempPhone = Phone.getText();
		String tempAddress = Address.getText();

		SignUpDTO member = new SignUpDTO(tempId, tempPw, tempName, tempPhone, tempAddress);

		SignUpDAO DAO = new SignUpDAO();

		try {
			DAO.insertMember(member);
			System.out.printf("���� �Ϸ�\n");
			System.out.println("����� ������ : " + member.getId() + " " + member.getPassword() + " " + member.getPhone()
					+ " " + member.getAddress());
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		try {
			Parent LoginScene = FXMLLoader.load(getClass().getResource("/view/Login.fxml"));

			Scene scene = new Scene(LoginScene);

			Stage primaryStage = (Stage) sceneChangeBtn.getScene().getWindow(); // ���� ������ ��������

			primaryStage.setScene(scene);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub

	}

}
