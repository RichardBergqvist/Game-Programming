package net.rb.tacitum.events.types;

import net.rb.tacitum.events.Event;

/**
 * @author Richard Berqvist
 * @since In-Development 10.5
 * @category Events
 * **/
public class MouseMovedEvent extends Event {
	private int x, y;
	private boolean dragged;
	
	public MouseMovedEvent(int x, int y, boolean dragged) {
		super(Event.Type.MOUSE_MOVED);
		this.x = x;
		this.y = y;
		this.dragged = dragged;
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public boolean getDragged() {
		return dragged;
	}
}
