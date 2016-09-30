package net.rb.tacitum.events;

/**
 * @author Richard Berqvist
 * @since In-Development 10.5
 * @category Events
 * **/
public class EventDispatcher {
	private Event event;
	
	public EventDispatcher(Event event) {
		this.event = event;
	}
	
	public void dispatch(Event.Type type, EventHandler handler) {
		if (event.handled)
			return;
		
		if (event.getType() == type)
			event.handled = handler.onEvent(event);
	}
}