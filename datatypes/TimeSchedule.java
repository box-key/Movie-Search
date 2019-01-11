package datatypes;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeSchedule {
	private String movie;
	private String cinema;
	private String time;
	
	public TimeSchedule(String movie, String cinema, String time) {
		this.movie = movie;
		this.cinema = cinema;
		this.time = time;
	}
	
	private Date convertStringToDate(String t) {
		SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy");
		Date date = null;
		try {
	    	date = df.parse(t);
	    } catch (Exception e) {
	    	e.printStackTrace();
	    }
	    return date;
	}

	public String getMovie() {
		return movie;
	}

	public void setMovie(String movie) {
		this.movie = movie;
	}

	public String getCinema() {
		return cinema;
	}

	public void setCinema(String cinema) {
		this.cinema = cinema;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}
	
	
	
}
