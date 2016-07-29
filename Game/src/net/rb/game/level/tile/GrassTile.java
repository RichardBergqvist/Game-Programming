package net.rb.game.level.tile;

import net.rb.game.graphics.Screen;
import net.rb.game.graphics.Sprite;

public class GrassTile extends Tile
{
	public GrassTile(Sprite sprite)
	{
		super(sprite);
	}
	
	public void render(int x, int y, Screen screen)
	{
		screen.renderTile(x << 4, y << 4, this);
	}
}