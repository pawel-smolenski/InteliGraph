package pl.edu.pw.pszt.inteligraph.model;

/**
 * Klasa reprezentująca punkt będący środkiem wierzchołka grafu 
 */
public class Point {
	Coordinate x, y;
	
	/**
	 * @param x
	 * 		współrzędna x-owa
	 * @param y
	 * 		współrzędna y-owa
	 */
	public Point(Coordinate x, Coordinate y) {
		this.x = x;
		this.y = y;
	}
}
