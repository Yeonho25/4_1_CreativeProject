package application;
	
import java.io.IOException;

import controller.Tour_CRUD;
import controller.Tour_Review;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;


public class Main extends Application {
	public static Client client;
	@Override
	public void start(Stage primaryStage) {
		
		client = new Client();
		
		client.startclient("localhost", 9999);

		
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/TourInformation.fxml"));
			Parent root = (Parent)loader.load();
			Scene scene = new Scene(root, 1000, 600);
			
			Tour_CRUD crud = loader.getController();
			crud.initData("10");
			
			primaryStage.setTitle("Tour Information");
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
