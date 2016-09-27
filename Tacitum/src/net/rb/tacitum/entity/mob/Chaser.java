package net.rb.tacitum.entity.mob;

import java.util.List;

import net.rb.tacitum.graphics.AnimatedSprite;
import net.rb.tacitum.graphics.Screen;
import net.rb.tacitum.graphics.SpriteSheet;

public class Chaser extends Mob {
	private AnimatedSprite chaser_down  = new AnimatedSprite(SpriteSheet.player_down, 32, 32, 3);
	private AnimatedSprite chaser_up    = new AnimatedSprite(SpriteSheet.player_up, 32, 32, 3);
	private AnimatedSprite chaser_left  = new AnimatedSprite(SpriteSheet.player_left, 32, 32, 3);
	private AnimatedSprite chaser_right = new AnimatedSprite(SpriteSheet.player_right, 32, 32, 3);
	                       
	private AnimatedSprite chaserSprite = chaser_down;
	
	private int xa = 0;
	private int ya = 0;
	
	public Chaser(int x, int y) {
		this.x = x << 4;
		this.y = y << 4;
		this.chaserSprite = chaser_down;
	}
	
	private void move() {
		xa = 0;
		ya = 0;
		
		List<Mob> players = level.getPlayers(this, 100);
		if (players.size() > 0) {
			Mob player = players.get(0); 
			if (x < player.getX()) xa++;
			if (x > player.getX()) xa--;
			if (y < player.getY()) ya++;
			if (y > player.getY()) ya--;
		}
		
		if (xa != 0 || ya != 0) {
			move(xa, ya);
			walking = true;
		} else {
			walking = false;
		}
	}
	
	public void update() {	
		move();
		if (walking) chaserSprite.update();
		else chaserSprite.setFrame(0);
		this.chaserSprite.setFrameRate(8);
		if (ya < 0) {
			chaserSprite = chaser_up;
			dir = Direction.UP;
		}
		else if (ya > 0) {
			chaserSprite = chaser_down;
			dir = Direction.DOWN;
		}
		if (xa < 0) {
			chaserSprite = chaser_left;
			dir = Direction.LEFT;
		}
		else if (xa > 0) {
			chaserSprite = chaser_right;
			dir = Direction.RIGHT;
		}
	}

	public void render(Screen screen) {
		sprite = chaserSprite.getSprite();
		screen.renderMob(x - 16, y - 16, this);
	}
}