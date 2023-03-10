package com.leafclient.screen.ui;

public abstract class UIBase {
	
	UIBase() {}
	
	public abstract void render();
	
	public abstract void onMouseMove(int mouseX, int mouseY);
	
	public abstract void onMouseClick(int mouseX, int mouseY, int button);
	
	public void doThings() {};
}
