package pl.edu.pw.pszt.inteligraph.controller;

import java.util.Map;

import pl.edu.pw.pszt.inteligraph.events.Event;
import pl.edu.pw.pszt.inteligraph.events.EventHandler;
import pl.edu.pw.pszt.inteligraph.events.EventName;
import pl.edu.pw.pszt.inteligraph.events.EventsBlockingQueue;
import pl.edu.pw.pszt.inteligraph.events.EventsHandlersMap;
import pl.edu.pw.pszt.inteligraph.model.Model;
import pl.edu.pw.pszt.inteligraph.view.View;

public class Controller {

	EventsBlockingQueue blockingQueue;
	Model model;
	View view;
	EventsHandlersMap eventHandlers = new EventsHandlersMap();
	
	public Controller(Model model, View view, EventsBlockingQueue blockingQueue) {
		this.model = model;
		this.view = view;
		this.blockingQueue = blockingQueue;
		
		this.loadHandlers();
		
		try {
			blockingQueue.put(new Event(EventName.SHOW_EMPTY_WINDOW));
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Ładuje handlery do obsługi zdarzeń
	 */
	private void loadHandlers() {
		eventHandlers.put(EventName.SHOW_EMPTY_WINDOW, new EventHandler() {
			
			@Override
			public void execute() {
				view.showWindow();
			}
		});		
	}

	/**
	 * Główna pętla programu obsługująca kolejkę zdarzeń
	 */
	public void programStart() {
		
		Event event;
		EventHandler eventHandler;
		
		while(true)
		{
			try {
				event = this.blockingQueue.take();
				eventHandler = eventHandlers.get(event.getEventName());
				eventHandler.execute();
				
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}

}
