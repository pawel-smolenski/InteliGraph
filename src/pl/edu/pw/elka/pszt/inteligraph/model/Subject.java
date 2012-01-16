package pl.edu.pw.elka.pszt.inteligraph.model;

import java.awt.Point;

/**
 * Klasa reprezentująca osobnika
 */
public class Subject {
	
	private VertexName vertexName;
	private Point point;
	private Deviation deviationX;
	private Deviation deviationY;
	/**
	 * @param vertex 
	 * @param point 
	 * 		współrzędne środka wierzchołka 
	 * @param deviation
	 * 		odchylenie standardowe
	 */
	public Subject(VertexName vertex, Point point, Deviation deviationX, Deviation deviationY) {
		this.vertexName = vertex;
		this.point = point;
		this.deviationX = deviationX;
		this.deviationY = deviationY;
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
	public Deviation getDeviationX() {
		return deviationX;
	}
	
	public Deviation getDeviationY() {
		return deviationY;
	}

	/**
	 * Zwarca nazwę(numer) wierzchołka
	 * @return the vertexName
	 */
	public VertexName getVertexName() {
		return vertexName;
	}
	
	

}
