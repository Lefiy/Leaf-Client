package com.leafclient.screen.ui;

import java.util.List;

import com.leafclient.screen.ScaleFixer;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;

public class ScrollBar extends UIBase {
	
	private int x, y, w, h, bar_point;
	private int now, size, amount, nowy;

	public ScrollBar(List<?> list, int x, int y, int width, int height, int amount) {
		this.now = 0;
		this.nowy = 0;
		this.size = list.size();
		this.x = ScaleFixer.disW(x);
		this.y = ScaleFixer.disH(y);
		this.w = ScaleFixer.disW(width);
		this.h = ScaleFixer.disH(height);
		this.amount = amount;
		double value = (double)this.size / (double)this.amount;
		int h = (int) Math.ceil(value);
		this.bar_point = this.h / h;
	}

	@Override
	public void render() {
		int bar = y + (bar_point * nowy);
		GlStateManager.color(1, 1, 1, 1);
		Minecraft.getMinecraft().getTextureManager().bindTexture(new ResourceLocation("leafclient/scroll_main.png"));
		Gui.drawModalRectWithCustomSizedTexture(x, y, 0.0F, 0.0F, w, h, w, h);
		Minecraft.getMinecraft().getTextureManager().bindTexture(new ResourceLocation("leafclient/scroll_bar.png"));
		Gui.drawModalRectWithCustomSizedTexture(x, bar, 0.0F, 0.0F, w, bar_point, w, bar_point);
	}

	@Override
	public void onMouseMove(int mouseX, int mouseY) {}

	@Override
	public void onMouseClick(int mouseX, int mouseY, int button) {}
	
	public void onScroll() {
		if((now + amount) < size) {
			now += amount;
			nowy++;
		}
	}
	
	public void onUnScroll() {
		if((now - amount) >= 0) {
			now -= amount;
			nowy--;
		}
	}
	
	public boolean isScrollAble(int i) {
		return i < size && i < (amount * (nowy + 1));
	}
	
	public int getIndex() {
		return this.now;
	}
}
