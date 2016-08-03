package net.rb.game.entity.mob;

import net.rb.game.Game;
import net.rb.game.entity.projectile.Projectile;
import net.rb.game.entity.projectile.WizardProjectile;
import net.rb.game.graphics.AnimatedSprite;
import net.rb.game.graphics.Screen;
import net.rb.game.graphics.Sprite;
import net.rb.game.graphics.SpriteSheet;
import net.rb.game.input.Keyboard;
import net.rb.game.input.Mouse;

public class Player extends Mob
{
	private Keyboard input;
	private Sprite sprite;
	private boolean walking = false;
	private AnimatedSprite down  = new AnimatedSprite(SpriteSheet.player_down, 32, 32, 3);
	private AnimatedSprite up    = new AnimatedSprite(SpriteSheet.player_up, 32, 32, 3);
	private AnimatedSprite left  = new AnimatedSprite(SpriteSheet.player_left, 32, 32, 3);
	private AnimatedSprite right = new AnimatedSprite(SpriteSheet.player_right, 32, 32, 3);
	
	private AnimatedSprite playerSprite = down;
	
	private int fireRate = 0;
	
	public Player(Keyboard input )
	{
		this.input = input;
		this.fireRate = WizardProjectile.FIRE_RATE;
		this.playerSprite = down;
	}
	
	public Player(int x, int y, Keyboard input)
	{
		this.x = x;
		this.y = y;
		this.input = input;
		this.fireRate = WizardProjectile.FIRE_RATE;
		this.playerSprite = down;
	}
	
	public void update()
	{
		if (walking) playerSprite.update();
		else playerSprite.setFrame(0);
		if (fireRate > 0) fireRate--;
		int xa = 0, ya = 0;
	
		playerSprite.setFrameRate(8);
		if (input.up)
		{
			playerSprite = up;
			ya--;
		}
		
		if (input.down)
		{
			playerSprite = down;
			ya++;
		}
		
		if (input.left)
		{
			playerSprite = left;
			xa--;
		}
		
		if (input.right)
		{
			playerSprite = right;
			xa++;
		}
		
		if (xa != 0 || ya != 0)
		{
			move(xa, ya);
			walking = true;
		} else {
			walking = false;
		}
		
		clear();
		updateShooting();
	}
	
	private void clear()
	{
		for (int i = 0; i < level.getProjectiles().size(); i++)
		{
			Projectile p = level.getProjectiles().get(i);
			if (p.isRemoved()) level.getProjectiles().remove(i);
		}
	}

	private void updateShooting()
	{
		if (Mouse.getButton() == 1 && fireRate <= 0)
		{
			double dx = Mouse.getX() - Game.getWindowWidth() / 2;
			double dy = Mouse.getY() - Game.getWindowHeight() / 2;
			double dir = Math.atan2(dy, dx);
			shoot(x, y, dir);
			fireRate = WizardProjectile.FIRE_RATE;
		}
	}
	
	public void render(Screen screen)
	{
		sprite = playerSprite.getSprite();
		screen.renderPlayer(x - 16, y - 16, sprite);
	}
}