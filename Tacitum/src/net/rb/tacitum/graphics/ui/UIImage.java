package net.rb.tacitum.graphics.ui;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;

import net.rb.tacitum.util.Vector2i;

public class UIImage extends UIComponent {
	private Image image;
	
	public UIImage(Vector2i position, Vector2i size) {
		super(position, size);
		Vector2i lp = new Vector2i(position);
		lp.x += 4;
		lp.y += size.y - 5;
	}
	
	public UIImage(Vector2i position, BufferedImage image) {
		super(position, new Vector2i(image.getWidth(), image.getHeight()));
		setImage(image);
	}
	
	void init(UIPanel panel) {
		super.init(panel);
	}
	
	public void setImage(Image image) {
		this.image = image;
	}
	
	public void render(Graphics g) {
		int x = position.x + offset.x;
		int y = position.y + offset.y;
		if (image != null) {
			g.drawImage(image, x, y, null);
		} else {
			g.setColor(color);
			g.fillRect(x, y, size.x, size.y);
		}
	}
}