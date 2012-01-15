package pl.edu.pw.elka.pszt.inteligraph.model;

import java.awt.Point;
import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

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
		
		//this.pickBestSubjectCollection();
		
		this.bestSubjectCollection = currentPopulation.get(0);
		
		this.calculationThread = new Thread(new Runnable()
		{
			
			@Override
			public void run()
			{
				Population temporaryPopulation;
				Population childrenPopulation;
				SubjectCollection parentA, parentB, embryo, child;
				Subject subjectA, subjectB, embryoSubject, childSubject;
				Point embryoPoint, childPoint;
				Deviation embryoDeviation, childDeviation;
				Double embryoKsi, embryoSubjectKsi;
				Double tau, tauPrime;
				Double ni;
				
				Random random = new Random();
				
				tau = 1 / (Math.sqrt(2 * Math.sqrt(verticies.size())));
				tauPrime = 1 / (Math.sqrt(2 * verticies.size()));
				
				while(evolutionStepsToDo == null || Model.this.evolutionSteps < evolutionStepsToDo)
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
							embryoDeviation = new Deviation((subjectA.getDeviation().deviation+subjectB.getDeviation().deviation)/2);
							
							embryoSubject = new Subject(subjectA.getVertexName(), embryoPoint, embryoDeviation);
							
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
							
							childDeviation = new Deviation((int)(embryoSubject.getDeviation().deviation * Math.exp(tauPrime*embryoKsi + tau*embryoSubjectKsi)));
							childPoint = new Point((int)(embryoSubject.getPoint().x + childDeviation.deviation*ni), (int)(embryoSubject.getPoint().y + childDeviation.deviation*ni));
							
							childSubject = new Subject(embryoSubject.getVertexName(), childPoint, childDeviation);
							
							child.add(childSubject);
						}
						
						childrenPopulation.add(child);
						
						parentA = parentB;
						
					}
					
					Model.this.bestSubjectCollection = childrenPopulation.get(0);
					
					Model.this.evolutionSteps++;
					
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
	 * Wybiera najlepsze rozwiązanie z aktualnej populacji
	 */
	private void pickBestSubjectCollection()
	{
		for(SubjectCollection solution : this.currentPopulation)
		{
			if(this.bestSubjectCollection == null || this.calculateQuality(solution) >= this.bestSubjectCollection.getQuality())
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
		int quality = 0;
		Sections check = new Sections();
		List<Pair<VertexName>> vertexPairList = getGraphEdges();
		Map<VertexName, Point> map= new HashMap<VertexName, Point>();
		for(Pair<VertexName> p: vertexPairList)
		{
			map.put(p.getFirst(), solution.getPoint(p.getFirst()));
			map.put(p.getSecond(), solution.getPoint(p.getSecond()));

		}
		
		for(Pair<VertexName> pFirst: vertexPairList )
		{
			for(Pair<VertexName> pSecond: vertexPairList )
			{
				if(!pFirst.getFirst().equals(pSecond.getFirst()) || !pFirst.getSecond().equals(pSecond.getSecond()) )
				{
					switch (check.isCrossing(map.get(pFirst.getFirst()), map.get(pFirst.getSecond()), map.get(pSecond.getFirst()), map.get(pSecond.getSecond()))) {
					case -1:
						System.out.println("do dupy");
						solution.setQuality(-1);
						return -1;

					case 1:
						System.out.println(pFirst + "	" +pSecond);

						quality++;
						break;
					}
					
				}
					
			}
		}
		System.out.println(quality);
		solution.setQuality(quality);
		return quality;
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
					deviation = new Deviation((int)(random.nextGaussian()*200 + 400));
					
					//Tworzenie osobnika
					subject = new Subject(vertex, point, deviation);
					
					//Dodawanie osobnika do rozwiązania
					subjectCollection.add(subject);
					
				}
				
			} while( this.calculateQuality(subjectCollection)<0 ); //Losuje tak długo, aż rozwiązanie będzie dopuszczalne
			
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
}
