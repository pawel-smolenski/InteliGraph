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
    	
    	controller.programStart();
//    	SimpleGraph simpleGraph = new SimpleGraph();
    }
    
}
