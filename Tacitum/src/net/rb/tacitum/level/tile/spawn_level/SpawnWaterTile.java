package net.rb.tacitum.level.tile.spawn_level;

import net.rb.tacitum.graphics.Screen;
import net.rb.tacitum.graphics.Sprite;
import net.rb.tacitum.level.tile.Tile;

/**
 * @author Richard Berqvist
 * @since In-Development 6.3
 * @category Tile
 * **/
public class SpawnWaterTile extends Tile {
	public SpawnWaterTile(Sprite sprite) {
		super(sprite);
	}
	
	public void render(int x, int y, Screen screen) {
		screen.renderTile(x << 4, y << 4, this);
	}
}