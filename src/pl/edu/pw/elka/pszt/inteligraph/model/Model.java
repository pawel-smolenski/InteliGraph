package pl.edu.pw.elka.pszt.inteligraph.model;

import java.awt.Point;
import java.io.File;
import java.util.Collection;
import java.util.Random;

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
	
	private Population currentPopulation;
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
		SubjectCollection subjectCollection;
		Subject subject;
		Point point;
		Deviation deviation;
		
		Collection<VertexName> verticies = this.graph.getVertices();
		
		this.currentPopulation = this.generateFirstPopulation(verticies, mi);
		
		
		this.bestSubjectCollection = currentPopulation.get(0);
	}
	
	/**
	 * @param mi
	 * @return Pierwsza populacja
	 */
	private Population generateFirstPopulation(Collection<VertexName> verticies, Integer mi)
	{
		Random random = new Random();
		Population firstPopulation = new Population();
		SubjectCollection subjectCollection;
		Point point;
		Deviation deviation;
		Subject subject;
		
		//Generowanie "mi" losowych rozwiązań(SubjectCollections)
		for(int i=0; i < mi; i++)
		{
			subjectCollection = new SubjectCollection();
			
			//Dla każdego wierzchołka
			do
			{
				for(VertexName vertex : verticies)
				{
					//Generowanie punktu
					point = new Point(random.nextInt(800), random.nextInt(600));
					
					//Generowanie odchylenia
					deviation = new Deviation(0.1);
					
					//Tworzenie osobnika
					subject = new Subject(vertex, point, deviation);
					
					//Dodawanie osobnika do rozwiązania
					subjectCollection.add(subject);
				}
			} while(false); //Losuje tak długo, aż rozwiązanie będzie dopuszczalne
			
			//Dodawanie rozwiązania do populacji
			firstPopulation.add(subjectCollection);
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
