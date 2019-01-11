package datatypes;

import java.util.Date;

public class CinemaAndTime {
	
	private String cinema_name;
	private String cinema_address;
	private String time;
	private String matinee;
	private String afternoon;
	private String evening;
	private String midnight;
	
	public CinemaAndTime(String cinema_name, String cinema_address, String time) {
		this.cinema_name = cinema_name;
		this.cinema_address = cinema_address;
		this.time = time;
	}
	
	public CinemaAndTime(String name, String address) {
		cinema_name = name;
		cinema_address = address;
	}

	public String getCinema_name() {
		return cinema_name;
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

	public void setCinema_name(String cinema_name) {
		this.cinema_name = cinema_name;
	}

	public String getCinema_address() {
		return cinema_address;
	}

	public void setCinema_address(String cinema_address) {
		this.cinema_address = cinema_address;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}
	
}
