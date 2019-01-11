package application;

import admin.AdminControl;
import admin.NewMovieControl;
import database.ConnectDB;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;


public class Main extends Application  {
	private static Scene scene;
	private static Stage stage;
	private static Pane root;

	public void start(Stage primaryStage) throws Exception {
		setStage(primaryStage);
		FXMLLoader loader = new FXMLLoader(getClass().getResource("MainGUI.fxml"));
		root = (Pane) loader.load();
		scene = new Scene(root,600,500);
		primaryStage.setScene(scene);
		primaryStage.setTitle("Hello Users");
		primaryStage.show();
	}
	
	public static void main(String[] args) {
		ConnectDB database = ConnectDB.CreateConnection("jdbc:mysql://104.248.59.79:3306/MovieApp", "root", "J2V.gPrUuTM@");
		setDbForController(database);
		launch(args);
	}
	 	
	private void setStage(Stage s) {
		stage = s;
	}
	
	public static Stage getStage() {
		return stage;
	}
	
	public static Pane getRoot() {
		return root ;
	}
	
	private static void setDbForController(ConnectDB db) {
		AdminControl.setDB(db);
		NewMovieControl.setDB(db);
	}

}
