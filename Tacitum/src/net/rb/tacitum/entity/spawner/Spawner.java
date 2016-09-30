package net.rb.tacitum.entity.spawner;

import net.rb.tacitum.entity.Entity;
import net.rb.tacitum.level.Level;

/**
 * @author Richard Berqvist
 * @since In-Development 8.5
 * @category Entities
 * **/
public class Spawner extends Entity {
	public enum Type {
		MOB,
		PARTICLE;
	}
	
	protected Type type;
	
	public Spawner(int x, int y, Type type, int amount, Level level) {
		init(level);
		
		this.x = x;
		this.y = y;
		this.type = type;
	}
}