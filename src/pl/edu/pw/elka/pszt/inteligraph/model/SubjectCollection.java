package pl.edu.pw.elka.pszt.inteligraph.model;

import java.util.ArrayList;

/**
 * Klasa reprezentująca zbiór osobników
 */
<<<<<<< HEAD
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
	
	
	

=======
public class SubjectCollection extends ArrayList<Subject> {
	
	private Integer quality;

	public Integer getQuality()
	{
		return quality;
	}

	public void setQuality(Integer quality)
	{
		this.quality = quality;
	}
	
	
>>>>>>> 1aa77c03ba3e2b99943defce73c2f5291a909622
}
