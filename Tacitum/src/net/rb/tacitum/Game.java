package net.rb.tacitum;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;

import net.rb.tacitum.entity.mob.Player;
import net.rb.tacitum.events.Event;
import net.rb.tacitum.events.EventListener;
import net.rb.tacitum.graphics.Screen;
import net.rb.tacitum.graphics.layers.Layer;
import net.rb.tacitum.graphics.ui.UIManager;
import net.rb.tacitum.input.Keyboard;
import net.rb.tacitum.input.Mouse;
import net.rb.tacitum.level.Level;
import net.rb.tacitum.level.TileCoordinate;
import net.rb.tacitum.net.player.PlayerMP;

public class Game extends Canvas implements Runnable, EventListener {
	/** This line has no effect on the game whatsoever. **/
	private static final long serialVersionUID = 1L;	
	/** Width for game window **/
	private static int width = 300 - 80;
	/** Height for game window **/
	private static int height = 168;
	/** Scale for the game window **/
	public static int scale = 3;
	
	/** Game title **/
	public static final String TITLE = "Tacitum";
	
	/** These strings should be changed every time the game updates, as they contain all version info. **/
	public static final String VERSION = "Pre-Alpha 1.00";
	public static final String RELEASE_DATE = "Aug 11 2016";
	public static final String RELEASE_TIME = "00:08:00";
	
	/** These strings should not change as they do not hold version info. **/
	public static final String RELEASE = "(" + RELEASE_DATE + "/" + RELEASE_TIME + ")[PUBLIC]";
	public static final String RELEASE_VERSION = "<Release/" + VERSION + "> Client";
	public static final String VERSION_INFORMATION_STRING = "Version: " + VERSION + " " + RELEASE + RELEASE_VERSION;
	
	private Thread thread;
	private JFrame frame;
	private Keyboard key;
	private Level level;
	private Player player;
	private boolean running = false;
	
	private static UIManager uiManager;
	
	private int fps = 0;
	
	private Screen screen;
	private BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
	private int[] pixels = ((DataBufferInt) image.getRaster().getDataBuffer()).getData();
	
	private List<Layer> layerStack = new ArrayList<Layer>();
	
	public Game() {
		Dimension size = new Dimension(width * scale + 80 * 3, height * scale);
		setPreferredSize(size);
		
		screen = new Screen(width, height);
		uiManager = new UIManager();
		frame = new JFrame();
		key = new Keyboard();
		level = Level.spawn;
		addLayer(level);
		
		TileCoordinate playerSpawn = new TileCoordinate(20, 57);
		player = new Player("Tacitum", playerSpawn.getX(), playerSpawn.getY(), key);
		level.addPlayer(player);
		level.addPlayer(new PlayerMP());
	
		addKeyListener(key);
		
		Mouse mouse = new Mouse(this);
		addMouseListener(mouse);
		addMouseMotionListener(mouse);
	}
	
	public static int getWindowWidth() {
		return width * scale;
	}
	
	public static int getWindowHeight() {
		return height * scale;
	}
	
	public static UIManager getUIManager() {
		return uiManager;
	}
	
	public void addLayer(Layer layer) {
		layerStack.add(layer);
	}
	
	public synchronized void start() {
		running = true;
		
		thread = new Thread(this, "Display");
		thread.start();
	}
	
	public synchronized void stop() {
		running = false;
		try {
			thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public void run() {
		long lastTime = System.nanoTime();
		long timer = System.currentTimeMillis();
		final double ns = 1000000000.0 / 60.0;
		double delta = 0;
		int frames = 0;
		int updates = 0;
		
		requestFocus();
		while (running) {
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;
			while (delta >= 1) {
				update();
				updates++;
				delta--;
			}	
			render();
			frames++;
	
			if (System.currentTimeMillis() - timer > 1000) {
				timer += 1000;
				fps = frames;
				System.out.println(updates + " ups, " + frames + " fps");
				updates = 0;
				frames = 0;
			}
		}
		stop();
	}
	
	public void onEvent(Event event) {
		for (int i = layerStack.size() - 1; i >= 0; i--) {
			layerStack.get(i).onEvent(event);
		}
	}
	
	public void update() {
		key.update();
		uiManager.update();
		
		for (int i = 0; i < layerStack.size(); i++) {
			layerStack.get(i).update();
		}
	}
	
	public void render() {
		BufferStrategy bs = getBufferStrategy();
		
		if (bs == null) {
			createBufferStrategy(3);
			return;
		}
		
		screen.clear();
		int xScroll = player.getX() - screen.width / 2;
		int yScroll = player.getY() - screen.height/ 2;
		level.setScroll(xScroll, yScroll);
		
		for (int i = 0; i < layerStack.size(); i++) {
			layerStack.get(i).render(screen);
		}
		
		for (int i = 0; i < pixels.length; i++) {
			pixels[i] = screen.pixels[i];
		}
		
		Graphics g = bs.getDrawGraphics();
		g.setColor(new Color(0xFF00FF));
		g.fillRect(0, 0, getWidth(), getHeight());
		g.drawImage(image, 0, 0, width * scale, height * scale, null);
		uiManager.render(g);
		{
			g.setColor(Color.WHITE);
			g.setFont(new java.awt.Font("Consolas", 0, 10));
			g.drawString("FPS: " + fps, 0, 10);
			g.drawString(VERSION_INFORMATION_STRING, 0, 500);
		}
		g.dispose();
		bs.show();
	}
	
	public static void main(String[] args) {
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