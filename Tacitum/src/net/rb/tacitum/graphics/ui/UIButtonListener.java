package net.rb.tacitum.graphics.ui;

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