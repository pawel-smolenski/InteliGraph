package pl.edu.pw.elka.pszt.inteligraph.controller;

import pl.edu.pw.elka.pszt.inteligraph.events.EventsBlockingQueue;
import pl.edu.pw.elka.pszt.inteligraph.model.Model;
import pl.edu.pw.elka.pszt.inteligraph.view.View;

public class Controller {

	EventsBlockingQueue blockingQueue;
	Model model;
	View view;
	
	public Controller(Model model, View view, EventsBlockingQueue blockingQueue) {
		this.model = model;
		this.view = view;
		this.blockingQueue = blockingQueue;
	}

	public void programStart() {
		view.showWindow();
	}

}
