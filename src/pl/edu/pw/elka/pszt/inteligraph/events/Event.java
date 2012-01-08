package pl.edu.pw.elka.pszt.inteligraph.events;

public class Event {
	
	EventName eventName;
	
	public Event(EventName eventName) {
		this.eventName = eventName;
	}
	
	public Object getEventName() {
		return eventName;
	}

}
