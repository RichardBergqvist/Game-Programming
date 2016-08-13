package net.rb.tacitum.net.player;

import net.rb.tacitum.entity.mob.Mob;
import net.rb.tacitum.graphics.Screen;

public class PlayerMP extends Mob {
	public PlayerMP() {
		this.x = 22 * 16;
		this.y = 42 * 16;
	}
	
	public void update() {
		
	}

	public void render(Screen screen) {
		screen.fillRect(x, y, 32, 32, 0x2030CC, true);
	}
}