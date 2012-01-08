package pl.edu.pw.elka.pszt.inteligraph.model;

import java.io.File;

import pl.edu.pw.elka.pszt.inteligraph.events.EventsBlockingQueue;

import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.SparseGraph;

public class Model
{
	private EventsBlockingQueue blockingQueue;
	
	/**
	 * Przechowuje logiczny model grafu
	 */
	private Graph<VertexName, String> graph;
	
	private SubjectCollection bestSubjectCollection;
	
	public Model(EventsBlockingQueue blockingQueue)
	{
		this.blockingQueue = blockingQueue;
		this.graph = new SparseGraph<VertexName, String>();
	}
	
	
	/**
	 * @return Graf
	 */
	public Graph<VertexName, String> getGraph()
	{
		return graph;
	}

	public void buildGraph(File xmlFile)
	{
		GraphParser read = new GraphParser();
		InputGraph readConfig = read.readXmlGraph(xmlFile);
		graph = readConfig.getGraph();
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
	
	/**
	 * Przerywa obliczenia
	 */
	public void stopCalculations()
	{
		
	}
}
