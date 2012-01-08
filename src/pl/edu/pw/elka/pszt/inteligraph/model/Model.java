package pl.edu.pw.elka.pszt.inteligraph.model;

import java.io.File;

import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.SparseGraph;

public class Model
{
	/**
	 * Przechowuje logiczny model grafu
	 */
	private Graph<VertexName, Object> graph;
	
	private SubjectCollection bestSubjectCollection;
	
	public Model()
	{
		this.graph = new SparseGraph<VertexName, Object>();
	}
	
	
	/**
	 * @return Graf
	 */
	public Graph<VertexName, Object> getGraph()
	{
		return graph;
	}

	public void buildGraph(File xmlFile)
	{
		//TODO: Podpiąć budowanie grafu na podstawie pliku XML
	}

	/**
	 * @return Najlepsze uzyskane rozmieszczenie 
	 * @throws Exception 
	 */
	public Arrangement getBestArrangement() throws Exception
	{
		if(bestSubjectCollection == null)
			throw new Exception("No arrangement has been found");
		
		Arrangement arrangement = new Arrangement();
		
		for(Subject subject : this.bestSubjectCollection)
		{
			arrangement.put(subject.getVertexName(), subject.getPoint());
		}
		
		return arrangement;
	}

	/**
	 * Funkcja realizująca strategię ewolucyjną mi+labda.
	 * 
	 * @param mi
	 * @param lambda 
	 * @param iterations Liczba iteracji, po której algorytm ma się zakończyć
	 */
	public void calculateVerticesPositions(Integer mi, Integer lambda, Integer iterations)
	{
		
	}
	
	public void calculateVerticesPositions(Integer mi, Integer lambda)
	{
		this.calculateVerticesPositions(mi, lambda, null);
	}
}
