package net.rb.tacitum.level;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import net.rb.tacitum.entity.mob.*;

public class SpawnLevel extends Level {
	public final static int col_spawn_grass = 0xFF00FF00;
	public final static int col_spawn_hedge = 0xFF007F0E;
	public final static int col_spawn_water = 0xFF0094FF;
	public final static int col_spawn_wall1 = 0xFF808080;
	public final static int col_spawn_wall2 = 0xFF303030;
	public final static int col_spawn_wall3 = 0xFF404040;
	public final static int col_spawn_wall4 = 0xFF5B5B5B;
	public final static int col_spawn_floor = 0xFF724715;
	
	public SpawnLevel(String path) {
		super(path);
	}
	
	protected void loadLevel(String path) {
		try {
			BufferedImage image = ImageIO.read(SpawnLevel.class.getResource(path));
			int w = width = image.getWidth();
			int h = height = image.getHeight();
			
			tiles = new int[w * h];
			image.getRGB(0, 0, w, h, tiles, 0, w);
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Exception: Could not load level file!");
		}
		
		//add(new Zombie(20, 55));
		//add(new Skeleton(21, 56));
		//add(new Dummy(20, 55));
		//add(new Shooter(20, 50));
		//add(new Star(20, 53));
		//add(new ArcherMob(25, 53));
	}
}