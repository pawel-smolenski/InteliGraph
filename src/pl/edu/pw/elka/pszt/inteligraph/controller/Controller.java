package pl.edu.pw.elka.pszt.inteligraph.controller;

import java.awt.Point;
import java.awt.geom.Point2D;
import java.util.Collection;
import java.util.Random;

import pl.edu.pw.elka.pszt.inteligraph.Constans;
import pl.edu.pw.elka.pszt.inteligraph.events.Event;
import pl.edu.pw.elka.pszt.inteligraph.events.EventHandler;
import pl.edu.pw.elka.pszt.inteligraph.events.EventName;
import pl.edu.pw.elka.pszt.inteligraph.events.EventsBlockingQueue;
import pl.edu.pw.elka.pszt.inteligraph.events.EventsHandlersMap;
import pl.edu.pw.elka.pszt.inteligraph.model.Arrangement;
import pl.edu.pw.elka.pszt.inteligraph.model.Model;
import pl.edu.pw.elka.pszt.inteligraph.model.VertexName;
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
				view.getGraphParametersPanel().setStopButtonActive(false);
			}
		});
		
		eventHandlers.put(EventName.DRAW_GRAPH_N, new EventHandler() {
		    
		    @Override
		    public void execute() {
			view.getGraphParametersPanel().setStopButtonActive(true);
			model.calculateVerticesPositions(view.getMi(), view.getLambda(), view.getSteps());
			view.getStatusBar().setAppState(Constans.STATE_COMPUTING);
		    }
		});
		
		eventHandlers.put(EventName.DRAW_GRAPH_INF, new EventHandler() {
		    
		    @Override
		    public void execute() {
			view.getGraphParametersPanel().setStopButtonActive(true);
			model.calculateVerticesPositions(view.getMi(), view.getLambda());	
			view.getStatusBar().setAppState(Constans.STATE_COMPUTING);
		    }
		});
		
		eventHandlers.put(EventName.DRAW_GRAPH_STOP, new EventHandler() {
		    
		    @Override
		    public void execute() {
			view.getGraphParametersPanel().setStopButtonActive(false);
			model.stopCalculations();
			model.getEvolutionSteps();
			view.getStatusBar().setAppState(Constans.STATE_COMPUTING_ENDED);
			view.getStatusBar().setEvolutionSteps(model.getEvolutionSteps());
		    }
		});
		eventHandlers.put(EventName.CHOOSE_FILE, new EventHandler() {
		    
		    @Override
		    public void execute() {
			model.buildGraph(view.getGraphFile());
			view.getGraphParametersPanel().setStopButtonActive(false);
			view.getStatusBar().setAppState(view.getGraphFile().getAbsolutePath());
			
			model.calculateVerticesPositions(5, 10);
			try
			{
				//model.getBestArrangement();
				view.setGraphView(model.getGraph(), model.getBestArrangement());
			} catch (Exception e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
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
