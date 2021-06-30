package application;
	
import java.io.IOException;

import controller.Restaurant_CRUD;
import controller.Tour_CRUD;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;


public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/Login.fxml"));
			Parent root = (Parent)loader.load();	
			
			primaryStage.setTitle("Login");
			primaryStage.setScene(new Scene(root));
			primaryStage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
