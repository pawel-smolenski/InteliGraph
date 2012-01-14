package pl.edu.pw.elka.pszt.inteligraph.model;

/**
 * Klasa reprezentująca odchylenie standardowe
 */
public class Deviation {
	double deviation;
	
	/**
	 * @param deviation
	 * 		wartość odchylenia standardowego
	 */
	public Deviation(double deviation) {
		this.deviation = deviation;
	}

	@Override
	public String toString()
	{
		return "Deviation [deviation=" + deviation + "]";
	}
	
	
	
}
