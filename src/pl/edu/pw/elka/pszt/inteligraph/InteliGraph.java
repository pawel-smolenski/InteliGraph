package pl.edu.pw.elka.pszt.inteligraph;

import pl.edu.pw.elka.pszt.inteligraph.controller.Controller;
import pl.edu.pw.elka.pszt.inteligraph.events.EventsBlockingQueue;
import pl.edu.pw.elka.pszt.inteligraph.model.Model;
import pl.edu.pw.elka.pszt.inteligraph.view.View;

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
    }

}
