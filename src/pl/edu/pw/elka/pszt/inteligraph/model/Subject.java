package pl.edu.pw.elka.pszt.inteligraph.model;

import java.awt.Point;

/**
 * Klasa reprezentująca osobnika
 */
public class Subject {
	
	private VertexName vertexName;
	private Point point;
	private Deviation deviation;
	
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

	/**
	 * Zwarca nazwę(numer) wierzchołka
	 * @return the vertexName
	 */
	public VertexName getVertexName() {
		return vertexName;
	}
	
	

}
