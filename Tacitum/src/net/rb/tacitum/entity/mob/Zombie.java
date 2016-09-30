package net.rb.tacitum.entity.mob;

import java.util.List;

import net.rb.tacitum.graphics.AnimatedSprite;
import net.rb.tacitum.graphics.Screen;
import net.rb.tacitum.graphics.SpriteSheet;
import net.rb.tacitum.level.Node;
import net.rb.tacitum.util.Vector2i;

/**
 * @author Richard Berqvist
 * @since Pre-Alpha 5.5
 * @category Entities
 * **/
public class Zombie extends Mob {
	private AnimatedSprite zombie_down  = new AnimatedSprite(SpriteSheet.zombie_down, 32, 32, 3);
	private AnimatedSprite zombie_up    = new AnimatedSprite(SpriteSheet.zombie_up, 32, 32, 3);
	private AnimatedSprite zombie_left  = new AnimatedSprite(SpriteSheet.zombie_left, 32, 32, 3);
	private AnimatedSprite zombie_right = new AnimatedSprite(SpriteSheet.zombie_right, 32, 32, 3);
	
	private AnimatedSprite zombieSprite = zombie_down;
	
	private int xa = 0;
	private int ya = 0;
	private int time = 0;
	private List<Node> path = null;
	
	public Zombie(int x, int y) {
		this.x = x << 4;
		this.y = y << 4;
		this.zombieSprite = zombie_down;
	}
	
	private void move() {
		xa = 0;
		ya = 0;
		
		List<Mob> players = level.getPlayers(this, 100);
		if (players.size() > 0) {
			int px = level.getPlayerAt(0).getX();
			int py = level.getPlayerAt(0).getY();
			Vector2i start = new Vector2i(getX() >> 4, getY() >> 4);
			Vector2i goal = new Vector2i(px >> 4, py >> 4);
			if (time  % 3 == 0) path = level.findPath(start, goal);
			if (path != null) {
				if (path.size() > 0) {
					Vector2i vector = path.get(path.size() - 1).tile;
					if (x < vector.getX() << 4) xa++;
					if (x > vector.getX() << 4) xa--;
					if (y < vector.getY() << 4) ya++;
					if (y > vector.getY() << 4) ya--;
				}
			}
		}
		if (xa != 0 || ya != 0) {
			move(xa, ya);
			walking = true;
		} else {
			walking = false;
		}
	}
	
	public void update() {
		time++;
		move();
		if (walking) zombieSprite.update();
		else zombieSprite.setFrame(0);
		this.zombieSprite.setFrameRate(8);
		if (ya < 0) {
			zombieSprite = zombie_up;
			dir = Direction.UP;
		}
		else if (ya > 0) {
			zombieSprite = zombie_down;
			dir = Direction.DOWN;
		}
		if (xa < 0) {
			zombieSprite = zombie_left;
			dir = Direction.LEFT;
		}
		else if (xa > 0) {
			zombieSprite = zombie_right;
			dir = Direction.RIGHT;
		}
	}

	public void render(Screen screen) {
		sprite = zombieSprite.getSprite();
		screen.renderMob(x - 16, y - 16, this);
	}
}