package net.rb.tacitum.events.types;

import net.rb.tacitum.events.Event;

/**
 * @author Richard Berqvist
 * @since In-Development 10.5
 * @category Events
 * **/
public class MouseReleasedEvent extends MouseButtonEvent {
	public MouseReleasedEvent(int button, int x, int y) {
		super(button, x, y, Event.Type.MOUSE_RELEASED);
	}
}
