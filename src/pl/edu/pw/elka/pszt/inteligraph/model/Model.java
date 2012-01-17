package pl.edu.pw.elka.pszt.inteligraph.model;

import java.awt.Point;
import java.awt.geom.Line2D;
import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import pl.edu.pw.elka.pszt.inteligraph.Constans;
import pl.edu.pw.elka.pszt.inteligraph.controller.Controller;
import pl.edu.pw.elka.pszt.inteligraph.events.Event;
import pl.edu.pw.elka.pszt.inteligraph.events.EventName;
import pl.edu.pw.elka.pszt.inteligraph.events.EventsBlockingQueue;
import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.util.Pair;

public class Model
{
	private EventsBlockingQueue blockingQueue;
	
	/**
	 * Ilość iteracji wykonana przez algorytm ewolucyjny;
	 */
	private int evolutionSteps = 0;
	
	/**
	 * Waga krawędzi.
	 */
	private double edgeWeight = 0.5;

	/**
	 * Logiczny model grafu
	 */
	private Graph<VertexName, String> graph;
	
	/**
	 * Aktualna populacja
	 */
	private Population currentPopulation;
	
	/**
	 * Najlepsze uzyskane rozwiązanie
	 */
	private SubjectCollection bestSubjectCollection;
	
	/**
	 * Wątek przeprowadzający obliczenia
	 */
	private Thread calculationThread;
	private volatile Boolean stopThread = false;
	
	public Model(EventsBlockingQueue blockingQueue)
	{
		this.blockingQueue = blockingQueue;
	}	
	
	/**
	 * @return Graf
	 */
	public Graph<VertexName, String> getGraph()
	{
		return graph;
	}

	/**
	 * @return liczba przebytych kroków algorytmu
	 */
	public int getEvolutionSteps() {
	    return evolutionSteps;
	}
	
	/**
	 * @return jakość aktualnego rozwiązania grafu
	 */
	public int getGraphQuality() {
	    return bestSubjectCollection.getQuality();
	}


	/**
	 * Buduje graf na podstawie informacji z pliku XML
	 * @param xmlFile plik xml z definicją grafu
	 */
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
		
		System.out.println("UWAGA TUTAJ BEDZIE TO CZEGO SZUKAMY::::");
		calculateQuality(bestSubjectCollection);
		
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
	public void calculateVerticesPositions(final Integer mi, final Integer lambda, final Integer evolutionStepsToDo)
	{
		SubjectCollection subjectCollection;
		
		final Collection<VertexName> verticies = this.graph.getVertices();
		
		this.currentPopulation = this.generateFirstPopulation(verticies, mi);
		
		this.pickBestSubjectCollection();
	
		this.calculationThread = new Thread(new Runnable()
		{
			
			@Override
			public void run()
			{
				Population temporaryPopulation, childrenPopulation, joinedPopulation;
				SubjectCollection parentA, parentB, embryo, child;
				Subject subjectA, subjectB, embryoSubject, childSubject;
				Point embryoPoint, childPoint;
				Deviation embryoDeviationX, embryoDeviationY, childDeviationX, childDeviationY;
				Double embryoKsi, embryoSubjectKsi;
				Double tau, tauPrime;
				Double ni;
				
				Random random = new Random();
				
				tau = 1 / (Math.sqrt(2 * Math.sqrt(verticies.size())));
				tauPrime = 1 / (Math.sqrt(2 * verticies.size()));
				
				Model.this.evolutionSteps = 0;
				
				Model.this.stopThread = false;
				
				while(!Model.this.stopThread && (evolutionStepsToDo == null || Model.this.evolutionSteps < evolutionStepsToDo))
				{
					
					//Losowanie tymczasowej populacji
					temporaryPopulation = new Population();
					for(int i = 0; i < lambda; i++)
					{
						temporaryPopulation.add(Model.this.currentPopulation.get(random.nextInt(mi)));
					}
					
					//Tworzenie populacji potomnej
					childrenPopulation = new Population();
					
					parentA = temporaryPopulation.get(0); 
					for(int i = 1; i < lambda; i++)
					{
						parentB = temporaryPopulation.get(i);
						embryo = new SubjectCollection();
						
						
						//Tworzenie zarodków poprzez uśrednianie
						for(int j = 0; j < verticies.size(); j++)
						{
							subjectA = parentA.get(j);
							subjectB = parentB.get(j);
							
							embryoPoint = new Point((subjectA.getPoint().x+subjectB.getPoint().x)/2, (subjectA.getPoint().y+subjectB.getPoint().y)/2);
							embryoDeviationX = new Deviation((subjectA.getDeviationX().deviation+subjectB.getDeviationX().deviation)/2);
							embryoDeviationY = new Deviation((subjectA.getDeviationY().deviation+subjectB.getDeviationY().deviation)/2);
							
							
							embryoSubject = new Subject(subjectA.getVertexName(), embryoPoint, embryoDeviationX, embryoDeviationY);
							
							embryo.add(embryoSubject);
						}
						
						
						
						
						embryoKsi = random.nextGaussian();
						
						child = new SubjectCollection();
						
						//Tworzenie dzieci poprzez mutację zarodków
						for(int k = 0; k < verticies.size(); k++)
						{
							embryoSubject = embryo.get(k);
							embryoSubjectKsi = random.nextGaussian();
							ni = random.nextGaussian();
							
							childDeviationX = new Deviation((int)(embryoSubject.getDeviationX().deviation * Math.exp(tauPrime*embryoKsi + tau*embryoSubjectKsi)));
							childDeviationY = new Deviation((int)(embryoSubject.getDeviationY().deviation * Math.exp(tauPrime*embryoKsi + tau*embryoSubjectKsi)));
							childPoint = new Point((int)(embryoSubject.getPoint().x + childDeviationX.deviation*ni), (int)(embryoSubject.getPoint().y + childDeviationY.deviation*ni));
							
							childSubject = new Subject(embryoSubject.getVertexName(), childPoint, childDeviationX, childDeviationY);
							
							child.add(childSubject);
						}
						
						//Oblicza jakość powstałego rozwiązania
						Model.this.calculateQuality(child);
						
						childrenPopulation.add(child);
						
						parentA = parentB;
						
					}
					
					if (Model.this.bestSubjectCollection == null) {
					    Model.this.bestSubjectCollection = childrenPopulation.get(0);
					    Model.this.calculateQuality(Model.this.bestSubjectCollection);
					}
									
					for(int l = 0; l < childrenPopulation.size(); l++)
					{
						if(Model.this.bestSubjectCollection.getQuality() > Model.this.calculateQuality(childrenPopulation.get(l)))
						{
							Model.this.bestSubjectCollection = childrenPopulation.get(l);
						}
					}
					
					//Wybór mi najlepszych osobników spośród sumy populacji bazowej i dzieci
					joinedPopulation = new Population();
					joinedPopulation.addAll(Model.this.currentPopulation);
					joinedPopulation.addAll(childrenPopulation);
					Collections.sort(joinedPopulation);
					Model.this.currentPopulation.clear();
					
					for(int m = 0; m < mi; m++)
					{
						Model.this.currentPopulation.add(joinedPopulation.get(m));
					}
					
					//Zwiększanie licznika iteracji
					Model.this.evolutionSteps++;
					blockingQueue.add(new Event(EventName.ITERATION_ALGORITHM));
					
				}
				
				try
				{
					Model.this.blockingQueue.put(new Event(EventName.DRAW_GRAPH_STOP));
				} catch (InterruptedException e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
		});
		
		calculationThread.start();
	}


	/**
	 * Przerywa obliczenia
	 */
	public synchronized void stopCalculations()
	{
		this.stopThread = true;
	}

	/**
	 * Wybiera najlepsze rozwiązanie z aktualnej populacji
	 */
	private void pickBestSubjectCollection()
	{
		for(SubjectCollection solution : this.currentPopulation)
		{
			if(this.bestSubjectCollection == null || this.calculateQuality(solution) < this.bestSubjectCollection.getQuality())
			{
				this.bestSubjectCollection = solution;
			}
		}
	}


	/**
	 * Oblicza jakość rozwiązania pod wzglądem spełnienia kryteriów
	 * @param solution
	 * @return jakośc rozwiązania
	 */
	private Integer calculateQuality(SubjectCollection solution)
	{
		int quality = 0, numberOfEdges = 0;
		double currentEdgeLength = 0, edgeLengthSum = 0, avg = 0, longestEgde  = 0;
		List<Pair<VertexName>> vertexPairList = getGraphEdges(); //lista krawędzi jako pary wierzchołków
		Collection<VertexName> vertexCollection = graph.getVertices();
		
		
		Map<VertexName, Point> map = new HashMap<VertexName, Point>();
		for(Pair<VertexName> p: vertexPairList)
		{	

			map.put(p.getFirst(), solution.getPoint(p.getFirst()));
			map.put(p.getSecond(), solution.getPoint(p.getSecond()));

		}
		int przecinanie = 0, odchylenie = 0, odlegloscVertexOdKrawedzi = 0;
		przecinanie = 100000*this.calculateCrossing(map,vertexPairList);
		odchylenie = this.edgeLengthVariation(map,vertexPairList,200);
		if( odchylenie == Integer.MAX_VALUE)
		{
			solution.setQuality(Integer.MAX_VALUE);
			return Integer.MAX_VALUE;
		}
		odlegloscVertexOdKrawedzi += (int)100*this.edgeDistanseToVertex(map,vertexPairList, vertexCollection, 200);
		if(odlegloscVertexOdKrawedzi == Integer.MAX_VALUE)
		{
			solution.setQuality(Integer.MAX_VALUE);
			return Integer.MAX_VALUE;
		}
		

		
		
		
		System.out.println("Przecinanie: " + przecinanie + " odchylenie: " + odchylenie + "odleglosc: " + odlegloscVertexOdKrawedzi);
		quality = przecinanie + odchylenie + odlegloscVertexOdKrawedzi;
		solution.setQuality(quality);
		return quality;
	}
	

	private double edgeDistanseToVertex(Map<VertexName, Point> map,
			List<Pair<VertexName>> vertexPairList,
			Collection<VertexName> vertexCollection, int distance) {
		double penalty = 0, currentDistance = 0;
		for (VertexName currentVertex : vertexCollection)
		{
			for(Pair<VertexName> currentEdge : vertexPairList)
			{
				if(!currentEdge.contains(currentVertex))
				{
					currentDistance = Line2D.ptSegDist(map.get(currentEdge.getFirst()).x,
							map.get(currentEdge.getFirst()).y,
							map.get(currentEdge.getSecond()).x,
							map.get(currentEdge.getSecond()).y,
							map.get(currentVertex).x, 
							map.get(currentVertex).y);
					if(currentDistance < 20)
						return Integer.MAX_VALUE;
					currentDistance -= distance;
					if(currentDistance < 0)
						penalty += currentDistance * currentDistance;
					
				}
			}
		}
		return penalty;
	}

	private int edgeLengthVariation(Map<VertexName, Point> map,
			List<Pair<VertexName>> vertexPairList, double avg) 
	{
		int variation = 0;
		double currentVariation = 0;
		for(Pair<VertexName> currentPair: vertexPairList )
		{
			
			System.out.println(map.get(currentPair.getFirst()).distance(
					map.get(currentPair.getSecond())));
				currentVariation = map.get(currentPair.getFirst()).distance(map.get(currentPair.getSecond()))- avg;
				if(currentVariation < 0)
					currentVariation *= -1;
				if(currentVariation > 900)
				return Integer.MAX_VALUE;
					
				if(currentVariation > 10)
				variation += currentVariation * currentVariation;
		}
		return variation;
	}

	private int  calculateCrossing(Map<VertexName, Point> map,
			List<Pair<VertexName>> vertexPairList) {
		
		int crossing = 0;
		
		for(Pair<VertexName> pFirst: vertexPairList )
		{
			for(Pair<VertexName> pSecond: vertexPairList )
			{	
				if(!checkPoints(pFirst, pSecond))
				{
				  //  System.out.println("wywołuje dla: [" + pFirst
					//    + "] [" + pSecond + "]");
					switch (Sections.isCrossing(map.get(pFirst.getFirst()), map.get(pFirst.getSecond()), map.get(pSecond.getFirst()), map.get(pSecond.getSecond()))) 
					{
					case -1:
						return -1;

					case 1:
						crossing += 1;
						break;
					}
					
				}
					
			}
		}
		return crossing/2;
		
	}

	/**
	 * Sprawdza czy dwie pary punktów są takie same.
	 * @param p1
	 * @param p2
	 * @return TURE jak są takie same
	 */
	private boolean checkPoints(Pair<VertexName> p1, Pair<VertexName> p2) {
	    if (p1.getFirst().getName().equals(p2.getFirst().getName()) && p1.getSecond().getName().equals(p2.getSecond().getName()) ) {
		return true;
	    }
	    return false;
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
		Deviation deviationX, deviationY;
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
					deviationX = new Deviation((int)(random.nextGaussian() + 800));
					
					//Generowanie odchylenia
					deviationY = new Deviation((int)(random.nextGaussian()+ 600));
					
					//Tworzenie osobnika
					subject = new Subject(vertex, point, deviationX, deviationY);
					
					//Dodawanie osobnika do rozwiązania
					subjectCollection.add(subject);
					
				}
				System.out.println("zawiecha");
			} while( this.calculateQuality(subjectCollection) < 0 ); //Losuje tak długo, aż rozwiązanie będzie dopuszczalne
			
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
	 * Na podstawie nazwy krawędzi ustala i tworzy listę par wierzchołków krawędzi.
	 * @return lista par wierzchołków
	 */
	private List<Pair<VertexName>> getGraphEdges() {
	    ArrayList<String> edgesList = new ArrayList<String>();
	    edgesList.addAll(graph.getEdges());
	    List<Pair<VertexName>> ret = new ArrayList<Pair<VertexName>>();
	    for(String s : edgesList) {
		ret.add(graph.getEndpoints(s));
	    }
	    return ret;
	}

	public void clearbestSubjectCollection() {
		bestSubjectCollection = null;
		
	}
	
	public void setEdgeWeight(double edgeWeight) {
	    this.edgeWeight = edgeWeight;
	}

	public double getEdgeWeight() {
	    return edgeWeight;
	}
}
