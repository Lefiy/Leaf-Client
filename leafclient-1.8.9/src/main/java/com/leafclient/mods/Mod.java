package com.leafclient.mods;

import java.awt.Color;

public class Mod {
	
	private String name;
	private int x, y, imagex, imagey, color_v, size_v;
	private float size;
	private boolean toggle, showHUD;
	private HudManager hud;
	
	public Mod(String name, int x, int y, int red, int green, int blue, int size_v, boolean toggle, boolean showHUD) {
		this.name = name;
		this.x = x;
		this.y = y;
		this.size_v = size_v;
		this.size = (float)size_v / 127.5F;
		this.color_v = new Color(red, green, blue).getRGB();
		this.toggle = toggle;
		this.showHUD = showHUD;
		this.hud = new HudManager(this);
	}
	
	public void setX(int x) {
		this.x = x;
	}
	
	public void setY(int y) {
		this.y = y;
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public void setEnable() {
		this.toggle = !this.toggle;
	}
	
	public boolean isEnable() {
		return this.toggle;
	}
	
	public String getName() {
		return this.name;
	}
	
	public HudManager hud() {
		return hud;
	}
	
	public void setColor(int red, int green, int blue) {
		this.color_v = new Color(red, green, blue).getRGB();
	}
	
	public int getColorValue() {
		return this.color_v;
	}
	
	public void setSize(int size) {
		this.size_v = size;
		this.size = (float)size_v / 127.5F;
	}
	
	public int getSizeValue() {
		return this.size_v;
	}
	
	public float getSize() {
		return this.size;
	}
	
	public void setImageX(int imagex) {
		this.imagex = imagex;
	}
	
	public void setImageY(int imagey) {
		this.imagey = imagey;
	}
	
	public int getImageX() {
		return this.imagex;
	}
	
	public int getImageY() {
		return this.imagey;
	}
	
	public boolean isShowHUD() {
		return this.showHUD;
	}
	
	public int getWidth() {return 0;}
	
	public int getHeight() {return 0;}
	
	public void render() {}
	
	public void rendersub(int mouseX, int mouseY) {}
}
