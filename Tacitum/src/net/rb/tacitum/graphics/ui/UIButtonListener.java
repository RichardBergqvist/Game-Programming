package net.rb.tacitum.graphics.ui;

/**
 * @author Richard Berqvist
 * @since In-Development 10.5
 * @category Graphics
 * **/
public class UIButtonListener {
	public void entered(UIButton button) {
		button.setColor(0xFF0000);
	}
	
	public void exited(UIButton button) {
		button.setColor(0xAAAAAA);
	}
	
	public void pressed(UIButton button) {
		button.setColor(0x7F0000);
	}
	
	public void released(UIButton button) {
		button.setColor(0xFF0000);
	}
}