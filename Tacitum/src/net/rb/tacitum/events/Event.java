package net.rb.tacitum.events;

/**
 * @author Richard Berqvist
 * @since In-Development 10.5
 * @category Events
 * **/
public class Event {
	public enum Type {
		MOUSE_PRESSED,
		MOUSE_RELEASED,
		MOUSE_MOVED;
	}
	
	private Type type;
	boolean handled;
	
	protected Event(Type type) {
		this.type = type;
	}
	
	public Type getType() {
		return type;
	}
}