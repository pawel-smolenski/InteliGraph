package pl.edu.pw.pszt.inteligraph.model;

/**
 * Klasa reprezentująca osobnika
 */
public class Subject {
	
	Point point;
	Deviation deviation;
	
	/**
	 * @param point 
	 * 		współrzędne środka wierzchołka 
	 * @param deviation
	 * 		odchylenie standardowe
	 */
	public Subject(Point point, Deviation deviation) {
		this.point = point;
		this.deviation = deviation;
	}

}
