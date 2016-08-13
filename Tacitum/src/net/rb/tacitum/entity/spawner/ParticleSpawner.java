package net.rb.tacitum.entity.spawner;

import net.rb.tacitum.entity.particle.Particle;
import net.rb.tacitum.graphics.Sprite;
import net.rb.tacitum.level.Level;

public class ParticleSpawner extends Spawner {
	protected int life;
	
	public ParticleSpawner(int x, int y, int life, int amount, Level level, Sprite sprite) {
		super(x, y, Type.PARTICLE, amount, level);
		this.life = life;
		for (int i = 0; i < amount; i++) {
			level.add(new Particle(x, y, life, sprite));
		}
	}
}