package com.leafclient.screen.ui;

import org.lwjgl.opengl.GL11;

import com.leafclient.font.CustomFont;
import com.leafclient.screen.ScaleFixer;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.util.ResourceLocation;

public class ToggleButton extends UIBase {
	
	protected boolean select;
	
	private String name, toggle;
	private int x, y, w, h;
	private boolean isCover;

	public ToggleButton(String name, int x, int y, int width, int height, boolean select) {
		this.name = name;
		this.x = ScaleFixer.disW(x);
		this.y = ScaleFixer.disH(y);
		this.w = ScaleFixer.disW(width);
		this.h = ScaleFixer.disH(height);
		this.select = select;
		this.toggle = String.valueOf(select);
		this.isCover = false;
	}

	@Override
	public void render() {
		GL11.glColor4f(isCover ? 0 : 1, isCover ? 100 : 1, isCover ? 0 : 1, 1);
		Minecraft.getMinecraft().getTextureManager().bindTexture(new ResourceLocation("leafclient/" + toggle + ".png"));
		Gui.drawModalRectWithCustomSizedTexture(x, y, 0.0F, 0.0F, w, h, w, h);
		int color = (name.contains("Adjust")) ? -1 : 0;
		CustomFont.getRender().drawString(name, x - ScaleFixer.disW(410), y + (h / 2) - ScaleFixer.disH(10), color);
	}

	@Override
	public void onMouseMove(int mouseX, int mouseY) {
		if(mouseX >= x && mouseX <= x + w && mouseY >= y && mouseY <= y + h) {
			this.isCover = true;} else {this.isCover = false;
		}
	}

	@Override
	public void onMouseClick(int mouseX, int mouseY, int button) {
		if(mouseX >= x && mouseX <= x + w && mouseY >= y && mouseY <= y + h) {
			select = !select;
			this.toggle = String.valueOf(select);
			this.doThings();
		}
	}

	@Override
	public void doThings() {}
}