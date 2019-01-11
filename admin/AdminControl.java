package admin;
import java.net.URL;
import java.util.ResourceBundle;

import application.Main;
import database.ConnectDB;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class AdminControl implements Initializable   {
	protected static ConnectDB db;
	protected FXMLLoader loader;
	protected Scene scene;
	protected Stage stage;
	protected Pane mainRoot;
	protected VBox root;
	@FXML
	public TextField title;

	public void showNewMovie() throws Exception {
		loader = new FXMLLoader(getClass().getResource("NewMovie.fxml"));	
		updatePage(loader.load());
	}
	
	public void showNewCinemas()throws Exception {
		loader = new FXMLLoader(getClass().getResource("NewCinema.fxml"));	
		updatePage(loader.load());
	}

	public void showEditMovie()throws Exception {
		loader = new FXMLLoader(getClass().getResource("DeleteMovie.fxml"));	
		updatePage(loader.load());
	}
	
	public void showEditCinemas()throws Exception {
		loader = new FXMLLoader(getClass().getResource("DeleteCinema.fxml"));	
		updatePage(loader.load());
	}
	
	public void showSchedule() throws Exception{
		loader = new FXMLLoader(getClass().getResource("AddSchedule.fxml"));	
		updatePage(loader.load());
	}
	
	public void returnToUser()throws Exception {
		stage = Main.getStage();
		scene = stage.getScene();
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/MainGUI.fxml"));
		mainRoot = loader.load();
		scene = new Scene(mainRoot,600,500);
		stage.setScene(scene);
		stage.setTitle("Admin");
		stage.show();
	}
	
	protected void updatePage(Pane pane) {
		stage = Main.getStage();
		scene = stage.getScene();
		pane.setPrefWidth(600);
		pane.setPrefHeight(351);
		root = (VBox) scene.getRoot();
		root.getChildren().remove(1);
		root.getChildren().add(pane);
		scene.setRoot(root);
		stage.setScene(scene);
		stage.show();
	}
	
	public static void setDB(ConnectDB database) {
		db = database;
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		stage = Main.getStage();
	}
	

}
