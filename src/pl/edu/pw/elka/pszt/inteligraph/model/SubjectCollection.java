package pl.edu.pw.elka.pszt.inteligraph.model;

import java.util.ArrayList;

/**
 * Klasa reprezentująca zbiór osobników
 */
public class SubjectCollection extends ArrayList<Subject>
{
	private Integer quality = null;

	public Integer getQuality() 
	{
		if(this.quality == null)
		{
			this.calculateQuality();
		}
		
		return quality;
	}
	 
	/**
	 * funkcja ocenia wygenerowany graf
	 * ujemna wartość dla odrzuconego grfu
	 */
	public void  calculateQuality()
	{
		//this.
		int i,det; //wyznacznik macierzy
		int x[] = new int[4]; //tablica wspolrzednych x punktow
		int y[] = new int[4]; //tablica wspolrzednych y punktow
		
		
	}
	
	
	

}
