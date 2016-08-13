package net.rb.tacitum.events.types;

import net.rb.tacitum.events.Event;

public class MouseReleasedEvent extends MouseButtonEvent {
	public MouseReleasedEvent(int button, int x, int y) {
		super(button, x, y, Event.Type.MOUSE_RELEASED);
	}
}
