package pl.edu.pw.elka.pszt.inteligraph.model;


/**
 * Klasa reprezentująca odchylenie standardowe
 */
public class Deviation{
	Integer deviation;
	
	/**
	 * @param deviation
	 * 		wartość odchylenia standardowego
	 */
	public Deviation(Integer deviation) {
		this.deviation = deviation;
	}

	@Override
	public String toString()
	{
		return deviation.toString();
	}
	
	
	
}
