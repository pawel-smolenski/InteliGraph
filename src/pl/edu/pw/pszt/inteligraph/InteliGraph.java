package pl.edu.pw.pszt.inteligraph;

import pl.edu.pw.pszt.inteligraph.controller.Controller;
import pl.edu.pw.pszt.inteligraph.events.EventsBlockingQueue;
import pl.edu.pw.pszt.inteligraph.model.Model;
import pl.edu.pw.pszt.inteligraph.view.View;
import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.SparseMultigraph;
import edu.uci.ics.jung.graph.util.EdgeType;


public class InteliGraph {

    /**
     * @param args
     */
    public static void main(String[] args) {
    	EventsBlockingQueue blockingQueue = new EventsBlockingQueue();
    	Model model = new Model();
    	View view = new View(blockingQueue);
    	Controller controller = new Controller(model, view, blockingQueue);
    	
//    	controller.programStart();
    	SimpleGraph simpleGraph = new SimpleGraph();
    }
    
    public void jung()
    {
    	 Graph<Integer, String> g = new SparseMultigraph<Integer, String>();
    			 
    	 g.addVertex((Integer)1);
         g.addVertex((Integer)2);
         g.addVertex((Integer)3); 
         // Add some edges. From above we defined these to be of type String
         // Note that the default is for undirected edges.
         g.addEdge("Edge-A", 1, 2); // Note that Java 1.5 auto-boxes primitives
         g.addEdge("Edge-B", 2, 3);       
         // Let's see what we have. Note the nice output from the
         // SparseMultigraph<V,E> toString() method
         System.out.println("The graph g = " + g.toString());
         // Note that we can use the same nodes and edges in two different graphs.
         Graph<Integer, String> g2 = new SparseMultigraph<Integer, String>();
         g2.addVertex((Integer)1);
         g2.addVertex((Integer)2);
         g2.addVertex((Integer)3); 
         g2.addEdge("Edge-A", 1,3);
         g2.addEdge("Edge-B", 2,3, EdgeType.DIRECTED);
         g2.addEdge("Edge-C", 3, 2, EdgeType.DIRECTED);
         g2.addEdge("Edge-P", 2,3); // A parallel edge
         System.out.println("The graph g2 = " + g2.toString());  
    }

}
