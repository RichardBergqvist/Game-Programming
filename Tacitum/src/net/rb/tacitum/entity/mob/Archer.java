package net.rb.tacitum.entity.mob;

import java.util.List;

import net.rb.tacitum.entity.Entity;
import net.rb.tacitum.entity.projectile.ArcherProjectile;
import net.rb.tacitum.graphics.AnimatedSprite;
import net.rb.tacitum.graphics.Screen;
import net.rb.tacitum.graphics.SpriteSheet;
import net.rb.tacitum.util.Vector2i;

public class Archer extends Mob {
	// TODO: Replace with arcer sprites 
	private AnimatedSprite archer_down  = new AnimatedSprite(SpriteSheet.dummy_down, 32, 32, 3);
	private AnimatedSprite archer_up    = new AnimatedSprite(SpriteSheet.dummy_up, 32, 32, 3);
	private AnimatedSprite archer_left  = new AnimatedSprite(SpriteSheet.dummy_left, 32, 32, 3);
	private AnimatedSprite archer_right = new AnimatedSprite(SpriteSheet.dummy_right, 32, 32, 3);
	                       
	private AnimatedSprite archerSprite = archer_down;
	
	private int time = 0;
	private int xa = 0, ya = 0;
	private int fireRate = 0;
	
	public Archer(int x, int y) {
		this.x = x << 4;
		this.y = y << 4;
		this.fireRate = ArcherProjectile.FIRE_RATE;
		this.archerSprite = archer_down;
	}

	public void update() {
		time++;
		if (time % (random.nextInt(50) + 30) == 0) {
			xa = random.nextInt(3) - 1;
			ya = random.nextInt(3) - 1;
			
			if (random.nextInt(4) == 0) {
				xa = 0;
				ya = 0;
			}
		}
		
		if (walking) archerSprite.update();
		else archerSprite.setFrame(0);
		if (fireRate > 0) fireRate--;
		
		this.archerSprite.setFrameRate(8);
		if (ya < 0) {
			archerSprite = archer_up;
			dir = Direction.UP;
		}
		if (ya > 0) {
			archerSprite = archer_down;
			dir = Direction.DOWN;
		}
		if (xa < 0) {
			archerSprite = archer_left;
			dir = Direction.LEFT;
		}
		if (xa > 0) {
			archerSprite = archer_right;
			dir = Direction.RIGHT;
		}
		
		if (xa != 0 || ya != 0) {
			move(xa, ya);
			walking = true;
		} else {
			walking = false;
		}
		
		shootClosest();
	}
	
	private void shootClosest() {
		List<Entity> entities = level.getEntities(this, 700);
		entities.add(level.getClientPlayer());
		
		double min = 0;
		Entity closest = null;
		for (int i = 0; i < entities.size(); i++) {
			Entity e = entities.get(i);
			double distance = Vector2i.getDistance(new Vector2i(x, y), new Vector2i(e.getX(), e.getY()));
			if (i == 0 || distance < min) {
				min = distance;
				closest = e;
			}
		}
		
		if (closest != null && fireRate <= 0) {
			double dx = closest.getX() - x;
			double dy = closest.getY() - y;
			double dir = Math.atan2(dy, dx);
			shoot(x, y, dir, new ArcherProjectile(x, y, dir));
			fireRate = ArcherProjectile.FIRE_RATE;
		}
	}

	public void render(Screen screen) {
		sprite = archerSprite.getSprite();
		screen.renderMob(x - 16, y - 16, this);
	}
}