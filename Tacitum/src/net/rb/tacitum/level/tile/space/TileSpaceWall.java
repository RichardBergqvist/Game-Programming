package net.rb.tacitum.level.tile.space;

import net.rb.tacitum.graphics.Screen;
import net.rb.tacitum.graphics.Sprite;
import net.rb.tacitum.level.tile.Tile;

public class TileSpaceWall extends Tile {
	public TileSpaceWall(Sprite sprite) {
		super(sprite);
	}
	
	public void render(int x, int y, Screen screen) {
		screen.renderTile(x << 4, y << 4, this);
	}
	
	public boolean isSolid(){
		return true;
	}
}