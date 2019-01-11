package admin;

import java.net.URL;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ResourceBundle;
import java.util.Vector;

import javax.swing.JOptionPane;

import database.CinemaType;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.VBox;

public class AddScheduleControl extends AdminControl implements Initializable {
	private String cyear = "";
	private String cmonth = "";
	private String cday = "";
	private String cmatineeh = "";
	private String cmatineem = "";
	private String cafternoonh = "";
	private String cafternoonm = "";
	private String ceveningh = "";
	private String ceveningm = "";
	private String clatenighth ="";
	private String clatenightm ="";
	protected Vector<String> cinemaList = new Vector<>();
	protected Vector<String> movieList = new Vector<>();
	private Vector<String> timeList = new Vector<>();
	@FXML
	public ComboBox<String> year;
	@FXML
	public ComboBox<String> month;
	@FXML
	public ComboBox<String> day;
	@FXML
	public ComboBox<String> matinee;
	@FXML
	public ComboBox<String> afternoon;
	@FXML
	public ComboBox<String> evening;
	@FXML
	public ComboBox<String> latenight;
	@FXML
	public ComboBox<String> movie;
	@FXML
	public ComboBox<String> cinema;

	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		year.getItems().addAll(
				"2017",
				"2018",
				"2019");
		year.getSelectionModel().select("2018");
		cyear = "2018";
		year.setOnAction((Event) -> {
			cyear = year.getSelectionModel().getSelectedItem();
		});
		
		month.getItems().addAll(
				"01",
				"02",
				"03",
				"04",
				"05",
				"06",
				"07",
				"08",
				"09",
				"10",
				"11",
				"12");
		month.getSelectionModel().select("11");
		cmonth = "11";
		month.setOnAction((Event) -> {
			cmonth = month.getSelectionModel().getSelectedItem();
		});
		
		day.getItems().addAll(
				"01",
				"02",
				"03",
				"04",
				"05",
				"06",
				"07",
				"08",
				"09",
				"10",
				"11",
				"12",
				"13",
				"14",
				"15",
				"16",
				"17",
				"18",
				"19",
				"20",
				"21",
				"22",
				"23",
				"24",
				"25",
				"26",
				"27",
				"28",
				"29",
				"30"
				);
		day.setOnAction((event) -> {
			cday = day.getSelectionModel().getSelectedItem();
		});
		
		matinee.getItems().addAll(
				"",
				"1:00 PM",
				"1:30 PM");
		matinee.setOnAction((Event)->{
			if(matinee.getSelectionModel().getSelectedItem().equals("")) {
				cmatineeh = "";
				cmatineem = "";
			}
			if(matinee.getSelectionModel().getSelectedItem().equals("1:00 PM")) {
				cmatineeh = "13";
				cmatineem = "00";
			}
			if(matinee.getSelectionModel().getSelectedItem().equals("1:30 PM")) {
				cmatineeh = "13";
				cmatineem = "30";
			}
		});
		
		afternoon.getItems().addAll(
				"",
				"4:00 PM",
				"4:30 PM");
		afternoon.setOnAction((Event)->{
			if(afternoon.getSelectionModel().getSelectedItem().equals("")) {
				cafternoonh = "";
				cafternoonm = "";
			}
			if(afternoon.getSelectionModel().getSelectedItem().equals("4:00 PM")) {
				cafternoonh = "16";
				cafternoonm = "00";
			}
			if(afternoon.getSelectionModel().getSelectedItem().equals("4:30 PM")) {
				cafternoonh = "16";
				cafternoonm = "30";
			}
		});
				
		evening.getItems().addAll(
				"",
				"7:00 PM",
				"8:00 PM",
				"9:00 PM");
		evening.setOnAction((Event)->{
			if(evening.getSelectionModel().getSelectedItem().equals("")) {
				ceveningh = "";
				ceveningm = "";
			}
			if(evening.getSelectionModel().getSelectedItem().equals("7:00 PM")) {
				ceveningh = "19";
				ceveningm = "00";
			}
			if(evening.getSelectionModel().getSelectedItem().equals("8:00 PM")) {
				ceveningh = "20";
				ceveningm = "00";
			}
			if(evening.getSelectionModel().getSelectedItem().equals("9:00 PM")) {
				ceveningh = "21";
				ceveningm = "00";
			}
		});
		
		latenight.getItems().addAll(
				"",
				"10:30 PM",
				"11:30 PM");
		latenight.setOnAction((Event)->{
			if(latenight.getSelectionModel().getSelectedItem().equals("")) {
				clatenighth = "";
				clatenightm = "";
			}
			if(latenight.getSelectionModel().getSelectedItem().equals("10:30 PM")) {
				clatenighth = "22";
				clatenightm = "30";
			}
			if(latenight.getSelectionModel().getSelectedItem().equals("11:30 PM")) {
				clatenighth = "23";
				clatenightm = "30";
			}
		});

		updateMovie();
		updateCinema();
	}
	
	
		
	public void add() {

		if(movie.getSelectionModel().isEmpty()|| cinema.getSelectionModel().isEmpty()){
			JOptionPane.showMessageDialog(null, "Please Select Movie and Cinema first", "Something Wrong", 1);
			return;
		}
		if(movie.getSelectionModel().getSelectedItem().equals("SM370") && !cinema.getSelectionModel().getSelectedItem().equals("Indie Arts Theater")) {
			JOptionPane.showMessageDialog(null, "SM370 can only play at the Indie Arts Theater", "Something Wrong", 1);
			return;
		}
		if(cday.equals("")) {
			JOptionPane.showMessageDialog(null, "Please Select a day", "Something Wrong", 1);
			return;
		}
		String time = cmonth + "/" + cday + "/" + cyear + " ";
		String date = cmonth + "/" + cday + "/" + cyear;
		try {
			timeList = db.getSchedule(date, cinema.getSelectionModel().getSelectedItem());
		} catch (SQLException | ParseException e1) {
			//e1.printStackTrace();
		}
		
		if(movie.getSelectionModel().getSelectedItem().equals("SM370") && cinema.getSelectionModel().getSelectedItem().equals("Indie Arts Theater")) {
			if(!cmatineeh.equals("") ||!cafternoonh.equals("") || !ceveningh.equals("")) {
				JOptionPane.showMessageDialog(null, "SM370 can only play at latenight", "Something Wrong", 1);
				return;
			}
		}

		boolean check = true;
		if(!cmatineeh.equals("") ) {
			try {
				check = upload(cmatineeh,cmatineem,time,"matinee");
			} catch (SQLException e) {
				String respone = cmatineeh + ":" + cmatineem + " PM" ;
				JOptionPane.showMessageDialog(null, "Cinema already playing movies on " + respone, "Something Wrong", 1);
				return;
			}
		}
		if(check == false) {
			JOptionPane.showMessageDialog(null, "This Cinema already have a movie playing at Matinee", "Something Wrong", 1);
			return;
		}
		if(!cafternoonh.equals("")) {
			try {
				check = upload(cafternoonh,cafternoonm,time,"afternoon");
			} catch (SQLException e) {
				String respone = cafternoonh + ":" + cafternoonm + " PM" ;
				JOptionPane.showMessageDialog(null, "Cinema already playing movies on " + respone, "Something Wrong", 1);
				return;
			}
		}
		if(check == false) {
			JOptionPane.showMessageDialog(null, "This Cinema already have a movie playing at Afternoon", "Something Wrong", 1);
			return;
		}
		if(!ceveningh.equals("")) {
			try {
				check = upload(ceveningh,ceveningm,time,"evening");
			} catch (SQLException e) {
				String respone = ceveningh + ":" + ceveningm + " PM" ;
				JOptionPane.showMessageDialog(null, "Cinema already playing movies on " + respone, "Something Wrong", 1);
				return;
			}
		}
		if(check == false) {
			JOptionPane.showMessageDialog(null, "This Cinema already have a movie playing at Evening", "Something Wrong", 1);
			return;
		}
		if(!clatenighth.equals("")) {
			try {
				check = upload(clatenighth,clatenightm,time,"latenight");
			} catch (SQLException e) {
				String respone = clatenighth + ":" + clatenightm + " PM" ;
				JOptionPane.showMessageDialog(null, "Cinema already playing movies on " + respone, "Something Wrong", 1);
				return;
			}
		}
		if(check == false) {
			JOptionPane.showMessageDialog(null, "This Cinema already have a movie playing at Latenight", "Something Wrong", 1);
			return;
		}
		
		if(cafternoonh.equals("") && cmatineeh.equals("") && ceveningh.equals("") && clatenighth.equals("")) {
			JOptionPane.showMessageDialog(null, "Select a time please", "Something Wrong", 1);
			return;
		}
		JOptionPane.showMessageDialog(null, "schedule uploaded", "Congratulations!", 2);
	}
	
	protected boolean upload(String h, String m, String t, String section) throws SQLException {
		if(!h.equals("")) {
			String time = t + h + ":" + m;
			
			if(section.equals("matinee")) {
				String temp1 = cyear + "-" + cmonth + "-" + cday + " " + "13" + ":" + "00" + ":" + "00";
				String temp2 = cyear + "-" + cmonth + "-" + cday + " " + "13" + ":" + "30" + ":" + "00";
				
				if(timeList.contains(temp1) || timeList.contains(temp2)) {
				
					return false;
				}
			}
			if(section.equals("afternoon")) {
				String temp1 = cyear + "-" + cmonth + "-" + cday + " " + "16" + ":" + "00" + ":" + "00";
				String temp2 = cyear + "-" + cmonth + "-" + cday + " " + "16" + ":" + "30" + ":" + "00";
				
				if(timeList.contains(temp1) || timeList.contains(temp2)) {
			
					return false;
				}
			}
			if(section.equals("evening")) {
				String temp1 = cyear + "-" + cmonth + "-" + cday + " " + "19" + ":" + "00" + ":" + "00";
				String temp2 = cyear + "-" + cmonth + "-" + cday + " " + "20" + ":" + "00" + ":" + "00";
				String temp3 = cyear + "-" + cmonth + "-" + cday + " " + "21" + ":" + "00" + ":" + "00";
				if(timeList.contains(temp1) || timeList.contains(temp2) || timeList.contains(temp3)) {
				
					return false;
				}
			}
			if(section.equals("latenight")) {
				String temp1 = cyear + "-" + cmonth + "-" + cday + " " + "22" + ":" + "30" + ":" + "00";
				String temp2 = cyear + "-" + cmonth + "-" + cday + " " + "23" + ":" + "30" + ":" + "00";
				if(timeList.contains(temp1) || timeList.contains(temp2)) {
					
					return false;
				}
			}
			
			db.insertSchedule(movie.getSelectionModel().getSelectedItem(),cinema.getSelectionModel().getSelectedItem(),time);
			
			}
		return true;
		}

	
	
	private void updateMovie() {
		movieList = db.getMovieList();
		for(int i = 0; i < movieList.size(); i++) {
			movie.getItems().add(movieList.elementAt(i));
		}
	}
	
	private void updateCinema() {
	
		cinemaList = db.getCinema();
		for(int i = 0; i < cinemaList.size(); i++) {
			cinema.getItems().add(cinemaList.elementAt(i));
		}
	}
	
	
}
