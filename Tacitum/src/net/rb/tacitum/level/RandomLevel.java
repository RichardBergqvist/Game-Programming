package net.rb.tacitum.level;

import java.util.Random;

/**
 * @author Richard Bergqvist
 * @since In-Development 7.9
 * @category Mapping
 * **/
public class RandomLevel extends Level {
	private static final Random random = new Random();
	
	public RandomLevel(int width, int height) {
		super(width, height);
	}
	
	protected void generateLevel() {
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				tilesInt[x + y * width] = random.nextInt(4);
			}
		}
	}
}