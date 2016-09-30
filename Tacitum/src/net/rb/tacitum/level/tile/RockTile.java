package net.rb.tacitum.level.tile;

import net.rb.tacitum.graphics.Screen;
import net.rb.tacitum.graphics.Sprite;

/**
 * @author Richard Berqvist
 * @since In-Development 6.2
 * @category Tile
 * **/
public class RockTile extends Tile {
	public RockTile(Sprite sprite) {
		super(sprite);
	}
	
	public void render(int x, int y, Screen screen) {
		screen.renderTile(x << 4, y << 4, this);
	}
}
