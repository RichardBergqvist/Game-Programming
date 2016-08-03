package net.rb.game.level.tile.spawn_level;

import net.rb.game.graphics.Screen;
import net.rb.game.graphics.Sprite;
import net.rb.game.level.tile.Tile;

public class SpawnHedgeTile extends Tile
{
	public SpawnHedgeTile(Sprite sprite)
	{
		super(sprite);
	}
	
	public void render(int x, int y, Screen screen)
	{
		screen.renderTile(x << 4, y << 4, this);
	}
	
	public boolean isSolid()
	{
		return true;
	}
	
	public boolean isBreakable()
	{
		return true;
	}
}