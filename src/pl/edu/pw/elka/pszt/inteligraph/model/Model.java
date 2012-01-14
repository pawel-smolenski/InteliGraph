package pl.edu.pw.elka.pszt.inteligraph.model;

import java.awt.Point;
import java.io.File;
import java.util.Collection;

import pl.edu.pw.elka.pszt.inteligraph.events.EventsBlockingQueue;
import sun.security.provider.certpath.Vertex;
import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.SparseGraph;

public class Model
{
	private EventsBlockingQueue blockingQueue;
	
	/**
	 * Ilość iteracji wykonana przez algorytm ewolucyjny;
	 */
	private int evolutionSteps = 0;
	
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
		Population basePopulation = new Population();
		SubjectCollection subjectCollection;
		Subject subject;
		Point point;
		Deviation deviation;
		
		Collection<VertexName> verticies = this.graph.getVertices();
		
		basePopulation = this.generateFirstPopulation(verticies, mi);
		
	}
	
	/**
	 * @param mi
	 * @return Pierwsza populacja
	 */
	private Population generateFirstPopulation(Collection<VertexName> verticies, Integer mi)
	{
		Population firstPopulation = null;
		
		//Generowanie "mi" losowych rozwiązań(SubjectCollections)
		for(int i=0; i < mi; i++)
		{
			//Dla każdego wierzchołka
			for(VertexName vertex : verticies)
			{
				//Generowanie punktu
				//Generowanie odchylenia
				//Tworzenie osobnika
				//Dodawanie osobnika do rozwiązania
			}
			
			//Dodawanie rozwiązania do populacji
		}
				
		return firstPopulation;
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


	public int getEvolutionSteps() {
	    return evolutionSteps;
	}
	
	
}
