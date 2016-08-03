package net.rb.game.entity.projectile;

import java.util.Random;

import net.rb.game.entity.Entity;
import net.rb.game.graphics.Sprite;

public abstract class Projectile extends Entity
{
	protected final int xOrigin, yOrigin;
	protected double angle;
	protected Sprite sprite;
	protected double x, y;
	protected double nx, ny;
	protected double distance;
	protected double speed, range, damage;
	
	protected Random random = new Random();
	
	public Projectile(int x, int y, double dir)
	{
		this.xOrigin = x;
		this.yOrigin = y;
		this.angle = dir;
		this.x = x;
		this.y = y;
	}
	
	public Sprite getSprite()
	{
		return sprite;
	}
	
	public int getSpriteSize()
	{
		return sprite.SIZE;
	}
	
	protected void move()
	{
		
	}
}