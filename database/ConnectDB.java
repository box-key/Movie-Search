package database;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;

import datatypes.Cinema;
import datatypes.CinemaAndTime;
import datatypes.Movie;
import datatypes.MovieAndTime;

public class ConnectDB {
	
	private String dbAddress;
	private String dbName;
	private String dbPassword;
	private java.sql.Connection conn;
	private Statement stmt;
	private static ConnectDB theConnection = null;
	
	public static ConnectDB CreateConnection(String dbPath, String name, String pw) {
		if(theConnection == null) {
			theConnection = new ConnectDB(dbPath, name, pw);	
		}
		return theConnection;
	}

	private ConnectDB(String dbPath, String name, String pw){
		dbAddress = dbPath;
		dbName = name;
		dbPassword = pw;
		try {
			conn = DriverManager.getConnection(
					dbAddress, dbName, dbPassword
				);
			stmt = conn.createStatement();
			System.out.println("You're successfully connected");
		} catch(Exception e) {
			e.printStackTrace();
			System.out.println("Something is wrong");
		}
	}
	
	public void insertMovie(String title, String rating) throws SQLException   {
		String query = "INSERT INTO Movies (title, rating) values (?, ?)";
		PreparedStatement pstmt;
		pstmt = conn.prepareStatement(query);
		pstmt.setString(1, title);
		pstmt.setString(2, rating);
		pstmt.executeUpdate();
	}
	
	public void insertCinema(String name, int x, int y, CinemaType ct) throws SQLException  {
		String query_parent = "INSERT INTO Cinemas (cinema_name, x_coordinate, y_coordinate) values (?, ?, ?)";
		String query_child = "INSERT INTO CinemaDetails (cinema_id, rate_G, rate_PG, rate_PG13, rate_R, rate_NC17) values ((select cinema_id from Cinemas where cinema_name = ?), ?, ?, ?, ?, ?)";
		PreparedStatement pstmt;
		pstmt = conn.prepareStatement(query_parent);
		pstmt.setString(1, name);
		pstmt.setInt(2, x);
		pstmt.setInt(3, y);
		pstmt.executeUpdate();
			
		pstmt = conn.prepareStatement(query_child);
		pstmt.setString(1, name);
		pstmt.setInt(2, (ct.getG()) ? 1:0);
		pstmt.setInt(3, (ct.getPG()) ? 1:0);
		pstmt.setInt(4, (ct.getPG_13()) ? 1:0);
		pstmt.setInt(5, (ct.getR()) ? 1:0);
		pstmt.setInt(6, (ct.getNC17()) ? 1:0);
		pstmt.executeUpdate();
	}
	
	public Vector<String> getMovieList() {
		Vector<String> movie = new Vector<>();
		String query = "select title from Movies group by movie_id";
		ResultSet result = getResultOf(query);
		try {
			while(result.next()) {
				movie.add(result.getString("title"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return movie;
	}
	public Vector<String> getRating() {
		Vector<String> rating = new Vector<>();
		String query = "select rating from Movies group by movie_id";
		ResultSet result = getResultOf(query);
		try {
			while(result.next()) {
				rating.add(result.getString("rating"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return rating;
	}
	
	public Vector<String> getCinema() {
		Vector<String> cinema = new Vector<>();
		String query = "select cinema_name from Cinemas group by cinema_id";
		ResultSet result = getResultOf(query);
		try {
			while(result.next()) {
				cinema.add(result.getString("cinema_name"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return cinema;
	}
	/*
	public Vector<String> getSchedule(String title, String cinema_name) throws SQLException{
		Vector<String> schedule = new Vector<>();
		String query = "select showtime FROM TimeSchedules WHERE movie_id ="
				+ " (select movie_id from Movies where title = '" + title +"' ) "
						+ "and cinema_id = "
						+ "(select cinema_id from Cinemas where cinema_name ='" + cinema_name +"') ";
	
		ResultSet result = getResultOf(query);
	

		while(result.next()) {
			schedule.add(result.getString("showtime"));
		}
		
		return schedule;
		
	}
	*/
	
	public Vector<String> getSchedule(String date, String cinema_name) throws SQLException, ParseException{
		Vector<String> schedule = new Vector<>();
		String query = "select showtime FROM TimeSchedules WHERE DATE(showtime) = ? and cinema_id = "
						+ "(select cinema_id from Cinemas where cinema_name = ?) ";
		SimpleDateFormat df = new SimpleDateFormat("MM/dd/yy");
		Date d = df.parse(date);
		PreparedStatement ps = conn.prepareStatement(query);
		ps.setDate(1, new java.sql.Date(d.getTime()));
		ps.setString(2, cinema_name);
		ResultSet result = ps.executeQuery();

		while(result.next()) {
			schedule.add(result.getString("showtime"));
		}
		
		return schedule;
		
	}
	

	public Vector<String> getXCord() {
		Vector<String> xCord = new Vector<>();
		String query = "select x_coordinate from Cinemas group by cinema_id";
		ResultSet result = getResultOf(query);
		try {
			while(result.next()) {
				xCord.add(result.getString("x_coordinate"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return xCord;
	}

	public Vector<String> getYCord() {
		Vector<String> yCord = new Vector<>();
		String query = "select y_coordinate from Cinemas group by cinema_id";
		ResultSet result = getResultOf(query);
		try {
			while(result.next()) {
				yCord.add(result.getString("y_coordinate"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return yCord;
	}
	
	public int getCinemaId(String title) {
		int id = 0 ;
		String query = "select cinema_id from Cinemas where cinema_name = '" + title +"' ";
		ResultSet result = getResultOf(query);
		try {
			while(result.next()) {
				id = result.getInt("cinema_id");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return id;
	}
	public CinemaType getType(String id) {
		int x = Integer.parseInt(id);
		String query = "select*  from CinemaDetails where cinema_id = '"+ x +"'" ;
		ResultSet result = getResultOf(query);
		CinemaType type = null;
		try {
			result.next();
			int g = (int) result.getObject("rate_G");
			int pg = (int) result.getObject("rate_PG");
			int pg13 = (int) result.getObject("rate_PG13");
			int r = (int) result.getObject("rate_R");
			int nc17 = (int) result.getObject("rate_NC17");
			type = new CinemaType(convertToBoolean(g),convertToBoolean(pg)
					,convertToBoolean(pg13),convertToBoolean(r),convertToBoolean(nc17));
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return type;
	}
	
	private Timestamp convertStringToTimestamp(String time) {
		Timestamp timestamp = null;
		try {
			SimpleDateFormat df = new SimpleDateFormat("MM/dd/yy hh:mm");
		    Date date = df.parse(time);
		    timestamp = new Timestamp(date.getTime());
		} catch(Exception e) { 
			e.printStackTrace();
		}
		return timestamp;
	}
	
	public void insertSchedule(String movie_title, String cinema_name, String time) throws SQLException {
		String query = "INSERT INTO TimeSchedules (movie_id, cinema_id, showtime) values "
				+ "( (select movie_id from Movies where title = ?), (select cinema_id from Cinemas where cinema_name = ?), ?)";
			PreparedStatement pstmt = conn.prepareStatement(query);
			pstmt.setString(1, movie_title);
			pstmt.setString(2, cinema_name);
			pstmt.setTimestamp(3, convertStringToTimestamp(time));
			pstmt.executeUpdate();

	}
	
	
	public void deleteCinema(String name) {
		String query_parent = "DELETE FROM Cinemas WHERE cinema_name = ?";
		String query_child1 = "DELETE FROM CinemaDetails WHERE cinema_id = (SELECT cinema_id FROM Cinemas WHERE cinema_name = ?)";
		String query_child2 = "DELETE FROM TimeSchedules WHERE cinema_id = (SELECT cinema_id FROM Cinemas WHERE cinema_name = ?)";
		try {
			PreparedStatement pstmt = conn.prepareStatement(query_child1);
			pstmt.setString(1, name);
			pstmt.execute();
	
			pstmt = conn.prepareStatement(query_child2);
			pstmt.setString(1, name);
			pstmt.execute();
			
			pstmt = conn.prepareStatement(query_parent);
			pstmt.setString(1, name);
			pstmt.execute();		
		} catch (SQLException sqle) {
			sqle.printStackTrace();
		}
	}
	
	public void deleteMovie(String title) {
		String query_parent = "DELETE FROM Movies WHERE title = ?";
		String query_child = "DELETE FROM TimeSchedules WHERE movie_id = (SELECT movie_id FROM Movies WHERE title = ?)";
		try {
			PreparedStatement pstmt = conn.prepareStatement(query_child);
			pstmt.setString(1, title);
			pstmt.execute();
			
			pstmt = conn.prepareStatement(query_parent);
			pstmt.setString(1, title);
			pstmt.execute();		
		} catch (SQLException sqle) {
			sqle.printStackTrace();
		}
	}
	
	public void deleteSchedule(String movie_title, String cinema_name, String time) {
		String query = "DELETE FROM TimeSchedules "
					+  "WHERE movie_id =  (select movie_id from Movies where title = ?) "
					+  "and cinema_id = (select cinema_id from Cinemas where cinema_name = ?) "
					+  " and showtime = ?";
		try {
			Timestamp timestamp = null;
			try {
				SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			    Date date = df.parse(time);
			    timestamp = new Timestamp(date.getTime());
			} catch(Exception e) { 
				e.printStackTrace();
			}
			
			PreparedStatement pstmt = conn.prepareStatement(query);
			pstmt.setString(1, movie_title);
			pstmt.setString(2, cinema_name);
			pstmt.setTimestamp(3, timestamp);
			pstmt.executeUpdate();
		} catch (SQLException sqle) {
			sqle.printStackTrace();
		}		
	}
	
	public void updateMovie(String targetTitle, String newTitle, String newRating) throws SQLException {
		String query = "UPDATE Movies SET title = ?, rating = ? where title = ?";
		PreparedStatement pstmt = conn.prepareStatement(query);
		pstmt.setString(1, newTitle);
		pstmt.setString(2, newRating);
		pstmt.setString(3, targetTitle);
		pstmt.executeUpdate();
	}
	

	public void updateCinema(String targetName, String newName, int newX, int newY, CinemaType ct) throws SQLException {
		String query = "update Cinemas as c	inner join CinemaDetails as cd on c.cinema_id = cd.cinema_id " 
						+ "set c.cinema_name = ?"
						+ ", c.x_coordinate = ?"
						+ ", c.y_coordinate = ?"
						+ ", cd.rate_G = ?"
						+ ", cd.rate_PG = ?"
						+ ", cd.rate_PG13 = ?"
						+ ", cd.rate_R = ?"
						+ ", cd.rate_NC17 = ?"
						+ " where c.cinema_name = ?";
		PreparedStatement pstmt = conn.prepareStatement(query);
		pstmt.setString(1, newName);
		pstmt.setInt(2, newX);
		pstmt.setInt(3, newY);
		pstmt.setInt(4, (ct.getG()) ? 1:0);
		pstmt.setInt(5, (ct.getPG()) ? 1:0);
		pstmt.setInt(6, (ct.getPG_13()) ? 1:0);
		pstmt.setInt(7, (ct.getR()) ? 1:0);
		pstmt.setInt(8, (ct.getNC17()) ? 1:0);
		pstmt.setString(9, targetName);
		pstmt.executeUpdate();
	}

	
	private ResultSet getResultOf(String sqlQuery){
		try {
			ResultSet rset = stmt.executeQuery(sqlQuery);
			return rset;
		} catch(SQLException sqle) {
			sqle.printStackTrace();
			return null;
		} 
	}
	private boolean convertToBoolean(int i) {
		if(i==1) return true;
		else return false;
	}
	public Vector<Cinema> getCinemas(int x, int y, int radius, int g, int pg, int pg13, int r, int nc17){
		Vector<Cinema> list = new Vector<>();
		String query = "{call MovieApp.CinemasWithinRadius(?,?,?,?,?,?,?,?)}" ;
		try {
			PreparedStatement pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, x);
			pstmt.setInt(2, y);
			pstmt.setInt(3, radius);
			pstmt.setInt(4, g);
			pstmt.setInt(5, pg);
			pstmt.setInt(6, pg13);
			pstmt.setInt(7, r);
			pstmt.setInt(8, nc17);
			ResultSet rs = pstmt.executeQuery();
			while(rs.next()) {
				list.add(new Cinema(rs.getInt("id"), rs.getString("cname"), rs.getInt("x"), rs.getInt("y")));
			}
		} catch(SQLException sqle) {
			sqle.printStackTrace();
		}	
		return list;
	}
	
	public Vector<MovieAndTime> getMoviesAndTime(int cinema_id){
		Vector<MovieAndTime> list = new Vector<>();
		try{
			String query = "SELECT m.title as title, m.rating as rating, t.showtime as time "
						 + "FROM (SELECT cinema_id, movie_id, showtime FROM TimeSchedules WHERE DATE(showtime) = ? ) as t "
						 + "INNER JOIN Movies as m ON m.movie_id = t.movie_id "
						 + "WHERE t.cinema_id = ? "
						 + "ORDER BY t.movie_id, t.showtime";
			PreparedStatement ps = conn.prepareStatement(query);
			ps.setDate(1, getCurrentTime());
			ps.setInt(2, cinema_id);
		
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				SimpleDateFormat df = new SimpleDateFormat("MM/dd/yy hh:mm");
				Date d = rs.getTimestamp("time");
				list.add(new MovieAndTime(rs.getString("title"), rs.getString("rating"), df.format(d)));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
	private java.sql.Date getCurrentTime() {
		Date date = new Date();
		return new java.sql.Date(date.getTime()); 
	}
	
	public Vector<Movie> getCurrentMovies(String movie_rating){
		Vector<Movie> list = new Vector<>();
		try {
			String query = "SELECT distinct m.movie_id as id, m.title as title, m.rating as rating "
						 + "FROM (SELECT cinema_id, movie_id, showtime FROM TimeSchedules WHERE DATE(showtime) = ? ) as t "
						 + "INNER JOIN Movies as m ON m.movie_id = t.movie_id ";
			if(!movie_rating.equals("All")) query += " WHERE m.rating = ? ";
			PreparedStatement ps = conn.prepareStatement(query);
			ps.setDate(1, getCurrentTime());
			if(!movie_rating.equals("All")) ps.setString(2, movie_rating);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				list.add(new Movie(rs.getInt("id"), rs.getString("title"), rs.getString("rating")));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
	public Vector<String> getMovieTitles() {
		Vector<String> list = new Vector<>();
		String query = "SELECT title FROM Movies";
		ResultSet rs = getResultOf(query);
		try {
			while(rs.next()) {
				list.add(rs.getString("title"));
			}
		} catch (SQLException sqle) {
			sqle.printStackTrace();
		}
		return list;
	}
	
	public Vector<String> getCinemaNames() {
		Vector<String> list = new Vector<>();
		String query = "SELECT cinema_name as name FROM Cinemas";
		ResultSet rs = getResultOf(query);
		try {
			while(rs.next()) {
				list.add(rs.getString("name"));
			}
		} catch (SQLException sqle) {
			sqle.printStackTrace();
		}
		return list;
	}
	
	
	public Vector<CinemaAndTime> getCinemasAndTime(int movie_id){
		Vector<CinemaAndTime> list = new Vector<>();
		String address;
		try {	
			String query = "SELECT c.cinema_name as name, c.x_coordinate as x, c.y_coordinate as y, t.showtime as time "
						 + "FROM (select cinema_id, movie_id, showtime from TimeSchedules WHERE DATE(showtime) = ? ) as t "
						 + "INNER JOIN Cinemas as c ON c.cinema_id = t.cinema_id "
						 + "WHERE t.movie_id = ? "
						 + "ORDER BY t.cinema_id, t.showtime";
			PreparedStatement ps = conn.prepareStatement(query);
			ps.setDate(1, getCurrentTime());
			ps.setInt(2, movie_id);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				SimpleDateFormat df = new SimpleDateFormat("MM/dd/yy hh:mm");
				Date d = rs.getTimestamp("time");
				//System.out.println(df.format(d));
				address = "(" + rs.getInt("x") + ", " + rs.getInt("y") + ")";
				list.add(new CinemaAndTime(rs.getString("name"), address, df.format(d)));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	


		
}