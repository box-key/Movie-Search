package datatypes;

public class Cinema {
	
	private int id;
	private String name;
	private int x_coordinate;
	private int y_coordinate;
	private String adrs;
	
	public String getAdrs() {
		return adrs;
	}

	public void setAdrs(String adrs) {
		this.adrs = adrs;
	}

	public Cinema(int i, String n, int x, int y) {
		id = i;
		name = n;
		x_coordinate = x;
		y_coordinate = y;
		adrs = x_coordinate + ", " + y_coordinate;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getX_coordinate() {
		return x_coordinate;
	}

	public void setX_coordinate(int x_coordinate) {
		this.x_coordinate = x_coordinate;
	}

	public int getY_coordinate() {
		return y_coordinate;
	}

	public void setY_coordinate(int y_coordinate) {
		this.y_coordinate = y_coordinate;
	}	
}
