package com.leafclient.screen.ui;

import com.leafclient.font.CustomFont;
import com.leafclient.screen.ScaleFixer;

import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;

public class CosmeticButton extends UIBase {
	
	private int x, y, w, h;
	private String name;
	private boolean select, isCover;

	public CosmeticButton(String name, int x, int y, int width, int height, boolean select) {
		if(name.contains("/")) {
			this.name = name.substring(name.indexOf("/") + 1, name.indexOf("."));
		} else {
			this.name = name;}
		this.x = ScaleFixer.disW(x);
		this.y = ScaleFixer.disH(y);
		this.w = ScaleFixer.disW(width);
		this.h = ScaleFixer.disH(height);
		this.select = select;
		this.isCover = false;
	}

	@Override
	public void render() {
		GlStateManager.color(isCover ? 0 : select ? 1 : 100, isCover ? 100 : select ? 1 : 0, isCover ? 0 : select ? 1 : 0, 1);
		Minecraft.getMinecraft().getTextureManager().bindTexture(new ResourceLocation("leafclient/select.png"));
		Gui.drawModalRectWithCustomSizedTexture(x, y, 0.0F, 0.0F, w, h, w, h);
		CustomFont.getRender().drawCenteredString(name, x + (w / 2), y + (h / 2) - ScaleFixer.disH(10), 0);
	}

	@Override
	public void onMouseMove(int mouseX, int mouseY) {
		if(mouseX >= x && mouseX <= x + w && mouseY >= y && mouseY <= y + h) {
			this.isCover = true;
		} else {
			this.isCover = false;
		}
	}

	@Override
	public void onMouseClick(int mouseX, int mouseY, int button) {
		if(mouseX >= x && mouseX <= x + w && mouseY >= y && mouseY <= y + h) {
			Minecraft.getMinecraft().getSoundHandler().playSound(PositionedSoundRecord.create(new ResourceLocation("gui.button.press"), 1.0F));
			this.doThings();
		}
	}
	
	@Override
	public void doThings() {}
}
