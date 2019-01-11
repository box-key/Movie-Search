package application;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import database.ConnectDB;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class MainGUIControl implements Initializable {
	private static ConnectDB db;
	private FXMLLoader loader;
	private Scene scene;
	private Stage stage;
	private Pane root ;
	
	@FXML
	public Button movie;
	@FXML
	public Button cinema;
	@FXML
	public Button admin;
	@FXML
	public PasswordField password;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		stage = Main.getStage();
	}
	
	public void showMovies() throws Exception {
		stage = Main.getStage();
		scene = stage.getScene();
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/user/MovieSearchResult.fxml"));
		root = loader.load();
		scene = new Scene(root,800,500);
		stage.setScene(scene);
		stage.setTitle("Movie");
		stage.show();
	}
	
	public void showCinemas() throws Exception {
		stage = Main.getStage();
		scene = stage.getScene();
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/user/CinemaSearchResult.fxml"));
		root = loader.load();
		scene = new Scene(root,850,600);
		stage.setScene(scene);
		stage.setTitle("Cinema");
		stage.show();
	}
	
	public void showAdmin() throws Exception {
		if(password.getText().equals("")) {
			showAlert("Password field is empty");
			return;
		}
		if(password.getText().equals("admin")) {
			stage = Main.getStage();
			scene = stage.getScene();
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/admin/Admin.fxml"));
			root = loader.load();
			scene = new Scene(root,600,500);
			stage.setScene(scene);
			stage.setTitle("Admin");
			stage.show();
		}
		else {
			showAlert("Password is incorrrect");
		}
	}
	
	private void showAlert(String output) {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Illegal Input");
		alert.setHeaderText(null);
		alert.setContentText(output);
		alert.showAndWait();
	}
	

}
