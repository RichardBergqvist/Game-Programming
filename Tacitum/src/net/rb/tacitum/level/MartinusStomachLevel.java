package net.rb.tacitum.level;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

/**
 * @author Richard Bergqvist
 * @since 6.1
 * @category Mapping
 */
public class MartinusStomachLevel extends Level {
	public MartinusStomachLevel(String path) {
		super(path);
	}
	
	protected void loadLevel(String path) {
		try {
			BufferedImage image = ImageIO.read(SpaceLevel.class.getResource(path));
			int w = width = image.getWidth();
			int h = height = image.getHeight();
			
			tiles = new int[w * h];
			image.getRGB(0, 0, w, h, tiles, 0, w);
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Exception: Could not load level file!");
		}
	}
} 