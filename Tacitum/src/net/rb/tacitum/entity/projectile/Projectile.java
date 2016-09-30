package net.rb.tacitum.entity.projectile;

import java.util.Random;

import net.rb.tacitum.entity.Entity;
import net.rb.tacitum.graphics.Sprite;

/**
 * @author Richard Berqvist
 * @since In-Development 8.5
 * @category Entities
 * **/
public abstract class Projectile extends Entity {
	protected Sprite sprite;
	protected final double xOrigin, yOrigin;
	protected double angle;
	protected double x, y;
	protected double nx, ny;
	protected double distance;
	protected double speed, range, damage;
	
	protected Random random = new Random();
	
	public Projectile(double x, double y, double dir) {
		this.xOrigin = x;
		this.yOrigin = y;
		this.angle = dir;
		this.x = x;
		this.y = y;
	}
	
	public Sprite getSprite() {
		return sprite;
	}
	
	public int getSpriteSize() {
		return sprite.SIZE;
	}
	
	protected void move() {
		
	}
}