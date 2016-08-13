package net.rb.tacitum.entity.mob;

import net.rb.tacitum.graphics.AnimatedSprite;
import net.rb.tacitum.graphics.Screen;
import net.rb.tacitum.graphics.SpriteSheet;

public class Dummy extends Mob {
	private AnimatedSprite dummy_down  = new AnimatedSprite(SpriteSheet.dummy_down, 32, 32, 3);
	private AnimatedSprite dummy_up    = new AnimatedSprite(SpriteSheet.dummy_up, 32, 32, 3);
	private AnimatedSprite dummy_left  = new AnimatedSprite(SpriteSheet.dummy_left, 32, 32, 3);
	private AnimatedSprite dummy_right = new AnimatedSprite(SpriteSheet.dummy_right, 32, 32, 3);
	
	private AnimatedSprite dummySprite = dummy_down;
	
	private int time = 0;
	private int xa = 0;
	private int ya = 0;
	
	public Dummy(int x, int y) {
		this.x = x << 4;
		this.y = y << 4;
		this.dummySprite = dummy_down;
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
		
		if (walking) dummySprite.update();
		else dummySprite.setFrame(0);
		
		this.dummySprite.setFrameRate(8);
		if (ya < 0) {
			dummySprite = dummy_up;
			dir = Direction.UP;
		}
		if (ya > 0) {
			dummySprite = dummy_down;
			dir = Direction.DOWN;
		}
		if (xa < 0) {
			dummySprite = dummy_left;
			dir = Direction.LEFT;
		}
		if (xa > 0) {
			dummySprite = dummy_right;
			dir = Direction.RIGHT;
		}
		
		if (xa != 0 || ya != 0) {
			move(xa, ya);
			walking = true;
		} else {
			walking = false;
		}
	}

	public void render(Screen screen) {
		sprite = dummySprite.getSprite();
		screen.renderMob(x - 16, y - 16, this);
	}
}