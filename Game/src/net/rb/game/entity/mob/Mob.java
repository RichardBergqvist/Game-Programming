package net.rb.game.entity.mob;

import net.rb.game.entity.Entity;
import net.rb.game.entity.projectile.Projectile;
import net.rb.game.entity.projectile.WizardProjectile;
import net.rb.game.graphics.Screen;
import net.rb.game.graphics.Sprite;

public abstract class Mob extends Entity
{
	protected Sprite sprite;
	protected int dir = 0;
	protected boolean moving = false;
	protected boolean walking = false;
	
	public void move(int xa, int ya)
	{
		if (xa != 0 && ya != 0)
		{
			move (xa, 0);
			move (0, ya);
			return;
		}
		
		/**
        - Side: Direction 1 & 3 [1 = right, 3 = left]
		- Up:   Direction 0
		- Down: Direction 2
		**/
		
		if (xa > 0) dir = 1;
		if (xa < 0) dir = 3;
		if (ya > 0) dir = 2;
		if (ya < 0) dir = 0;
		
		if (!collision(xa, ya))
		{
			x += xa;
			y += ya;
		}
	}
	
	public abstract void update();
	public abstract void render(Screen screen);
	
	protected void shoot(int x, int y, double dir)
	{
		Projectile p = new WizardProjectile(x, y, dir);
		level.add(p);
	}
	 
	private boolean collision(int xa, int ya)
	{
		boolean solid = false;
		for (int c = 0; c < 4; c++)
		{
			int xt = ((x + xa) + c % 2 * 14 - 8) / 16;
			int yt = ((y + ya) + c / 2 * 12 + 3) / 16;
			if (level.getTile(xt, yt).isSolid()) solid = true;
		}
		return solid;
	}
}
