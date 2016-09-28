package net.rb.tacitum.level.tile;

import net.rb.tacitum.graphics.Screen;
import net.rb.tacitum.graphics.Sprite;
import net.rb.tacitum.level.tile.space.TileSpaceFloor;
import net.rb.tacitum.level.tile.space.TileSpaceWall;
import net.rb.tacitum.level.tile.spawn_level.SpawnFloorTile;
import net.rb.tacitum.level.tile.spawn_level.SpawnGrassTile;
import net.rb.tacitum.level.tile.spawn_level.SpawnHedgeTile;
import net.rb.tacitum.level.tile.spawn_level.SpawnWallTile;
import net.rb.tacitum.level.tile.spawn_level.SpawnWaterTile;

public class Tile {
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
	
	public static Tile space_floor = new TileSpaceFloor(Sprite.space_floor);
	public static Tile space_wall = new TileSpaceWall(Sprite.space_wall);
	public static Tile space_wall1 = new TileSpaceWall(Sprite.space_wall1);
	public static Tile space_tile = new VoidTile(Sprite.space_tile);
	
	public Tile(Sprite sprite) {
		this.sprite = sprite;
	}
	
	public void render(int x, int y, Screen screen) {
		
	}
	
	public boolean isSolid() {
		return false;
	}
	
	public boolean isBreakable() {
		return false;
	}
}