package net.rb.tacitum.graphics;

/**
 * @author Richard Berqvist
 * @since In-Development 0.4
 * @category Graphics
 * **/
public class Sprite {
	public final int SIZE;
	private int x, y;
	private int width, height;
	public int[] pixels;
	protected SpriteSheet sheet;
	
	/** Normal Sprites **/
	public static Sprite voidTile = new Sprite(16, 0x1B87E0);
	public static Sprite grass 	  = new Sprite(16, 0, 0, SpriteSheet.tiles);
	public static Sprite flower   = new Sprite(16, 1, 0, SpriteSheet.tiles);
	public static Sprite rock	  = new Sprite(16, 2, 0, SpriteSheet.tiles);
	
	/** Spawn Level Sprites **/
	public static Sprite spawn_grass = new Sprite(16, 0, 0, SpriteSheet.spawn_level);
	public static Sprite spawn_hedge = new Sprite(16, 1, 0, SpriteSheet.spawn_level);
	public static Sprite spawn_water = new Sprite(16, 2, 0, SpriteSheet.spawn_level);
	public static Sprite spawn_wall1 = new Sprite(16, 0, 1, SpriteSheet.spawn_level);
	public static Sprite spawn_wall2 = new Sprite(16, 0, 2, SpriteSheet.spawn_level);
	public static Sprite spawn_wall3 = new Sprite(16, 1, 1, SpriteSheet.spawn_level);
	public static Sprite spawn_wall4 = new Sprite(16, 1, 2, SpriteSheet.spawn_level);
	public static Sprite spawn_floor = new Sprite(16, 2, 1, SpriteSheet.spawn_level);
	
	/** Projectile Sprites **/
	public static Sprite wizard_water = new Sprite(16, 1, 0, SpriteSheet.wizard_projectiles);
	public static Sprite wizard_fire = new Sprite(16, 2, 0, SpriteSheet.wizard_projectiles);
	public static Sprite wizard_air = new Sprite(16, 0, 1, SpriteSheet.wizard_projectiles);
	public static Sprite wizard_earth = new Sprite(16, 0, 0, SpriteSheet.wizard_projectiles);
	public static Sprite archer = new Sprite(16, 0, 0, SpriteSheet.archer_projectiles);
	
	/** Particle Sprites **/
	public static Sprite particle_water = new Sprite(3, 0x0094FF);  
	public static Sprite particle_water1 = new Sprite(3, 0x3DAEFF);  
	public static Sprite particle_fire = new Sprite(3, 0xFF0000);
	public static Sprite particle_fire1 = new Sprite(3, 0xFF6A00);
	
	/** Space Level Sprites **/
	public static Sprite space_wall = new Sprite(16, 0, 0, SpriteSheet.space);
	public static Sprite space_wall1 = new Sprite(16, 1, 0, SpriteSheet.space);
	public static Sprite space_floor = new Sprite(16, 2, 0, SpriteSheet.space);
	public static Sprite space_tile = new Sprite(16, 0);
	public static Sprite star = new Sprite(16, 0, 1, SpriteSheet.star);
	
	protected Sprite(SpriteSheet sheet, int width, int height) {
		this.SIZE = (width == height) ? width : -1;
		this.width = width;
		this.height = height;
		this.sheet = sheet;
	}
	
	public Sprite(int size, int x, int y, SpriteSheet sheet) {
		this.SIZE = size;
		this.width = size;
		this.height = size;
		this.x = x * size;
		this.y = y * size;
		this.pixels = new int[SIZE * SIZE];
		this.sheet = sheet;
		
		load();
	}
	
	public Sprite(int width, int height, int color) {
		this.SIZE = -1;
		this.width = width;
		this.height = height;
		this.pixels = new int[width * height];
		
		setColor(color);
	}
	
	public Sprite(int size, int color) {
		this.SIZE = size;
		this.width = size;
		this.height = size;
		this.pixels = new int[SIZE * SIZE];
		
		setColor(color);
	}
	
	public Sprite(int[] pixels, int width, int height) {
		this.SIZE = (width == height) ? width : -1;
		this.width = width;
		this.height = height;
		this.pixels = new int[pixels.length];
		for (int i = 0; i < pixels.length; i++) {
			this.pixels[i] = pixels[i];
		}
	}
	
	public static Sprite rotate(Sprite sprite, double angle) {
		return new Sprite(rotate(sprite.pixels, sprite.width, sprite.width, angle), sprite.width, sprite.height);
	}
	
	private static int[] rotate(int[] pixels, int width, int height, double angle) {
		int[] result = new int[width * height];
		
		double nx_x = rotateX(-angle, 1.0, 0.0);
		double nx_y = rotateY(-angle, 1.0, 0.0);
		double ny_x = rotateX(-angle, 0.0, 1.0);
		double ny_y = rotateY(-angle, 0.0, 1.0);
		
		double x0 = rotateX(-angle, -width / 2.0, -height / 2.0) + width / 2.0;
		double y0 = rotateY(-angle, -width / 2.0, -height / 2.0) + height / 2.0;
		
		for (int y = 0; y < height; y++) {
			double x1 = x0;
			double y1 = y0;
			for (int x = 0; x < width; x++) {
				int xx = (int) x1;
				int yy = (int) y1;
				int col = 0;
				if (xx < 0 || xx >= width || yy < 0 || yy >= height) col = 0xFFFF00FF;
				else col = pixels[xx + yy * width];
				result[x + y * width] = col;
				x1 += nx_x;
				y1 += nx_y;
			}
			x0 += ny_x;
			y0 += ny_y;
		}
		return result;
	}
	
	private static double rotateX(double angle, double x, double y) {
		double cos = Math.cos(angle - Math.PI / 2);
		double sin = Math.sin(angle - Math.PI / 2);
		return x * cos + y * -sin;
	}
	
	private static double rotateY(double angle, double x, double y) {
		double cos = Math.cos(angle - Math.PI / 2);
		double sin = Math.sin(angle - Math.PI / 2);
		return x * sin + y * cos;
	}
	
	public static Sprite[] split(SpriteSheet sheet) {
		int amount = (sheet.getWidth() * sheet.getHeight()) / (sheet.SPRITE_WIDTH * sheet.SPRITE_HEIGHT);
		Sprite[] sprites = new Sprite[amount];	
		int current = 0;
		int[] pixels = new int[sheet.SPRITE_WIDTH * sheet.SPRITE_HEIGHT];
		for (int yp = 0; yp < sheet.getHeight() / sheet.SPRITE_HEIGHT; yp++) {
			for (int xp = 0; xp < sheet.getWidth() / sheet.SPRITE_WIDTH; xp++) {
				for (int y = 0; y < sheet.SPRITE_HEIGHT; y++) {
					for (int x = 0; x < sheet.SPRITE_WIDTH; x++) {
						int xo = x + xp * sheet.SPRITE_WIDTH;
						int yo = y + yp * sheet.SPRITE_HEIGHT;
						pixels[x + y * sheet.SPRITE_WIDTH] = sheet.getPixels()[xo + yo * sheet.getWidth()];
					}
				}
				sprites[current++] = new Sprite(pixels, sheet.SPRITE_WIDTH, sheet.SPRITE_HEIGHT);
			}
		}
		return sprites;
	}

	private void setColor(int color) {
		for (int i = 0; i < width * height; i++) {
			pixels[i] = color;
		}
	}
	
	public int getWidth() {
		return width;
	}
	
	public int getHeight() {
		return height;
	}

	private void load() {
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				pixels[x + y * width] = sheet.pixels[(x + this.x) + (y + this.y) * sheet.SPRITE_WIDTH];
			}
		}
	}
}