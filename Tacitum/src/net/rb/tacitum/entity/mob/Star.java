package net.rb.tacitum.entity.mob;

import java.util.List;

import net.rb.tacitum.graphics.AnimatedSprite;
import net.rb.tacitum.graphics.Screen;
import net.rb.tacitum.graphics.SpriteSheet;
import net.rb.tacitum.level.Node;
import net.rb.tacitum.util.Vector2i;

public class Star extends Mob {
	private AnimatedSprite star_down  = new AnimatedSprite(SpriteSheet.player_down, 32, 32, 3);
	private AnimatedSprite star_up    = new AnimatedSprite(SpriteSheet.player_up, 32, 32, 3);
	private AnimatedSprite star_left  = new AnimatedSprite(SpriteSheet.player_left, 32, 32, 3);
	private AnimatedSprite star_right = new AnimatedSprite(SpriteSheet.player_right, 32, 32, 3);
	                       
	private AnimatedSprite starSprite = star_down;
	
	private int xa = 0;
	private int ya = 0;
	private int time = 0;
	private List<Node> path = null;
	
	public Star(int x, int y) {
		this.x = x << 4;
		this.y = y << 4;
		this.starSprite = star_down;
	}
	
	private void move() {
		xa = 0;
		ya = 0;
		
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
		if (walking) starSprite.update();
		else starSprite.setFrame(0);
		this.starSprite.setFrameRate(8);
		if (ya < 0) {
			starSprite = star_up;
			dir = Direction.UP;
		} else if (ya > 0) {
			starSprite = star_down;
			dir = Direction.DOWN;
		}
		if (xa < 0) {
			starSprite = star_left;
			dir = Direction.LEFT;
		} else if (xa > 0) {
			starSprite = star_right;
			dir = Direction.RIGHT;
		}
	}

	public void render(Screen screen) {
		sprite = starSprite.getSprite();
		screen.renderMob(x - 16, y - 16, this);
	}
}