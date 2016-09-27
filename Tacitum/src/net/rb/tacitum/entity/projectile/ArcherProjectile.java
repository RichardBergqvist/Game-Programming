package net.rb.tacitum.entity.projectile;

import net.rb.tacitum.graphics.Screen;
import net.rb.tacitum.graphics.Sprite;

public class ArcherProjectile extends Projectile {
	public static final int FIRE_RATE = 80;
	
	public ArcherProjectile(double x, double y, double dir, Type type) {
		super(x, y, dir);
		range = 400;
		speed = 7;
		damage = 80;
		sprite = Sprite.rotate(type.getSprite(), angle);
		
		nx = speed * Math.cos(angle);
		ny = speed * Math.sin(angle);
	}
	
	public void update() {
		if (level.tileCollision((int) (x + nx), (int) (y + ny), 8, 4, 4)) {
			remove();
		}
		move();
	}
	
	protected void move() {
		x += nx;
		y += ny;	
		
		if (getDistance() > range) remove();
	}
	
	private double getDistance() {
		double distance = 0;
		distance = Math.sqrt(Math.abs((xOrigin - x) * (xOrigin - x) + (yOrigin - y) * (yOrigin - y)));
		return distance;
	}
	
	public void render(Screen screen) {
		screen.renderProjectile((int) x - 11, (int) y - 2, this);
	}
	
	// TODO: Add different sprites
	public enum Type {
		WOOD("water", Sprite.archer),
		FIRE("fire", Sprite.archer),
		POISON("air", Sprite.archer);
		
		private String name;
		private Sprite sprite;
		
		Type(String name, Sprite sprite) {
			this.name = name;
			this.sprite = sprite;
		}
		
		public String getName() {
			return name;
		}
		
		public Sprite getSprite() {
			return sprite;
		}
	}
}