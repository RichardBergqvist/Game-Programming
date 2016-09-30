package net.rb.tacitum.entity.mob.player;

import java.awt.Font;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import net.rb.tacitum.Game;
import net.rb.tacitum.entity.projectile.ArcherProjectile;
import net.rb.tacitum.graphics.Screen;
import net.rb.tacitum.graphics.ui.UIActionListener;
import net.rb.tacitum.graphics.ui.UIButton;
import net.rb.tacitum.graphics.ui.UIButtonListener;
import net.rb.tacitum.graphics.ui.UILabel;
import net.rb.tacitum.graphics.ui.UIPanel;
import net.rb.tacitum.graphics.ui.UIProgressBar;
import net.rb.tacitum.input.Keyboard;
import net.rb.tacitum.input.Mouse;
import net.rb.tacitum.util.Vector2i;

/**
 *  @author Richard Bergqvist
 *  @since Pre-Alpha 4.0
 *  @category Entities
 * **/
public class Archer extends Player {
	private ArcherProjectile.Type type;
	private int fireRate;
	
	public Archer(String name, int x, int y, Keyboard input) {
		super(name, PlayerClass.ARCHER.getPlayerClass(), x, y, input);
	}
	
	public Archer(String name, int x, int y, Keyboard input, ArcherProjectile.Type type) {
		super(name, PlayerClass.ARCHER.getPlayerClass(), x, y, input);
		
		setProjectileType(type);
	}
	
	public void renderUI() {
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
		
		UILabel playerClassLabel = new UILabel(new Vector2i(150, 200), this.getPlayerClass());
		playerClassLabel.setColor(0xBBBBBB);
		playerClassLabel.setFont(new Font("Verdana", Font.BOLD, 14));
		playerClassLabel.dropShadow = true;
		panel.addComponent(playerClassLabel);
		
		image      = null;
		imageHover = null;
		
		try {
			image = ImageIO.read(new File("res/textures/misc/exit.png"));
			imageHover = ImageIO.read(new File("res/textures/misc/exit_open.png"));
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
		
		if (fireRate > 0) fireRate--;
		
		updateShooting();
		updatePlayerStats();
		clear();
	}
	
	public void updatePlayerStats() {
		uiHealthBar.setProgress(health / 100.0);
	}
	
	private void updateShooting() {
		if (!shooting || fireRate > 0) return;
		double dx = Mouse.getX() - Game.getWindowWidth() / 2;
		double dy = Mouse.getY() - Game.getWindowHeight() / 2;
		double dir = Math.atan2(dy, dx);
		if (resource > cost)
			shoot(x, y, dir, new ArcherProjectile(x, y, dir, ArcherProjectile.Type.WOOD));
		fireRate = ArcherProjectile.FIRE_RATE;
	}
	
	public void setFireRate(int fireRate) {
		this.fireRate = fireRate;
	}
	
	public int getFireRate() {
		return fireRate;
	}

	public void setProjectileType(ArcherProjectile.Type type) {
		this.type = type;
	}
	
	public ArcherProjectile.Type getProjectileType() {
		return type;
	}
	
	public void render(Screen screen) {
		sprite = playerSprite.getSprite();
		screen.renderMob(x - 16, y - 16, sprite);
	}
}