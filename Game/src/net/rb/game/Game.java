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
import net.rb.game.input.Mouse;
import net.rb.game.level.Level;
import net.rb.game.level.TileCoordinate;

public class Game extends Canvas implements Runnable
{
	/** This line has no effect on the game whatsoever. **/
	private static final long serialVersionUID = 1L;
	
	/** Width for Game window **/
	private static int width = 300;
	/** Height for game window **/
	private static int height = 168;
	/** Scale for the game window **/
	public static int scale = 3;
	
	/** Game title **/
	public static final String TITLE = "Game";
	
	/** These strings should be changed every time the game updates, as they contain all version info. **/
	public static final String VERSION = "In-Dev 3.50";
	public static final String RELEASE_DATE = "Aug 1 2016";
	public static final String RELEASE_TIME = "22:53:00";
	
	/** These strings should not change as they do not hold version info. **/
	public static final String RELEASE = "(" + RELEASE_DATE + "/" + RELEASE_TIME + "[PUBLIC]";
	public static final String RELEASE_VERSION = "<Release/" + VERSION + "> Client";
	public static final String VERSION_INFORMATION_STRING = "Version: " + VERSION + " " + RELEASE + RELEASE_VERSION;
	
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
		level = Level.spawn;
		
		TileCoordinate playerSpawn = new TileCoordinate(20, 57);
		player = new Player(playerSpawn.getX(), playerSpawn.getY(), key);
		player.init(level);
		
		Mouse mouse = new Mouse();
		addKeyListener(key);
		addMouseListener(mouse);
		addMouseMotionListener(mouse);
	}
	
	public static int getWindowWidth()
	{
		return width * scale;
	}
	
	public static int getWindowHeight()
	{
		return height * scale;
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
				frame.setTitle(Game.TITLE + "  |  " + updates + " ups, " + frames + " fps");
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
		level.update();
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
			g.setFont(new Font("Consolas", 0, 10));
			g.drawString("FPS: " + fps, 0, 10);
			g.drawString(VERSION_INFORMATION_STRING, 0, 500);
		}
		g.dispose();
		bs.show();
	}
	
	public static void main(String[] args)
	{
		Game game = new Game();
		game.frame.setResizable(false);
		game.frame.setTitle(Game.TITLE);
		game.frame.add(game);
		game.frame.pack();
		game.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		game.frame.setLocationRelativeTo(null);
		game.frame.setVisible(true);
		
		game.start();
	}
}