package pl.edu.pw.elka.pszt.inteligraph.model;

import java.awt.Point;

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

	/**
	 * Zwraca współrzędne śrdoska wierzchołka
	 * @return
	 */
	public Point getPoint() {
		return point;
	}

	/**
	 * Zwarca odchylenie standardowe
	 * @return
	 */
	public Deviation getDeviation() {
		return deviation;
	}

}
