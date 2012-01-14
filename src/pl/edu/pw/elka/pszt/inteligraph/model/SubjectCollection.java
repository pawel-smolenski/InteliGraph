package pl.edu.pw.elka.pszt.inteligraph.model;

import java.util.ArrayList;

/**
 * Klasa reprezentująca zbiór osobników
 */

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
	
}
