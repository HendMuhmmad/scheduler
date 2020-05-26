package application;
	
import javafx.application.Application;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.scene.layout.BorderPane;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;


public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			
			BorderPane root = (BorderPane)FXMLLoader.load(getClass().getResource("Sample.fxml"));
			Scene scene = new Scene(root,600,600);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setMaximized(true);
			primaryStage.setTitle("Schedular");
			primaryStage.setScene(scene);
			primaryStage.show();					
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
