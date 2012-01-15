package pl.edu.pw.elka.pszt.inteligraph.model;

import java.awt.Point;
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

	public Point getPoint(VertexName first) 
	{
		for(Subject s : this)
		{
			if(s.getVertexName().equals(first))
				return s.getPoint();
		}
		return null;
	}
	
}
