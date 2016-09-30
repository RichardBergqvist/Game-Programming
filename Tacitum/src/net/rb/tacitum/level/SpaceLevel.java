package net.rb.tacitum.level;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import net.rb.tacitum.graphics.Font;
import net.rb.tacitum.graphics.Screen;

/**
 * @author Richard Bergqvist, Waincai Liang
 * @since Pre-Alpha 5.0
 * @category Mapping
 * **/
public class SpaceLevel extends Level {
	public final static int col_space_floor = 0xFF808080;
	public final static int col_space_wall = 0xFF404040;
	public static final int col_space_wall1 = 0xFF232323;
	public static final int col_space_star = 0xFFFFD800;
	
	public SpaceLevel(String path) {
		super(path);
	}
	
	public static void renderText(Screen screen) {
		Font font = new Font();
		font.render(63, 103, "LIANG", screen);
		
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