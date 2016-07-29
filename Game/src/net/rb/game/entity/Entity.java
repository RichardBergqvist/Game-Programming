package net.rb.game.entity;

import java.util.Random;

import net.rb.game.graphics.Screen;
import net.rb.game.level.Level;

public abstract class Entity
{
	public int x, y;
	private boolean removed = false;
	protected Level level;
	protected final Random random = new Random();
	
	public void update()
	{
		
	}
	
	public void render(Screen screen)
	{
		
	}
	
	public void remove()
	{
		removed = true;
	}
	
	public boolean isRemoved()
	{
		return removed;
	}
}