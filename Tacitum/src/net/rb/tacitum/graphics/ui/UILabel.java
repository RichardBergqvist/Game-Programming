package net.rb.tacitum.graphics.ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import net.rb.tacitum.util.Vector2i;

/**
 * @author Richard Berqvist
 * @since In-Development 10.5
 * @category Graphics
 * **/
public class UILabel extends UIComponent {
	public String text;
	private Font font;
	public boolean dropShadow = false;
	public int dropShadowOffset = 2;
	
	public UILabel(Vector2i position, String text) {
		super(position);
		this.font = new Font("Verdana", Font.PLAIN, 20);
		this.text = text;
		this.color = new Color(0xFF00FF);
	}
	
	public UILabel setFont(Font font) {
		this.font = font;
		return this;
	}
	
	public void render(Graphics g) {
		if (dropShadow) {			
			g.setFont(font);
			g.setColor(Color.BLACK);
			g.drawString(text, position.x + offset.x + dropShadowOffset, position.y + offset.y + dropShadowOffset);
		}
		
		g.setColor(color);
		g.setFont(font);
		g.drawString(text, position.x + offset.x, position.y + offset.y);
	}
}