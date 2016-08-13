package net.rb.tacitum.entity.mob;

import java.awt.Font;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import net.rb.tacitum.Game;
import net.rb.tacitum.entity.projectile.Projectile;
import net.rb.tacitum.entity.projectile.WizardProjectile;
import net.rb.tacitum.events.Event;
import net.rb.tacitum.events.EventDispatcher;
import net.rb.tacitum.events.EventListener;
import net.rb.tacitum.events.types.MousePressedEvent;
import net.rb.tacitum.events.types.MouseReleasedEvent;
import net.rb.tacitum.graphics.AnimatedSprite;
import net.rb.tacitum.graphics.Screen;
import net.rb.tacitum.graphics.Sprite;
import net.rb.tacitum.graphics.SpriteSheet;
import net.rb.tacitum.graphics.ui.UIActionListener;
import net.rb.tacitum.graphics.ui.UIButton;
import net.rb.tacitum.graphics.ui.UIButtonListener;
import net.rb.tacitum.graphics.ui.UILabel;
import net.rb.tacitum.graphics.ui.UIManager;
import net.rb.tacitum.graphics.ui.UIPanel;
import net.rb.tacitum.graphics.ui.UIProgressBar;
import net.rb.tacitum.input.Keyboard;
import net.rb.tacitum.input.Mouse;
import net.rb.tacitum.util.Vector2i;

public class Player extends Mob implements EventListener {	
	private String name;
	private Keyboard input;
	private Sprite sprite;
	private boolean walking = false;
	private boolean shooting = false;
	private AnimatedSprite player_down  = new AnimatedSprite(SpriteSheet.player_down,  32, 32, 3);
	private AnimatedSprite player_up    = new AnimatedSprite(SpriteSheet.player_up,    32, 32, 3);
	private AnimatedSprite player_left  = new AnimatedSprite(SpriteSheet.player_left,  32, 32, 3);
	private AnimatedSprite player_right = new AnimatedSprite(SpriteSheet.player_right, 32, 32, 3);
	
	private AnimatedSprite playerSprite = player_down;
	
	private int fireRate = 0;
	
	private UIManager ui;
	private UIProgressBar uiHealthBar;
	private UIProgressBar uiResourceBar;
	private UIProgressBar uiLevelBar;
	private UIButton uiButton;
	
	private BufferedImage image, imageHover;
	
	@Deprecated
	public Player(String name, Keyboard input) {
		this.name = name;
		this.input = input;
		this.fireRate = WizardProjectile.FIRE_RATE;
		this.playerSprite = player_down;
		this.health = 100;
		this.resource = 100;
		this.playerLevel = 0;
		this.cost = 1;
	}
	
	public Player(String name, int x, int y, Keyboard input) {
		this.name = name;
		this.x = x;
		this.y = y;
		this.input = input;
		this.fireRate = WizardProjectile.FIRE_RATE;
		this.playerSprite = player_down;
		this.health = 100;
		this.resource = 100;
		this.playerLevel = 0;
		this.cost = 1;
		
		ui = Game.getUIManager();
		UIPanel panel = (UIPanel) new UIPanel(new Vector2i((300 - 80) * 3, 0), new Vector2i(80 * 3, 168 * 3)).setColor(0x4F4F4F);
		ui.addPanel(panel);
		UILabel nameLabel = new UILabel(new Vector2i(40, 200), getName());
		nameLabel.setColor(0xBBBBBB);
		nameLabel.setFont(new Font("Verdana", Font.BOLD, 24));
		nameLabel.dropShadow = true;
		panel.addComponent(nameLabel);
		
		uiHealthBar = new UIProgressBar(new Vector2i(10, 215), new Vector2i(80 * 3 - 20, 20));
		uiHealthBar.setColor(0x6A6A6A);
		uiHealthBar.setForegroundColor(0xEE3030);
		panel.addComponent(uiHealthBar);
		UILabel hpLabel = new UILabel(new Vector2i(uiHealthBar.position).add(16), "HP");
		hpLabel.setColor(0xFFFFFF);
		hpLabel.setFont(new Font("Verdana", Font.BOLD, 18));
		hpLabel.dropShadow = true;
		panel.addComponent(hpLabel);
		
		uiResourceBar = new UIProgressBar(new Vector2i(10, 240), new Vector2i(80 * 3 - 20, 20));
		uiResourceBar.setColor(0x6A6A6A);
		uiResourceBar.setForegroundColor(0x2571F5);
		panel.addComponent(uiResourceBar);
		UILabel rpLabel = new UILabel(new Vector2i(uiResourceBar.position).add(16), "MP");
		rpLabel.setColor(0xFFFFFF);
		rpLabel.setFont(new Font("Verdana", Font.BOLD, 18));
		rpLabel.dropShadow = true;
		panel.addComponent(rpLabel);
		
		uiLevelBar = new UIProgressBar(new Vector2i(10, 265), new Vector2i(80 * 3 - 20, 20));
		uiLevelBar.setColor(0x6A6A6A);
		uiLevelBar.setForegroundColor(0x61FA61);
		panel.addComponent(uiLevelBar);
		UILabel lvlLabel = new UILabel(new Vector2i(uiLevelBar.position).add(16), "XP");
		lvlLabel.setColor(0xFFFFFF);
		lvlLabel.setFont(new Font("Verdana", Font.BOLD, 18));
		lvlLabel.dropShadow = true;
		panel.addComponent(lvlLabel);
		
		UILabel levelLabel = new UILabel(new Vector2i(150, 200), "Level: " + this.getLevel() + 1);
		levelLabel.setColor(0xBBBBBB);
		levelLabel.setFont(new Font("Verdana", Font.BOLD, 14));
		levelLabel.dropShadow = true;
		panel.addComponent(levelLabel);
		
		/*uiButton = new UIButton(new Vector2i(10, 450), new Vector2i(100, 40), new UIActionListener() {
			public void perform() {
				System.exit(0);
			}
		});
		uiButton.label.setFont(new Font("Verdana", Font.BOLD, 30));
		uiButton.setText("EXIT");
		panel.addComponent(uiButton);*/
		
		image = null;
		imageHover = null;
		
		try {
			image = ImageIO.read(new File("res/textures/exit.png"));
			imageHover = ImageIO.read(new File("res/textures/exit_open.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		UIButton imageButton = new UIButton(new Vector2i(10, 470), image, new UIActionListener() {
			public void perform() {
				System.exit(0);
			}
		});
		
		imageButton.setButtonListener(new UIButtonListener() {
			public void entered(UIButton button) {
				button.setImage(imageHover);
			}
			
			public void exited(UIButton button) {
				button.setImage(image);
			}
		});
		panel.addComponent(imageButton);
	}
	
	public String getName() {
		return name;
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
		if (fireRate > 0) fireRate--;
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
		updateShooting();
		updatePlayerStats();
	}
	
	private void updateShooting() {
		if (!shooting || fireRate > 0) return;
		double dx = Mouse.getX() - Game.getWindowWidth() / 2;
		double dy = Mouse.getY() - Game.getWindowHeight() / 2;
		double dir = Math.atan2(dy, dx);
		if (resource > cost)
			shoot(x, y, dir, new WizardProjectile(x, y, dir));
		fireRate = WizardProjectile.FIRE_RATE;
	}
	
	private void updatePlayerStats() {
		uiHealthBar.setProgress(health / 100.0);
		uiResourceBar.setProgress(resource / 100.0);
		uiLevelBar.setProgress(playerLevel / 100.0);
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
	
	private void clear() {
		for (int i = 0; i < level.getProjectiles().size(); i++) {
			Projectile p = level.getProjectiles().get(i);
			if (p.isRemoved()) level.getProjectiles().remove(i);
		}
	}
	
	public void render(Screen screen) {
		sprite = playerSprite.getSprite();
		screen.renderMob(x - 16, y - 16, sprite);
	}
}