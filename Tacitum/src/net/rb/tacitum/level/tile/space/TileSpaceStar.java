package net.rb.tacitum.level.tile.space;

import net.rb.tacitum.graphics.Screen;
import net.rb.tacitum.graphics.Sprite;
import net.rb.tacitum.level.tile.Tile;

/**
 * @author Richard Berqvist
 * @since Pre-Alpha 6.0
 * @category Tile
 * **/
public class TileSpaceStar extends Tile {
	public TileSpaceStar(Sprite sprite) {
		super(sprite);
	}
	
	public void render(int x, int y, Screen screen) {
		screen.renderTile(x << 4, y << 4, this);
	}
}