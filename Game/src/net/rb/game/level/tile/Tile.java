package net.rb.game.level.tile;

import net.rb.game.graphics.Screen;
import net.rb.game.graphics.Sprite;
import net.rb.game.level.tile.spawn_level.SpawnFloorTile;
import net.rb.game.level.tile.spawn_level.SpawnGrassTile;
import net.rb.game.level.tile.spawn_level.SpawnHedgeTile;
import net.rb.game.level.tile.spawn_level.SpawnWallTile;
import net.rb.game.level.tile.spawn_level.SpawnWaterTile;

public class Tile
{
	public int x, y;
	public Sprite sprite;
	
	public static Tile void_tile		= new VoidTile(Sprite.voidTile);
	public static Tile grass			= new GrassTile(Sprite.grass);
	public static Tile flower			= new FlowerTile(Sprite.flower);
	public static Tile rock			    = new RockTile(Sprite.rock);
	
	public static Tile spawn_grass_tile = new SpawnGrassTile(Sprite.spawn_grass);
	public static Tile spawn_hedge_tile = new SpawnHedgeTile(Sprite.spawn_hedge);
	public static Tile spawn_water_tile = new SpawnWaterTile(Sprite.spawn_water);
	public static Tile spawn_wall1_tile = new SpawnWallTile(Sprite.spawn_wall1);
	public static Tile spawn_wall2_tile = new SpawnWallTile(Sprite.spawn_wall2);
	public static Tile spawn_wall3_tile = new SpawnWallTile(Sprite.spawn_wall3);
	public static Tile spawn_wall4_tile = new SpawnWallTile(Sprite.spawn_wall4);
	public static Tile spawn_floor_tile = new SpawnFloorTile(Sprite.spawn_floor);
	
	public final static int col_spawn_grass = 0xFF00FF00;
	public final static int col_spawn_hedge = 0xFF007F0E;
	public final static int col_spawn_water = 0xFF0094FF;
	public final static int col_spawn_wall1 = 0xFF808080;
	public final static int col_spawn_wall2 = 0xFF303030;
	public final static int col_spawn_wall3 = 0xFF404040;
	public final static int col_spawn_wall4 = 0xFF5B5B5B;
	public final static int col_spawn_floor = 0xFF724715;
	
	public Tile(Sprite sprite)
	{
		this.sprite = sprite;
	}
	
	public void render(int x, int y, Screen screen)
	{
		
	}
	
	public boolean isSolid()
	{
		return false;
	}
}