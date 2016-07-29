package net.rb.game.level.tile;

import net.rb.game.graphics.Screen;
import net.rb.game.graphics.Sprite;

public class RockTile extends Tile
{
	public RockTile(Sprite sprite)
	{
		super(sprite);
	}
	
	public void render(int x, int y, Screen screen)
	{
		screen.renderTile(x << 4, y << 4, this);
	}
}
