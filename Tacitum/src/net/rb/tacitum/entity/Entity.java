package net.rb.tacitum.entity;

import java.util.Random;

import net.rb.tacitum.graphics.Screen;
import net.rb.tacitum.graphics.Sprite;
import net.rb.tacitum.level.Level;

/**
 * @author Richard Berqvist
 * @since In-Development 5.5
 * @category Entities
 * **/
public class Entity {
	protected int x, y;
	protected Sprite sprite;
	protected Level level;
	protected final Random random = new Random();
	private boolean removed = false;
	
	public Entity() {
		
	}
	
	public Entity(int x, int y, Sprite sprite) {
		this.x = x;
		this.y = y;
		this.sprite = sprite;
	}
	
	public void update() {
		
	}
	
	public void render(Screen screen) {
		if (sprite != null) screen.renderSprite(x, y, sprite, true);
	}
	
	public void remove() {
		removed = true;
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public Sprite getSprite() {
		return sprite;
	}
	
	public boolean isRemoved() {
		return removed;
	}
	
	public void init(Level level) {
		this.level = level;
	}
}