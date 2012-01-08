package pl.edu.pw.elka.pszt.inteligraph.controller;

import java.awt.BorderLayout;
import java.util.Map;

import pl.edu.pw.elka.pszt.inteligraph.events.Event;
import pl.edu.pw.elka.pszt.inteligraph.events.EventHandler;
import pl.edu.pw.elka.pszt.inteligraph.events.EventName;
import pl.edu.pw.elka.pszt.inteligraph.events.EventsBlockingQueue;
import pl.edu.pw.elka.pszt.inteligraph.events.EventsHandlersMap;
import pl.edu.pw.elka.pszt.inteligraph.model.Model;
import pl.edu.pw.elka.pszt.inteligraph.view.GraphView;
import pl.edu.pw.elka.pszt.inteligraph.view.View;

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
		
		eventHandlers.put(EventName.DRAW_GRAPH_N, new EventHandler() {
		    
		    @Override
		    public void execute() {
			// TODO Auto-generated method stub
			
		    }
		});
		
		eventHandlers.put(EventName.DRAW_GRAPH_INF, new EventHandler() {
		    
		    @Override
		    public void execute() {
			// TODO Auto-generated method stub
			
		    }
		});
		
		eventHandlers.put(EventName.DRAW_GRAPH_STOP, new EventHandler() {
		    
		    @Override
		    public void execute() {
			// TODO Auto-generated method stub
			
		    }
		});
		eventHandlers.put(EventName.CHOOSE_FILE, new EventHandler() {
		    
		    @Override
		    public void execute() {
			model.buildGraph(view.getGraphFile());
			view.setGraphView(model.getGraph(), null);			
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
