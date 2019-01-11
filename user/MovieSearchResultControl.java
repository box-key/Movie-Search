package user;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Vector;

import application.Main;
import database.ConnectDB;
import datatypes.CinemaAndTime;
import datatypes.Movie;
import datatypes.MovieAndTime;
import datatypes.TimeSchedule;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class MovieSearchResultControl implements Initializable {
	
	ConnectDB db;
	Vector<Movie> vm;
	Movie selectedMovie;
	protected FXMLLoader loader;
	protected Scene scene;
	protected Stage stage;
	protected Pane mainRoot;
	String rating;
	@FXML
	public Button b;
	@FXML
	public TableView<Movie> movieTable;
	@FXML
	public TableColumn<Movie, String> moviesTitle;
	@FXML
	public TableColumn<Movie, String> movieRating;
	@FXML
	public TableView<CinemaAndTime> cinemaTable;
	@FXML
	public TableColumn<CinemaAndTime, String> cinema;
	@FXML
	public TableColumn<CinemaAndTime, String> address;
	@FXML
	public TableColumn<CinemaAndTime, String> matinee;
	@FXML
	public TableColumn<CinemaAndTime, String> afternoon;
	@FXML
	public TableColumn<CinemaAndTime, String> evening;
	@FXML
	public TableColumn<CinemaAndTime, String> midnight;
	@FXML
	public ComboBox<String> selectRating;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		db = ConnectDB.CreateConnection("jdbc:mysql://104.248.59.79:3306/MovieApp", "root", "J2V.gPrUuTM@");
		rating  = "All";
		updateMovies(rating);
		selectRating.getItems().addAll(
				"G",
				"PG",
				"PG13",
				"R",
				"NC17",
				"All"
				);
		selectRating.getSelectionModel().select(5);
		
		selectRating.setOnAction((event) -> {
			rating = selectRating.getSelectionModel().getSelectedItem();
			updateMovies(rating);
		});
		
		movieTable.setOnMouseClicked((event) -> {
			selectedMovie = movieTable.getSelectionModel().getSelectedItem();
			updateCinemas(searchMovieId(selectedMovie.getTitle()));
		});

	}
	
	private void updateMovies(String r) {
		vm = db.getCurrentMovies(r);
		moviesTitle.setCellValueFactory(new PropertyValueFactory<Movie, String>("title"));
		movieRating.setCellValueFactory(new PropertyValueFactory<Movie, String>("rating"));
		movieTable.getItems().setAll(vm);
	}
	
	private int searchMovieId(String name) {
		int len = vm.size();
		for(int i=0;i<len;i++) 
			if(vm.get(i).getTitle().equals(name)) {
				//System.out.println(vc.get(i).getId() + " " + vc.get(i).getName());
				return vm.get(i).getId();
			}
		return -1;
	}
	
	public void updateCinemas(int movie_id) {
		cinema.setCellValueFactory(new PropertyValueFactory<CinemaAndTime, String>("cinema_name"));
		address.setCellValueFactory(new PropertyValueFactory<CinemaAndTime, String>("cinema_address"));
		matinee.setCellValueFactory(new PropertyValueFactory<CinemaAndTime, String>("matinee"));		
		afternoon.setCellValueFactory(new PropertyValueFactory<CinemaAndTime, String>("afternoon"));		
		evening.setCellValueFactory(new PropertyValueFactory<CinemaAndTime, String>("evening"));		
		midnight.setCellValueFactory(new PropertyValueFactory<CinemaAndTime, String>("midnight"));		
		cinemaTable.getItems().setAll(formatData(db.getCinemasAndTime(movie_id)));
	}
	
	private Vector<CinemaAndTime> formatData(Vector<CinemaAndTime> vmt){
		Vector<CinemaAndTime> newList = new Vector<>();
		int len = vmt.size();
		CinemaAndTime newData;
		String [] times = new String[4];
		String startTitle, temp;
		int j;
		for(int i=0;i<len;i++) {
			for(int k=0;k<4;k++) times[k] = "-";
			startTitle = vmt.get(i).getCinema_name();
			newData = new CinemaAndTime(startTitle, vmt.get(i).getCinema_address());
			for(j=0;(i+j)<len;j++) {
				if(!startTitle.equals(vmt.get(i+j).getCinema_name())) break;
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
			

				newData.setMatinee(times[0]); 
			

				newData.setAfternoon(times[1]); 
			
	
				newData.setEvening(times[2]); 
			
	
				newData.setMidnight(times[3]);
			
			newList.add(newData);
		}
		return newList;
	}

	public void goToHome() throws Exception {
		stage = Main.getStage();
		scene = stage.getScene();
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/MainGUI.fxml"));
		mainRoot = loader.load();
		scene = new Scene(mainRoot,600,500);
		stage.setScene(scene);
		stage.setTitle("Admin");
		stage.show();
	}

}
