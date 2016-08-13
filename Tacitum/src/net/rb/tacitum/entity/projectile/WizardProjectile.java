package net.rb.tacitum.entity.projectile;

import net.rb.tacitum.entity.spawner.ParticleSpawner;
import net.rb.tacitum.graphics.Screen;
import net.rb.tacitum.graphics.Sprite;

public class WizardProjectile extends Projectile {
	public static final int FIRE_RATE = 20;
	
	public WizardProjectile(double x, double y, double dir) {
		super(x, y, dir);
		range = 200;
		speed = 5;
		damage = 20;
		sprite = Sprite.wizard;
		
		nx = speed * Math.cos(angle);
		ny = speed * Math.sin(angle);
	}
	
	public void update() {
		if (level.tileCollision((int) (x + nx), (int) (y + ny), 8, 4, 4)) {
			level.add(new ParticleSpawner((int) x, (int) y, 30, 30, level, Sprite.particle_water));
			level.add(new ParticleSpawner((int) x, (int) y, 30, 20, level, Sprite.particle_water1));
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
}