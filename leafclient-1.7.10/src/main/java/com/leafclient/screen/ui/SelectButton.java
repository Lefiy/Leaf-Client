package com.leafclient.screen.ui;

import java.util.List;

import org.lwjgl.opengl.GL11;

import com.leafclient.font.CustomFont;
import com.leafclient.screen.ScaleFixer;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.util.ResourceLocation;

public class SelectButton extends UIBase {
	
	protected List<String> list;
	protected int index;
	
	private String name;
	private int x, y, w, h;
	private boolean isCover;

	public SelectButton(String name, int x, int y, int width, int height, List<String> list, String select) {
		this.name = name;
		this.x = ScaleFixer.disW(x);
		this.y = ScaleFixer.disH(y);
		this.w = ScaleFixer.disW(width);
		this.h = ScaleFixer.disH(height);
		this.list = list;
		this.index = list.indexOf(select);
		this.isCover = false;
	}

	@Override
	public void render() {
		GL11.glColor4f(isCover ? 0 : 1, isCover ? 100 : 1, isCover ? 0 : 1, 1);
		Minecraft.getMinecraft().getTextureManager().bindTexture(new ResourceLocation("leafclient/select.png"));
		Gui.drawModalRectWithCustomSizedTexture(x, y, 0.0F, 0.0F, w, h, w, h);
		CustomFont.getRender().drawCenteredString("< " + list.get(index) + " >", x + (w / 2), y + (h / 2) - ScaleFixer.disH(10), 0);
		CustomFont.getRender().drawString(name, x - ScaleFixer.disW(210), y + (h / 2) - ScaleFixer.disH(10), 1);
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
			if((index + 1) < list.size()) {
				index++;
			} else {
				index = 0;
			}
			this.doThings();
		}
	}

	@Override
	public void doThings() {}
}