package net.rb.game;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

import javax.swing.JFrame;

import net.rb.game.entity.mob.Player;
import net.rb.game.graphics.Screen;
import net.rb.game.input.Keyboard;
import net.rb.game.level.Level;
import net.rb.game.level.SpawnLevel;

public class Game extends Canvas implements Runnable
{
	private static final long serialVersionUID = 1L;
	
	public static int width = 300;
	public static int height = 168;
	public static int scale = 3;
	public static String title = "Game";
	public static String version = "In-Dev 1.5";
	
	private Thread thread;
	private JFrame frame;
	private Keyboard key;
	private Level level;
	private Player player;
	private boolean running = false;
	
	private int fps = 0;
	
	private Screen screen;
	
	private BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
	private int[] pixels = ((DataBufferInt) image.getRaster().getDataBuffer()).getData();
	
	public Game()
	{
		Dimension size = new Dimension(width * scale, height * scale);
		setPreferredSize(size);
		
		screen = new Screen(width, height);
		frame = new JFrame();
		key = new Keyboard();
		level = new SpawnLevel("/textures/levels/level.png");
		player = new Player(110, 130, key);
		
		addKeyListener(key);
	}
	
	public synchronized void start()
	{
		running = true;
		thread = new Thread(this, "Display");
		thread.start();
	}
	
	public synchronized void stop()
	{
		running = false;
		try {
			thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public void run()
	{
		long lastTime = System.nanoTime();
		long timer = System.currentTimeMillis();
		final double ns = 1000000000.0 / 60.0;
		double delta = 0;
		int frames = 0;
		int updates = 0;
		requestFocus();
		while (running)
		{
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;
			while (delta >= 1)
			{
				update();
				updates++;
				delta--;
			}	
			render();
			frames++;
	
			if (System.currentTimeMillis() - timer > 1000)
			{
				timer += 1000;
				fps = frames;
				System.out.println(updates + " ups, " + frames + " fps");
				frame.setTitle(Game.title + "  |  " + updates + " ups, " + frames + " fps");
				updates = 0;
				frames = 0;
			}
		}
		stop();
	}
	
	public void update()
	{
		key.update();
		player.update();
	}
	
	public void render()
	{
		BufferStrategy bs = getBufferStrategy();
		
		if (bs == null)
		{
			createBufferStrategy(3);
			return;
		}
		
		screen.clear();
		int xScroll = player.x - screen.width / 2;
		int yScroll = player.y - screen.height/ 2;
		level.render(xScroll, yScroll, screen);
		player.render(screen);
		
		for (int i = 0; i < pixels.length; i++)
		{
			pixels[i] = screen.pixels[i];
		}
		
		Graphics g = bs.getDrawGraphics();
		g.fillRect(0, 0, getWidth(), getHeight());
		g.drawImage(image, 0, 0, getWidth(), getHeight(), null);
		
		{
			g.setColor(Color.WHITE);
			g.setFont(new Font("Verdana", 0, 30));
			g.drawString(title, 30, 30);
			g.setFont(new Font("Verdana", 0, 20));
			g.drawString("Version: " + version, 50, 50);
			g.drawString("FPS: " + fps, 50, 70);
			g.drawString("Coordinates:", 50, 90);
			g.drawString("X: " + player.x + ", Y: " + player.y, 70, 110);
		}
		
		g.dispose();
		bs.show();
	}
	
	public static void main(String[] args)
	{
		Game game = new Game();
		game.frame.setResizable(false);
		game.frame.setTitle(Game.title);
		game.frame.add(game);
		game.frame.pack();
		game.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		game.frame.setLocationRelativeTo(null);
		game.frame.setVisible(true);
		
		game.start();
	}
}