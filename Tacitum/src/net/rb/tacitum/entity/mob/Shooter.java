package net.rb.tacitum.entity.mob;

import java.util.List;

import net.rb.tacitum.entity.Entity;
import net.rb.tacitum.entity.particle.Particle;
import net.rb.tacitum.entity.projectile.Projectile;
import net.rb.tacitum.entity.projectile.WizardProjectile;
import net.rb.tacitum.entity.projectile.WizardProjectile.Type;
import net.rb.tacitum.graphics.AnimatedSprite;
import net.rb.tacitum.graphics.Screen;
import net.rb.tacitum.graphics.SpriteSheet;
import net.rb.tacitum.util.Vector2i;

/**
 * @author Richard Berqvist
 * @since In-Development 5.5
 * @category Entities
 * **/
public class Shooter extends Mob {
	private AnimatedSprite shooter_down  = new AnimatedSprite(SpriteSheet.player_down, 32, 32, 3);
	private AnimatedSprite shooter_up    = new AnimatedSprite(SpriteSheet.player_up, 32, 32, 3);
	private AnimatedSprite shooter_left  = new AnimatedSprite(SpriteSheet.player_left, 32, 32, 3);
	private AnimatedSprite shooter_right = new AnimatedSprite(SpriteSheet.player_right, 32, 32, 3);
	                       
	private AnimatedSprite shooterSprite = shooter_down;
	
	private int time = 0;
	private int xa = 0, ya = 0;
	private int fireRate = 0;
	
	private Entity rand = null;
	
	public Shooter(int x, int y) {
		this.x = x << 4;
		this.y = y << 4;
		this.fireRate = WizardProjectile.FIRE_RATE;
		this.shooterSprite = shooter_down;
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
		
		if (walking) shooterSprite.update();
		else shooterSprite.setFrame(0);
		if (fireRate > 0) fireRate--;
		
		this.shooterSprite.setFrameRate(8);
		if (ya < 0) {
			shooterSprite = shooter_up;
			dir = Direction.UP;
		}
		if (ya > 0) {
			shooterSprite = shooter_down;
			dir = Direction.DOWN;
		}
		if (xa < 0) {
			shooterSprite = shooter_left;
			dir = Direction.LEFT;
		}
		if (xa > 0) {
			shooterSprite = shooter_right;
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
	
	private void shootRandom(){
		List<Entity> entities = level.getEntities(this, 500);
		entities.add(level.getClientPlayer());
		if (time % (30 + random.nextInt(91)) == 0) {
			int index = random.nextInt(entities.size());
			rand = entities.get(index);
		}
		
		if (rand != null && rand instanceof Projectile || rand instanceof Particle && fireRate <= 0) {
			double dx = rand.getX() - x;
			double dy = rand.getY() - y;
			double dir = Math.atan2(dy, dx);
			shoot(x, y, dir, new WizardProjectile(x, y, dir, Type.EARTH));
			fireRate = WizardProjectile.FIRE_RATE;
		}
	}

	private void shootClosest() {
		List<Entity> entities = level.getEntities(this, 500);
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
			if (!(closest instanceof Projectile) && !((closest) instanceof Particle)) {
				double dx = closest.getX() - x;
				double dy = closest.getY() - y;
				double dir = Math.atan2(dy, dx);
				shoot(x, y, dir, new WizardProjectile(x, y, dir, Type.EARTH));
				fireRate = WizardProjectile.FIRE_RATE;
			}
		}
	}
	
	public void render(Screen screen) {
		sprite = shooterSprite.getSprite();
		screen.renderMob(x - 16, y - 16, this);
	}
}