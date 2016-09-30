package net.rb.tacitum.entity.projectile;

import net.rb.tacitum.entity.spawner.ParticleSpawner;
import net.rb.tacitum.graphics.Screen;
import net.rb.tacitum.graphics.Sprite;

/**
 * @author Richard Berqvist
 * @since In-Development 8.5
 * @category Entities
 * **/
public class WizardProjectile extends Projectile {
	public static final int FIRE_RATE = 20;
	private Type type;
	
	public WizardProjectile(double x, double y, double dir, Type type) {
		super(x, y, dir);
		range = 200;
		speed = 5;
		damage = 20;
		sprite = type.getSprite();
		this.type = type;
		if (type.getSprite() == Sprite.wizard_fire) sprite = Sprite.rotate(type.getSprite(), angle);
		
		nx = speed * Math.cos(angle);
		ny = speed * Math.sin(angle);
	}
	
	// TODO: Fix different particles for different projectiles
	public void update() {
		if (level.tileCollision((int) (x + nx), (int) (y + ny), 8, 4, 4)) {
			if (type == Type.WATER) {
				level.add(new ParticleSpawner((int) x, (int) y, 30, 30, level, Sprite.particle_water));
				level.add(new ParticleSpawner((int) x, (int) y, 30, 20, level, Sprite.particle_water1));
			}
			if (type == Type.FIRE) {
				level.add(new ParticleSpawner((int) x, (int) y, 30, 30, level, Sprite.particle_fire));
				level.add(new ParticleSpawner((int) x, (int) y, 30, 20, level, Sprite.particle_fire1));
			}
			if (type == Type.AIR) {
				
			}
			if (type == Type.EARTH) {
				
			}
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
	
	public enum Type {
		WATER("water", Sprite.wizard_water, 7),
		FIRE("fire", Sprite.wizard_fire, 3),
		AIR("air", Sprite.wizard_air, 1),
		EARTH("earth", Sprite.wizard_earth, 4);
		
		private String name;
		private Sprite sprite;
		private int cost;
		
		Type(String name, Sprite sprite, int cost) {
			this.name = name;
			this.sprite = sprite;
			this.cost = cost;
		}
		
		public String getName() {
			return name;
		}
		
		public Sprite getSprite() {
			return sprite;
		}
		
		public int getCost() {
			return cost;
		}
	}
}