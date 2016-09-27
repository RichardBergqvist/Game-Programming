package net.rb.tacitum.entity.mob.player;

import java.awt.Font;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import net.rb.tacitum.Game;
import net.rb.tacitum.graphics.Screen;
import net.rb.tacitum.graphics.ui.UIActionListener;
import net.rb.tacitum.graphics.ui.UIButton;
import net.rb.tacitum.graphics.ui.UIButtonListener;
import net.rb.tacitum.graphics.ui.UILabel;
import net.rb.tacitum.graphics.ui.UIPanel;
import net.rb.tacitum.graphics.ui.UIProgressBar;
import net.rb.tacitum.input.Keyboard;
import net.rb.tacitum.util.Vector2i;

public class Tank extends Player {
	public Tank(String name, int x, int y, Keyboard input) {
		super(name, PlayerClass.TANK.getPlayerClass(), x, y, input);
		
		this.shield = 100;
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
		
		uiResourceBar = new UIProgressBar(new Vector2i(10, 240), new Vector2i(80 * 3 - 20, 20));
		uiResourceBar.setColor(0x6A6A6A);
		uiResourceBar.setForegroundColor(0xE3E856);
		panel.addComponent(uiResourceBar);
		UILabel rpLabel = new UILabel(new Vector2i(uiResourceBar.position).add(16), "EP");
		rpLabel.setColor(0xFFFFFF);
		rpLabel.setFont(new Font("Verdana", Font.BOLD, 18));
		rpLabel.dropShadow = true;
		panel.addComponent(rpLabel);
		
		UILabel playerClassLabel = new UILabel(new Vector2i(150, 200), this.getPlayerClass());
		playerClassLabel.setColor(0xBBBBBB);
		playerClassLabel.setFont(new Font("Verdana", Font.BOLD, 14));
		playerClassLabel.dropShadow = true;
		panel.addComponent(playerClassLabel);
		
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
	
	public void updatePlayerStats() {
		uiHealthBar.setProgress(health / 100.0);
		uiResourceBar.setProgress(resource / 100.0);
	}
	
	public void render(Screen screen) {
		sprite = playerSprite.getSprite();
		screen.renderMob(x - 16, y - 16, sprite);
	}
}