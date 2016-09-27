package net.rb.tacitum.entity.mob.player;

import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import net.rb.tacitum.entity.mob.Mob;
import net.rb.tacitum.entity.projectile.Projectile;
import net.rb.tacitum.events.Event;
import net.rb.tacitum.events.EventDispatcher;
import net.rb.tacitum.events.EventListener;
import net.rb.tacitum.events.types.MousePressedEvent;
import net.rb.tacitum.events.types.MouseReleasedEvent;
import net.rb.tacitum.graphics.AnimatedSprite;
import net.rb.tacitum.graphics.Screen;
import net.rb.tacitum.graphics.Sprite;
import net.rb.tacitum.graphics.SpriteSheet;
import net.rb.tacitum.graphics.ui.UIManager;
import net.rb.tacitum.graphics.ui.UIProgressBar;
import net.rb.tacitum.input.Keyboard;
import net.rb.tacitum.input.Mouse;

public class Player extends Mob implements EventListener {	
	private String name;
	private String playerClass;
	protected Keyboard input;
	protected Sprite sprite;
	protected boolean walking = false;
	protected boolean shooting = false;
	protected AnimatedSprite player_down  = new AnimatedSprite(SpriteSheet.player_down,  32, 32, 3);
	protected AnimatedSprite player_up    = new AnimatedSprite(SpriteSheet.player_up,    32, 32, 3);
	protected AnimatedSprite player_left  = new AnimatedSprite(SpriteSheet.player_left,  32, 32, 3);
	protected AnimatedSprite player_right = new AnimatedSprite(SpriteSheet.player_right, 32, 32, 3);
	
	// TODO: Change back to private
	protected AnimatedSprite playerSprite = player_down;
	
	protected UIManager ui;
	protected UIProgressBar uiHealthBar;
	protected UIProgressBar uiResourceBar;
	
	protected BufferedImage image, imageHover;
	
	@Deprecated
	public Player(String name, String playerClass, Keyboard input) {
		this.name = name;
		this.playerClass = playerClass;
		this.input = input;
		this.playerSprite = player_down;
		this.health = 100;
		this.resource = 100;
		this.cost = 5;
		
		renderUI();
	}
	
	public Player(String name, String playerClass, int x, int y, Keyboard input) {
		this.name = name;
		this.playerClass = playerClass;
		this.x = x;
		this.y = y;
		this.input = input;
		this.playerSprite = player_down;
		this.health = 100;
		this.resource = 100;
		this.cost = 5;
		
		renderUI();
	}
	
	public String getName() {
		return name;
	}
	
	/**
	 * Player Classes:
	 * Mage
	 * Archer
	 * Fighter
	 * Tank
	 */
	public String getPlayerClass() {
		return playerClass;
	}
	
	public void onEvent(Event event) {
		EventDispatcher dispatcher = new EventDispatcher(event);
		dispatcher.dispatch(Event.Type.MOUSE_PRESSED, (Event e) -> onMousePressed((MousePressedEvent) e));
		dispatcher.dispatch(Event.Type.MOUSE_RELEASED, (Event e) -> onMouseReleased((MouseReleasedEvent) e));
	}
	
	int time;
	public void update() {
		time++;
		if (walking) playerSprite.update();
		else playerSprite.setFrame(0);
		double xa = 0;
		double ya = 0;
		double sprint_speed = 1;
		
		if (input.shift) this.playerSprite.setFrameRate(5);
		else this.playerSprite.setFrameRate(8);
		if (input.up) {
			playerSprite = player_up;
			if (input.shift) ya -= sprint_speed;
			ya--;
		}
		else if (input.down) {
			playerSprite = player_down;
			if (input.shift) ya += sprint_speed;
			ya++;
		}
		if (input.left) {
			playerSprite = player_left;
			if (input.shift) xa -= sprint_speed;
			xa--;
		}
		else if (input.right) {
			playerSprite = player_right;
			if (input.shift) xa += sprint_speed;
			xa++;
		}
		if (xa != 0 || ya != 0) {
			move(xa, ya);
			walking = true;
		} else {
			walking = false;
		}
		
		if (time >= 100 && !shooting) {
			time = 0;
			if (resource < 100) resource++;
		}
		
		clear();
		updatePlayerStats();
	}
	
	public void updatePlayerStats() {
		uiHealthBar.setProgress(health / 100.0);
		uiResourceBar.setProgress(resource / 100.0);
	}
	
	public boolean onMousePressed(MousePressedEvent e) {
		if (Mouse.getX() > 660)
			return false;
		if (e.getButton() == MouseEvent.BUTTON1) {
			shooting = true;
			return true;
		}
		return false;
	}
	
	public boolean onMouseReleased(MouseReleasedEvent e) {
		if (e.getButton() == MouseEvent.BUTTON1) {
			shooting = false;
			return true;
		}
		return false;
	}
	
	public void clear() {
		for (int i = 0; i < level.getProjectiles().size(); i++) {
			Projectile p = level.getProjectiles().get(i);
			if (p.isRemoved()) level.getProjectiles().remove(i);
		}
	}
	
	public void renderUI() {
		
	}
	
	public void render(Screen screen) {
		sprite = playerSprite.getSprite();
		screen.renderMob(x - 16, y - 16, sprite);
	}
	
	public void setResource(int resource) {
		this.resource = resource;
	}
	
	public enum PlayerClass {
		MAGE("Mage"),
		ARCHER("Archer"),
		FIGHTER("Fighter"),
		TANK("Tank");
		
		private String playerClass;
		
		PlayerClass(String playerClass) {
			this.playerClass = playerClass;
		}
		
		public String getPlayerClass() {
			return playerClass;
		}
	}
}