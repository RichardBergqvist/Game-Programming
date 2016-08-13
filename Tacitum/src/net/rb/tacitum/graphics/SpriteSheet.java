package net.rb.tacitum.graphics;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class SpriteSheet {
	private String path;
	public final int SIZE;
	public final int SPRITE_WIDTH, SPRITE_HEIGHT;
	private int width, height;
	public int[] pixels;
	
	public static SpriteSheet tiles 	  		 = new SpriteSheet("/textures/sheets/spritesheet.png", 256);
	public static SpriteSheet spawn_level 		 = new SpriteSheet("/textures/sheets/spawn_level.png", 48);
	public static SpriteSheet trees				 = new SpriteSheet("/textures/sheets/trees.png", 48);
	public static SpriteSheet wizard_projectiles = new SpriteSheet("/textures/sheets/projectiles/wizard_projectiles.png", 48);
	public static SpriteSheet archer_projectiles = new SpriteSheet("/textures/sheets/projectiles/archer_projectiles.png", 48);
	
	public static SpriteSheet player 			 = new SpriteSheet("/textures/sheets/player.png", 128, 96);
	public static SpriteSheet player_down        = new SpriteSheet(player, 0, 0, 1, 3, 32);
	public static SpriteSheet player_up          = new SpriteSheet(player, 1, 0, 1, 3, 32);
	public static SpriteSheet player_left        = new SpriteSheet(player, 2, 0, 1, 3, 32);
	public static SpriteSheet player_right       = new SpriteSheet(player, 3, 0, 1, 3, 32);
	
	public static SpriteSheet dummy 			 = new SpriteSheet("/textures/sheets/player.png", 128, 96);
	public static SpriteSheet dummy_down         = new SpriteSheet(dummy, 0, 0, 1, 3, 32);
	public static SpriteSheet dummy_up           = new SpriteSheet(dummy, 1, 0, 1, 3, 32);
	public static SpriteSheet dummy_left         = new SpriteSheet(dummy, 2, 0, 1, 3, 32);
	public static SpriteSheet dummy_right        = new SpriteSheet(dummy, 3, 0, 1, 3, 32);
	
	public static SpriteSheet archer 			 = new SpriteSheet("/textures/sheets/archer.png", 128, 96);
	public static SpriteSheet archer_down         = new SpriteSheet(archer, 0, 0, 1, 3, 32);
	public static SpriteSheet archer_up           = new SpriteSheet(archer, 1, 0, 1, 3, 32);
	public static SpriteSheet archer_left         = new SpriteSheet(archer, 2, 0, 1, 3, 32);
	public static SpriteSheet archer_right        = new SpriteSheet(archer, 3, 0, 1, 3, 32);
	
	private Sprite[] sprites;
	
	public SpriteSheet(String path, int size) {
		this.path = path;
		this.SIZE = size;
		this.SPRITE_WIDTH = size;
		this.SPRITE_HEIGHT = size;
		this.pixels = new int[SIZE * SIZE];
		
		load();
	}
	
	public SpriteSheet(String path, int width, int height) {
		this.path = path;
		this.SIZE = -1;
		this.SPRITE_WIDTH = width;
		this.SPRITE_HEIGHT = height;
		this.pixels = new int[SPRITE_WIDTH * SPRITE_HEIGHT];
		
		load();
	}
	
	public SpriteSheet(SpriteSheet sheet, int x, int y, int width, int height, int spriteSize) {
		int xx = x * spriteSize;
		int yy = y * spriteSize;
		int w  = width * spriteSize;
		int h  = height * spriteSize;
		
		if (width == height) SIZE = width;
		else this.SIZE = -1;
		this.SPRITE_WIDTH = w;
		this.SPRITE_HEIGHT = h;
		this.pixels = new int[w * h];
		
		for (int y0 = 0; y0 < h; y0++) {
			int yp = yy + y0;
			for (int x0 = 0; x0 < w; x0++) {
				int xp = xx + x0;
				pixels[x0 + y0 * w] = sheet.pixels[xp + yp * sheet.SPRITE_WIDTH];
			} 
		}
		
		int frame = 0;
		sprites = new Sprite[width * height];
		for (int ya = 0; ya < height; ya++) {
			for (int xa = 0; xa < width; xa++) {
				int[] spritePixels = new int[spriteSize * spriteSize];
				for (int y0 = 0; y0 < spriteSize; y0++) {
					for (int x0 = 0; x0 < spriteSize; x0++) {
						spritePixels[x0 + y0 * spriteSize] = pixels[(x0 + xa * spriteSize) + (y0 + ya * spriteSize) * SPRITE_WIDTH];
					}
				}
				
				Sprite sprite = new Sprite(spritePixels, spriteSize, spriteSize);
				sprites[frame++] = sprite;
			}
		}
	}
	
	public Sprite[] getSprites() {
		return sprites;
	}
	
	public int getWidth() {
		return width;
	}
	
	public int getHeight() {
		return height;
	}
	
	public int[] getPixels() {
		return pixels;
	}
	
	private void load() {
		try {
			System.out.print("Trying to load: " + path + "...");
			BufferedImage image = ImageIO.read(SpriteSheet.class.getResource(path));
			System.out.println(" Loading Successful!");
			width = image.getWidth();
			height = image.getHeight();
			pixels = new int[width * height];
			image.getRGB(0, 0, width, height, pixels, 0, width);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {			
			System.err.println("Loading Failed!");
		}
	}
}