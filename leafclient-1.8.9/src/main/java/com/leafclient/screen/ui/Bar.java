package com.leafclient.screen.ui;

import org.lwjgl.input.Mouse;

import com.leafclient.font.CustomFont;
import com.leafclient.screen.ScaleFixer;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;

public class Bar extends UIBase {
	
	protected int x_point;
	private int x, y, w, h, ex;
	private String name;
	private boolean isCover, isClick;
	private float value;
	
	public Bar(String name, int x, int y, int width, int height, int x_point, int extend, int max) {
		this.name = name;
		this.value = 255 / max;
		this.x = ScaleFixer.disW(x + extend);
		this.y = ScaleFixer.disH(y);
		this.w = ScaleFixer.disW(width);
		this.h = ScaleFixer.disH(height);
		this.x_point = ScaleFixer.disW(x_point);
		this.ex = extend * 2;
		this.isCover = false;
	}
	
	public void render() {
		Minecraft.getMinecraft().getTextureManager().bindTexture(new ResourceLocation("leafclient/bar_main.png"));
		Gui.drawModalRectWithCustomSizedTexture(x, y, 0.0F, 0.0F, w, h, w, h);
		GlStateManager.color(isCover ? 0 : 1, isCover ? 100 : 1,isCover ? 0 : 1, 1);
		Minecraft.getMinecraft().getTextureManager().bindTexture(new ResourceLocation("leafclient/bar_point.png"));
		Gui.drawModalRectWithCustomSizedTexture((x + x_point) - (h / 2), y, 0.0F, 0.0F, h, h, h, h);
		CustomFont.getRender().drawString(name, x - ScaleFixer.disW(250 + ex), y + (h / 2) - ScaleFixer.disH(10), 0);
		float value_f = (float)ScaleFixer.disWB(x_point) / value; String value_s = String.valueOf(Math.round(value_f));
		int pos = (int)CustomFont.getRender().getStringWidth(value_s) / 2;
		CustomFont.getRender().drawString(value_s, (x + x_point) - pos, y - ScaleFixer.disH(8), 0);
	}
	
	public void onMouseMove(int mouseX, int mouseY) {
		if(mouseX >= x && mouseX <= x + w && mouseY >= y && mouseY <= y + h) {
			isCover = true;
		} else {
			isCover = false;
		}
		if(isCover && isClick) {
			if(Mouse.isButtonDown(0)) {
				x_point = mouseX - x;
			} else {
				isClick = false;
				this.doThings();
			}
		}
	}
	
	public void onMouseClick(int mouseX, int mouseY, int button) {
		if(mouseX >= x && mouseX <= x + w && mouseY >= y && mouseY <= y + h) {
			isClick = true;
		}
	}
	
	@Override
	public void doThings() {}
}