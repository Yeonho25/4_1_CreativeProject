package application;

import java.net.URL;
import java.util.ResourceBundle;

import Point.PointDAO;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class PointController implements Initializable {

	@FXML
	private TextField point;

	PointDAO pointDao = new PointDAO();

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
	}
	
	public void initData(String id, String pw)
	{
		String getPoint = null;
		try {
			getPoint = pointDao.getPoint(id, pw);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		point.setText(getPoint);
	}

	@FXML
	public void spend3000() {
		int beforePoint=Integer.parseInt(point.getText());
		String change=pointDao.spendPoint(beforePoint, 3000);
//		this.point=new Label();
		point.setText(change);
	}
}
