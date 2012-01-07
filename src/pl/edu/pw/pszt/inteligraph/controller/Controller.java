package pl.edu.pw.pszt.inteligraph.controller;

import java.util.concurrent.BlockingQueue;

import pl.edu.pw.pszt.inteligraph.events.Event;
import pl.edu.pw.pszt.inteligraph.events.EventsBlockingQueue;
import pl.edu.pw.pszt.inteligraph.model.Model;
import pl.edu.pw.pszt.inteligraph.view.View;

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
