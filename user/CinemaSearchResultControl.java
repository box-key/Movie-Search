package user;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Vector;

import application.Main;
import database.ConnectDB;
import datatypes.Cinema;
import datatypes.CinemaAndTime;
import datatypes.Movie;
import datatypes.MovieAndTime;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class CinemaSearchResultControl implements Initializable {
	protected ConnectDB db;
	protected FXMLLoader loader;
	protected Scene scene;
	protected Stage stage;
	protected Pane root;
	private Vector<Cinema> vc = new Vector<Cinema>();
	private int val_g, 
				val_pg, 
				val_pg13, 
				val_r, 
				val_nc17, 
				val_x, 
				val_y, 
				val_radius; 
	private Cinema selectedCinema;
	
	@FXML
	public Button search;
	@FXML
	public Button back;
	@FXML
	public CheckBox g;
	@FXML
	public CheckBox pg;
	@FXML
	public CheckBox pg13;
	@FXML
	public CheckBox r;
	@FXML
	public CheckBox nc17;
	@FXML
	public CheckBox selectAll;
	@FXML
	public TextField y;
	@FXML
	public TextField x;
	@FXML
	public TextField radius;
	@FXML
	public TableView<MovieAndTime> movieTable;
	@FXML
	public TableColumn<MovieAndTime, String> movieTitle;
	@FXML
	public TableColumn<MovieAndTime, String> movieRating;
	@FXML
	public TableColumn<MovieAndTime, String> matinee;
	@FXML
	public TableColumn<MovieAndTime, String> afternoon;
	@FXML
	public TableColumn<MovieAndTime, String> evening;
	@FXML
	public TableColumn<MovieAndTime, String> midnight;
	@FXML
	public TableView<Cinema> cinemaTable;
	@FXML
	public TableColumn<Cinema, String> cinema;
	@FXML
	public TableColumn<Cinema, String> address;
	
	
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		db = ConnectDB.CreateConnection("jdbc:mysql://104.248.59.79:3306/MovieApp", "root", "J2V.gPrUuTM@");
		selectAll();
		updateCinemas();
		search.setOnMouseClicked((event) -> {
			updateCinemas();
		});
		
		cinemaTable.setOnMouseClicked((event) -> {
			selectedCinema = cinemaTable.getSelectionModel().getSelectedItem();
			updateMovies(searchCinemaId(selectedCinema.getName()));
		});
		
		selectAll.setOnAction((event) -> {
			if(selectAll.isSelected()) selectAll();
			else unSelectAll();
		});
				
	}
	
	private int searchCinemaId(String name) {
		int len = vc.size();
		for(int i=0;i<len;i++) 
			if(vc.get(i).getName().equals(name)) {
				//System.out.println(vc.get(i).getId() + " " + vc.get(i).getName());
				return vc.get(i).getId();
			}
		return -1;
	}
	
	private void updateMovies(int cinema_id) {
		movieTitle.setCellValueFactory(new PropertyValueFactory<MovieAndTime, String>("movie_title"));
		movieRating.setCellValueFactory(new PropertyValueFactory<MovieAndTime, String>("movie_rating"));
		matinee.setCellValueFactory(new PropertyValueFactory<MovieAndTime, String>("matinee"));		
		afternoon.setCellValueFactory(new PropertyValueFactory<MovieAndTime, String>("afternoon"));		
		evening.setCellValueFactory(new PropertyValueFactory<MovieAndTime, String>("evening"));		
		midnight.setCellValueFactory(new PropertyValueFactory<MovieAndTime, String>("midnight"));		
		movieTable.getItems().setAll(formatData(db.getMoviesAndTime(cinema_id)));
	}
	
	private void updateCinemas() {
		
		if(!checkUserInput()) {
			showAlert();
			return;
		}
		setAllStaticValues();
		vc = db.getCinemas(
				val_x,
				val_y,
				val_radius,
				val_g,
				val_pg,
				val_pg13,
				val_r,
				val_nc17
				);
		cinema.setCellValueFactory(new PropertyValueFactory<Cinema, String>("name"));
		address.setCellValueFactory(new PropertyValueFactory<Cinema, String>("adrs"));
		cinemaTable.getItems().setAll(vc);
		
	}
	
	private boolean checkUserInput() {
		String tempX, tempY, tempRadius;
		tempX = x.getText();
		tempY = y.getText();
		tempRadius = radius.getText();
		
		if(tempX.equals("") || tempY.equals("") || tempRadius.equals("")) {
			val_x = 50;
			val_y = 50;
			val_radius = 50;	
			return true;
		}
		
		try {
			val_x = Integer.parseInt(tempX);
			val_y = Integer.parseInt(tempY);
			val_radius = Integer.parseInt(tempRadius);
		} catch(Exception e) {
			return false;
		}
		
		if(val_x<0 || val_x>100) return false;
		if(val_y<0 || val_y>100) return false;
		if(val_radius<0) {
			return false;
		}
		return true;
	}
	
	private void showAlert() {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Illegal Input");
		alert.setHeaderText(null);
		alert.setContentText("Check the value of address and radius again.");
		alert.showAndWait();
	}
	
	private void setAllStaticValues() {	
		val_g = (g.isSelected()) ? 1:-1;
		val_pg = (pg.isSelected()) ? 1:-1;
		val_pg13 = (pg13.isSelected()) ? 1:-1;
		val_r = (r.isSelected()) ? 1:-1;
		val_nc17 = (nc17.isSelected()) ? 1:-1;
	}
	

	private Vector<MovieAndTime> formatData(Vector<MovieAndTime> vmt){
		Vector<MovieAndTime> newList = new Vector<>();
		int len = vmt.size();
		MovieAndTime newData;
		String [] times = new String[4];
		String startTitle, temp = "";
		int j;
		for(int i=0;i<len;i++) {
			for(int k=0;k<4;k++) times[k] = "-";
			startTitle = vmt.get(i).getMovie_title();
			newData = new MovieAndTime(startTitle, vmt.get(i).getMovie_rating());
			for(j=0;(i+j)<len;j++) {
				if(!startTitle.equals(vmt.get(i+j).getMovie_title())) break;
				//System.out.println(j + ": " + vmt.get(i+j).getMovie_title() + " " + vmt.get(i+j).getTime());
				temp = vmt.get(i+j).getTime().substring(9);
				if(temp.equals("01:00")) times[0] = "13:00";
				if(temp.equals("01:30")) times[0] = "13:30";
				if(temp.equals("04:00")) times[1] = "16:00";
				if(temp.equals("04:30")) times[1] = "16:30";
				if(temp.equals("07:00")) times[2] = "19:00";
				if(temp.equals("08:00")) times[2] = "20:00";
				if(temp.equals("09:00")) times[2] = "21:00";
				if(temp.equals("10:30")) times[3] = "22:30";	
				if(temp.equals("11:30")) times[3] = "23:30";	

			}
			i = i + j -1;
			
				newData.setMatinee(times[0] ); 
			
			
				newData.setAfternoon(times[1]); 
			
			
				newData.setEvening(times[2] ); 
			
		
				newData.setMidnight(times[3]);
			
			newList.add(newData);
		}
		return newList;
	}
	
	protected void selectAll() {
		g.setSelected(true);
		pg.setSelected(true);
		pg13.setSelected(true);
		r.setSelected(true);
		nc17.setSelected(true);
		selectAll.setSelected(true);
	}
	
	protected void unSelectAll() {
		g.setSelected(false);
		pg.setSelected(false);
		pg13.setSelected(false);
		r.setSelected(false);
		nc17.setSelected(false);
		selectAll.setSelected(false);
	}

	protected void disableAll() {
		g.setDisable(true);
		pg.setDisable(true);
		pg13.setDisable(true);
		r.setDisable(true);
		nc17.setDisable(true);
		x.setDisable(true);
		y.setDisable(true);
		radius.setDisable(true);
	}
	
	protected void enableAll() {
		g.setDisable(false);
		pg.setDisable(false);
		pg13.setDisable(false);
		r.setDisable(false);
		nc17.setDisable(false);
		x.setDisable(false);
		y.setDisable(false);
		radius.setDisable(false);
	}
	public void goToHome() throws Exception {
		stage = Main.getStage();
		scene = stage.getScene();
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/MainGUI.fxml"));
		root = loader.load();
		scene = new Scene(root,600,500);
		stage.setScene(scene);
		stage.setTitle("");
		stage.show();
	}
	
}
