package net.rb.tacitum.level;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class SpawnLevel extends Level {
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
		
		//add(new Dummy(20, 55));
		//add(new Shooter(20, 50));
		//add(new Chaser(20, 50));
	//	add(new Star(20, 53));
	//	add(new Archer(25, 53));
		//add(new ShadowKing(20, 55));
	}
}