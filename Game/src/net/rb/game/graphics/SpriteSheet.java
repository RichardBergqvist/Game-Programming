package net.rb.game.graphics;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class SpriteSheet
{
	private String path;
	public final int SIZE;
	public final int WIDTH, HEIGHT;
	public int[] pixels;
	
	public static SpriteSheet tiles 	  		 = new SpriteSheet("/textures/sheets/spritesheet.png", 256);
	public static SpriteSheet spawn_level 		 = new SpriteSheet("/textures/sheets/spawn_level.png", 48);
	public static SpriteSheet wizard_projectiles = new SpriteSheet("/textures/sheets/projectiles/wizard_projectiles.png", 48);
	
	public static SpriteSheet player 			 = new SpriteSheet("/textures/sheets/player.png", 128, 96);
	public static SpriteSheet player_down        = new SpriteSheet(player, 0, 0, 1, 3, 32);
	public static SpriteSheet player_up          = new SpriteSheet(player, 1, 0, 1, 3, 32);
	public static SpriteSheet player_left        = new SpriteSheet(player, 2, 0, 1, 3, 32);
	public static SpriteSheet player_right       = new SpriteSheet(player, 3, 0, 1, 3, 32);
	
	private Sprite[] sprites;
	
	public SpriteSheet(String path, int size)
	{
		this.path = path;
		this.SIZE = size;
		this.WIDTH = size;
		this.HEIGHT = size;
		this.pixels = new int[SIZE * SIZE];
		
		load();
	}
	
	public SpriteSheet(String path, int width, int height)
	{
		this.path = path;
		this.SIZE = -1;
		this.WIDTH = width;
		this.HEIGHT = height;
		this.pixels = new int[WIDTH * HEIGHT];
		
		load();
	}
	
	public SpriteSheet(SpriteSheet sheet, int x, int y, int width, int height, int spriteSize)
	{
		int xx = x * spriteSize;
		int yy = y * spriteSize;
		int w  = width * spriteSize;
		int h  = height * spriteSize;
		
		if (width == height) SIZE = width;
		else this.SIZE = -1;
		this.WIDTH = w;
		this.HEIGHT = h;
		this.pixels = new int[w * h];
		
		for (int y0 = 0; y0 < h; y0++)
		{
			int yp = yy + y0;
			for (int x0 = 0; x0 < w; x0++)
			{
				int xp = xx + x0;
				pixels[x0 + y0 * w] = sheet.pixels[xp + yp * sheet.WIDTH];
			} 
		}
		
		int frame = 0;
		sprites = new Sprite[width * height];
		for (int ya = 0; ya < height; ya++)
		{
			for (int xa = 0; xa < width; xa++)
			{
				int[] spritePixels = new int[spriteSize * spriteSize];
				for (int y0 = 0; y0 < spriteSize; y0++)
				{
					for (int x0 = 0; x0 < spriteSize; x0++)
					{
						spritePixels[x0 + y0 * spriteSize] = pixels[(x0 + xa * spriteSize) + (y0 + ya * spriteSize) * WIDTH];
					}
				}
				
				Sprite sprite = new Sprite(spritePixels, spriteSize, spriteSize);
				sprites[frame++] = sprite;
			}
		}
	}
	
	public Sprite[] getSprites()
	{
		return sprites;
	}
	
	private void load()
	{
		try
		{
			BufferedImage image = ImageIO.read(SpriteSheet.class.getResource(path));
			int w = image.getWidth();
			int h = image.getHeight();
			image.getRGB(0, 0, w, h, pixels, 0, w);
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
}