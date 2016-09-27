package net.rb.tacitum.entity.mob;

import java.util.List;

import net.rb.tacitum.graphics.AnimatedSprite;
import net.rb.tacitum.graphics.Screen;
import net.rb.tacitum.graphics.SpriteSheet;
import net.rb.tacitum.level.Node;
import net.rb.tacitum.util.Vector2i;

public class Skeleton extends Mob {
	private AnimatedSprite skeleton_down  = new AnimatedSprite(SpriteSheet.skeleton_down, 64, 64, 3);
	private AnimatedSprite skeleton_up    = new AnimatedSprite(SpriteSheet.skeleton_up, 64, 64, 3);
	private AnimatedSprite skeleton_left  = new AnimatedSprite(SpriteSheet.skeleton_left, 64, 64, 3);
	private AnimatedSprite skeleton_right = new AnimatedSprite(SpriteSheet.skeleton_right, 64, 64, 3);
	
	private AnimatedSprite skeletonSprite = skeleton_down;
	
	private int time = 0;
	private int xa = 0;
	private int ya = 0;
	private List<Node> path = null;
	
	private boolean chasingPlayer;
	
	public Skeleton(int x, int y) {
		this.x = x << 4;
		this.y = y << 4;
		this.skeletonSprite = skeleton_down;
		chasingPlayer = false;
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
	
	public void patrol() {
		if (time % (random.nextInt(50) + 30) == 0) {
			xa = random.nextInt(3) - 1;
			ya = random.nextInt(3) - 1;
				
			if (random.nextInt(4) == 0) {
				xa = 0;
				ya = 0;
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
		
		List<Mob> players = level.getPlayers(this, 100);
		if (players.size() > 0) {			
			move();
			chasingPlayer = true;
		} else chasingPlayer = false;
		
		if (!chasingPlayer) 
			patrol();
		
		if (walking) skeletonSprite.update();
		else skeletonSprite.setFrame(0);
		
		this.skeletonSprite.setFrameRate(8);
		if (ya < 0) {
			skeletonSprite = skeleton_up;
			dir = Direction.UP;
		}
		if (ya > 0) {
			skeletonSprite = skeleton_down;
			dir = Direction.DOWN;
		}
		if (xa < 0) {
			skeletonSprite = skeleton_left;
			dir = Direction.LEFT;
		}
		if (xa > 0) {
			skeletonSprite = skeleton_right;
			dir = Direction.RIGHT;
		}
	}

	public void render(Screen screen) {
		sprite = skeletonSprite.getSprite();
		screen.renderMob(x - 16, y - 16, this);
	}
}