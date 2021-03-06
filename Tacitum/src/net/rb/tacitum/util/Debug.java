package net.rb.tacitum.util;

import net.rb.tacitum.graphics.Screen;

/**
 * @author Richard Bergqvist
 * @since In-Development 2.0
 * @category Utils
 */
public class Debug {
	private Debug() {
		
	}
	
	public static void drawRect(Screen screen, int x, int y, int width, int height, boolean fixed) {
		drawRect(screen, x, y, width, height, 0xFF0000, fixed);
	}
	
	public static void drawRect(Screen screen, int x, int y, int width, int height, int color, boolean fixed) {
		screen.drawRect(x, y, width, height, color, fixed);
	}
}