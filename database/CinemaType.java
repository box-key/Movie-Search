package database;

public class CinemaType {
	
	boolean G;
	boolean PG;
	boolean PG_13;
	boolean R;
	boolean NC17;

	public CinemaType(boolean g, boolean pG, boolean pG_13, boolean r, boolean nC17) {
		G = g;
		PG = pG;
		PG_13 = pG_13;
		R = r;
		NC17 = nC17;
	}

	public boolean getG() {
		return G;
	}

	public boolean getPG() {
		return PG;
	}

	public boolean getPG_13() {
		return PG_13;
	}

	public boolean getR() {
		return R;
	}

	public boolean getNC17() {
		return NC17;
	}

}