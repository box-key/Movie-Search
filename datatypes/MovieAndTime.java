package datatypes;

public class MovieAndTime {
	
	private String movie_title;
	private String movie_rating;
	private String time;
	private String matinee;
	private String afternoon;
	private String evening;
	private String midnight;
	
	public MovieAndTime(String title, String rating, String t) {
		movie_title = title;
		movie_rating = rating;
		time = t;
	}
	
	public MovieAndTime(String title, String rating) {
		movie_title = title;
		movie_rating = rating;
	}
	
	
	public String getTime() {
		return time;
	}
	
	
	public String getMovie_title() {
		return movie_title;
	}
	public void setMovie_title(String movie_title) {
		this.movie_title = movie_title;
	}
	public String getMovie_rating() {
		return movie_rating;
	}
	public void setMovie_rating(String movie_rating) {
		this.movie_rating = movie_rating;
	}
	public String getMatinee() {
		return matinee;
	}
	public void setMatinee(String matinee) {
		this.matinee = matinee;
	}
	public String getAfternoon() {
		return afternoon;
	}
	public void setAfternoon(String afternoon) {
		this.afternoon = afternoon;
	}
	public String getEvening() {
		return evening;
	}
	public void setEvening(String evening) {
		this.evening = evening;
	}
	public String getMidnight() {
		return midnight;
	}
	public void setMidnight(String midnight) {
		this.midnight = midnight;
	}

}
